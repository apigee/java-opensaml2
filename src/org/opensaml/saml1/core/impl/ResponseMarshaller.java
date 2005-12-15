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

/**
 * 
 */
package org.opensaml.saml1.core.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallingException;
import org.opensaml.common.io.impl.AbstractMarshaller;
import org.opensaml.saml1.core.Response;
import org.w3c.dom.Element;

/**
 *
 */
public class ResponseMarshaller extends AbstractMarshaller implements Marshaller {

    /**
     * Constructor
     *
     * @param target
     */
    public ResponseMarshaller() {
        super(Response.QNAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractMarshaller#marshallAttributes(org.opensaml.common.SAMLObject, org.w3c.dom.Element)
     */
    @Override
    protected void marshallAttributes(SAMLObject samlElement, Element domElement) throws MarshallingException {
        
        Response response = (Response) samlElement;
        
        if (response.getInResponseTo() != null){
            
           domElement.setAttribute(Response.INRESPONSETO_ATTRIB_NAME, response.getInResponseTo());
        }
        
        if (response.getIssueInstant() != null) {
            
            DateFormat formatter;

            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            
            String date = formatter.format(response.getIssueInstant());
            
            domElement.setAttribute(Response.ISSUEINSTANT_ATTRIB_NAME, date);
        }

        String minorVersion = Integer.toString(response.getMinorVersion());

        domElement.setAttribute(Response.MINORVERSION_ATTRIB_NAME, minorVersion);
        domElement.setAttribute(Response.MAJORVERSION_ATTRIB_NAME, "1");
        
        if (response.getRecipient() != null) {
            
            domElement.setAttribute(Response.RECIPIENT_ATTRIB_NAME, response.getRecipient());
        }
        
        if (response.getResponseID() != null) {
            
            domElement.setAttribute(Response.RESPONSEID_ATTRIB_NAME, response.getResponseID());
        }

    }

}
