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

package org.opensaml.saml2.binding.encoding;

import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.BaseMessageEncoder;
import org.opensaml.ws.message.encoder.MessageEncodingException;

/**
 * SAML 2 Artifact Binding encoder, support both HTTP GET and POST.
 */
public class HTTPArtifactEncoder extends BaseMessageEncoder implements SAMLMessageEncoder {
    
    /** {@inheritDoc} */
    public String getBindingURI() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /** {@inheritDoc} */
    protected void doEncode(MessageContext messageContext) throws MessageEncodingException {
        // TODO Auto-generated method stub
        
    }

//    /** Artifact encoding methods. */
//    public static enum ENCODING_METHOD {
//        /** Artifact URL encoding with HTTP Redirect binding. */
//        URL,
//
//        /** Artifact FORM encoding with HTTP Post binding. */
//        FORM
//    };
//
//    /** Class logger. */
//    private static Logger log = Logger.getLogger(HTTPArtifactEncoder.class);
//
//    /** Velocity engine used to evaluate the template when performing POST encoding. */
//    private VelocityEngine velocityEngine;
//
//    /** ID of the velocity template used when performing POST encoding. */
//    private String velocityTemplateId;
//
//    /** Type code of artifacts to use. */
//    private byte[] artifactType;
//
//    /** Factory for building artifacts. */
//    private SAMLArtifactFactory artifactFactory;
//
//    /** Artifact map for built artifacts and messages. */
//    private SAMLArtifactMap artifactMap;
//
//    /** HTTP submit method. */
//    private ENCODING_METHOD encodingMethod;
//
//    /** Artifact generated for the given SAML message. */
//    private SAMLArtifact artifact;
//
//    /** {@inheritDoc} */
//    public String getBindingURI() {
//        if (encodingMethod == ENCODING_METHOD.FORM) {
//            return HTTPPostEncoder.BINDING_URI;
//        } else {
//            return HTTPRedirectDeflateEncoder.BINDING_URI;
//        }
//    }
//
//    /**
//     * Gets the velocity engine used to evaluate the template when performing POST encoding.
//     * 
//     * @return velocity engine used to evaluate the template when performing POST encoding
//     */
//    public VelocityEngine getVelocityEngine() {
//        return velocityEngine;
//    }
//
//    /**
//     * Sets the velocity engine used to evaluate the template when performing POST encoding.
//     * 
//     * @param engine velocity engine used to evaluate the template when performing POST encoding
//     */
//    public void setVelocityEngine(VelocityEngine engine) {
//        velocityEngine = engine;
//    }
//
//    /**
//     * Gets the ID of the velocity template used for POST encoding.
//     * 
//     * @return ID of the velocity template used for POST encoding
//     */
//    public String getVelocityTemplateId() {
//        return velocityTemplateId;
//    }
//
//    /**
//     * Sets the ID of the velocity template used for POST encoding.
//     * 
//     * @param id ID of the velocity template used for POST encoding
//     */
//    public void setVelocityTemplateId(String id) {
//        velocityTemplateId = id;
//    }
//
//    /**
//     * Gets the HTTP submit method to use.
//     * 
//     * @return HTTP submit method to use
//     */
//    public ENCODING_METHOD getEncodingMethod() {
//        return encodingMethod;
//    }
//
//    /**
//     * Sets the HTTP submit method to use.
//     * 
//     * @param method HTTP submit method to use
//     */
//    public void setEncodingMethod(ENCODING_METHOD method) {
//        encodingMethod = method;
//    }
//
//    /**
//     * Gets the artifact factory used to create artifacts for this encoder.
//     * 
//     * @return artifact factory used to create artifacts for this encoder
//     */
//    public SAMLArtifactFactory getArtifactFactory() {
//        return artifactFactory;
//    }
//
//    /**
//     * Sets the artifact factory used to create artifacts for this encoder.
//     * 
//     * @param factory artifact factory used to create artifacts for this encoder
//     */
//    public void setArtifactFactory(SAMLArtifactFactory factory) {
//        artifactFactory = factory;
//    }
//
//    /**
//     * Gets the artifact map used to map artifacts and messages.
//     * 
//     * @return artifact map used to map artifacts and messages
//     */
//    public SAMLArtifactMap getArtifactMap() {
//        return artifactMap;
//    }
//
//    /**
//     * Sets the artifact map used to map artifacts and messages.
//     * 
//     * @param map artifact map used to map artifacts and messages
//     */
//    public void setArtifactMap(SAMLArtifactMap map) {
//        artifactMap = map;
//    }
//
//    /**
//     * Gets the type of artifact this encoder will use.
//     * 
//     * @return type of artifact this encoder will use
//     */
//    public byte[] getArtifactType() {
//        return artifactType;
//    }
//
//    /**
//     * Sets the type of artifact this encoder will use.
//     * 
//     * @param type type of artifact this encoder will use
//     */
//    public void setArtifactType(byte[] type) {
//        artifactType = type;
//    }
//
//    /**
//     * Gets the artifact created for the given SAML message.
//     * 
//     * @return artifact created for the given SAML message
//     */
//    public SAMLArtifact getArtifact() {
//        return artifact;
//    }
//
//    /** {@inheritDoc} */
//    public void encode() throws BindingException {
//        if (log.isDebugEnabled()) {
//            log.debug("Beginning SAML 2 HTTP Artifact encoding");
//        }
//
//        HttpServletResponse response = getResponse();
//        response.setCharacterEncoding("UTF-8");
//
//        if (log.isDebugEnabled()) {
//            log.debug("Generating SAML artifact and mapping SAML message to it");
//        }
//        generateArtifact();
//
//        if (encodingMethod == ENCODING_METHOD.FORM) {
//            postEncode(getArtifact().base64Encode());
//        } else if (encodingMethod == ENCODING_METHOD.URL) {
//            getEncode(getArtifact().base64Encode());
//        }
//    }
//
//    /**
//     * Performs HTTP POST based encoding.
//     * 
//     * @param artifactString the base64 encoded artifact
//     * 
//     * @throws BindingException thrown if there is a problem invoking the velocity template to create the form
//     */
//    protected void postEncode(String artifactString) throws BindingException {
//        if (log.isDebugEnabled()) {
//            log.debug("Performing HTTP POST SAML 2 artifact encoding");
//        }
//
//        if (log.isDebugEnabled()) {
//            log.debug("Creating velocity context");
//        }
//        VelocityContext context = new VelocityContext();
//        context.put("action", getEndpointURL());
//        context.put("SAMLArt", artifactString);
//
//        String relayState = getRelayState();
//        if (checkRelayState()) {
//            context.put("RelayState", relayState);
//        }
//
//        try {
//            if (log.isDebugEnabled()) {
//                log.debug("Performing HTTP GET SAML 2 artifact encoding");
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("Invoking velocity template");
//            }
//            velocityEngine.mergeTemplate(velocityTemplateId, "UTF-8", context, getResponse().getWriter());
//        } catch (Exception e) {
//            log.error("Error invoking velocity template to create POST form", e);
//            throw new BindingException("Error creating output document", e);
//        }
//    }
//
//    /**
//     * Performs HTTP GET based econding.
//     * 
//     * @param artifactString the base64 encoded artifact
//     * 
//     * @throws BindingException thrown if there is a problem redirecting the response
//     */
//    protected void getEncode(String artifactString) throws BindingException {
//        if (log.isDebugEnabled()) {
//            log.debug("Performing HTTP GET SAML 2 artifact encoding");
//        }
//
//        try {
//            URLBuilder urlBuilder = new URLBuilder(getEndpointURL());
//
//            List<Pair<String, String>> params = urlBuilder.getQueryParams();
//
//            params.add(new Pair<String, String>("SAMLArt", artifactString));
//
//            if (!DatatypeHelper.isEmpty(getRelayState())) {
//                params.add(new Pair<String, String>("RelayState", getEncodeRelayState()));
//            }
//
//            getResponse().sendRedirect(urlBuilder.buildURL());
//        } catch (IOException e) {
//            log.error("Unable to send HTTP redirect", e);
//            throw new BindingException("Unable to send HTTP redirect", e);
//        }
//    }
//
//    /**
//     * Generates the artifact to use and maps the given SAML message to it.
//     * 
//     * @throws BindingException thrown if the artifact can not be created
//     */
//    protected void generateArtifact() throws BindingException {
//        SAMLArtifactFactory factory = getArtifactFactory();
//        artifact = factory.buildArtifact(SAMLVersion.VERSION_20, getArtifactType(), getRelyingParty().getEntityID());
//        artifactMap.put(artifact.getArtifactBytes(), getRelyingParty().getID(), getIssuer(), getSamlMessage());
//    }
}