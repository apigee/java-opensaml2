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
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.Scoping;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.AuthnRequest} objects.
 */
public class AuthnRequestUnmarshaller extends RequestUnmarshaller {

    /**
     * Constructor
     *
     */
    public AuthnRequestUnmarshaller() {
        super(SAMLConstants.SAML20P_NS, AuthnRequest.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue) throws UnmarshallingException, UnknownAttributeException {
        AuthnRequest req = (AuthnRequest) samlObject;
        
        if (attributeName.equals(AuthnRequest.FORCE_AUTHN_ATTRIB_NAME))
            req.setForceAuthn(Boolean.valueOf(attributeValue));
        else if (attributeName.equals(AuthnRequest.IS_PASSIVE_ATTRIB_NAME))
            req.setIsPassive(Boolean.valueOf(attributeValue));
        else if (attributeName.equals(AuthnRequest.PROTOCOL_BINDING_ATTRIB_NAME))
            req.setProtocolBinding(attributeValue);
        else if (attributeName.equals(AuthnRequest.ASSERTION_CONSUMER_SERVICE_INDEX_ATTRIB_NAME))
            req.setAssertionConsumerServiceIndex(Integer.valueOf(attributeValue));
        else if (attributeName.equals(AuthnRequest.ASSERTION_CONSUMER_SERVICE_URL_ATTRIB_NAME))
            req.setAssertionConsumerServiceURL(attributeValue);
        else if (attributeName.equals(AuthnRequest.ATTRIBUTE_CONSUMING_SERVICE_INDEX_ATTRIB_NAME))
            req.setAttributeConsumingServiceIndex(Integer.valueOf(attributeValue));
        else if (attributeName.equals(AuthnRequest.PROVIDER_NAME_ATTRIB_NAME))
            req.setProviderName(attributeValue);
        else
            super.processAttribute(samlObject, attributeName, attributeValue);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject) throws UnmarshallingException, UnknownElementException {
        AuthnRequest req = (AuthnRequest) parentSAMLObject;
        
        if (childSAMLObject instanceof Subject)
            req.setSubject((Subject) childSAMLObject);
        else if (childSAMLObject instanceof NameIDPolicy)
            req.setNameIDPolicy((NameIDPolicy) childSAMLObject);
        else if (childSAMLObject instanceof Conditions)
            req.setConditions((Conditions) childSAMLObject);
        else if (childSAMLObject instanceof RequestedAuthnContext)
            req.setRequestedAuthnContext((RequestedAuthnContext) childSAMLObject);
        else if (childSAMLObject instanceof Scoping)
            req.setScoping((Scoping) childSAMLObject);
        else 
            super.processChildElement(parentSAMLObject, childSAMLObject);
    }
}