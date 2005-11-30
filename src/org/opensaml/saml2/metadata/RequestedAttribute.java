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

package org.opensaml.saml2.metadata;

import javax.xml.namespace.QName;

import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.saml2.core.Attribute;

/**
 * SAML 2.0 Metadata RequestedAttribute
 *
 */
public interface RequestedAttribute extends Attribute {

    /** Local name, no namespace */
    public final static String LOCAL_NAME = "RequestedAttribute";
    
    /** QName for element */
    public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /** "isRequired" attribute's local name */
    public final static String IS_REQUIRED_ATTRIB_NAME = "index";
    
    /** "isRequired" attribute's QName */
    public final static QName IS_REQUIRED_ATTRIB_QNAME = new QName(XMLConstants.SAML20MD_NS, IS_REQUIRED_ATTRIB_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /**
     * Checks to see if this requested attribute is also required.
     * 
     * @return true if this attribute is required
     */
    public boolean isRequired();
    
    /**
     * Sets if this requested attribute is also required.
     * 
     * @param isRequired true if this attribute is required
     */
    public void setIsRequired(boolean isRequired);
}
