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

import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.io.impl.AbstractUnmarshaller;
import org.opensaml.saml2.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.metadata.Endpoint;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.Endpoint} objects.
 */
public class EndpointUnmarshaller extends AbstractUnmarshaller implements Unmarshaller {

    public EndpointUnmarshaller() {
        super(Endpoint.QNAME);
    }
    
    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(AbstractSAMLObject parentElement, AbstractSAMLObject childElement){
        //Doesn't have any children, do nothing
    }
    
    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(AbstractSAMLObject samlElement, String attributeName, String attributeValue) throws UnmarshallingException{
        Endpoint endpoint = (Endpoint)samlElement;
        if(attributeName.equals(Endpoint.BINDING_ATTRIB_NAME)) {
            endpoint.setBinding(attributeValue);
        }else if(attributeName.equals(Endpoint.LOCATION_ATTRIB_NAME)) {
            endpoint.setLocation(attributeValue);
        }else if(attributeName.equals(Endpoint.RESPONSE_LOCATION_ATTRIB_NAME)) {
            endpoint.setResponseLocation(attributeValue);
        }else {
            throw new UnmarshallingException("Attribute " + attributeName + " is not a valid attribute for an Endpoint");
        }
    }

}
