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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AssertionIDReference;

/**
 *  A thread-safe {@link org.opensaml.xml.io.Unmarshaller} for {@link org.opensaml.saml1.core.AssertionIDReference} Objects
 */
public class AssertionIDReferenceUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AssertionIDReferenceUnmarshaller() {
        super(SAMLConstants.SAML1_NS, AssertionIDReference.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#unmarshallElementContent(org.opensaml.common.SAMLObject,
     *      java.lang.String)
     */
    protected void unmarshallElementContent(SAMLObject samlObject, String elementContent) {
        AssertionIDReference assertionIDReference = (AssertionIDReference) samlObject;

        assertionIDReference.setNCName(elementContent);
    }
}