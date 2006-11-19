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

package org.opensaml.saml2.binding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.impl.AbstractHTTPMessageEncoder;
import org.opensaml.xml.util.Base64;

/**
 * SAML 2.0 HTTP Redirect encoder using the DEFLATE encoding method.
 */
public class HTTPRedirectDeflateEncoder extends AbstractHTTPMessageEncoder {
    
    /** URL to send message to via HTTP redirect */
    private String redirectURL;

    /** Whether to sign URL params */
    private boolean signMessage = false;
    
    /**
     * Gets whether the message was signed per the redirect binding specification.
     * 
     * @return whether the message was signed per the redirect binding specification
     */
    public boolean isSigned(){
        return signMessage;
    }

    /** {@inheritDoc} */
    public void encode() throws BindingException {
        SignableSAMLObject message = (SignableSAMLObject) getSAMLMessage();
        removeSignature(message);
        byte[] encodedMessage = defalteAndBase64Encode(message);;

    }

    /**
     * Removes the signature from the protocol message.
     * 
     * @param message the protocol message
     */
    protected void removeSignature(SignableSAMLObject message) {
        //TODO Remove signature
    }

    /**
     * DEFLATE (RFC1951) compresses the given SAML message.
     * 
     * @param message SAML message
     * 
     * @return DEFLATE compressed message
     * 
     * @throws BindingException thrown if there is a problem compressing the message
     */
    protected byte[] defalteAndBase64Encode(SAMLObject message) throws BindingException {
        try {
            String messageStr = marshallMessage(message);

            ByteArrayOutputStream messageOut = new ByteArrayOutputStream();
            Base64.OutputStream b64Out = new Base64.OutputStream(messageOut);
            Deflater deflater = new Deflater(Deflater.DEFLATED, true);
            DeflaterOutputStream deflaterStream = new DeflaterOutputStream(b64Out, deflater);
            deflaterStream.write(messageStr.getBytes());
            deflaterStream.close();
            return messageOut.toByteArray();
        } catch (IOException e) {
            throw new BindingException("Unable to DEFLATE and Base64 encode SAML message", e);
        }
    }
}