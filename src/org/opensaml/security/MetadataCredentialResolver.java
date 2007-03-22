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
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialCriteria;
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
public class MetadataCredentialResolver extends AbstractCredentialResolver<SAMLMDCredentialContext> 
    implements CredentialResolver<SAMLMDCredentialContext> {

    /** The default credential context class to use in resolved credentials.  */
    public static final Class<SAMLMDCredentialContext> DEFAULT_CONTEXT_CLASS = SAMLMDCredentialContext.class;

    /** Class logger. */
    private static Logger log = Logger.getLogger(MetadataCredentialResolver.class);
    
    /** Role from which to fetch the credential information. */
    private QName role;

    /** Suported protocol of the role from which to fetch the credential information. */
    private String protocol;

    /** Metadata provider from which to fetch the credentials. */
    private MetadataProvider metadata;

    /** Cache of resolved credentials. [entityID, [UsageType, Credential]] */
    private Map<String, Map<UsageType, SoftReference<Credential>>> cache;
    
    /** Credential resolver used to resolve credentials from role descriptor KeyInfo elements. */
    private KeyInfoCredentialResolver keyInfoCredentialResolver;

    /**
     * Constructor.
     * 
     * @param entityRole role of the entities credentials will be retrieved for
     * @param supportedProtocol protocol supported by the entity
     * @param metadataProvider provider of the metadata
     * 
     * @throws IllegalArgumentException thrown if the given role, supported protocol, or provider is null
     */
    public MetadataCredentialResolver(QName entityRole, String supportedProtocol, MetadataProvider metadataProvider)
            throws IllegalArgumentException {
        if (entityRole == null || DatatypeHelper.isEmpty(supportedProtocol) || metadataProvider == null) {
            throw new IllegalArgumentException("Role, supported protocol, or  metadata provider may not be null");
        }
        role = entityRole;
        protocol = DatatypeHelper.safeTrim(supportedProtocol);
        metadata = metadataProvider;

        if (metadata instanceof ObservableMetadataProvider) {
            ObservableMetadataProvider observable = (ObservableMetadataProvider) metadataProvider;
            observable.getObservers().add(new MetadataProviderObserver());
        }
        
        setContextClass(DEFAULT_CONTEXT_CLASS);
        
        keyInfoCredentialResolver = new KeyInfoCredentialResolver();
        keyInfoCredentialResolver.setContextClass(DEFAULT_CONTEXT_CLASS);
    }
    
    /**
     * Get the KeyInfo credential resolver used by this metadata resolver to handle KeyInfo elements.
     * 
     * @return KeyInfo credential resolver
     */
    public KeyInfoCredentialResolver getKeyInfoCredentialResolver() {
        return keyInfoCredentialResolver;
    }
    
    /**
     * Set the KeyInfo credential resolver used by this metadata resolver to handle KeyInfo elements.
     * 
     * @param keyInfoResolver the new KeyInfoCredentialResolver to use
     */
    public void setKeyInfoCredentialResolver(KeyInfoCredentialResolver keyInfoResolver) {
        keyInfoCredentialResolver = keyInfoResolver;
        keyInfoResolver.setContextClass(this.getContextClass());
    }

    /** {@inheritDoc} */
    public Iterable<Credential> resolveCredentials(CredentialCriteria criteria) throws SecurityException {
        Collection<Credential> credentials;
        
        if (criteria.getEntityID() == null) {
            throw new SecurityException("No entity was specified as credential criteria");
        }
        
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
        
        credentials = retrieveFromMetadata(criteria);

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
     * Retrieves a credential from the provided metadata.
     * 
     * @param criteria criteria by which to resolve credentials
     * 
     * @return the credential or null
     * 
     * @throws SecurityException thrown if the key, certificate, or CRL information is represented in an unsupported
     *             format
     */
    protected Collection<Credential> retrieveFromMetadata(CredentialCriteria criteria) throws SecurityException {
        String entity = criteria.getEntityID();
        UsageType usage = criteria.getUsage();
        
        
        if (log.isDebugEnabled()) {
            log.debug("Attempting to retreive credential for entity " + entity + "from metadata");
        }
        try {
            if (log.isDebugEnabled()) {
                log.debug("Retrieving credential for entity " + entity + " in role " + role);
            }
            RoleDescriptor roleDescriptor = metadata.getRole(entity, role, protocol);
            if (roleDescriptor == null) {
                return null;
            }

            List<KeyDescriptor> keyDescriptors = roleDescriptor.getKeyDescriptors();
            if (keyDescriptors == null) {
                return null;
            }
            
            Collection<Credential> credentials = new HashSet<Credential>();

            for (KeyDescriptor keyDescriptor : keyDescriptors) {
                if (keyDescriptor.getUse() == usage) {
                    if (keyDescriptor.getKeyInfo() != null) {
                        
                        // TODO need to add other things from the original criteria
                        // to KeyInfo criteria - does that make sense?
                        KeyInfoCredentialCriteria keyInfoCriteria = 
                            new KeyInfoCredentialCriteria(keyDescriptor.getKeyInfo());
                        keyInfoCriteria.setEntityID(criteria.getEntityID());
                        keyInfoCriteria.setUsage(criteria.getUsage());
                        keyInfoCriteria.setKeyAlgorithm(criteria.getKeyAlgorithm());
                        
                        for (Credential cred : keyInfoCredentialResolver.resolveCredentials(keyInfoCriteria)) {
                            credentials.add(cred);
                        }
                    }
                }
            }

            return credentials;
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