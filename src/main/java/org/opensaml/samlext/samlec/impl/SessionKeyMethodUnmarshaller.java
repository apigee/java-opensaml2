/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.samlext.samlec.impl;

import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.samlext.samlec.EncType;
import org.opensaml.samlext.samlec.SessionKeyMethod;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe unmarshaller for {@link SessionKeyMethod} objects.
 */
public class SessionKeyMethodUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** {@inheritDoc} */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        SessionKeyMethod key = (SessionKeyMethod) samlObject;

        if (attribute.getLocalName().equals(SessionKeyMethod.ALGORITHM_ATTRIB_NAME)) {
            key.setAlgorithm(attribute.getValue());
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childObject)
            throws UnmarshallingException {
        SessionKeyMethod key = (SessionKeyMethod) parentSAMLObject;

        if (childObject instanceof EncType) {
            key.getEncTypes().add((EncType) childObject);
        } else if (!childObject.getElementQName().getNamespaceURI().equals(SAMLConstants.SAMLEC_GSS_NS)) {
            key.getUnknownXMLObjects().add(childObject);
        } else {
            super.processChildElement(parentSAMLObject, childObject);
        }
    }
}