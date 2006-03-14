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

package org.opensaml.saml1.core.impl;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AssertionArtifact;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.Query;
import org.opensaml.saml1.core.Request;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread safe Unmarshaller for {@link org.opensaml.saml1.core.Request} objects.
 */
public class RequestUnmarshaller extends RequestAbstractTypeUnmarshaller {

    /**
     * Constructor
     */
    public RequestUnmarshaller() throws IllegalArgumentException {
        super(SAMLConstants.SAML1P_NS, Request.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject,
     *      org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentElement, XMLObject childElement) throws UnmarshallingException {
        Request request = (Request) parentElement;

        try {
            if (childElement instanceof Query) {
                request.setQuery((Query) childElement);
            } else if (childElement instanceof AssertionIDReference) {
                request.getAssertionIDReferences().add((AssertionIDReference) childElement);
            } else if (childElement instanceof AssertionArtifact) {
                request.getAssertionArtifacts().add((AssertionArtifact) childElement);
            } else {
                super.processChildElement(parentElement, childElement);
            }
        } catch (IllegalArgumentException e) {
            throw new UnmarshallingException(e);
        }
    }

  
}