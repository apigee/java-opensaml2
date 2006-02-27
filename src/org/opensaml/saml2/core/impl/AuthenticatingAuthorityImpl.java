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

import java.util.List;

import org.opensaml.saml2.core.AuthenticatingAuthority;
import org.opensaml.xml.XMLObject;

/**
 * A concrete implemenation of {@link org.opensaml.saml2.core.AuthenticatingAuthority}.
 */
public class AuthenticatingAuthorityImpl extends AbstractAssertionSAMLObject implements AuthenticatingAuthority {

    /** URI of the Authenticating Authority */
    private String uri;

    /** Constructor */
    protected AuthenticatingAuthorityImpl() {
        super(AuthenticatingAuthority.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml2.core.AuthenticatingAuthority#getURI()
     */
    public String getURI() {
        return uri;
    }

    /*
     * @see org.opensaml.saml2.core.AuthenticatingAuthority#setURI(java.lang.String)
     */
    public void setURI(String newURI) {
        this.uri = prepareForAssignment(this.uri, newURI);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}