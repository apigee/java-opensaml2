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

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.ArtifactMap;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.SAMLArtifact;
import org.opensaml.common.binding.SAMLArtifactFactory;
import org.opensaml.common.binding.impl.AbstractHTTPMessageEncoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * SAML 2 Artifact Binding encoder, support both HTTP GET and POST.
 */
public class HTTPArtifactEncoder extends AbstractHTTPMessageEncoder {

    /** Location of the velocity template */
    public final static String VELOCITY_TEMPLATE = "/templates/saml2-post-artifact-binding.vm";

    /** Class logger */
    private final static Logger log = Logger.getLogger(HTTPArtifactEncoder.class);

    /** HTTP submit method */
    private String method;

    /** URL for the form action field */
    private String actionURL;
    
    /** Type code of artifacts to use */
    private byte[] artifactType;
    
    /** Factory for building artifacts */
    private SAMLArtifactFactory artifactFactory;
    
    /** Artifact map for built artifacts and messages */
    private ArtifactMap artifactMap;
    
    /** Artifact generated for the given SAML message */
    private SAMLArtifact artifact;

    /**
     * Gets the HTTP submit method to use.
     * 
     * @return HTTP submit method to use
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the HTTP submit method to use.
     * 
     * @param method HTTP submit method to use
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the URL for the form action field.
     * 
     * @return URL for the form action field
     */
    public String getActionURL() {
        return actionURL;
    }

    /**
     * Sets the URL for the form action field.
     * 
     * @param url URL for the form action field
     */
    public void setActionURL(String url) {
        actionURL = url;
    }
    
    /**
     * Gets the artifact factory used to create artifacts for this encoder.
     * 
     * @return artifact factory used to create artifacts for this encoder
     */
    public SAMLArtifactFactory getArtifactFactory() {
        return artifactFactory;
    }

    /**
     * Sets the artifact factory used to create artifacts for this encoder.
     * 
     * @param artifactFactory artifact factory used to create artifacts for this encoder
     */
    public void setArtifactFactory(SAMLArtifactFactory artifactFactory) {
        this.artifactFactory = artifactFactory;
    }

    /**
     * Gets the artifact map used to map artifacts and messages.
     * 
     * @return artifact map used to map artifacts and messages
     */
    public ArtifactMap getArtifactMap() {
        return artifactMap;
    }

    /**
     * Sets the artifact map used to map artifacts and messages.
     * 
     * @param artifactMap artifact map used to map artifacts and messages
     */
    public void setArtifactMap(ArtifactMap artifactMap) {
        this.artifactMap = artifactMap;
    }

    /**
     * Gets the type of artifact this encoder will use.
     * 
     * @return type of artifact this encoder will use
     */
    public byte[] getArtifactType() {
        return artifactType;
    }

    /**
     * Sets the type of artifact this encoder will use.
     * 
     * @param artifactType type of artifact this encoder will use
     */
    public void setArtifactType(byte[] artifactType) {
        this.artifactType = artifactType;
    }

    /**
     * Gets the artifact created for the given SAML message.
     * 
     * @return artifact created for the given SAML message
     */
    public SAMLArtifact getArtifact() {
        return artifact;
    }

    /** {@inheritDoc} */
    public void encode() throws BindingException {
        HttpServletResponse response = getResponse();
        response.setCharacterEncoding("UTF-8");
        
        if(log.isDebugEnabled()){
            log.debug("Generating SAML artifact and mapping SAML message to it");
        }
        generateArtifact();

        if (log.isDebugEnabled()) {
            log.debug("Creating velocity context");
        }
        VelocityContext context = new VelocityContext();
        context.put("action", getActionURL());
        context.put("SAMLArt", getArtifact());

        if (!DatatypeHelper.isEmpty(getRelayState())) {
            context.put("RelayState", getRelayState());
        }

        if (method.equalsIgnoreCase(SAMLConstants.POST_METHOD)) {
            postEncode(context);
        } else if (method.equalsIgnoreCase(SAMLConstants.GET_METHOD)){
            getEncode(context);
        }
    }

    /**
     * Performs HTTP POST based encoding.
     * 
     * @throws BindingException thrown if there is a problem invoking the velocity template to create the form
     */
    protected void postEncode(VelocityContext context) throws BindingException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Performing HTTP GET SAML 2 artifact encoding");
            }

            if (log.isDebugEnabled()) {
                log.debug("Invoking velocity template");
            }
            Velocity.mergeTemplate(VELOCITY_TEMPLATE, "UTF-8", context, getResponse().getWriter());
        } catch (Exception e) {
            log.error("Error invoking velocity template to create POST form", e);
            throw new BindingException("Error creating output document", e);
        }
    }

    /**
     * Performs HTTP GET based econding.
     * 
     * @throws BindingException
     */
    protected void getEncode(VelocityContext context) throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Performing HTTP GET SAML 2 artifact encoding");
        }

        try {
            String redirectURLTemplate = "${action}?SAMLArt=${SAMLArt}#if($RelayState)&RelayState=${RelayState}#end";
            StringWriter writer = new StringWriter();
            Velocity.evaluate(context, writer, HTTPArtifactEncoder.class.getName(), redirectURLTemplate);
            getResponse().sendRedirect(writer.toString());
        } catch (IOException e) {
            log.error("Unable to send HTTP redirect", e);
            throw new BindingException("Unable to send HTTP redirect", e);
        } catch (Exception e) {
            log.error("Error invoking velocity template to create redirect URL", e);
            throw new BindingException("Error creating redirect URL", e);
        }
    }
    
    /**
     * Generates the artifact to use and maps the given SAML message to it.
     * 
     * @throws BindingException thrown if the artifact can not be created
     */
    protected void generateArtifact() throws BindingException{
        SAMLArtifactFactory factory = getArtifactFactory();
        artifact = factory.buildArtifact(SAMLVersion.VERSION_20, getArtifactType(), getRelyingParty());
        artifactMap.put(artifact, getSAMLMessage());
    }    
}