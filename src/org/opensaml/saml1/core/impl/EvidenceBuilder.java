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

import org.opensaml.saml1.core.Evidence;
import org.opensaml.xml.XMLObject;
import org.w3c.dom.Element;

/**
 * A class whose sole purpose is to create a {@link org.opensaml.saml1.core.impl.EvidenceImpl} Object
 */
public class EvidenceBuilder  extends AbstractSAMLObjectBuilder {

    /**
     * Constructor
     */
    public EvidenceBuilder() {

    }

    /*
     * @see org.opensaml.xml.XMLObjectBuilder#buildObject()
     */
    public Evidence buildObject() {
        return new EvidenceImpl(null);
    }

    /*
     * @see org.opensaml.xml.ExtendedXMLObjectBuilder#buildObject(org.w3c.dom.Element, java.util.Map)
     */
    public XMLObject buildObject(Element domElement, Map<String, Object> context) {
        return new EvidenceImpl(getVersion(context));    
    }
}