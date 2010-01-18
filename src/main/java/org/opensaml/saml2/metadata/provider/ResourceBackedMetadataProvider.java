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

import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.opensaml.saml2.common.SAML2Helper;
import org.opensaml.util.resource.Resource;
import org.opensaml.util.resource.ResourceException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A metadata provider that reads metadata from a {#link {@link Resource}.
 * 
 * @since 2.2
 */
public class ResourceBackedMetadataProvider extends AbstractObservableMetadataProvider {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceBackedMetadataProvider.class);

    /** Timer used to execute metadata polling tasks. */
    private Timer taskTimer;

    /** Maximum amount of time metadata will be cached, in milliseconds. */
    private long maxCacheDuration;

    /** Resource from which metadata is read. */
    private Resource metadataResource;

    /** The last time the metadata was updated as reported by the resource. */
    private DateTime lastMetadataUpdate;

    /** Whether cached metadata should be discarded if it expires and can't be refreshed. */
    private boolean maintainExpiredMetadata;

    /** Cached metadata. */
    private XMLObject cachedMetadata;

    /**
     * Constructor.
     * 
     * @param resource resource from which to read the metadata file.
     * @param timer task timer used to schedule metadata refresh tasks
     * @param maxMetadataCacheDuration maximum amount of time, in milliseconds, that metadata may be cached before being
     *            re-read
     * 
     * @throws MetadataProviderException thrown if there is a problem retrieving information about the resource
     */
    public ResourceBackedMetadataProvider(Resource resource, Timer timer, long maxMetadataCacheDuration)
            throws MetadataProviderException {
        super();

        taskTimer = timer;

        if (maxMetadataCacheDuration < 1) {
            throw new IllegalArgumentException("Max cache duration must be a positive number");
        }
        maxCacheDuration = maxMetadataCacheDuration;

        try {
            if (!resource.exists()) {
                throw new MetadataProviderException("Resource " + resource.getLocation() + " does not exist.");
            }
            metadataResource = resource;
            maintainExpiredMetadata = false;
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
        return cachedMetadata;
    }

    /**
     * Retrieves, unmarshalls, and filters the metadata from the metadata resource.
     * 
     * @throws MetadataProviderException thrown if there is a problem reading, parsing, or validating the metadata
     */
    private void refreshMetadata() throws MetadataProviderException {
        try {
            boolean metadataChanged = false;
            
            XMLObject metadata = getLatestMetadata();
            if(metadata != cachedMetadata){
                metadataChanged = true;
            }

            // If the metadata has expired, and we're not configured to use expired metadata, discard it else use it
            DateTime expirationTime = SAML2Helper.getEarliestExpiration(metadata);
            if (expirationTime != null && !maintainExpiredMetadata() && expirationTime.isBeforeNow()) {
                log.debug("Metadata from resource {} is expired and this provider is configured not to retain expired metadata.",
                                metadataResource.getInputStream());
                cachedMetadata = null;
                metadataChanged = true;
            } else {
                cachedMetadata = metadata;
            }

            // Let everyone know metadata has changed
            if (metadataChanged) {
                emitChangeEvent();
            }

            // Determine the next time to check for a metadata change and schedule it
            long nextUpdateDelay=0;
            if (expirationTime != null && expirationTime.isBefore(System.currentTimeMillis() + maxCacheDuration)) {
                nextUpdateDelay = expirationTime.getMillis() - System.currentTimeMillis();
            }
            
            if(nextUpdateDelay <= 0){
                nextUpdateDelay = maxCacheDuration;
            }
            
            log.debug("Next refresh of metadata from resource {} scheduled in {}ms", metadataResource.getLocation(),
                    nextUpdateDelay);
            taskTimer.schedule(new MetadataPollTask(), nextUpdateDelay);
        } catch (ResourceException e) {
            String errorMsg = "Unable to read metadata file";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }
    }

    /**
     * Gets the latest metadata from the resource if the resource reports a modification time after the last update,
     * otherwise uses the currently cached metadata.
     * 
     * @return latest metadata
     * 
     * @throws MetadataProviderException thrown if the metadata can not be loaded from the resource, unmarshalled, and
     *             filtered
     */
    private XMLObject getLatestMetadata() throws MetadataProviderException {
        XMLObject metadata;

        try {
            DateTime metadataUpdateTime = metadataResource.getLastModifiedTime();
            if (lastMetadataUpdate == null || metadataUpdateTime.isAfter(lastMetadataUpdate)) {
                lastMetadataUpdate = metadataUpdateTime;
                log.debug("Refreshing metadata from resource {}", metadataResource.getLocation());
                metadata = unmarshallMetadata(metadataResource.getInputStream());
                filterMetadata(metadata);
                releaseMetadataDOM(metadata);
            } else {
                metadata = cachedMetadata;
            }

            return metadata;
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

    /** Timer task that periodically refreshes metadata. */
    private class MetadataPollTask extends TimerTask {

        /** {@inheritDoc} */
        public void run() {
            try {
                log.debug("Checking if metadata from resource {} should be updated", metadataResource.getLocation());
                refreshMetadata();
            } catch (MetadataProviderException e) {
                log.error("Unable to refresh metadata", e);
            }
        }
    }
}