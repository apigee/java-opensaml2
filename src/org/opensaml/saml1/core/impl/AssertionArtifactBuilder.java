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

import org.opensaml.xml.XMLObject;
import org.w3c.dom.Element;

/**
 * A class which exists to create {@link org.opensaml.saml1.core.impl.AssertionArtifactImpl} objects
 */
public class AssertionArtifactBuilder extends AbstractSAMLObjectBuilder {

    /**
     * Constructor
     */
    public AssertionArtifactBuilder() {

    }

    /*
     * @see org.opensaml.xml.XMLObjectBuilder#buildObject()
     */
    public XMLObject buildObject() {
        return new AssertionArtifactImpl(null);
    }

    public XMLObject buildObject(Element domElement, Map<String, Object> context) {
        return new AssertionArtifactImpl(getVersion(context));
    }
}