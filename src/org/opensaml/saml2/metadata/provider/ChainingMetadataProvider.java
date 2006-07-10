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

import java.util.List;

import javax.xml.namespace.QName;

import javolution.util.FastList;

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

    /** Registred providers */
    private FastList<MetadataProvider> providers;

    /**
     * Constructor
     */
    public ChainingMetadataProvider() {
        super();
        providers = new FastList<MetadataProvider>();
    }

    /**
     * Gets an immutable the list of currently registered providers.
     * 
     * @return list of currently registered providers
     */
    public List<MetadataProvider> getProviders() {
        return providers.unmodifiable();
    }

    /**
     * Adds a metadata provider to the list of registered providers.
     * 
     * @param newProvider the provider to be added
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

        MetadataProvider provider;
        FastList.Node<MetadataProvider> head = providers.head();
        for (FastList.Node<MetadataProvider> current = head.getNext(); current != providers.tail(); current = current
                .getNext()) {
            provider = current.getValue();
            provider.setRequireValidMetadata(requireValidMetadata);
        }

    }

    /** {@inheritDoc} */
    public void setMetadataFilter(MetadataFilter newFilter) throws MetadataProviderException {
        super.setMetadataFilter(newFilter);

        MetadataProvider provider;
        FastList.Node<MetadataProvider> head = providers.head();
        for (FastList.Node<MetadataProvider> current = head.getNext(); current != providers.tail(); current = current
                .getNext()) {
            provider = current.getValue();
            provider.setMetadataFilter(newFilter);
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

        MetadataProvider provider;
        XMLObject providerMetadata;
        FastList.Node<MetadataProvider> head = providers.head();
        for (FastList.Node<MetadataProvider> current = head.getNext(); current != providers.tail(); current = current
                .getNext()) {
            provider = current.getValue();
            providerMetadata = provider.getMetadata();
            if (providerMetadata instanceof EntitiesDescriptor) {
                metadataRoot.getEntitiesDescriptors().add((EntitiesDescriptor) providerMetadata);
            } else if (providerMetadata instanceof EntityDescriptor) {
                metadataRoot.getEntityDescriptors().add((EntityDescriptor) providerMetadata);
            }
        }

        return metadataRoot;
    }
    
    /** {@inheritDoc} */
    public EntitiesDescriptor getEntitiesDescriptor(String name) throws MetadataProviderException {
        MetadataProvider provider;
        EntitiesDescriptor descriptor;
        FastList.Node<MetadataProvider> head = providers.head();
        for (FastList.Node<MetadataProvider> current = head.getNext(); current != providers.tail(); current = current
                .getNext()) {
            provider = current.getValue();
            descriptor = provider.getEntitiesDescriptor(name);
            if (descriptor != null) {
                return descriptor;
            }
        }

        return null;
    }

    /** {@inheritDoc} */
    public EntityDescriptor getEntityDescriptor(String entityID) throws MetadataProviderException {
        MetadataProvider provider;
        EntityDescriptor descriptor;
        FastList.Node<MetadataProvider> head = providers.head();
        for (FastList.Node<MetadataProvider> current = head.getNext(); current != providers.tail(); current = current
                .getNext()) {
            provider = current.getValue();
            descriptor = provider.getEntityDescriptor(entityID);
            if (descriptor != null) {
                return descriptor;
            }
        }

        return null;
    }

    /** {@inheritDoc} */
    public List<RoleDescriptor> getRole(String entityID, QName roleName) throws MetadataProviderException {
        MetadataProvider provider;
        List<RoleDescriptor> roles;
        FastList.Node<MetadataProvider> head = providers.head();
        for (FastList.Node<MetadataProvider> current = head.getNext(); current != providers.tail(); current = current
                .getNext()) {
            provider = current.getValue();
            roles = provider.getRole(entityID, roleName);
            if (roles != null && roles.size() > 0) {
                return roles;
            }
        }

        return null;
    }

    /** {@inheritDoc} */
    public List<RoleDescriptor> getRole(String entityID, QName roleName, String supportedProtocol)
            throws MetadataProviderException {
        MetadataProvider provider;
        List<RoleDescriptor> roles;
        FastList.Node<MetadataProvider> head = providers.head();
        for (FastList.Node<MetadataProvider> current = head.getNext(); current != providers.tail(); current = current
                .getNext()) {
            provider = current.getValue();
            roles = provider.getRole(entityID, roleName, supportedProtocol);
            if (roles != null && roles.size() > 0) {
                return roles;
            }
        }

        return null;
    }
}