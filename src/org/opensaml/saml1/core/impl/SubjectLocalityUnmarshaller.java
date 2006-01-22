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
package org.opensaml.saml1.core.impl;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.SubjectLocality;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.xml.io.Unmarshaller} for {@link org.opensaml.saml1.core.SubjectLocality} objects.
 */
public class SubjectLocalityUnmarshaller extends AbstractSAMLObjectUnmarshaller {
    
    /**
     * Constructor
     */
    public SubjectLocalityUnmarshaller() {
        super(SAMLConstants.SAML1_NS, SubjectLocality.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        SubjectLocality subjectLocality = (SubjectLocality) samlObject;
        
        if (SubjectLocality.DNSADDRESS_ATTRIB_NAME.equals(attributeName)) {
            subjectLocality.setDNSAddress(attributeValue);
        } else if (SubjectLocality.IPADDRESS_ATTRIB_NAME.equals(attributeName)) {
            subjectLocality.setIPAddress(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}