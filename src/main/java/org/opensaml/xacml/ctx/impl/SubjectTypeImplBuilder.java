/*
Copyright 2008 Members of the EGEE Collaboration.
Copyright 2008 University Corporation for Advanced Internet Development,
Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

/** Builder for {@link SubjectType} objects. */
public class SubjectTypeImplBuilder extends AbstractXACMLObjectBuilder<SubjectType> {

    /** Constructor. */
    public SubjectTypeImplBuilder() {

    }

    /** {@inheritDoc} */
    public SubjectType buildObject() {
        return buildObject(SubjectType.DEFAULT_ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public SubjectType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}