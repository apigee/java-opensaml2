/*
 * Copyright 2006 University Corporation for Advanced Internet Development, Inc.
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.saml2.common.SAML2Helper;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.w3c.dom.Document;

/** An abstract, base, implementation of a metadata provider. */
public abstract class AbstractMetadataProvider extends BaseMetadataProvider {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(AbstractMetadataProvider.class);

    /** Cache of entity IDs to their descriptors. */
    private HashMap<String, EntityDescriptor> indexedDescriptors;

    /** Pool of parsers used to process XML. */
    private ParserPool parser;

    /** Constructor. */
    public AbstractMetadataProvider() {
        super();
        indexedDescriptors = new HashMap<String, EntityDescriptor>();
    }

    /** {@inheritDoc} */
    public EntitiesDescriptor getEntitiesDescriptor(String name) throws MetadataProviderException {
        if (DatatypeHelper.isEmpty(name)) {
            return null;
        }

        XMLObject metadata = getMetadata();
        if (metadata == null) {
            log.debug("Metadata document was empty, unable to look for an EntitiesDescriptor with the name {}", name);
            return null;
        }

        if (metadata instanceof EntitiesDescriptor) {
            EntitiesDescriptor descriptor = (EntitiesDescriptor) metadata;
            return getEntitiesDescriptorByName(name, descriptor);
        }

        log.debug("Metadata document does not contain an EntitiesDescriptor with the name {}", name);
        return null;
    }

    /** {@inheritDoc} */
    public EntityDescriptor getEntityDescriptor(String entityID) throws MetadataProviderException {
        if (DatatypeHelper.isEmpty(entityID)) {
            return null;
        }

        XMLObject metadata = getMetadata();
        if (metadata == null) {
            log.debug("Metadata document was empty, unable to look for an EntityDescriptor with the ID {}", entityID);
            return null;
        }

        EntityDescriptor descriptor = getEntityDescriptorById(entityID, metadata);
        if (descriptor == null) {
            log.debug("Metadata document does not contain an EntityDescriptor with the ID {}", entityID);
            return null;
        }
        return descriptor;
    }

    /** {@inheritDoc} */
    public List<RoleDescriptor> getRole(String entityID, QName roleName) throws MetadataProviderException {
        if (DatatypeHelper.isEmpty(entityID) || roleName == null) {
            return null;
        }

        EntityDescriptor entity = getEntityDescriptor(entityID);
        if (entity != null) {
            return entity.getRoleDescriptors(roleName);
        } else {
            return null;
        }
    }

    /** {@inheritDoc} */
    public RoleDescriptor getRole(String entityID, QName roleName, String supportedProtocol)
            throws MetadataProviderException {
        if (DatatypeHelper.isEmpty(entityID) || roleName == null || DatatypeHelper.isEmpty(supportedProtocol)) {
            return null;
        }

        List<RoleDescriptor> roles = getRole(entityID, roleName);
        if (roles == null || roles.isEmpty()) {
            return null;
        }

        Iterator<RoleDescriptor> rolesItr = roles.iterator();
        RoleDescriptor role;
        while (rolesItr.hasNext()) {
            role = rolesItr.next();
            if (role != null && role.isSupportedProtocol(supportedProtocol)) {
                return role;
            }
        }

        return null;
    }

    /**
     * Gets the pool of parsers to use to parse XML.
     * 
     * @return pool of parsers to use to parse XML
     */
    public ParserPool getParserPool() {
        return parser;
    }

    /**
     * Sets the pool of parsers to use to parse XML.
     * 
     * @param pool pool of parsers to use to parse XML
     */
    public void setParserPool(ParserPool pool) {
        parser = pool;
    }

    /**
     * Clears the entity ID to entity descriptor index.
     */
    protected void clearDescriptorIndex() {
        indexedDescriptors.clear();
    }

    /**
     * Unmarshalls the metadata from the given stream. The stream is closed by this method and the returned metadata
     * released its DOM representation.
     * 
     * @param metadataInput the input reader to the metadata.
     * 
     * @return the unmarshalled metadata
     * 
     * @throws UnmarshallingException thrown if the metadata can no be unmarshalled
     */
    protected XMLObject unmarshallMetadata(InputStream metadataInput) throws UnmarshallingException {
        try {
            log.trace("Parsing retrieved metadata into a DOM object");
            Document mdDocument = parser.parse(metadataInput);

            log.trace("Unmarshalling and caching metdata DOM");
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(mdDocument.getDocumentElement());
            if (unmarshaller == null) {
                String msg = MessageFormatter.format("No unmarshaller registered for document element {}", XMLHelper
                        .getNodeQName(mdDocument.getDocumentElement()));
                log.error(msg);
                throw new UnmarshallingException(msg);
            }
            XMLObject metadata = unmarshaller.unmarshall(mdDocument.getDocumentElement());
            return metadata;
        } catch (Exception e) {
            throw new UnmarshallingException(e);
        } finally {
            try {
                metadataInput.close();
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
    protected void filterMetadata(XMLObject metadata) throws FilterException {
        if (getMetadataFilter() != null) {
            log.debug("Applying metadata filter");
            getMetadataFilter().doFilter(metadata);
        }
    }

    /**
     * Releases the DOM representation from the metadata object.
     * 
     * @param metadata the metadata object
     */
    protected void releaseMetadataDOM(XMLObject metadata) {
        if (metadata != null) {
            metadata.releaseDOM();
            metadata.releaseChildrenDOM(true);
        }
    }

    /**
     * Gets the EntityDescriptor with the given ID from the cached metadata.
     * 
     * @param entityID the ID of the entity to get the descriptor for
     * @param metadata metadata associated with the entity
     * 
     * @return the EntityDescriptor
     */
    protected EntityDescriptor getEntityDescriptorById(String entityID, XMLObject metadata) {
        EntityDescriptor descriptor = null;

        log.debug("Searching for entity descriptor with an entity ID of {}", entityID);
        if (entityID != null && indexedDescriptors.containsKey(entityID)) {
            descriptor = indexedDescriptors.get(entityID);
            if (isValid(descriptor)) {
                log.trace("Entity descriptor for the ID {} was found in index cache, returning", entityID);
                return descriptor;
            } else {
                indexedDescriptors.remove(descriptor);
            }
        }

        if (metadata != null) {
            if (metadata instanceof EntityDescriptor) {
                log.trace("Metadata root is an entity descriptor, checking if it's the one we're looking for.");
                descriptor = (EntityDescriptor) metadata;
                if (!DatatypeHelper.safeEquals(descriptor.getEntityID(), entityID)) {
                    // skip this one, it isn't what we're looking for
                    descriptor = null;
                }
                if (!isValid(descriptor)) {
                    log.trace("Found entity descriptor for entity with ID {} but it is no longer valid, skipping it.",
                            entityID);
                    descriptor = null;
                }
            } else {
                log
                        .trace("Metadata was an EntitiesDescriptor, checking if any of its descendant EntityDescriptor elements is the one we're looking for.");
                if (metadata instanceof EntitiesDescriptor) {
                    descriptor = getEntityDescriptorById(entityID, (EntitiesDescriptor) metadata);
                }
            }
        }

        if (descriptor != null) {
            log.trace("Located entity descriptor, creating an index to it for faster lookups");
            indexedDescriptors.put(entityID, descriptor);
        }

        return descriptor;
    }

    /**
     * Gets the entity descriptor with the given ID that is a descendant of the given entities descriptor.
     * 
     * @param entityID the ID of the entity whose descriptor is to be fetched
     * @param descriptor the entities descriptor
     * 
     * @return the entity descriptor
     */
    protected EntityDescriptor getEntityDescriptorById(String entityID, EntitiesDescriptor descriptor) {
        log.trace("Checking to see if EntitiesDescriptor {} contains the requested descriptor", descriptor.getName());
        List<EntityDescriptor> entityDescriptors = descriptor.getEntityDescriptors();
        if (entityDescriptors != null && !entityDescriptors.isEmpty()) {
            for (EntityDescriptor entityDescriptor : entityDescriptors) {
                log.trace("Checking entity descriptor with entity ID {}", entityDescriptor.getEntityID());
                if (DatatypeHelper.safeEquals(entityDescriptor.getEntityID(), entityID) && isValid(entityDescriptor)) {
                    return entityDescriptor;
                }
            }
        }

        log.trace("Checking to see if any of the child entities descriptors contains the entity descriptor requested");
        EntityDescriptor entityDescriptor;
        List<EntitiesDescriptor> entitiesDescriptors = descriptor.getEntitiesDescriptors();
        if (entitiesDescriptors != null && !entitiesDescriptors.isEmpty()) {
            for (EntitiesDescriptor entitiesDescriptor : descriptor.getEntitiesDescriptors()) {
                entityDescriptor = getEntityDescriptorById(entityID, entitiesDescriptor);
                if (entityDescriptor != null) {
                    // We don't need to check for validity because getEntityDescriptorById only returns a valid
                    // descriptor
                    return entityDescriptor;
                }
            }
        }

        return null;
    }

    /**
     * Gets the entities descriptor with the given name.
     * 
     * @param name name of the entities descriptor
     * @param rootDescriptor the root descriptor to search in
     * 
     * @return the EntitiesDescriptor with the given name
     */
    protected EntitiesDescriptor getEntitiesDescriptorByName(String name, EntitiesDescriptor rootDescriptor) {
        EntitiesDescriptor descriptor = null;

        if (DatatypeHelper.safeEquals(name, rootDescriptor.getName()) && isValid(rootDescriptor)) {
            descriptor = rootDescriptor;
        } else {
            List<EntitiesDescriptor> childDescriptors = rootDescriptor.getEntitiesDescriptors();
            if (childDescriptors == null || childDescriptors.isEmpty()) {
                return null;
            }
            for (EntitiesDescriptor childDescriptor : childDescriptors) {
                childDescriptor = getEntitiesDescriptorByName(name, childDescriptor);
                if (childDescriptor != null) {
                    descriptor = childDescriptor;
                }
            }
        }

        return descriptor;
    }

    /**
     * Returns whether the given descriptor is valid. If valid metadata is not required this method always returns true.
     * 
     * @param descriptor the descriptor to check
     * 
     * @return true if valid metadata is not required or the given descriptor is valid, false otherwise
     */
    protected boolean isValid(XMLObject descriptor) {
        if (descriptor == null) {
            return false;
        }

        if (!requireValidMetadata()) {
            return true;
        }

        return SAML2Helper.isValid(descriptor);
    }
}