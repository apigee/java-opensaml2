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

import org.bouncycastle.util.encoders.Base64;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.impl.AbstractHTTPMessageEncoder;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 * SAML 2.0 HTTP Redirect encoder using the DEFLATE encoding method.
 */
public class HTTPRedirectDeflateEncoder extends AbstractHTTPMessageEncoder {

    private boolean signMessage = false;

    /** {@inheritDoc} */
    public void encode() throws BindingException {
        SignableSAMLObject message = (SignableSAMLObject) getSAMLMessage();
        removeSignature(message);
        byte[] deflatedMessage = compressMessage(message);
        byte[] encodedMessage = base64EncodeMessage(deflatedMessage);

    }

    /**
     * Removes the signature from the protocol message.
     * 
     * @param message the protocol message
     */
    protected void removeSignature(SignableSAMLObject message) {
        if (message.getSignature() != null) {
            signMessage = true;
            message.setSignature(null);
        }
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
    protected byte[] compressMessage(SAMLObject message) throws BindingException {
        try {
            Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(message);
            Element messageDOM = marshaller.marshall(message);
            String messageStr = XMLHelper.nodeToString(messageDOM);

            ByteArrayOutputStream messageOut = new ByteArrayOutputStream();
            Deflater deflater = new Deflater(Deflater.DEFLATED, true);
            DeflaterOutputStream deflaterStream = new DeflaterOutputStream(messageOut, deflater);
            deflaterStream.write(messageStr.getBytes());
            deflaterStream.close();
            return messageOut.toByteArray();
        } catch (MarshallingException e) {
            throw new BindingException("Unable to marshall SAML message", e);
        } catch (IOException e) {
            throw new BindingException("Error DEFLATE compressing SAML message", e);
        }
    }

    /**
     * Base64 encodes the given message.
     * 
     * @param message message to encode
     * 
     * @return Base64 encoded message
     */
    protected byte[] base64EncodeMessage(byte[] message) {
        return Base64.encode(message);
    }
}