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

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.opensaml.saml2.common.SAML2Helper;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Element;

/**
 * A <code>MetadataProvider</code> implementation that retrieves metadata from a DOM <code>Element</code> as
 * supplied by the user.
 * 
 * @author Walter Hoehn
 */

public class DOMMetadataProvider extends AbstractMetadataProvider implements MetadataProvider {

    private final Logger log = Logger.getLogger(DOMMetadataProvider.class);

    private Element domElement;

    /** Unmarshalled metadata */
    private XMLObject metadata;

    /** Maximum amount of time to keep metadata cached */
    private long maxCacheDuration = 0;

    /** When the cached metadata becomes stale */
    private DateTime mdExpirationTime;

    public DOMMetadataProvider(Element e) {

        super();
        domElement = e;
        try {
            XMLObject newMetadata = getFreshMetadata();
            metadata = newMetadata;
        } catch (UnmarshallingException ume) {
            // TODO throw an error if we can't construct
        }
    }

    @Override
    protected XMLObject fetchMetadata() {

        if (mdExpirationTime.isBeforeNow()) {
            log.debug("Cached metadata is stale, refreshing");
            try {
                clearDescriptorIndex();
                XMLObject newMetadata = getFreshMetadata();
                filterMetadata(newMetadata);
                metadata = newMetadata;
            } catch (UnmarshallingException e) {
                log.error("Error fetching metdata from DOM.  Continuing to use stale metadata: " + e);
            } catch (FilterException e) {
                log.error("Error filtering metdata from metadata DOM.  Continuing to use stale metadata: " + e);
            }
        }

        return metadata;
    }

    private XMLObject getFreshMetadata() throws UnmarshallingException {

        Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(domElement);
        XMLObject metadata = unmarshaller.unmarshall(domElement);
        metadata.releaseDOM();
        metadata.releaseChildrenDOM(true);

        DateTime now = new DateTime();
        if (maxCacheDuration > 0) {
            mdExpirationTime = SAML2Helper.getEarliestExpiration(metadata, now.plus(maxCacheDuration), now);
        } else {
            mdExpirationTime = SAML2Helper.getEarliestExpiration(metadata);
        }
        if (log.isDebugEnabled()) {
            log.debug("Metadata cache expires on " + mdExpirationTime);
        }

        return metadata;

    }

    /**
     * Sets the maximum amount of time, in milliseconds, for which the metadata will be cached.
     * 
     * @param newDuration the maximum amount of time metadata will be cached for
     */
    public void setMaxDuration(long newDuration) {
        maxCacheDuration = newDuration;
        fetchMetadata();
    }
}
