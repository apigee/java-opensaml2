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

import org.opensaml.xacml.ctx.ResultType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

/** Builder for {@link ResultType} objects. */
public class ResultTypeImplBuilder extends AbstractXACMLObjectBuilder<ResultType> {

    /** Constructor. */
    public ResultTypeImplBuilder() {

    }

    /** {@inheritDoc} */
    public ResultType buildObject() {
        return buildObject(ResultType.DEFAULT_ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public ResultType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResultTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}