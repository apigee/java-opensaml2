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

import java.util.Map;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.xml.XMLObject;
import org.w3c.dom.Element;

/** Class whose sole job is the creation of a {@link AssertionImpl} object */

public class AssertionBuilder extends SAMLObjectBuilder {

    /**
     * Constructor
     */
    public AssertionBuilder() {

    }

    /*
     * @see org.opensaml.xml.XMLObjectBuilder#buildObject()
     */
    public SAMLObject buildObject() {
        return new AssertionImpl(getVersion(null));
    }

    /*
     * @see org.opensaml.xml.ExtendedXMLObjectBuilder#buildObject(org.w3c.dom.Element, java.util.Map)
     */
    public XMLObject buildObject(Element domElement, Map<String, Object> context) {

        return new AssertionImpl(getVersion(domElement, context, Assertion.MINORVERSION_ATTRIB_NAME));
    }
}