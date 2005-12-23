/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.metadata.impl;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.impl.AbstractMarshaller;
import org.opensaml.common.util.xml.XMLParserException;
import org.opensaml.saml2.metadata.Endpoint;
import org.w3c.dom.Element;

/**
 * A thread safe {@link org.opensaml.common.io.Marshaller} for {@link org.opensaml.saml2.metadata.Endpoint} objects.
 */
public class EndpointMarshaller extends AbstractMarshaller implements Marshaller {
    
    /**
     * 
     * Constructor
     *
     * @throws XMLParserException thrown when an JAXP DatatypeFactory can not be created
     */
    public EndpointMarshaller(){
        super(Endpoint.QNAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractMarshaller#doMarshalling(org.opensaml.common.SAMLElement, org.w3c.dom.Document)
     */
    public void marshallAttributes(SAMLObject samlElement, Element domElement){
        Endpoint endpoint = (Endpoint)samlElement;
        
        domElement.setAttribute(Endpoint.BINDING_ATTRIB_NAME, endpoint.getBinding().toString());
        domElement.setAttribute(Endpoint.LOCATION_ATTRIB_NAME, endpoint.getLocation().toString());
        
        if(endpoint.getResponseLocation() != null){
            domElement.setAttribute(Endpoint.RESPONSE_LOCATION_ATTRIB_NAME, endpoint.getResponseLocation().toString());
        }
    }
}
