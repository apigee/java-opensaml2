/*
 * Copyright 2008 Members of the EGEE Collaboration.
 * Copyright 2008 University Corporation for Advanced Internet Development, Inc.
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

package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.XACMLObjectBuilder;
import org.opensaml.xacml.policy.ResourceMatchType;
import org.opensaml.xml.AbstractXMLObjectBuilder;

/** Builder of {@link ResourceMatchType} objects. */
public class ResourceMatchTypeImplBuilder extends AbstractXMLObjectBuilder<ResourceMatchType> implements
        XACMLObjectBuilder<ResourceMatchType> {

    /** {@inheritDoc} */
    public ResourceMatchType buildObject() {
        return buildObject(ResourceMatchType.DEFAULT_ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public ResourceMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResourceMatchTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}