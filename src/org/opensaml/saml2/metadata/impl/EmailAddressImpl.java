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

package org.opensaml.saml2.metadata.impl;

import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EmailAddress;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.EmailAddress}
 */
public class EmailAddressImpl extends AbstractSAMLObject implements EmailAddress {

    /** The email address */
    private String address;

    /**
     * Constructor
     */
    public EmailAddressImpl() {
        super(SAMLConstants.SAML20MD_NS, EmailAddress.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml2.metadata.EmailAddress#getAddress()
     */
    public String getAddress() {
        return address;
    }

    /*
     * @see org.opensaml.saml2.metadata.EmailAddress#setAddress(java.lang.String)
     */
    public void setAddress(String address) {
        this.address = prepareForAssignment(this.address, address);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }
}