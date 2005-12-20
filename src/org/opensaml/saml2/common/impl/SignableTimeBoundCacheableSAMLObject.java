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

package org.opensaml.saml2.common.impl;

import org.opensaml.common.SignableObject;
import org.opensaml.common.SigningContext;
import org.opensaml.common.util.xml.DigitalSignatureHelper;

/**
 * Abstract SAML object handling Signing, validUntil, and cacheDuration functions
 */
public abstract class SignableTimeBoundCacheableSAMLObject extends TimeBoundCacheableSAMLObject
        implements SignableObject {
    
    /**
     * Helper for dealing with SignableElement infterface methods
     */
    private SigningContext signingContext;
    
    /**
     * 
     * Constructor
     *
     */
    public SignableTimeBoundCacheableSAMLObject(){
        super();
    }

    /*
     * @see org.opensaml.common.SignableObject#getIdAttributeName()
     */
    public String getIdAttributeName() {
        if(signingContext == null) {
            return null;
        }
        
        return signingContext.getIdAttributeName();
    }
    
    /*
     * @see org.opensaml.common.SignableObject#getIdAttributeValue()
     */
    public String getIdAttributeValue() {
        if(signingContext == null) {
            return null;
        }
        
        return signingContext.getIdAttributeValue();
    }

    /*
     * @see org.opensaml.common.SignableElement#isSigned()
     */
    public boolean isSigned() {
        return DigitalSignatureHelper.isSigned(this);
    }

    /*
     * @see org.opensaml.common.SignableElement#removeSignature()
     */
    public void removeSignature() {
        DigitalSignatureHelper.removeSignature(this);
    }
    
    /*
     * @see org.opensaml.common.SignableElement#getSigningContext()
     */
    public SigningContext getSigningContext(){
        return signingContext;
    }
    
    /*
     * @see org.opensaml.common.SignableElement#setSigningContext(SigningContext)
     */
    public void setSigningContext(SigningContext signingContext){
        this.signingContext = signingContext;
    }
}
