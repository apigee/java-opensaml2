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
import org.opensaml.xml.security.credential.EntityCredentialCriteria;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialCriteriaSet;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialCriteria;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A credential resolver capable of pulling information from SAML 2 metadata for a particular role.
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

    /** Cache of resolved credentials. [entityID, [UsageType, Credential]] */
    private Map<String, Map<UsageType, SoftReference<Credential>>> cache;
    
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
    public Iterable<Credential> resolveCredentials(CredentialCriteriaSet criteriaSet) throws SecurityException {
        EntityCredentialCriteria entityCriteria = criteriaSet.get(EntityCredentialCriteria.class);
        MetadataCredentialCriteria mdCriteria = criteriaSet.get(MetadataCredentialCriteria.class);
        
        if (entityCriteria == null || mdCriteria == null) {
            throw new IllegalArgumentException("Both basic criteria and SAML metadata criteria must be supplied");
        }
        if (DatatypeHelper.isEmpty(entityCriteria.getOwnerID()) || mdCriteria.getRole() == null) {
            throw new IllegalArgumentException("Credential owner entity ID and metadata role must be supplied");
        }
        
        Collection<Credential> credentials;
        
        // TODO need to figure out whether and how to cache credentials based on CredentialCriteria
        // or else apply the criteria against the credentials already cached.
        // Optionally - hang cached keys, certs, etc right on the KeyInfo itself (using SoftReferences)
        // per Chad's suggestion a while back. 
        
        //credential = retrieveFromCache(entity, usage);
        
        /*
        if (credential == null) {
            credential = retrieveFromMetadata(entity, usage);
            cacheCredential(credential);
        }*/
        
        credentials = retrieveFromMetadata(entityCriteria, mdCriteria);

        return credentials;
    }

    /**
     * Retrieves a pre-resolved credential from the cache.
     * 
     * @param entity id of the credential bearing entity
     * @param usage usage type of the credential
     * 
     * @return the credential or null
     */
    protected Credential retrieveFromCache(String entity, UsageType usage) {
        if (log.isDebugEnabled()) {
            log.debug("Attempting to retreive credential for entity " + entity + "from cache");
        }
        if (cache.containsKey(entity)) {
            Map<UsageType, SoftReference<Credential>> entityCache = cache.get(entity);
            if (entityCache != null && entityCache.containsKey(usage)) {
                if (log.isDebugEnabled()) {
                    log.debug("Retreived credential for entity " + entity + "from cache");
                }
                return entityCache.get(usage).get();
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Unale to retreive credential for entity " + entity + "from cache");
        }
        return null;
    }

    /**
     * Retrieves credentials from the provided metadata.
     * 
     * @param entityCriteria basic credential criteria
     * @param metadataCriteria SAML metadata credential criteria
     * 
     * @return the resolved credentials or null
     * 
     * @throws SecurityException thrown if the key, certificate, or CRL information is represented in an unsupported
     *             format
     */
    protected Collection<Credential> retrieveFromMetadata(EntityCredentialCriteria entityCriteria, 
            MetadataCredentialCriteria metadataCriteria) throws SecurityException {
        String entityID = entityCriteria.getOwnerID();
        QName role = metadataCriteria.getRole();
        String protocol = metadataCriteria.getProtocol();
        UsageType usage = entityCriteria.getUsage();
        if (usage ==  null) {
            usage = UsageType.UNSPECIFIED;
        }
        
        if (log.isDebugEnabled()) {
            log.debug("Attempting to retreive credentials for entity " + entityID + "from metadata");
        }
        Collection<Credential> credentials = new HashSet<Credential>();
        
        for (RoleDescriptor roleDescriptor : getRoleDescriptors(entityID, role, protocol)) {
            List<KeyDescriptor> keyDescriptors = roleDescriptor.getKeyDescriptors();
            for (KeyDescriptor keyDescriptor : keyDescriptors) {
                if (keyDescriptor.getUse() == usage || usage == UsageType.UNSPECIFIED) {
                    if (keyDescriptor.getKeyInfo() != null) {
                        CredentialCriteriaSet critSet = new CredentialCriteriaSet();
                        critSet.add( new KeyInfoCredentialCriteria(keyDescriptor.getKeyInfo()) );
                        
                        for (Credential cred : getKeyInfoCredentialResolver().resolveCredentials(critSet)) {
                            if (cred instanceof BasicCredential) {
                                BasicCredential basicCred = (BasicCredential) cred;
                                basicCred.setEntityId(entityID);
                                basicCred.setUsageType(usage);
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
                log.debug("Retrieving credential for entity " + entityID + " in role " + role 
                        + " for protocol " + protocol);
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
     * @param credential credential to cache
     */
    protected void cacheCredential(Credential credential) {
        Map<UsageType, SoftReference<Credential>> entityCache = cache.get(credential.getEntityId());

        if (entityCache == null) {
            entityCache = new HashMap<UsageType, SoftReference<Credential>>();
            cache.put(credential.getEntityId(), entityCache);
        }

        entityCache.put(credential.getUsageType(), new SoftReference<Credential>(credential));
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