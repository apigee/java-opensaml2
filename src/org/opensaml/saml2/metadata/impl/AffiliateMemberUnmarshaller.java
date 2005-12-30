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

package org.opensaml.saml2.metadata.impl;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectUnmarshaller;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AffiliateMember;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.AffiliateMember}s.
 */
public class AffiliateMemberUnmarshaller extends AbstractSAMLObjectUnmarshaller implements SAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AffiliateMemberUnmarshaller() {
        super(SAMLConstants.SAML20MD_NS, AffiliateMember.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {
        // DO NOTHING; no child elements
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        // DO NOTHING; no attributes
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#unmarshallElementContent(org.opensaml.common.SAMLObject,
     *      java.lang.String)
     */
    protected void unmarshallElementContent(SAMLObject samlElement, String elementContent) {
        super.unmarshallElementContent(samlElement, elementContent);

        AffiliateMember member = (AffiliateMember) samlElement;
        member.setID(elementContent);
    }
}
