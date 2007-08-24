/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.metadata.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;

/**
 * A metadata provider that uses registered providers, in turn, to answer queries.
 */
public class ChainingMetadataProvider extends BaseMetadataProvider {

    /** Registred providers. */
    private ArrayList<MetadataProvider> providers;

    /** Lock used to block reads during write and vice versa. */
    private ReadWriteLock providerLock;

    /** Constructor. */
    public ChainingMetadataProvider() {
        super();
        providers = new ArrayList<MetadataProvider>();
        providerLock = new ReentrantReadWriteLock(true);
    }

    /**
     * Gets an immutable the list of currently registered providers.
     * 
     * @return list of currently registered providers
     */
    public List<MetadataProvider> getProviders() {
        return Collections.unmodifiableList(providers);
    }

    /**
     * Replaces the current set of metadata providers with give collection.
     * 
     * @param newProviders the metadata providers to replace the current providers with
     * 
     * @throws MetadataProviderException thrown if there is a problem adding the metadata provider
     */
    public void setProviders(List<MetadataProvider> newProviders) throws MetadataProviderException {
        providers.clear();
        for (MetadataProvider provider : newProviders) {
            addMetadataProvider(provider);
        }
    }

    /**
     * Adds a metadata provider to the list of registered providers.
     * 
     * @param newProvider the provider to be added
     * 
     * @throws MetadataProviderException thrown if there is a problem adding the metadata provider
     */
    public void addMetadataProvider(MetadataProvider newProvider) throws MetadataProviderException {
        if (newProvider != null) {
            newProvider.setRequireValidMetadata(requireValidMetadata());
            newProvider.setMetadataFilter(getMetadataFilter());
            providers.add(newProvider);
        }
    }

    /**
     * Removes a metadata provider from the list of registered providers.
     * 
     * @param provider provider to be removed
     */
    public void removeMetadataProvider(MetadataProvider provider) {
        providers.remove(provider);
    }

    /** {@inheritDoc} */
    public void setRequireValidMetadata(boolean requireValidMetadata) {
        super.setRequireValidMetadata(requireValidMetadata);

        Lock writeLock = providerLock.writeLock();
        writeLock.lock();
        for (MetadataProvider provider : providers) {
            provider.setRequireValidMetadata(requireValidMetadata);
        }
        writeLock.unlock();
    }

    /** {@inheritDoc} */
    public void setMetadataFilter(MetadataFilter newFilter) throws MetadataProviderException {
        super.setMetadataFilter(newFilter);

        Lock writeLock = providerLock.writeLock();
        writeLock.lock();
        try {
            for (MetadataProvider provider : providers) {
                provider.setMetadataFilter(newFilter);
            }
        } catch (MetadataProviderException e) {
            throw e;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Gets the metadata from every registered provider and places each within a newly created EntitiesDescriptor.
     * 
     * {@inheritDoc}
     */
    public XMLObject getMetadata() throws MetadataProviderException {
        XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
        SAMLObjectBuilder<EntitiesDescriptor> builder = (SAMLObjectBuilder<EntitiesDescriptor>) builderFactory
                .getBuilder(EntitiesDescriptor.DEFAULT_ELEMENT_NAME);
        EntitiesDescriptor metadataRoot = builder.buildObject();

        Lock readLock = providerLock.readLock();
        readLock.lock();

        XMLObject providerMetadata;
        try {
            for (MetadataProvider provider : providers) {
                providerMetadata = provider.getMetadata();
                if (providerMetadata instanceof EntitiesDescriptor) {
                    metadataRoot.getEntitiesDescriptors().add((EntitiesDescriptor) providerMetadata);
                } else if (providerMetadata instanceof EntityDescriptor) {
                    metadataRoot.getEntityDescriptors().add((EntityDescriptor) providerMetadata);
                }
            }
        } catch (MetadataProviderException e) {
            throw e;
        } finally {
            readLock.unlock();
        }

        return metadataRoot;
    }

    /** {@inheritDoc} */
    public EntitiesDescriptor getEntitiesDescriptor(String name) throws MetadataProviderException {
        Lock readLock = providerLock.readLock();
        readLock.lock();

        EntitiesDescriptor descriptor = null;
        try {
            for (MetadataProvider provider : providers) {
                descriptor = provider.getEntitiesDescriptor(name);
                if (descriptor != null) {
                    break;
                }
            }
        } catch (MetadataProviderException e) {
            throw e;
        } finally {
            readLock.unlock();
        }

        return descriptor;
    }

    /** {@inheritDoc} */
    public EntityDescriptor getEntityDescriptor(String entityID) throws MetadataProviderException {
        Lock readLock = providerLock.readLock();
        readLock.lock();

        EntityDescriptor descriptor = null;
        try {
            for (MetadataProvider provider : providers) {
                descriptor = provider.getEntityDescriptor(entityID);
                if (descriptor != null) {
                    break;
                }
            }
        } catch (MetadataProviderException e) {
            throw e;
        } finally {
            readLock.unlock();
        }

        return descriptor;
    }

    /** {@inheritDoc} */
    public List<RoleDescriptor> getRole(String entityID, QName roleName) throws MetadataProviderException {
        Lock readLock = providerLock.readLock();
        readLock.lock();

        List<RoleDescriptor> roles = null;
        try {
            for (MetadataProvider provider : providers) {
                roles = provider.getRole(entityID, roleName);
                if (roles != null && roles.size() > 0) {
                    break;
                }
            }
        } catch (MetadataProviderException e) {
            throw e;
        } finally {
            readLock.unlock();
        }

        return roles;
    }

    /** {@inheritDoc} */
    public RoleDescriptor getRole(String entityID, QName roleName, String supportedProtocol)
            throws MetadataProviderException {
        Lock readLock = providerLock.readLock();
        readLock.lock();

        RoleDescriptor role = null;
        try {
            for (MetadataProvider provider : providers) {
                role = provider.getRole(entityID, roleName, supportedProtocol);
                if (role != null) {
                    break;
                }
            }
        } catch (MetadataProviderException e) {
            throw e;
        } finally {
            readLock.unlock();
        }

        return role;
    }
}