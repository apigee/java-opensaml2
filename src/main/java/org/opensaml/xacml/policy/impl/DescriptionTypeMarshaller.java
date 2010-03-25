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

import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 *Marshaller for {@link org.opensaml.xacml.policy.DescriptionType}.
 */
public class DescriptionTypeMarshaller extends AbstractXACMLObjectMarshaller {

    /** Constructor. */
    public DescriptionTypeMarshaller() {
        super();
    }
    
    /** {@inheritDoc} */
    protected void marshallElementContent(XMLObject xmlobject, Element domElement) throws MarshallingException {
        DescriptionType message = (DescriptionType) xmlobject;

        if (message.getValue() != null) {
            XMLHelper.appendTextContent(domElement, message.getValue());
        }
    }

}
