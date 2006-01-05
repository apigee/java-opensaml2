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
import org.opensaml.saml2.metadata.AttributeProfile;

/**
 * A concrete implementation of {@link org.opensaml.saml2.metadata.AttributeProfile}
 */
public class AttributeProfileImpl extends AbstractSAMLObject implements AttributeProfile {

    /** Profile URI */
    private String profileURI;

    /**
     * Constructor
     */
    public AttributeProfileImpl() {
        super(SAMLConstants.SAML20MD_NS, AttributeProfile.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfile#getProfileURI()
     */
    public String getProfileURI() {
        return profileURI;
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfile#setProfileURI(java.lang.String)
     */
    public void setProfileURI(String profileURI) {
        this.profileURI = prepareForAssignment(this.profileURI, profileURI);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null; // No Children
    }
}