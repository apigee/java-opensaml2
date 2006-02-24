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
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Identifier;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.NameIDMappingRequest} objects.
 */
public class NameIDMappingRequestUnmarshaller extends RequestUnmarshaller {

    /**
     * Constructor
     *
     */
    public NameIDMappingRequestUnmarshaller() {
        super(SAMLConstants.SAML20P_NS, NameIDMappingRequest.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject) throws UnmarshallingException, UnknownElementException {
        NameIDMappingRequest req = (NameIDMappingRequest) parentSAMLObject;
        
        // NOTE: Issuer in superclass is also an instance of Identifier, so have to be careful
        if (childSAMLObject instanceof Identifier && !(childSAMLObject instanceof Issuer))
            req.setIdentifier((Identifier) childSAMLObject);
        else if (childSAMLObject instanceof NameIDPolicy)
            req.setNameIDPolicy((NameIDPolicy) childSAMLObject);
        else
            super.processChildElement(parentSAMLObject, childSAMLObject);
    }

}
