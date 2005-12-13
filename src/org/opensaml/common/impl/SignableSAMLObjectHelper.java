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

import org.opensaml.common.SignableObject;
import org.opensaml.common.SigningContext;
import org.opensaml.common.util.xml.DigitalSignatureHelper;
import org.w3c.dom.Element;

/**
 * A helper for SAMLElements that implement the {@link org.opensaml.common.SignableObject} interface. This helper is
 * thread safe.
 * 
 * @see org.opensaml.common.util.xml.XMLHelper#verifyDigitalSignature(Element)
 */
public class SignableSAMLObjectHelper {

    //TODO this has become useless now that everything here has either moved to SingingContext or DigitalSignatureHelper
    
    /** SAMLOBject this helper operates on */
    private SignableObject samlObject;

    /** Signature info */
    private SigningContext dsigCtx;

    /**
     * Constructor
     *
     * @param samlObject SAMLOBject this helper operates on
     */
    public SignableSAMLObjectHelper(SignableObject samlObject) {
        this.samlObject = samlObject;
    }

    /**
     * Gets the signing context containing information about the SAMLObject's signature.
     * 
     * @return context containing information about the SAMLObject's signature
     */
    public SigningContext getSigningContext() {
        return dsigCtx;
    }

    /**
     * Sets the signing context containing information about the SAMLObject's signature.
     * 
     * @param signingContext context containing information about the SAMLObject's signature
     */
    public void setSigningContext(SigningContext signingContext) {
        dsigCtx = signingContext;
    }

    /**
     * Gets the ID attribute of this helper's SAMLObject.
     * 
     * @return the ID attribute of this helper's SAMLObject
     */
    public String getId() {
        if(dsigCtx == null) {
            return null;
        }
        
        return dsigCtx.getId();
    }

    /**
     * Sets the ID attribute of this helper's SAMLObject.
     * 
     * @param id the ID attribute of this helper's SAMLObject
     */
    public void setId(String id) {
        if(dsigCtx == null) {
            dsigCtx = new SigningContext(id);
        }
    }

    /**
     * Checks if this helper's SAMLObject is signed.
     * 
     * @return true if the SAMLObject is signed, false if not
     */
    public boolean isSigned() {
        if(getSignatureElement() != null) {
            return true;
        }
        
        return false;
    }

    /**
     * Removes the Signature, element from this helper's SAMLObject.  The ID attribute is not 
     * touched and nothing occurs if the SAMLObject is not signed.
     */
    public void removeSignature() {
        Element signatureElement = getSignatureElement();
        if(signatureElement != null) {
            DOMCachingSAMLObject domCachingSAMLObject = (DOMCachingSAMLObject) samlObject;
            Element domElement = domCachingSAMLObject.getDOM();
            domElement.removeChild(signatureElement);
        }

    }
    
    /**
     * Checks if it's possible that this SAMLObject could be signed.  This is true if
     * the SAMLObject is DOM caching, i.e. an instance of {@link DOMCachingSAMLObject} and
     * {@link DOMCachingSAMLObject#getDOM()} does not return null.
     * 
     * @return true if this helper's SAMLObject could potentially be signed
     */
    private boolean isSignable() {
        if (!(samlObject instanceof DOMCachingSAMLObject)) {
            return false;
        }

        DOMCachingSAMLObject domCachingSAMLObject = (DOMCachingSAMLObject) samlObject;
        Element domElement = domCachingSAMLObject.getDOM();

        if (domElement == null) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Gets the Signature element for this helper's SAMLObject, or null if the SAMLObject 
     * does not cache a DOM representation of itself, the DOM representation is null, or 
     * it does not contain a Signature element.
     * 
     * @return the Signature element
     */
    private Element getSignatureElement() {
        if(!isSignable()) {
            return null;
        }
        
        DOMCachingSAMLObject domCachingSAMLObject = (DOMCachingSAMLObject) samlObject;
        Element domElement = domCachingSAMLObject.getDOM();
        return DigitalSignatureHelper.getSignatureElement(domElement);
    }
}