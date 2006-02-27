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

import org.opensaml.saml2.core.Issuer;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.Issuer}
 */
public class IssuerImpl extends NameIDImpl implements Issuer {

    /**Issuer of the assertion*/
    private String issuer;
    
    /** Constructor */
    protected IssuerImpl() {
        super(Issuer.LOCAL_NAME);
    }
    
    /*
     * @see org.opensaml.saml2.core.Issuer#getIssuer()
     */
    public String getIssuer() {
        return issuer;
    }
    
    /*
     * @see org.opensaml.saml2.core.Issuer#setIssuer(java.lang.String)
     */
    public void setIssuer(String newIssuer) {
        this.issuer = prepareForAssignment(this.issuer, newIssuer);
    }
}