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

package org.opensaml.common.binding;

import java.util.List;

import javax.servlet.ServletResponse;

import org.opensaml.soap.soap11.Envelope;
import org.opensaml.xml.XMLObject;

/**
 * Base interface for SOAP specific SAML bindings.
 */
public interface SOAPEncoder<ResponseType extends ServletResponse> extends MessageEncoder<ResponseType> {

    /**
     * Gets the SOAP version to use.
     * 
     * @return the SOAP version to use
     */
    public String getSOAPVersion();
    
    /**
     * Sets the SOAP version to use.  If not explicity set version 1.1 is assumed.
     * 
     * @param version the SOAP version, may not be null
     */
    public void setSOAPVersion(String version);
    
    /**
     * Gets a mutable list of SOAP headers to add to the message.
     * 
     * @return SOAP headers to add to the message
     */
    public List<XMLObject> getSOAPHeaders();
    
    /**
     * Gets the SOAP message built by the encoder.
     * 
     * @return SOAP message built by the encoder
     */
    public Envelope getSOAPMessage();
}