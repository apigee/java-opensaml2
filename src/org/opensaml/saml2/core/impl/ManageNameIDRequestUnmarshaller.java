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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.EncryptedID;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NewEncryptedID;
import org.opensaml.saml2.core.NewID;
import org.opensaml.saml2.core.Terminate;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.ManageNameIDRequest}
 * objects.
 */
public class ManageNameIDRequestUnmarshaller extends RequestUnmarshaller {

    /**
     * Constructor
     *
     */
    public ManageNameIDRequestUnmarshaller() {
        super(SAMLConstants.SAML20P_NS, ManageNameIDRequest.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue) throws UnmarshallingException, UnknownAttributeException {
        // no attributes of our own
        super.processAttribute(samlObject, attributeName, attributeValue);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject) throws UnmarshallingException, UnknownElementException {
        ManageNameIDRequest req = (ManageNameIDRequest) parentSAMLObject;
        
        //TODO may change depending on Chad's encryption implementation
        
        //TODO make sure there aren't any other hidden issues here...
        //NOTE: Issuer is a sub-interface of NameID, so need this additional check
        if (childSAMLObject instanceof NameID && !(childSAMLObject instanceof Issuer))
            req.setNameID((NameID) childSAMLObject);
        else if (childSAMLObject instanceof EncryptedID)
            req.setEncryptedID((EncryptedID) childSAMLObject);
        else if (childSAMLObject instanceof NewID)
            req.setNewID((NewID) childSAMLObject);
        else if (childSAMLObject instanceof NewEncryptedID)
            req.setNewEncryptedID((NewEncryptedID) childSAMLObject);
        else if (childSAMLObject instanceof Terminate)
            req.setTerminate((Terminate) childSAMLObject);
        else
            super.processChildElement(parentSAMLObject, childSAMLObject);

        
    }

}
