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

package org.opensaml.saml2.binding.decoding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusResponseType;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.util.URLBuilder;
import org.opensaml.ws.message.decoder.BaseMessageDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.InTransport;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for SAML 2 message decoders.
 */
public abstract class BaseSAML2MessageDecoder extends BaseMessageDecoder implements SAMLMessageDecoder{

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(BaseSAML2MessageDecoder.class);
    
    /** Constructor. */
    public BaseSAML2MessageDecoder() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param pool parser pool used to deserialize messages
     */
    public BaseSAML2MessageDecoder(ParserPool pool) {
        super(pool);
    }

    /**
     * Populates the message context with the message ID, issue instant, and issuer as well as the peer's entity
     * descriptor if a metadata provider is present in the message context and the peer's role descriptor if its entity
     * descriptor was retrieved and the message context has a populated peer role name.
     * 
     * @param messageContext message context to populate
     * 
     * @throws MessageDecodingException thrown if there is a problem populating the message context
     */
    protected void populateMessageContext(SAMLMessageContext messageContext) throws MessageDecodingException {
        populateMessageIdIssueInstantIssuer(messageContext);
        populateRelyingPartyMetadata(messageContext);
    }
    
    /**
     * Extracts the message ID, issue instant, and issuer from the incoming SAML message and populates the message
     * context with it.
     * 
     * @param messageContext current message context
     * 
     * @throws MessageDecodingException thrown if there is a problem populating the message context
     */
    protected void populateMessageIdIssueInstantIssuer(SAMLMessageContext messageContext)
            throws MessageDecodingException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.debug("Invalid message context type, this policy rule only support SAMLMessageContext");
            return;
        }
        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;

        SAMLObject samlMsg = samlMsgCtx.getInboundSAMLMessage();
        if (samlMsg == null) {
            log.error("Message context did not contain inbound SAML message");
            throw new MessageDecodingException("Message context did not contain inbound SAML message");
        }

        if (samlMsg instanceof RequestAbstractType) {
            log.debug("Extracting ID, issuer and issue instant from request");
            extractRequestInfo(samlMsgCtx, (RequestAbstractType) samlMsg);
        } else if (samlMsg instanceof StatusResponseType) {
            log.debug("Extracting ID, issuer and issue instant from status response");
            extractResponseInfo(samlMsgCtx, (StatusResponseType) samlMsg);
        } else {
            throw new MessageDecodingException("SAML 2 message was not a request or a response");
        }

        if (samlMsgCtx.getInboundMessageIssuer() == null) {
            log.warn("Issuer could not be extracted from SAML 2 message");
        }

    }

    /**
     * Extract information from a SAML StatusResponse message.
     * 
     * @param messageContext current message context
     * @param statusResponse the SAML message to process
     * 
     * @throws MessageDecodingException thrown if the response issuer has a format other than {@link NameIDType#ENTITY}
     *             or, if the response does not contain an issuer, if the contained assertions contain issuers that are
     *             not of {@link NameIDType#ENTITY} format or if the assertions contain different issuers
     */
    protected void extractResponseInfo(SAMLMessageContext messageContext, StatusResponseType statusResponse)
            throws MessageDecodingException {

        messageContext.setInboundSAMLMessageId(statusResponse.getID());
        messageContext.setInboundSAMLMessageIssueInstant(statusResponse.getIssueInstant());

        // If response doesn't have an issuer, look at the first
        // enclosed assertion
        String messageIssuer = null;
        if (statusResponse.getIssuer() != null) {
            messageIssuer = extractEntityId(statusResponse.getIssuer());
        } else if (statusResponse instanceof Response) {
            List<Assertion> assertions = ((Response) statusResponse).getAssertions();
            if (assertions != null && assertions.size() > 0) {
                log.info("Status response message had no issuer, attempting to extract issuer from enclosed Assertion(s)");
                String assertionIssuer;
                for (Assertion assertion : assertions) {
                    if (assertion != null && assertion.getIssuer() != null) {
                        assertionIssuer = extractEntityId(assertion.getIssuer());
                        if (messageIssuer != null && !messageIssuer.equals(assertionIssuer)) {
                            throw new MessageDecodingException("SAML 2 assertions, within response "
                                    + statusResponse.getID() + " contain different issuer IDs");
                        }
                        messageIssuer = assertionIssuer;
                    }
                }
            }
        }

        messageContext.setInboundMessageIssuer(messageIssuer);
    }

    /**
     * Extract information from a SAML RequestAbstractType message.
     * 
     * @param messageContext current message context
     * @param request the SAML message to process
     * 
     * @throws MessageDecodingException thrown if the request issuer has a format other than {@link NameIDType#ENTITY}
     */
    protected void extractRequestInfo(SAMLMessageContext messageContext, RequestAbstractType request)
            throws MessageDecodingException {
        messageContext.setInboundSAMLMessageId(request.getID());
        messageContext.setInboundSAMLMessageIssueInstant(request.getIssueInstant());
        messageContext.setInboundMessageIssuer(extractEntityId(request.getIssuer()));
    }

    /**
     * Extracts the entity ID from the SAML 2 Issuer.
     * 
     * @param issuer issuer to extract the entityID from
     * 
     * @return entity ID of the issuer
     * 
     * @throws MessageDecodingException thrown if the given issuer has a format other than {@link NameIDType#ENTITY}
     */
    protected String extractEntityId(Issuer issuer) throws MessageDecodingException {
        if (issuer != null) {
            if (issuer.getFormat() == null || issuer.getFormat().equals(NameIDType.ENTITY)) {
                return issuer.getValue();
            } else {
                throw new MessageDecodingException("SAML 2 Issuer is not of ENTITY format type");
            }
        }

        return null;
    }
    
    
    /**
     * Populates the peer's entity metadata if a metadata provide is present in the message context. Populates the
     * peer's role descriptor if the entity metadata was available and the role name is present in the message context.
     * 
     * @param messageContext current message context
     * 
     * @throws MessageDecodingException thrown if there is a problem populating the message context
     */
    protected void populateRelyingPartyMetadata(SAMLMessageContext messageContext) throws MessageDecodingException {
        MetadataProvider metadataProvider = messageContext.getMetadataProvider();
        try {
            if (metadataProvider != null) {
                EntityDescriptor relyingPartyMD = metadataProvider.getEntityDescriptor(messageContext
                        .getInboundMessageIssuer());
                messageContext.setPeerEntityMetadata(relyingPartyMD);

                QName relyingPartyRole = messageContext.getPeerEntityRole();
                if (relyingPartyMD != null && relyingPartyRole != null) {
                    List<RoleDescriptor> roles = relyingPartyMD.getRoleDescriptors(relyingPartyRole,
                            SAMLConstants.SAML11P_NS);
                    if (roles != null && roles.size() > 0) {
                        messageContext.setPeerEntityRoleMetadata(roles.get(0));
                    }
                }
            }
        } catch (MetadataProviderException e) {
            log.error("Error retrieving metadata for relying party " + messageContext.getInboundMessageIssuer(), e);
            throw new MessageDecodingException("Error retrieving metadata for relying party "
                    + messageContext.getInboundMessageIssuer(), e);
        }
    }
    
    /**
     * Check the validity of the SAML protocol message destination attribute.
     * 
     * @param messageContext current message context
     * @param bindingRequires flag to indicate whether the binding requires the protocol message
     *                        destination attribute to be present and valid on the SAML message
     *                        represented by this message context
     * @throws MessageDecodingException thrown if the message destination attribute is invalid
     *                                  with respect to the receiver's endpoint
     */
    protected void checkDestination(SAMLMessageContext messageContext, boolean bindingRequires) 
            throws MessageDecodingException {
        
        log.debug("Checking SAML 2 message destination against receiver endpoint");
        
        SAMLObject samlMessage = messageContext.getInboundSAMLMessage();
        String messageDestination = getDestinationURI(samlMessage);
        
        if (messageDestination == null) {
            if (bindingRequires) {
                log.error("SAML 2 protocol message destination required by binding was empty");
                throw new MessageDecodingException("SAML 2 protocol message destination was not specified");
            } else {
                log.debug("SAML 2 protocol message destination was empty, not required by binding, skipping");
                return;
            }
        }
        
        String receiverEndpoint = getReceiverEndpointURI(messageContext);
        
        log.debug("Message destination: {}", messageDestination);
        log.debug("Receiver endpoint: {}", receiverEndpoint);
        
        boolean matched = compareEndpointURIs(messageDestination, receiverEndpoint);
        if (!matched) {
            log.error("SAML 2 message destination '{}' did not match the recipient endpoint '{}'",
                    messageDestination, receiverEndpoint);
            throw new MessageDecodingException("SAML 2 message destination did not match recipient endpoint");
        } else {
            log.debug("Message destination matched recipient endpoint");
        }
    }
    
    /**
     * Extract the protocol message destination attribute value.
     * 
     * @param samlMessage the SAML 2 protocol message being processed
     * @return the value of the destination attribute, or null if not present or empty
     * @throws MessageDecodingException thrown if the message is not an instance of a SAML 2 request or response
     */
    protected String getDestinationURI(SAMLObject samlMessage) throws MessageDecodingException {
        String messageDestination = null;
        if (samlMessage instanceof RequestAbstractType) {
            RequestAbstractType request =  (RequestAbstractType) samlMessage;
            messageDestination = DatatypeHelper.safeTrimOrNullString(request.getDestination());
        } else if (samlMessage instanceof StatusResponseType) {
            StatusResponseType response = (StatusResponseType) samlMessage;
            messageDestination = DatatypeHelper.safeTrimOrNullString(response.getDestination());
        } else {
            log.error("Invalid SAML message type encountered: {}", samlMessage.getElementQName().toString());
            throw new MessageDecodingException("Invalid SAML message type encountered");
        }
        return messageDestination;
    }
    
    /**
     * Extract the transport endpoint at which this message was received.
     * 
     * <p>This default implementation assumes an underlying message context {@link InTransport} type
     * of {@link HttpServletRequestAdapter} and returns the string representation of the underlying
     * request URL as constructed via {@link HttpServletRequest#getRequestURL()}, with the query
     * string appended as returned via {@link HttpServletRequest#getQueryString()}.<p>
     * 
     * <p>Subclasses should override if binding-specific behavior or support for other transport
     * typs is required.  In this case, see also {@link #compareEndpointURIs(String, String).</p>
     * 
     * 
     * @param messageContext current message context
     * @return string representing the transport endpoint URI at which the current message was received
     * @throws MessageDecodingException thrown if the endpoint can not be extracted from the message
     *                              context and converted to a string representation
     */
    protected String getReceiverEndpointURI(SAMLMessageContext messageContext) throws MessageDecodingException {
        InTransport inTransport = messageContext.getInboundMessageTransport();
        if (! (inTransport instanceof HttpServletRequestAdapter)) {
            log.error("Message context InTransport instance was an unsupported type: {}", 
                    inTransport.getClass().getName());
            throw new MessageDecodingException("Message context InTransport instance was an unsupported type");
        }
        HttpServletRequest httpRequest = ((HttpServletRequestAdapter)inTransport).getWrappedRequest();
        
        StringBuffer urlBuilder = httpRequest.getRequestURL();
        if (httpRequest.getQueryString() != null) {
            urlBuilder.append('?');
            urlBuilder.append(httpRequest.getQueryString());
        }
        return urlBuilder.toString();
    }

    /**
     * Compare the message endpoint URI's specified.
     * 
     * <p>This default implementation handles endpoint URI's that are URL's.  Comparison is handled
     * via constructing {@link URL} instances and using that implementation's equals() method.</p>
     * 
     * <p>Subclasses should override if binding-specific behavior is required, or to support other
     * types of URI's.  In this case, see also {@link #getReceiverEndpointURI(SAMLMessageContext)}.</p>
     * 
     * @param messageDestination the intended message destination endpoint URI
     * @param receiverEndpoint the endpoint URI at which the message was received
     * @return true if the endpoints are equivalent, false otherwise
     * @throws MessageDecodingException thrown if the endpoints specified are not equivalent
     */
    protected boolean compareEndpointURIs(String messageDestination, String receiverEndpoint) 
            throws MessageDecodingException {
        
        URL messageURL = null;
        try {
            messageURL = new URL(messageDestination);
        } catch (MalformedURLException e) {
            log.error("Message destination URL was malformed in destination check: {}", e.getMessage());
            throw new MessageDecodingException("Message destination URL was malformed in destination check");
        }
        
        URL endpointURL = null;
        try {
            endpointURL = new URL(receiverEndpoint);
        } catch (MalformedURLException e) {
            log.error("Recipient endpoint URL was malformed in destination check: {}", e.getMessage());
            throw new MessageDecodingException("Recipient endpoint URL was malformed in destination check");
        }
        
        return messageURL.equals(endpointURL);
    }
    
    /**
     * Determine whether the SAML message represented by the message context is digitally signed.
     * 
     * <p>The default behavior is to examine whether an XML signature is present on the 
     * SAML protocol message.  Subclasses may augment or replace with binding-specific behavior.</p>
     * 
     * @param messageContext current message context
     * @return true if the message is considered to be digitially signed, false otherwise
     */
    protected boolean isMessageSigned(SAMLMessageContext messageContext) {
        SAMLObject samlMessage = messageContext.getInboundSAMLMessage();
        if (samlMessage instanceof SignableSAMLObject) {
            return ((SignableSAMLObject)samlMessage).isSigned();
        } else {
            return false;
        }
    }
    
}