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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.io.impl.AbstractUnmarshaller;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.Status;

/**
 *
 */
public class ResponseUnmarshaller extends AbstractUnmarshaller implements Unmarshaller {

    /**
     * Constructor
     *
     * @param target
     */
    public ResponseUnmarshaller() {
        super(Response.QNAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {
        
        Response response = (Response) parentElement;
        
        try {
            if (childElement instanceof Assertion) {
                
                response.addAssertion((Assertion)childElement);
            
            } else if (childElement instanceof Status) {
                
                response.setStatus((Status) childElement);
            
            } else { 
                if(!SAMLConfig.ignoreUnknownElements()){
                    throw new UnknownElementException(childElement.getElementQName() + " is not a supported element for Response objects");
                }
            }
        }
        catch(IllegalAddException e){
        
            throw new UnmarshallingException(e);
        }
    }    

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        Response response = (Response) samlElement; 
            
        if (attributeName.equals(Response.INRESPONSETO_ATTRIB_NAME)) {
            
            response.setInResponseTo(attributeValue);
            
        } else if (attributeName.equals(Response.ISSUEINSTANT_ATTRIB_NAME)) {
            
            // TODO Does this work?
            DateFormat formatter;
            
            int dot = attributeValue.indexOf('.');
            if (dot > 0) {
                formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            }
            else {
                formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            }
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            
            try {
                response.setIssueInstant(formatter.parse(attributeValue));
            } 
            catch (ParseException p) {
                
                throw new UnmarshallingException(p);
            }
    
        } else if (attributeName.equals(Response.MAJORVERSION_ATTRIB_NAME)) {
            
            try {
                if (Integer.parseInt(attributeValue) != 1) {
                    throw new UnmarshallingException("SAML version must be 1");
                }
            } 
            catch (NumberFormatException n) {
                throw new UnmarshallingException(n);
            }
            
        } else if (attributeName.equals(Response.MINORVERSION_ATTRIB_NAME)) {
            
            try {
                int newVersion = Integer.parseInt(attributeValue); 
    
                response.setMinorVersion(newVersion);
              
            } 
            catch (NumberFormatException n) {
                throw new UnmarshallingException(n);
            }
    
        } else if (attributeName.equals(Response.RECIPIENT_ATTRIB_NAME)) {
            
            response.setRecipient(attributeValue);
    
        } else if (attributeName.equals(Response.RESPONSEID_ATTRIB_NAME)) {
            
            response.setResponseID(attributeValue);
           
        } else {
            if(!SAMLConfig.ignoreUnknownAttributes()){
                throw new UnknownAttributeException(attributeName + " is not a supported attributed for Response objects");
            }
        }
    }
}