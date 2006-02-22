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
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextComparisonType;
import org.opensaml.saml2.core.AuthnContextDeclRef;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.RequestedAuthnContext}
 * objects.
 */
public class RequestedAuthnContextUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     *
     */
    public RequestedAuthnContextUnmarshaller() {
        super(SAMLConstants.SAML20P_NS, RequestedAuthnContext.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue) throws UnmarshallingException, UnknownAttributeException {
        RequestedAuthnContext rac = (RequestedAuthnContext) samlObject;
        
        if (attributeName.equals(RequestedAuthnContext.COMPARISON_ATTRIB_NAME)) {
            if ("exact".equals(attributeValue))
                rac.setComparison(AuthnContextComparisonType.EXACT);
            else if ("minimum".equals(attributeValue))
                rac.setComparison(AuthnContextComparisonType.MINIMUM);
            else if ("maximum".equals(attributeValue))
                rac.setComparison(AuthnContextComparisonType.MAXIMUM);
            else if ("better".equals(attributeValue))
                rac.setComparison(AuthnContextComparisonType.BETTER);
            else
                throw new UnmarshallingException("Saw an invalid value for Comparison attribute: " + attributeValue);
        } else
            super.processAttribute(samlObject, attributeName, attributeValue);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject) throws UnmarshallingException, UnknownElementException {
        RequestedAuthnContext rac = (RequestedAuthnContext) parentSAMLObject;
        if (childSAMLObject instanceof AuthnContextClassRef)
            rac.getAuthnContextClassRefs().add((AuthnContextClassRef) childSAMLObject);
        else if (childSAMLObject instanceof AuthnContextDeclRef)
            rac.getAuthnContextDeclRefs().add((AuthnContextDeclRef) childSAMLObject);
        else
            super.processChildElement(parentSAMLObject, childSAMLObject);
    }
    
    

}
