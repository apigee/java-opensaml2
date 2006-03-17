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

package org.opensaml.saml2.metadata.validator;

import org.apache.log4j.Logger;
import org.opensaml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml2.metadata.AdditionalMetadataLocation} for Spec compliance.
 */
public class AdditionalMetadataLocationSpecValidator implements Validator {

    /**
     * Logger.
     */
    private static Logger log = Logger.getLogger(AdditionalMetadataLocationSpecValidator.class);
    
    /** Constructor */
    public AdditionalMetadataLocationSpecValidator() {

    }

    /*
     * @see org.opensaml.xml.validation.Validator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
        AdditionalMetadataLocation aml = (AdditionalMetadataLocation) xmlObject;
        validateNamespace(aml);
    }

    /**
     * Checks that Namespace is correct.
     * 
     * @param aml
     * @throws ValidationException
     */
    protected void validateNamespace(AdditionalMetadataLocation aml) throws ValidationException {
        if (aml.getDOM() != null) {
            if (!aml.getDOM().getOwnerDocument().getNamespaceURI().equals(aml.getNamespaceURI())) {
                throw new ValidationException("Namespace must match that of root element of instance.");
            }
        } else if(log.isDebugEnabled()) {
            log.debug("Cannot validate Namespace as DOM is null.");
        }
    }
}