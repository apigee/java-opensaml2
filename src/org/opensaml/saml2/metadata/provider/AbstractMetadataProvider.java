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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.saml2.common.SAML2Helper;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Document;

/**
 * An abstract, base, implementation of a metadata provider.
 */
public abstract class AbstractMetadataProvider implements MetadataProvider {

    /** Logger */
    private final Logger log = Logger.getLogger(AbstractMetadataProvider.class);

    /** Whether metadata is required to be valid */
    private boolean requireValidMetadata;

    /** Unmarshaller factory used to get an unmarshaller for the metadata DOM */
    protected UnmarshallerFactory unmarshallerFactory;

    /** Filter applied to all metadata */
    private MetadataFilter mdFilter;

    /** Cache of entity IDs to their descriptors */
    private FastMap<String, EntityDescriptor> indexedDescriptors;

    /**
     * Constructor
     */
    public AbstractMetadataProvider() {
        unmarshallerFactory = Configuration.getUnmarshallerFactory();
        indexedDescriptors = new FastMap<String, EntityDescriptor>();
    }

    /** {@inheritDoc} */
    public EntityDescriptor getEntityDescriptor(String entityID) {
        if (log.isDebugEnabled()) {
            log.debug("Getting descriptor for entity " + entityID);
        }

        XMLObject metadata = fetchMetadata();
        EntityDescriptor descriptor = getEntityDescriptorById(entityID, metadata);
        if (descriptor == null) {
            if (log.isDebugEnabled()) {
                log.debug("Metadata document does not contain an entity descriptor with the ID " + entityID);
            }
            return null;
        }

        if (log.isDebugEnabled()) {
            log.debug("Entity descriptor found with the ID " + entityID);
        }

        if (requireValidMetadata()) {
            if (log.isDebugEnabled()) {
                log.debug("Valid metadata is required, checking descriptor's validity");
            }
            if (SAML2Helper.isValid(descriptor)) {
                if (log.isDebugEnabled()) {
                    log.debug("Entity descriptor with ID " + entityID + " is valid, returning");
                }
                return descriptor;
            } else {
                if (log.isDebugEnabled()) {
                    log
                            .debug("Entity descriptor with ID " + entityID
                                    + " was not valid and valid metadata is required");
                }
                return null;
            }
        } else {
            return descriptor;
        }
    }

    /** {@inheritDoc} */
    public List<RoleDescriptor> getRole(String entityID, QName roleName) {
        EntityDescriptor entity = getEntityDescriptor(entityID);
        return entity.getRoleDescriptors(roleName);
    }

    /** {@inheritDoc} */
    public List<RoleDescriptor> getRole(String entityID, QName roleName, String supportedProtocol) {
        Iterator<RoleDescriptor> roles = getRole(entityID, roleName).iterator();
        RoleDescriptor role;
        FastList<RoleDescriptor> protocolSupportingRoles = new FastList<RoleDescriptor>();
        while (roles.hasNext()) {
            role = roles.next();
            if (role.getSupportedProtocols().contains(supportedProtocol)) {
                protocolSupportingRoles.add(role);
            }
        }

        return protocolSupportingRoles;
    }

    /** {@inheritDoc} */
    public boolean requireValidMetadata() {
        return requireValidMetadata;
    }

    /** {@inheritDoc} */
    public void setRequireValidMetadata(boolean requireValidMetadata) {
        this.requireValidMetadata = requireValidMetadata;
    }

    /** {@inheritDoc} */
    public MetadataFilter getMetadataFilter() {
        return mdFilter;
    }

    /** {@inheritDoc} */
    public void setMetadataFilter(MetadataFilter newFilter) {
        mdFilter = newFilter;
    }

    /**
     * Clears the entity ID to entity descriptor index.
     */
    protected void clearDescriptorIndex() {
        indexedDescriptors.clear();
    }

    /**
     * Unmarshalls the metadata from the given stream. The stream is closed by this method.
     * 
     * @param metadataInputstream the input stream to the metadata.
     * 
     * @return the unmarshalled metadata
     * 
     * @throws UnmarshallingException thrown if the metadata can no be unmarshalled
     */
    protected XMLObject unmarshallMetadata(InputStream metadataInputstream) throws UnmarshallingException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Parsing retrieved metadata into a DOM object");
            }
            Document mdDocument = ParserPoolManager.getInstance().parse(metadataInputstream);

            if (log.isDebugEnabled()) {
                log.debug("Unmarshalling and caching metdata DOM");
            }
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(mdDocument.getDocumentElement());
            return unmarshaller.unmarshall(mdDocument.getDocumentElement());
        } catch (Exception e) {
            throw new UnmarshallingException(e);
        } finally {
            try {
                metadataInputstream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
    
    /**
     * Filters the given metadata.
     * 
     * @param metadata the metadata to be filtered
     * 
     * @throws FilterException thrown if there is an error filtering the metadata
     */
    protected void filterMetadata(XMLObject metadata) throws FilterException{
        if (getMetadataFilter() != null) {
            if (log.isDebugEnabled()) {
                log.debug("Applying metadata filter");
            }
            getMetadataFilter().doFilter(metadata);
        }
    }

    /**
     * Gets the EntityDescriptor with the given ID from the cached metadata.
     * 
     * @param entityID the ID of the entity to get the descriptor for
     * 
     * @return the EntityDescriptor
     */
    protected EntityDescriptor getEntityDescriptorById(String entityID, XMLObject metadata) {
        if (log.isDebugEnabled()) {
            log.debug("Searching for entity descriptor with an entity ID of " + entityID);
        }

        if (indexedDescriptors.containsKey(entityID)) {
            if (log.isDebugEnabled()) {
                log.debug("Entity descriptor for the ID " + entityID + " was found in index cache, returning");
            }
            return indexedDescriptors.get(entityID);
        }

        EntityDescriptor descriptor = null;

        if (metadata != null) {
            if (metadata instanceof EntityDescriptor) {
                if (log.isDebugEnabled()) {
                    log.debug("Metadata root is an entity descriptor, checking if it's the one we're looking for.");
                }
                descriptor = (EntityDescriptor) metadata;
                if (!descriptor.getEntityID().equals(entityID)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Entity descriptor does not have the correct entity ID, returning null");
                    }
                    descriptor = null;
                }
            } else {
                if (log.isDebugEnabled()) {
                    log
                            .debug("Metadata was an entities descriptor, checking if any of it's descendant entity descriptors is the one we're looking for.");
                }
                if (metadata instanceof EntitiesDescriptor) {
                    descriptor = getEntityDescriptorById(entityID, (EntitiesDescriptor) metadata);
                }
            }
        }

        if (descriptor != null) {
            if (log.isDebugEnabled()) {
                log.debug("Located entity descriptor, creating an index to it for faster lookups");
            }
            indexedDescriptors.put(entityID, descriptor);
        }

        return descriptor;
    }

    /**
     * Gets the entity descriptor with the given ID that is a descedant of the given entities descriptor.
     * 
     * @param entityID the ID of the entity whose descriptor is to be fetched
     * @param descriptor the entities descriptor
     * 
     * @return the entity descriptor
     */
    protected EntityDescriptor getEntityDescriptorById(String entityID, EntitiesDescriptor descriptor) {
        if (log.isDebugEnabled()) {
            log
                    .debug("Checking to see if any of the child entity descriptors of this entities descriptor is the requested descriptor");
        }
        List<EntityDescriptor> entityDescriptors = descriptor.getEntityDescriptors();
        if (entityDescriptors != null) {
            for (EntityDescriptor entityDescriptor : entityDescriptors) {
                if (log.isDebugEnabled()) {
                    log.debug("Checking entity descriptor with entity ID " + entityDescriptor.getEntityID());
                }
                if (entityDescriptor.getEntityID().equals(entityID)) {
                    return entityDescriptor;
                }
            }
        }

        if (log.isDebugEnabled()) {
            log
                    .debug("Checking to see if any of the child entities descriptors contains the entity descriptor requested");
        }
        EntityDescriptor entityDescriptor;
        List<EntitiesDescriptor> entitiesDescriptors = descriptor.getEntitiesDescriptors();
        if (entitiesDescriptors != null) {
            for (EntitiesDescriptor entitiesDescriptor : descriptor.getEntitiesDescriptors()) {
                entityDescriptor = getEntityDescriptorById(entityID, entitiesDescriptor);
                if (entityDescriptor != null) {
                    return entityDescriptor;
                }
            }
        }

        return null;
    }

    /**
     * Fetches the filtered metadata for this provider. Implementations should clear the descriptor index everytime a
     * new metadata document it retrieved using {@link #clearDescriptorIndex()}.
     * 
     * @return the metadata
     */
    protected abstract XMLObject fetchMetadata();
}