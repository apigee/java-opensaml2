/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.common.binding.security;

import org.opensaml.common.SAMLObject;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.xml.XMLObject;

/**
 * Utility class for SAML security policy rule processing.
 */
public final class SAMLSecurityPolicyHelper {
    
    /** Constructor. */
    private SAMLSecurityPolicyHelper() {}
    
    /**
     * Helper method to obtain the SAML message object from the more generic XMLObject message object.
     * 
     * @param message XMLObject message presumed to contain a SAML message
     * @return the SAML message object, or null if no SAML message found
     */
    public static SAMLObject getSAMLMessage(XMLObject message) {

        if (message instanceof SAMLObject) {
            return (SAMLObject) message;
        }

        if (message instanceof Envelope) {
            Envelope env = (Envelope) message;

            XMLObject xmlObject = env.getBody().getUnknownXMLObjects().get(0);
            if (xmlObject instanceof SAMLObject) {
                return (SAMLObject) xmlObject;
            }
        }

        return null;
    }

}
