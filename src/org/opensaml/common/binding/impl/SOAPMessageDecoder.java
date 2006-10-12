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
import java.security.cert.CertificateParsingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERString;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.HTTPMessageDecoder;
import org.opensaml.common.binding.MessageDecoder;
import org.opensaml.common.binding.SOAPException;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.impl.HttpX509EntityCredential;
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

    private static final String CN_OID = "2.5.4.3";

    private static Logger log = Logger.getLogger(SOAPMessageDecoder.class.getName());

    private HttpServletRequest request;

    private SAMLObject message;

    private boolean decoded = false;

    private MetadataProvider metadataProvider;

    private TrustEngine trustEngine;

    private RoleDescriptor issuerMetadata;

    public SOAPMessageDecoder(HttpServletRequest request) {
        this.request = request;
    }

    public SOAPMessageDecoder(HttpServletRequest request, MetadataProvider metadataProvider, TrustEngine trustEngine) {
        this.request = request;
        this.metadataProvider = metadataProvider;
        this.trustEngine = trustEngine;
    }

    public SOAPMessageDecoder(HttpServletRequest request, MetadataProvider metadataProvider) {
        this.request = request;
        this.metadataProvider = metadataProvider;
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

        if (metadataProvider == null) {
            throw new IllegalStateException("Cannot decode message without access to a Metadata Provider.");
        }

        // TODO SOAP/HTTP hooks?
        // TODO schema validation?
        // TODO constrain roles

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

        // Optionally, attempt to authenticate the message
        if (trustEngine != null) {
            authenticate();
        }

        decoded = true;
    }

    /** {@inheritDoc} */
    public RoleDescriptor getIssuerMetadata() {
        checkDecoded();
        return issuerMetadata;
    }

    /** {@inheritDoc} */
    public SAMLObject getSAMLMessage() {

        checkDecoded();
        return message;
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

    private void authenticate() throws BindingException {

        // TODO role?
        // TODO protocol?
        QName foo = null;
        String bar = null;

        // Determine issuer and authenticate same

        // Try to authenticate based on the transport info, for now BASIC_AUTH and SSL

        // First, try basic auth
        authenticateViaBasicAuth();

        // If that didn't work, try to use SSL-supplied credentials
        if (issuerMetadata == null) {
            // SAMLv2
            if (isSAML2ProtocolMessage(message)) {
                authenticateSAML2SSL();

                // SAMLv1
            } else if (isSAML1ProtocolMessage(message)) {
                authenticateSAML1SSL();

            } else {
                log.error("Expected SAML protocol message.  Got (" + message.getElementQName().getLocalPart() + ").");
                throw new BindingException("Unable to comlete decoding.  Message Decoder "
                        + "requires SAML protocol message.");
            }
        }

        // Also, validate response/request signature if it exists

        // If we haven't done transport authentication and we have a SAML2 message, try to AuthN based on the signature
        if (issuerMetadata == null && isSAML2ProtocolMessage(message)) {
            String issuerName = getSAML2IssuerName();

            try {
                RoleDescriptor roleDescriptor = metadataProvider.getRole(issuerName, foo, bar);
                if (roleDescriptor == null) {
                    log.debug("Unable to authenticate message.");
                    log.info("No metadata found for provider (" + issuerName + ").");
                    return;
                }

                if (message instanceof SignableSAMLObject && ((SignableSAMLObject) message).isSigned()
                        && trustEngine.validate((SignableSAMLObject) message, roleDescriptor) != null) {
                    issuerMetadata = roleDescriptor;
                } else {
                    log.error("Authentication failed for provider (" + issuerName + ").");
                    throw new BindingException("Authentication failed.");
                }

            } catch (MetadataProviderException e) {
                log.error("Error performing metadata lookup: " + e);
                throw new BindingException("Unable to perform metadata lookup.");
            }

            // Can't to AuthN via protocol message signature with SAML1 messages
        } else if (issuerMetadata == null) {
            log.debug("Unable to authenticate message.");
            return;

            // OK, we have done authentication, but we may need to validate signatures on the protocol messages
        } else {
            if (message instanceof SignableSAMLObject && ((SignableSAMLObject) message).isSigned()) {
                if (trustEngine.validate((SignableSAMLObject) message, issuerMetadata) != null) {
                    log.debug("Protocol message validation successful.");
                } else {
                    log.error("Protocol message validation failed for provider (" + issuerMetadata.getID() + ").");
                    throw new BindingException("Message validation failed.");
                }
            }
        }
    }

    private void authenticateViaBasicAuth() throws BindingException {

        String issuerName;
        if (request.getRemoteUser() != null && !request.getRemoteUser().equals("")) {

            // If the message is SAML2, make sure the BASIC-AUTH user matches the issuer
            if (isSAML2ProtocolMessage(message) && !(request.getRemoteUser().equals(getSAML2IssuerName()))) {
                log.error("Authentication failed. Basic Auth provider (" + request.getRemoteUser()
                        + ") did not match SAML2 issuer (" + getSAML2IssuerName() + ".");
                throw new BindingException("Authentication failed.");
            }
            issuerName = request.getRemoteUser();

            // TODO role?
            // TODO protocol?
            QName foo = null;
            String bar = null;

            try {
                RoleDescriptor roleDescriptor = metadataProvider.getRole(issuerName, foo, bar);
                if (roleDescriptor == null) {
                    log.info("No metadata found for provider (" + issuerName + ").");
                    return;
                }

                issuerMetadata = roleDescriptor;
                log.info("SOAP message authenticated via transport (HTTP BASIC_AUTH).");

            } catch (MetadataProviderException e) {
                log.error("Error performing metadata lookup: " + e);
                throw new BindingException("Unable to perform metadata lookup.");
            }
        }
    }

    private static boolean isSAML2ProtocolMessage(SAMLObject message) {
        return (message instanceof org.opensaml.saml2.core.Request || message instanceof org.opensaml.saml2.core.Response);
    }

    private static boolean isSAML1ProtocolMessage(SAMLObject message) {
        return (message instanceof org.opensaml.saml1.core.Request || message instanceof org.opensaml.saml1.core.Response);
    }

    private void authenticateSAML1SSL() throws BindingException {

        HttpX509EntityCredential credential;
        try {
            credential = new HttpX509EntityCredential(request);
        } catch (IllegalArgumentException e) {
            log.info("SOAP message not accompanied by SSL transport credentials.");
            return;
        }

        // Try and authenticate the requester as any of the potentially relevant identifiers we know.
        POSSIBLE_ISSUERS: for (String issuerName : getSAML1IssuerNames(credential)) {

            // TODO role?
            // TODO protocol?
            QName foo = null;
            String bar = null;

            try {
                RoleDescriptor roleDescriptor = metadataProvider.getRole(issuerName, foo, bar);
                if (roleDescriptor == null) {
                    log.info("No metadata found for provider (" + issuerName + ").");
                    continue POSSIBLE_ISSUERS;
                }

                if (trustEngine.validate(credential, roleDescriptor)) {
                    issuerMetadata = roleDescriptor;
                    break POSSIBLE_ISSUERS;
                } else {
                    log.error("Authentication failed for provider (" + issuerName + ").");
                    throw new BindingException("Authentication failed.");
                }

            } catch (MetadataProviderException e) {
                log.error("Error performing metadata lookup: " + e);
                throw new BindingException("Unable to perform metadata lookup.");
            }

        }

    }

    private void authenticateSAML2SSL() throws BindingException {

        String issuerName = getSAML2IssuerName();

        // TODO handle no credential

        // TODO role?
        // TODO protocol?
        QName foo = null;
        String bar = null;

        try {
            RoleDescriptor roleDescriptor = metadataProvider.getRole(issuerName, foo, bar);
            if (roleDescriptor == null) {
                log.info("No metadata found for provider (" + issuerName + ").");
                return;
            }

            if (trustEngine.validate(new HttpX509EntityCredential(request), roleDescriptor)) {
                issuerMetadata = roleDescriptor;
            } else {
                log.error("Authentication failed for provider (" + issuerName + ").");
                throw new BindingException("Authentication failed.");
            }

        } catch (MetadataProviderException e) {
            log.error("Error performing metadata lookup: " + e);
            throw new BindingException("Unable to perform metadata lookup.");
        }
    }

    private String getSAML2IssuerName() throws BindingException {

        if (message instanceof org.opensaml.saml2.core.Request) {
            org.opensaml.saml2.core.Request request = (org.opensaml.saml2.core.Request) message;
            if (request.getIssuer() == null || request.getIssuer().getValue() == null
                    || request.getIssuer().getValue().equals("")) {
                log.error("Expected SAML2 Request to contain a valid Issuer.");
                throw new BindingException("Expected SAML2 Request to contain a valid Issuer.");
            }
            return request.getIssuer().getValue();

        } else if (message instanceof org.opensaml.saml2.core.Response) {
            org.opensaml.saml2.core.Response response = (org.opensaml.saml2.core.Response) message;
            if (response.getIssuer() == null || response.getIssuer().getValue() == null
                    || response.getIssuer().getValue().equals("")) {
                log.error("Expected SAML2 Response to contain a valid Issuer.");
                throw new BindingException("Expected SAML2 Response to contain a valid Issuer.");
            }
            return response.getIssuer().getValue();
        }
        throw new IllegalStateException(
                "Could not determine SAML2 issuer name from message.  Excpected SAML2 Request or Response.");
    }

    private List<String> getSAML1IssuerNames(HttpX509EntityCredential credential) throws BindingException {

        // TODO what about resource from a request?

        ArrayList<String> names = new ArrayList<String>();
        names.add(credential.getEntityCertificate().getSubjectX500Principal().getName(X500Principal.RFC2253));
        try {
            Collection altNames = credential.getEntityCertificate().getSubjectAlternativeNames();
            if (altNames != null) {
                for (Iterator nameIterator = altNames.iterator(); nameIterator.hasNext();) {
                    List<String> altName = (List<String>) nameIterator.next();
                    if (altName.get(0).equals(new Integer(2))) { // 2 is DNS
                        names.add(altName.get(1));
                    } else if (altName.get(0).equals(new Integer(6))) { // 6 is URI
                        names.add(altName.get(1));
                    }
                }
            }
        } catch (CertificateParsingException e1) {
            log.error("Encountered an problem trying to extract Subject Alternate "
                    + "Name from supplied certificate: " + e1);
        }
        names.add(getHostNameFromDN(credential.getEntityCertificate().getSubjectX500Principal()));
        return names;

    }

    // TODO should this be moved?
    public static String getHostNameFromDN(X500Principal dn) {

        // Parse the ASN.1 representation of the dn and grab the last CN component that we find
        // We used to do this with the dn string, but the JDK's default parsing caused problems with some DNs
        try {
            ASN1InputStream asn1Stream = new ASN1InputStream(dn.getEncoded());
            DERObject parent = asn1Stream.readObject();

            if (!(parent instanceof DERSequence)) {
                log.error("Unable to extract host name name from certificate subject DN: incorrect ASN.1 encoding.");
                return null;
            }

            String cn = null;
            for (int i = 0; i < ((DERSequence) parent).size(); i++) {
                DERObject dnComponent = ((DERSequence) parent).getObjectAt(i).getDERObject();
                if (!(dnComponent instanceof DERSet)) {
                    log.debug("No DN components.");
                    continue;
                }

                // Each DN component is a set
                for (int j = 0; j < ((DERSet) dnComponent).size(); j++) {
                    DERObject grandChild = ((DERSet) dnComponent).getObjectAt(j).getDERObject();

                    if (((DERSequence) grandChild).getObjectAt(0) != null
                            && ((DERSequence) grandChild).getObjectAt(0).getDERObject() instanceof DERObjectIdentifier) {
                        DERObjectIdentifier componentId = (DERObjectIdentifier) ((DERSequence) grandChild).getObjectAt(
                                0).getDERObject();

                        if (CN_OID.equals(componentId.getId())) {
                            // OK, this dn component is actually a cn attribute
                            if (((DERSequence) grandChild).getObjectAt(1) != null
                                    && ((DERSequence) grandChild).getObjectAt(1).getDERObject() instanceof DERString) {
                                cn = ((DERString) ((DERSequence) grandChild).getObjectAt(1).getDERObject()).getString();
                            }
                        }
                    }
                }
            }
            asn1Stream.close();
            return cn;

        } catch (IOException e) {
            log.error("Unable to extract host name name from certificate subject DN: ASN.1 parsing failed: " + e);
            return null;
        }
    }
}
