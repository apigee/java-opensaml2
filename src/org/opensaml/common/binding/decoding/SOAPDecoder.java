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

package org.opensaml.common.binding.decoding;

import java.util.List;

import javax.servlet.ServletRequest;

import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.xml.XMLObject;

/**
 * Base interface for SAML SOAP bindings.
 * 
 * @param <RequestType> type of incoming protocol request
 */
public interface SOAPDecoder<RequestType extends ServletRequest> 
        extends MessageDecoder<RequestType> {

    /**
     * Gets the SOAP version to use.
     * 
     * @return the SOAP version to use
     */
    public String getSOAPVersion();
    
    /**
     * Gets the SOAP message.
     * 
     * @return SOAP message
     */
    public Envelope getSOAPMessage();
    
    /**
     * Gets the SOAP headers to add to the message.
     * 
     * @return SOAP headers to add to the message
     */
    public List<XMLObject> getSOAPHeaders();
}