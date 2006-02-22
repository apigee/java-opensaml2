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
package org.opensaml.saml2.core.impl;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread-safe {@link org.opensaml.common.io.Marshaller} for {@link org.opensaml.saml2.core.AuthnRequest}.
 */
public class AuthnRequestMarshaller extends RequestMarshaller {

    /**
     * Constructor
     *
     */
    public AuthnRequestMarshaller() {
        super(SAMLConstants.SAML20P_NS, AuthnRequest.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestMarshaller#marshallAttributes(org.opensaml.common.SAMLObject, org.w3c.dom.Element)
     */
    protected void marshallAttributes(SAMLObject samlObject, Element domElement) throws MarshallingException {
        AuthnRequest req = (AuthnRequest) samlObject;
        
        if (req.getForceAuthn() != null)
            domElement.setAttributeNS(null, AuthnRequest.FORCE_AUTHN_ATTRIB_NAME, req.getForceAuthn().toString());
        
        if (req.getIsPassive() != null)
            domElement.setAttributeNS(null, AuthnRequest.IS_PASSIVE_ATTRIB_NAME, req.getIsPassive().toString());
        
        if (req.getProtocolBinding() != null)
            domElement.setAttributeNS(null, AuthnRequest.PROTOCOL_BINDING_ATTRIB_NAME, req.getProtocolBinding());
        
        if (req.getAssertionConsumerServiceIndex() != null)
            domElement.setAttributeNS(null, AuthnRequest.ASSERTION_CONSUMER_SERVICE_INDEX_ATTRIB_NAME, req.getAssertionConsumerServiceIndex().toString());
        
        if (req.getAssertionConsumerServiceURL() != null)
            domElement.setAttributeNS(null, AuthnRequest.ASSERTION_CONSUMER_SERVICE_URL_ATTRIB_NAME, req.getAssertionConsumerServiceURL());
        
        if (req.getAttributeConsumingServiceIndex() != null)
            domElement.setAttributeNS(null, AuthnRequest.ATTRIBUTE_CONSUMING_SERVICE_INDEX_ATTRIB_NAME, req.getAttributeConsumingServiceIndex().toString());
        
        if (req.getProviderName() != null)
            domElement.setAttributeNS(null, AuthnRequest.PROVIDER_NAME_ATTRIB_NAME, req.getProviderName());
        
        super.marshallAttributes(samlObject, domElement);
    }
    
     

}
