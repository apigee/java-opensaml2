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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.BaseID;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.BaseID} objects.
 */
public class BaseIDUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public BaseIDUnmarshaller() {
        super(SAMLConstants.SAML20_NS, BaseID.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        BaseID baseID = (BaseID) samlObject;
        if (attributeName.equals(BaseID.NAME_QUALIFIER_ATTRIB_NAME)) {
            baseID.setNameQualifier(attributeValue);
        } else if (attributeName.equals(BaseID.SP_NAME_QUALIFIER_ATTRIB_NAME)) {
            baseID.setSPNameQualifier(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}