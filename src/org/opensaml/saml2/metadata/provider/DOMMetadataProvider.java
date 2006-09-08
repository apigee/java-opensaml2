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
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Element;

/**
 * A <code>MetadataProvider</code> implementation that retrieves metadata from a DOM <code>Element</code> as
 * supplied by the user.
 */
public class DOMMetadataProvider extends AbstractObservableMetadataProvider implements MetadataProvider {

    /** Logger */
    private final Logger log = Logger.getLogger(DOMMetadataProvider.class);

    /** Root metadata element exposed by this provider */
    private Element metadataElement;
    
    /** Unmarshalled metadata */
    private XMLObject metadata;

    /**
     * Constructor
     *
     * @param metadataElement the metadata element
     */
    public DOMMetadataProvider(Element metadataElement) throws MetadataProviderException {
        super();
        this.metadataElement = metadataElement;
    }

    /** {@inheritDoc} */
    public XMLObject getMetadata(){
        return metadata;
    }
    
    /** 
     * {@inheritDoc} 
     * @throws MetadataProviderException 
     */
    public void setMetadataFilter(MetadataFilter newFilter) throws MetadataProviderException{
        super.setMetadataFilter(newFilter);
        refreshMetadata();
    }
    
    private synchronized void refreshMetadata() throws MetadataProviderException{
        try {
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(metadataElement);
            metadata = unmarshaller.unmarshall(metadataElement);
            metadata.releaseDOM();
            metadata.releaseChildrenDOM(true);
            filterMetadata(metadata);
            emitChangeEvent();
        } catch (UnmarshallingException e) {
            String errorMsg = "Unable to unmarshall metadata element";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        } catch (FilterException e) {
            String errorMsg = "Unable to filter metadata";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }
    }
}