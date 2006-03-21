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

import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.xml.XMLObjectBuilder;
import org.w3c.dom.Element;

/**
 * Builder for {@link org.opensaml.saml2.core.impl.AttributeStatementImpl} objects.
 */
public class AttributeStatementBuilder implements XMLObjectBuilder {

    /** Constructor */
    public AttributeStatementBuilder() {

    }

    /*
     * @see org.opensaml.common.XMLObjectBuilder#buildObject()
     */
    public AttributeStatement buildObject() {
        return new AttributeStatementImpl();
    }

    /*
     * @see org.opensaml.xml.XMLObjectBuilder#buildObject(org.w3c.dom.Element)
     */
    public AttributeStatement buildObject(Element element) {
        return new AttributeStatementImpl();
    }
}