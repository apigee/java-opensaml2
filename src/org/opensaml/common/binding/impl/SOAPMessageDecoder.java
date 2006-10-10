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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.HTTPMessageDecoder;
import org.opensaml.common.binding.MessageDecoder;
import org.opensaml.common.binding.SOAPException;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.security.TrustEngine;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Message decoder implementation for for SAML SOAP binding.
 * 
 * @author Walter Hoehn
 * 
 */
public class SOAPMessageDecoder implements MessageDecoder, HTTPMessageDecoder {

    /** SOAP 1.1 Envelope XML namespace */
    public final static String SOAP11ENV_NS = "http://schemas.xmlsoap.org/soap/envelope/";

    private static Logger log = Logger.getLogger(SOAPMessageDecoder.class.getName());

    private HttpServletRequest request;

    private SAMLObject message;

    private boolean decoded = false;

    private MetadataProvider metadataProvider;

    private TrustEngine trustEngine;

    private boolean authenticated = false;

    public SOAPMessageDecoder(HttpServletRequest request) {
        this.request = request;
    }

    /** {@inheritDoc} */
    public void setMetadataProvider(MetadataProvider metadataProvider) {
        this.metadataProvider = metadataProvider;
    }

    /** {@inheritDoc} */
    public void setTrustEngine(TrustEngine trustEngine) {
        this.trustEngine = trustEngine;
    }

    /** {@inheritDoc} */
    public void decode() throws BindingException, SOAPException {

        // TODO authentication
        // TODO SOAP/HTTP hooks?
        // TODO schema validation?

        // Must be POST/XML
        if (!request.getMethod().equals("POST") || !request.getContentType().startsWith("text/xml")) {
            log.error("SOAP Message Decoder found bad HTTP method or content type");
            throw new BindingException("SOAP Message Decoder found bad HTTP method or content type");
        }

        // Parse input stream
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        Document requestDoc = null;
        try {
            requestDoc = ppMgr.parse(request.getInputStream());
        } catch (XMLParserException e) {
            log.error("Error parsing XML from request stream: " + e);
            throw new BindingException("Error parsing XML from request stream.");
        } catch (IOException e) {
            log.error("Error reading from request stream: " + e);
            throw new BindingException("Error reading from request stream.");
        }
        Element root = requestDoc.getDocumentElement();

        // The root must be a SOAP 1.1 envelope.
        if (!XMLHelper.isElementNamed(root, SOAP11ENV_NS, "Envelope")) {
            log.error("Malformed SOAP request: envelope not found.");
            throw new SOAPException("Malformed SOAP request: envelope not found.");
        }

        Element child = XMLHelper.getFirstChildElement(root);
        if (XMLHelper.isElementNamed(child, SOAP11ENV_NS, "Header")) {

            /*
             * Walk the children. If we encounter any headers with mustUnderstand, we have to bail. The thinking here
             * is, we're not a "real" SOAP processor, but we have to emulate one that understands no headers. For now,
             * assume we're the recipient.
             */
            Element header = XMLHelper.getFirstChildElement(child);
            while (header != null) {
                if (header.getAttributeNS(SOAP11ENV_NS, "mustUnderstand").equals("1")) {
                    log.error("Unable to enforce SOAP header constraints: Could not "
                            + "understand header marked (mustUnderstand).");
                    throw new SOAPException("Unable to enforce SOAP header constraints.");
                }
                header = XMLHelper.getNextSiblingElement(header);
            }

            // Advance to the Body element.
            child = XMLHelper.getNextSiblingElement(child);
        }

        if (child != null) {
            Element samlElement = XMLHelper.getFirstChildElement(child);
            // Extract the SAML
            UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(samlElement);

            try {
                message = (SAMLObject) unmarshaller.unmarshall(samlElement);
            } catch (UnmarshallingException e) {
                log.error("Error decoding SAML message: " + e);
                throw new SOAPException("Error decoding SAML message.");
            }

        } else {
            log.error("Malformed SOAP request: body not found.");
            throw new SOAPException("Malformed SOAP request: body not found.");
        }

        decoded = true;
    }

    /** {@inheritDoc} */
    public RoleDescriptor getIssuerMetadata() {
        checkDecoded();
        // TODO implement
        return null;
    }

    /** {@inheritDoc} */
    public SAMLObject getSAMLMessage() {

        checkDecoded();
        return message;
    }

    /** {@inheritDoc} */
    public boolean issuerWasAuthenticated() {
        checkDecoded();
        return authenticated;
    }

    /** {@inheritDoc} */
    public String getRelayState() {
        checkDecoded();
        // TODO implement
        return null;
    }

    /**
     * Verifies that this decoder has successfully decoded a message.
     * 
     * @throws IllegalStateException if decoding has not been attempted or if decoding was unsuccessful
     * 
     */
    private void checkDecoded() {
        if (!decoded) {
            throw new IllegalStateException("Deocder has not been run.");
        }
    }

}
