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

package org.opensaml.common.impl;

import java.util.List;

import javax.security.cert.X509Certificate;

import org.opensaml.common.SignableObject;
import org.opensaml.common.SigningContext;

/**
 * Abstract SAML object handling Signing fucntions
 */
public abstract class AbstractSignableSAMLObject extends AbstractSAMLObject implements SignableObject {
    
    /**
     * Helper for dealing with SignableElement infterface methods
     */
    private SignableSAMLObjectHelper signingHelper;
    
    /**
     * 
     * Constructor
     *
     */
    public AbstractSignableSAMLObject(){
        super();
        signingHelper = new SignableSAMLObjectHelper();
    }

    /*
     * @see org.opensaml.common.SignableElement#getId()
     */
    public String getId() {
        return SignableSAMLObjectHelper.getId(this);
    }

    /*
     * @see org.opensaml.common.SignableElement#isSigned()
     */
    public boolean isSigned() {
        return SignableSAMLObjectHelper.isSigned(this);
    }

    /*
     * @see org.opensaml.common.SignableElement#getDigestAlgorithm()
     */
    public String getDigestAlgorithm() {
        return SignableSAMLObjectHelper.getDigestAlgorithm(this);
    }

    /*
     * @see org.opensaml.common.SignableElement#getSignatureAlgorithm()
     */
    public String getSignatureAlgorithm() {
        return SignableSAMLObjectHelper.getSignatureAlgorithm(this);
    }

    /*
     * @see org.opensaml.common.SignableElement#getX509Certificates()
     */
    public List<X509Certificate> getX509Certificates() {
        return SignableSAMLObjectHelper.getX509Certificates(this);
    }

    /*
     * @see org.opensaml.common.SignableElement#removeSignature()
     */
    public void removeSignature() {
        SignableSAMLObjectHelper.removeSignature(this);
    }
    
    /*
     * @see org.opensaml.common.SignableElement#getSigningContext()
     */
    public SigningContext getSigningContext(){
        return signingHelper.getSigningContext();
    }
    
    /*
     * @see org.opensaml.common.SignableElement#setSigningContext(SigningContext)
     */
    public void setSigningContext(SigningContext signingContext){
        signingHelper.setSigningContext(signingContext);
    }
}
