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

package org.opensaml.saml2.binding.encoding;

import java.io.IOException;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.opensaml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.common.binding.artifact.SAMLArtifactFactory;
import org.opensaml.common.binding.encoding.MessageEncoder;
import org.opensaml.common.binding.encoding.MessageEncoderBuilder;
import org.opensaml.saml2.binding.encoding.HTTPArtifactEncoder.ENCODING_METHOD;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Builder of {@link HTTPArtifactEncoder}s.
 */
public class HTTPArtifactEncoderBuilder implements MessageEncoderBuilder {

    /** Artifact map used to store created artifacts. */
    private SAMLArtifactMap artifactMap;

    /** Factory used to create an artifact. */
    private SAMLArtifactFactory artifactFactory;

    /** Encoding method for the artifact message. */
    private ENCODING_METHOD encodingMethod;

    /** Velocity engine used to evaluate the template when performing POST encoding. */
    private VelocityEngine velocityEngine;

    /** ID of the velocity template. */
    private String velocityTempalteId;

    /**
     * Constructor. Creates a builder that performs URL artifact encoding.
     * 
     * @param factory factory used to create artifacts
     * @param map artifact map used to store create artifact information
     */
    public HTTPArtifactEncoderBuilder(SAMLArtifactFactory factory, SAMLArtifactMap map) {
        encodingMethod = ENCODING_METHOD.URL;
        artifactFactory = factory;
        artifactMap = map;
    }

    /**
     * Constructor. Creates a builder that performs a FORM artifact encoding.
     * 
     * @param factory factory used to create artifacts
     * @param map artifact map used to store create artifact information
     * @param engine velocity engine used during POST encoding
     * @param templatePath classpath location of the POST encoding template
     * 
     * @throws IOException thrown if the template can not be read from the classpath
     */
    public HTTPArtifactEncoderBuilder(SAMLArtifactFactory factory, SAMLArtifactMap map, VelocityEngine engine,
            String templatePath) throws IOException {
        encodingMethod = ENCODING_METHOD.FORM;
        artifactFactory = factory;
        artifactMap = map;
        velocityEngine = engine;
        velocityTempalteId = templatePath;
        registerTemplate(templatePath);
    }

    /** {@inheritDoc} */
    public MessageEncoder buildEncoder() {
        HTTPArtifactEncoder encoder = new HTTPArtifactEncoder();
        encoder.setArtifactFactory(artifactFactory);
        encoder.setArtifactMap(artifactMap);
        encoder.setEncodingMethod(encodingMethod);
        encoder.setVelocityEngine(velocityEngine);
        encoder.setVelocityTemplateId(velocityTempalteId);
        return encoder;
    }

    /**
     * Gets the template from the classpath and registers it with the velocity engine.
     * 
     * @param templatePath classpath location of the template
     * 
     * @throws IOException thrown if the template can not be read from the classpath
     */
    protected void registerTemplate(String templatePath) throws IOException {
        StringResourceRepository repository = StringResourceLoader.getRepository();
        String template = DatatypeHelper.inputstreamToString(getClass().getResourceAsStream(templatePath), null);
        repository.putStringResource(templatePath, template);
    }
}