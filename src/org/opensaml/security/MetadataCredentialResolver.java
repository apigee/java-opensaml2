/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.security;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.saml2.metadata.provider.ObservableMetadataProvider;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.AbstractCredentialResolver;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialCriteriaSet;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.EntityCriteria;
import org.opensaml.xml.security.credential.UsageCriteria;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.keyinfo.KeyInfoCriteria;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A credential resolver capable of resolving credentials from SAML 2 metadata;
 * 
 * The instance of {@link CredentialCriteriaSet} passed to {@link #resolve(CredentialCriteriaSet)} and
 * {@link #resolveSingle(CredentialCriteriaSet)} must minimally contain 2 criteria: {@link EntityCriteria}
 * and {@link MetadataCriteria}.  The values for {@link EntityCriteria#getEntityID()} and
 * {@link MetadataCriteria#getRole()} are mandatory. If the protocol value obtained via 
 * {@link MetadataCriteria#getProtocol()} is not supplied, credentials will be resolved from all 
 * matching roles, regardless of protocol support.  Specification of a {@link UsageCriteria} is optional.
 * If usage criteria is absent from the criteria set, the effective value {@link UsageType#UNSPECIFIED} will be used
 * for credential resolution.
 * 
 * This credential resolver will cache the resolved the credentials in a memory-sensitive cache. If the metadata
 * provider is an {@link ObservableMetadataProvider} this resolver will also clear its cache when the underlying
 * metadata changes.
 */
public class MetadataCredentialResolver extends AbstractCredentialResolver implements CredentialResolver {

    /** Class logger. */
    private static Logger log = Logger.getLogger(MetadataCredentialResolver.class);
    
    /** Metadata provider from which to fetch the credentials. */
    private MetadataProvider metadata;

    /** Cache of resolved credentials. [MetadataCacheKey, Credentials] */
    private Map<MetadataCacheKey, SoftReference<Collection<Credential>>> cache;
    
    /** Credential resolver used to resolve credentials from role descriptor KeyInfo elements. */
    private KeyInfoCredentialResolver keyInfoCredentialResolver;

    /**
     * Constructor.
     * 
     * @param metadataProvider provider of the metadata
     * 
     * @throws IllegalArgumentException thrown if the supplied provider is null
     */
    public MetadataCredentialResolver(MetadataProvider metadataProvider) {
        if (metadataProvider == null) {
            throw new IllegalArgumentException("Metadata provider may not be null");
        }
        metadata = metadataProvider;
        
        cache = new HashMap<MetadataCacheKey, SoftReference<Collection<Credential>>>();

        if (metadata instanceof ObservableMetadataProvider) {
            ObservableMetadataProvider observable = (ObservableMetadataProvider) metadataProvider;
            observable.getObservers().add(new MetadataProviderObserver());
        }
        
    }
    
    /**
     * Get the KeyInfo credential resolver used by this metadata resolver to handle KeyInfo elements.
     * 
     * @return KeyInfo credential resolver
     */
    public KeyInfoCredentialResolver getKeyInfoCredentialResolver() {
        if (keyInfoCredentialResolver == null) {
            //TODO temporary - If we default something here, need to chose a default set of providers, etc.
            //Pending finishing the KeyInfo resolver config machinery.
            keyInfoCredentialResolver = new KeyInfoCredentialResolver();
        }
        return keyInfoCredentialResolver;
    }
    
    /**
     * Set the KeyInfo credential resolver used by this metadata resolver to handle KeyInfo elements.
     * 
     * @param keyInfoResolver the new KeyInfoCredentialResolver to use
     */
    public void setKeyInfoCredentialResolver(KeyInfoCredentialResolver keyInfoResolver) {
        keyInfoCredentialResolver = keyInfoResolver;
    }

    /** {@inheritDoc} */
    public Iterable<Credential> resolve(CredentialCriteriaSet criteriaSet) throws SecurityException {
        
        checkCriteriaRequirements(criteriaSet);
        
        String entityID = criteriaSet.get(EntityCriteria.class).getEntityID();
        MetadataCriteria mdCriteria = criteriaSet.get(MetadataCriteria.class);
        QName role = mdCriteria.getRole();
        String protocol = mdCriteria.getProtocol();
        UsageCriteria usageCriteria = criteriaSet.get(UsageCriteria.class);
        UsageType usage = null;
        if (usageCriteria != null) {
            usage = usageCriteria.getUsage();
        } else {
            usage = UsageType.UNSPECIFIED;
        }
        
        MetadataCacheKey cacheKey = new MetadataCacheKey(entityID, role, protocol, usage);
        Collection<Credential> credentials  = retrieveFromCache(cacheKey);
        
        if (credentials == null) {
            credentials = retrieveFromMetadata(entityID, role, protocol, usage);
            cacheCredential(cacheKey, credentials);
        }

        return credentials;
    }
    
    /**
     * Check that all necessary credential criteria are available.
     * 
     * @param criteriaSet the credential set to evaluate
     */
    protected void checkCriteriaRequirements(CredentialCriteriaSet criteriaSet) {
        EntityCriteria entityCriteria = criteriaSet.get(EntityCriteria.class);
        MetadataCriteria mdCriteria = criteriaSet.get(MetadataCriteria.class);
        if (entityCriteria == null) {
            throw new IllegalArgumentException("Entity criteria must be supplied");
        }
        if (mdCriteria == null) {
            throw new IllegalArgumentException("SAML metadata criteria must be supplied");
        }
        if (DatatypeHelper.isEmpty(entityCriteria.getEntityID())) {
            throw new IllegalArgumentException("Credential owner entity ID criteria value must be supplied");
        }
        if (mdCriteria.getRole() == null) {
            throw new IllegalArgumentException("Credential metadata role criteria value must be supplied");
        }
    }

    /**
     * Retrieves pre-resolved credentials from the cache.
     * 
     * @param cacheKey the key to the metadata cache
     * 
     * @return the collection of cached credentials or null
     */
    protected Collection<Credential> retrieveFromCache(MetadataCacheKey cacheKey) {
        if (log.isDebugEnabled()) {
            log.debug("Attempting to retrieve credentials from cache using index: " + cacheKey);
        }
        if (cache.containsKey(cacheKey)) {
            SoftReference<Collection<Credential>> reference = cache.get(cacheKey);
            if (reference.get() != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Retrieved credentials from cache using index: " + cacheKey);
                }
                return reference.get();
            }
            
        }

        if (log.isDebugEnabled()) {
            log.debug("Unable to retrieve credentials from cache using index: " + cacheKey);
        }
        return null;
    }

    /**
     * Retrieves credentials from the provided metadata.
     * 
     * @param entityID entityID of the credential owner
     * @param role role in which the entity is operating
     * @param protocol protocol over which the entity is operating (may be null)
     * @param usage intended usage of resolved credentials
     * 
     * @return the resolved credentials or null
     * 
     * @throws SecurityException thrown if the key, certificate, or CRL information is represented in an unsupported
     *             format
     */
    protected Collection<Credential> retrieveFromMetadata(String entityID, QName role, String protocol, 
            UsageType usage) throws SecurityException {
        
        if (log.isDebugEnabled()) {
            log.debug("Attempting to retrieve credentials from metadata for entity: " + entityID);
        }
        Collection<Credential> credentials = new HashSet<Credential>();
        
        for (RoleDescriptor roleDescriptor : getRoleDescriptors(entityID, role, protocol)) {
            List<KeyDescriptor> keyDescriptors = roleDescriptor.getKeyDescriptors();
            for (KeyDescriptor keyDescriptor : keyDescriptors) {
                UsageType mdUsage = keyDescriptor.getUse();
                if (mdUsage == null) {
                    mdUsage = UsageType.UNSPECIFIED;
                }
                if (matchUsage(mdUsage, usage)) {
                    if (keyDescriptor.getKeyInfo() != null) {
                        CredentialCriteriaSet critSet = new CredentialCriteriaSet();
                        critSet.add( new KeyInfoCriteria(keyDescriptor.getKeyInfo()) );
                        
                        for (Credential cred : getKeyInfoCredentialResolver().resolve(critSet)) {
                            if (cred instanceof BasicCredential) {
                                BasicCredential basicCred = (BasicCredential) cred;
                                basicCred.setEntityId(entityID);
                                basicCred.setUsageType(mdUsage);
                                basicCred.getCredentalContextSet().add( new SAMLMDCredentialContext(keyDescriptor) );
                            }
                            credentials.add(cred);
                        }
                    }
                }
            }
            
        }
        
        return credentials;
    }
    
    /**
     * Match usage enum type values from metadata KeyDescriptor and from credential criteria.
     * 
     * @param metadataUsage the value from the 'use' attribute of a metadata KeyDescriptor element
     * @param criteriaUsage the value from credential criteria
     * @return true if the two usage specifiers match for purposes of resolving credentials, false otherwise
     */
    protected boolean matchUsage(UsageType metadataUsage, UsageType criteriaUsage) {
        if (metadataUsage == UsageType.UNSPECIFIED || criteriaUsage == UsageType.UNSPECIFIED) {
            return true;
        }
        return metadataUsage == criteriaUsage;
    }
    
    /**
     * Get the list of metadata role descriptors which match the given entityID, role and protocol.
     * 
     * @param entityID entity ID of the credential owner
     * @param role role in which the entity is operating
     * @param protocol protocol over which the entity is operating (may be null)
     * @return a list of role descriptors matching the given parameters, or null
     * @throws SecurityException thrown if there is an error retrieving role descriptors 
     *          from the metadata provider
     */
    protected List<RoleDescriptor> getRoleDescriptors(String entityID, QName role, String protocol) 
        throws SecurityException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Retrieving metadata for entity '" + entityID + "' in role '" + role 
                        + "' for protocol '" + protocol + "'");
            }
            
            if (DatatypeHelper.isEmpty(protocol)) {
                return metadata.getRole(entityID, role);
            } else {
                RoleDescriptor roleDescriptor = metadata.getRole(entityID, role, protocol);
                if (roleDescriptor == null) {
                    return null;
                }
                List<RoleDescriptor> roles = new ArrayList<RoleDescriptor>();
                roles.add(roleDescriptor);
                return roles;
            }
        } catch (MetadataProviderException e) {
            log.error("Unable to read metadata from provider", e);
            throw new SecurityException("Unable to read metadata provider", e);
        }
    }

    /**
     * Adds a resolved credential to the cache.
     * 
     * @param cacheKey the key for caching the credentials
     * @param credentials collection of credentials to cache
     */
    protected void cacheCredential(MetadataCacheKey cacheKey, Collection<Credential> credentials) {
        cache.put(cacheKey, new SoftReference<Collection<Credential>>(credentials));
    }
    
    /**
     * A class which serves as the key into the cache of credentials previously resolved.
     */
    protected class MetadataCacheKey {
        
        /** Entity ID of credential owner. */
        private String id;
        
        /** Role in which the entity is operating. */
        private QName role;
        
        /** Protocol over which the entity is operating (may be null). */
        private String protocol;
        
        /** Intended usage of the resolved credentials. */
        private UsageType usage;
        
        /**
         * Constructor.
         * 
         * @param entityID entity ID of the credential owner
         * @param entityRole role in which the entity is operating
         * @param entityProtocol protocol over which the entity is operating (may be null)
         * @param entityUsage usage of the resolved credentials
         */
        protected MetadataCacheKey(String entityID, QName entityRole, String entityProtocol, UsageType entityUsage) {
            if (entityID == null) {
                throw new IllegalArgumentException("Entity ID may not be null");
            }
            if (entityRole == null) {
                throw new IllegalArgumentException("Entity role may not be null");
            }
            if (entityUsage == null) {
                throw new IllegalArgumentException("Credential usage may not be null");
            }
            id = entityID;
            role = entityRole;
            protocol = entityProtocol;
            usage = entityUsage;
        }

        /** {@inheritDoc} */
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MetadataCacheKey)) {
                return false;
            }
            MetadataCacheKey other = (MetadataCacheKey) obj;
            if (! this.id.equals(other.id) || ! this.role.equals(other.role) || this.usage != other.usage) {
                return false;
            }
            if (this.protocol == null) {
                if (other.protocol != null) {
                    return false;
                }
            } else {
                if (! this.protocol.equals(other.protocol)) {
                    return false;
                }
            }
            return true;
        }

        /** {@inheritDoc} */
        public int hashCode() {
            int result = 17;
            result = 37*result + id.hashCode();
            result = 37*result + role.hashCode();
            if (protocol != null) {
                result = 37*result + protocol.hashCode();
            }
            result = 37*result + usage.hashCode();
            return result;
        }

        /** {@inheritDoc} */
        public String toString() {
            return String.format("[%s,%s,%s,%s]", id, role, protocol, usage);
        }
        
    }

    /**
     * An observer that clears the credential cache if the underlying metadata changes.
     */
    protected class MetadataProviderObserver implements ObservableMetadataProvider.Observer {

        /** {@inheritDoc} */
        public void onEvent(MetadataProvider provider) {
            cache.clear();
        }
    }
}