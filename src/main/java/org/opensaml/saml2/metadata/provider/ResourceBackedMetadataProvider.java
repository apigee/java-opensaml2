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

import org.joda.time.DateTime;
import org.opensaml.util.resource.Resource;
import org.opensaml.util.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A metadata provider that reads metadata from a {#link {@link Resource}.
 * 
 * @since 2.2
 */
public class ResourceBackedMetadataProvider extends AbstractReloadingMetadataProvider {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceBackedMetadataProvider.class);

    /** Resource from which metadata is read. */
    private Resource metadataResource;

    /** Time the metadata resource was last updated. */
    private DateTime lastResourceUpdate;

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
        super(timer);

        try {
            if (!resource.exists()) {
                throw new MetadataProviderException("Resource " + resource.getLocation() + " does not exist.");
            }
            metadataResource = resource;
        } catch (ResourceException e) {
            throw new MetadataProviderException("Unable to read resource", e);
        }
    }

    /** {@inheritDoc} */
    protected String getMetadataIdentifier() {
        return metadataResource.getLocation();
    }

    /** {@inheritDoc} */
    protected byte[] fetchMetadata() throws MetadataProviderException {
        try {
            DateTime metadataUpdateTime = metadataResource.getLastModifiedTime();
            if (lastResourceUpdate == null || metadataUpdateTime.isAfter(lastResourceUpdate)) {
                lastResourceUpdate = metadataUpdateTime;
                return inputstreamToByteArray(metadataResource.getInputStream());
            }

            return null;
        } catch (ResourceException e) {
            String errorMsg = "Unable to read metadata file";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }
    }
}