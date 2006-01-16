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

import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.NameIdentifier;

/**
 * Complete implementation of {@link org.opensaml.saml1.core.impl.NameIdentifierImpl}
 */

public class NameIdentifierImpl extends AbstractSAMLObject implements NameIdentifier {

    /** Contents of the NameQualifierAttribute */
    String nameQualifier;
    
    /** Contents of the Format */
    String format;
    
    /** Contents of the elemen body */
    String nameIdentifier;
    
    /**
     * Constructor
     */
    public NameIdentifierImpl() {
        super(SAMLConstants.SAML1_NS, NameIdentifier.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.saml1.core.NameIdentifier#getNameQualifier()
     */
    public String getNameQualifier() {
        return nameQualifier;
    }

    /*
     * @see org.opensaml.saml1.core.NameIdentifier#getFormat()
     */
    public String getFormat() {
        return this.format;
    }

    /*
     * @see org.opensaml.saml1.core.NameIdentifier#getNameIdentifier()
     */
    public String getNameIdentifier() {
        return nameIdentifier;
    }

    /*
     * @see org.opensaml.saml1.core.NameIdentifier#setNameQualifier(java.lang.String)
     */
    public void setNameQualifier(String nameQualifier) {
        this.nameQualifier = prepareForAssignment(this.nameQualifier, nameQualifier);
    }

    public void setFormat(String format) {
        this.format = prepareForAssignment(this.format, format);
    }

    public void setNameIdentifier(String nameIdentifier) {
        this.nameIdentifier = prepareForAssignment(this.nameIdentifier, nameIdentifier);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }

}
