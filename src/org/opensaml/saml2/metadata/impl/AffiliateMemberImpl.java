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

import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AffiliateMember;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.AffiliateMember}.
 */
public class AffiliateMemberImpl extends AbstractSAMLObject implements AffiliateMember {

    /** ID of this member */
    private String id;

    /**
     * Constructor
     */
    public AffiliateMemberImpl() {
        super(SAMLConstants.SAML20MD_NS, AffiliateMember.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliateMember#getID()
     */
    public String getID() {
        return id;
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliateMember#setID(java.lang.String)
     */
    public void setID(String newID) throws IllegalArgumentException {
        if (newID.length() > 1024) {
            throw new IllegalArgumentException("Member ID can not exceed 1024 characters in length");
        }

        id = prepareForAssignment(id, newID);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        // No children
        return null;
    }
}