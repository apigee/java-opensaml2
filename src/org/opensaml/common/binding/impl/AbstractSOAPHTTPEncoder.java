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

import javolution.util.FastList;

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.SOAPHTTPEncoder;
import org.opensaml.ws.soap.common.SOAPObjectBuilder;
import org.opensaml.ws.soap.soap11.Body;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.ws.soap.soap11.Header;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Base class for SOAP over HTTP message encoders.
 */
abstract public class AbstractSOAPHTTPEncoder extends AbstractHTTPMessageEncoder implements SOAPHTTPEncoder {
    
    /** Class logger */
    private final static Logger log = Logger.getLogger(AbstractSOAPHTTPEncoder.class);

    /** SOAP message built by the encoder */
    private Envelope soapMessage;
    
    /** SOAP headers to add to the message */
    private FastList<XMLObject> soapHeaders = new FastList<XMLObject>();
    
    /** Version of SOAP being used */
    private String soapVersion = "1.1";
    
    /** {@inheritDoc} */
    public Envelope getSOAPMessage(){
        return soapMessage;
    }
    
    /**
     * Sets the SOAP message built by this encoder.
     * 
     * @param message SOAP message built by this encoder
     */
    protected void setSOAPMessage(Envelope message){
        soapMessage = message;
    }
    
    /** {@inheritDoc} */
    public List<XMLObject> getSOAPHeaders() {
        return soapHeaders;
    }

    /** {@inheritDoc} */
    public String getSOAPVersion() {
        return soapVersion;
    }

    /** {@inheritDoc} */
    public void setSOAPVersion(String version) {
        if(DatatypeHelper.isEmpty(version)){
            soapVersion = "1.1";
        }else{
            soapVersion = version;
        }
    }
    
    /**
     * Builds the SOAP message to be encoded.  The the headers and SAML message set on the encoder will 
     * be populated in the returned SOAP message.
     * 
     * @return the SOAP message
     * 
     * @throws BindingException thrown if no SAML message has been set on the encoder
     */
    protected Envelope buildSOAPMessage() throws BindingException{
        if(log.isDebugEnabled()){
            log.debug("Building SOAP message");
        }
        XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
        
        SOAPObjectBuilder<Envelope> envBuilder = (SOAPObjectBuilder<Envelope>) builderFactory.getBuilder(Envelope.DEFAULT_ELEMENT_NAME);
        Envelope envelope = envBuilder.buildObject();
        
        List<XMLObject> headers = getSOAPHeaders();
        if(headers != null && headers.size() > 0){
            if(log.isDebugEnabled()){
                log.debug("Adding " + headers.size() + " headers to the SOAP message");
            }
            SOAPObjectBuilder<Header> headerBuilder = (SOAPObjectBuilder<Header>) builderFactory.getBuilder(Header.DEFAULT_ELEMENT_NAME);
            Header header = headerBuilder.buildObject();
            header.getUnknownXMLObjects().addAll(headers);
            envelope.setHeader(header);
        }

        if(getSAMLMessage() == null){
            log.error("No SAML message was set, unable to build SOAP body");
            throw new BindingException("No SAML message was set, unable to build SOAP body");
        }
        
        if(log.isDebugEnabled()){
            log.debug("Adding SAML message to the SOAP message's body");
        }
        SOAPObjectBuilder<Body> bodyBuilder = (SOAPObjectBuilder<Body>) builderFactory.getBuilder(Body.DEFAULT_ELEMENT_NAME);
        Body body = bodyBuilder.buildObject();
        body.getUnknownXMLObjects().add(getSAMLMessage());
        envelope.setBody(body);
        
        return envelope;
    }
}