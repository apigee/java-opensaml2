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

package org.opensaml.saml1.core.impl;

import java.util.List;

import org.opensaml.common.SAMLVersion;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.xml.XMLObject;

/**
 * Concrete Implementation of {@link org.opensaml.saml1.core.AssertionIDReference} Object
 */
public class AssertionIDReferenceImpl extends AbstractAssertionSAMLObject implements AssertionIDReference {

    /** String to contain the NCName */
    private String NCName;

    /**
     * Hidden Constructor
     * @deprecated 
     */
    private AssertionIDReferenceImpl() {
        super(AssertionIDReference.LOCAL_NAME, null);
    }

    /**
     * Constructor
     *
     * @param version which SAMLVersion to write
     */
    protected AssertionIDReferenceImpl(SAMLVersion version) {
        super(AssertionIDReference.LOCAL_NAME, version);
    }

    /*
     * @see org.opensaml.saml1.core.AssertionIDReference#getNCName()
     */
    public String getNCName() {
        return NCName;
    }

    /*
     * @see org.opensaml.saml1.core.AssertionIDReference#setNCName(java.lang.String)
     */
    public void setNCName(String NCName) {
        this.NCName = prepareForAssignment(this.NCName, NCName);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}