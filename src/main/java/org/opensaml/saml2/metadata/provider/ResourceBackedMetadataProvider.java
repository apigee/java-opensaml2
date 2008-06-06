/*
 * Copyright 2008 University Corporation for Advanced Internet Development, Inc.
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

import org.joda.time.DateTime;
import org.opensaml.saml2.common.SAML2Helper;
import org.opensaml.util.resource.Resource;
import org.opensaml.util.resource.ResourceException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A metadata provider that reads metadta from a {#link {@link Resource}.
 * 
 * @since 2.2
 */
public class ResourceBackedMetadataProvider extends AbstractObservableMetadataProvider {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceBackedMetadataProvider.class);

    /** Resource from which metadata is read. */
    private Resource metadataResource;

    /** Whether cached metadata should be discarded if it expires and can't be refreshed. */
    private boolean maintainExpiredMetadata;

    /** Last time the cached metadata was updated. */
    private long lastUpdate;

    /** Cached metadata. */
    private XMLObject cachedMetadata;

    /**
     * Constructor.
     * 
     * @param resource resource from which to read the metadata file.
     * 
     * @throws MetadataProviderException thrown if there is a problem retrieving information about the resource
     */
    public ResourceBackedMetadataProvider(Resource resource) throws MetadataProviderException {
        super();

        try {
            if (!resource.exists()) {
                throw new MetadataProviderException("Resource " + resource.getLocation() + " does not exist.");
            }
            metadataResource = resource;
            maintainExpiredMetadata = false;
            lastUpdate = -1;
        } catch (ResourceException e) {
            throw new MetadataProviderException("Unable to read resource", e);
        }
    }

    /**
     * Initializes the provider and prepares it for use.
     * 
     * @throws MetadataProviderException thrown if there is a problem reading, parsing, or validating the metadata
     */
    public void initialize() throws MetadataProviderException {
        refreshMetadata();
    }

    /**
     * Gets whether cached metadata should be discarded if it expires and can not be refreshed.
     * 
     * @return whether cached metadata should be discarded if it expires and can not be refreshed
     */
    public boolean maintainExpiredMetadata() {
        return maintainExpiredMetadata;
    }

    /**
     * Sets whether cached metadata should be discarded if it expires and can not be refreshed.
     * 
     * @param maintain whether cached metadata should be discarded if it expires and can not be refreshed
     */
    public void setMaintainExpiredMetadata(boolean maintain) {
        maintainExpiredMetadata = maintain;
    }

    /** {@inheritDoc} */
    public XMLObject getMetadata() throws MetadataProviderException {
        try {
            if (lastUpdate < metadataResource.getLastModifiedTime().getMillis()) {
                refreshMetadata();
            }

            return cachedMetadata;
        } catch (ResourceException e) {
            log.error("Unable to determine last modified time of resource {}", new Object[] { metadataResource
                    .getLocation(), }, e);
            throw new MetadataProviderException("Unable to determine last modified time of resource", e);
        }
    }

    /**
     * Retrieves, unmarshalls, and filters the metadata from the metadata file.
     * 
     * @throws MetadataProviderException thrown if there is a problem reading, parsing, or validating the metadata
     */
    private synchronized void refreshMetadata() throws MetadataProviderException {
        try {
            // Only read the resource last mod time once, store off for later use. See below.
            long metadataFileLastModified = metadataResource.getLastModifiedTime().getMillis();
            if (lastUpdate >= metadataFileLastModified) {
                // In case other requests stacked up behind the synchronize lock
                return;
            }

            log.debug("Refreshing metadata from resource {}", metadataResource.getLocation());
            XMLObject metadata = unmarshallMetadata(metadataResource.getInputStream());
            DateTime expirationTime = SAML2Helper.getEarliestExpiration(metadata);
            if (expirationTime != null && !maintainExpiredMetadata() && expirationTime.isBeforeNow()) {
                log
                        .debug(
                                "Metadata from resource {} is expired and this provider is configured not to retain expired metadata.",
                                metadataResource.getInputStream());
                cachedMetadata = null;
            } else {
                filterMetadata(metadata);
                releaseMetadataDOM(metadata);
                cachedMetadata = metadata;
            }

            // Note: this doesn't really avoid re-reading the metadata file unnecessarily on later refreshes
            // (case where file changed between reading/storing the last mod time above and when the contents
            // were read above).
            // It does however avoid the greater evil of *missing* a newer file on a subsequent refresh
            // (case where the file changed after the contents were read above, but before here).
            // To do this exactly correctly, we need to make use of OS filesystem-level file locking.
            lastUpdate = metadataFileLastModified;
            emitChangeEvent();
        } catch (ResourceException e) {
            String errorMsg = "Unable to read metadata file";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        } catch (UnmarshallingException e) {
            String errorMsg = "Unable to unmarshall metadata";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        } catch (FilterException e) {
            String errorMsg = "Unable to filter metadata";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }
    }
}