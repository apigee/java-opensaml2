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

package org.opensaml.common.binding.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.common.binding.SOAPHTTPDecoder;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Base class for SOAP over HTTP message encoders.
 * 
 */
public abstract class AbstractSOAPHTTPDecoder
    extends AbstractHTTPMessageDecoder implements SOAPHTTPDecoder {
    
    /** Class logger. */
    private static Logger log = Logger.getLogger(AbstractSOAPHTTPDecoder.class);
    
    /** Decoded SOAP message. */
    private Envelope soapMessage;
    
    /** Version of SOAP being used. */
    private String soapVersion = "1.1";
    
    /** {@inheritDoc} */
    public Envelope getSOAPMessage(){
        return soapMessage;
    }
    
    /**
     * Sets the decoded SOAP message.
     * 
     * @param message decoded SOAP message
     */
    protected void setSOAPMessage(Envelope message){
        soapMessage = message;
    }

    /** {@inheritDoc} */
    public List<XMLObject> getSOAPHeaders() {
        if(soapMessage != null){
            if(soapMessage.getHeader() != null){
                return soapMessage.getHeader().getUnknownXMLObjects();
            }
        }
        
        return null;
    }

    /** {@inheritDoc} */
    public String getSOAPVersion() {
        return soapVersion;
    }
    
    /**
     * Sets the SOAP version used in the message.
     * 
     * @param version SOAP version used in the message
     */
    protected void setSOAPVersion(String version) {
        if(DatatypeHelper.isEmpty(version)){
            soapVersion = "1.1";
        }else{
            soapVersion = version;
        }
    }
}