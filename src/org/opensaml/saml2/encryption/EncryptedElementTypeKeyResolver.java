/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.encryption;

import java.security.KeyException;

import org.opensaml.saml2.core.EncryptedElementType;
import org.opensaml.xml.encryption.EncryptedData;
import org.opensaml.xml.encryption.EncryptedKey;
import org.opensaml.xml.encryption.EncryptedKeyInfoResolver;
import org.opensaml.xml.security.InlineX509KeyInfoResolver;

/**
 * EncryptedKey resolver which uses a SAML 2 {@link EncryptedElementType} as the resolution context.
 */
public class EncryptedElementTypeKeyResolver extends InlineX509KeyInfoResolver implements EncryptedKeyInfoResolver {
    
    /** SAML 2 EncryptedElementType context in which to resolve. */
    private EncryptedElementType encryptedElement;
    
    /** Intended EncryptedKey recipient. */
    private String recipient;
    
    // TODO this needs more work, pending the possible resolver API refactor

    /**
     * Constructor.
     * @param newEncryptedElement the SAML 2 EncryptedElementType to use as the resolution context
     * @param newRecipient the recipient attribute of the EncryptedKey which is to be resolved
     */
    public EncryptedElementTypeKeyResolver(EncryptedElementType newEncryptedElement, String newRecipient) {
        this.encryptedElement = newEncryptedElement;
        this.recipient = newRecipient;
    }

    /** {@inheritDoc} */
    public EncryptedKey resolveKey(EncryptedData encryptedData) throws KeyException {
        if (encryptedElement.getEncryptedKeys().size() == 1) {
            return encryptedElement.getEncryptedKeys().get(0);
        }
        
        for (EncryptedKey encKey: encryptedElement.getEncryptedKeys()) {
           if (encKey.getRecipient().equals(recipient)) {
               return encKey;
           }
        }
        return null;
    }

}
