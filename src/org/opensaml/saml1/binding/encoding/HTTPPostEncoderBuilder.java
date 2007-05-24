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

package org.opensaml.saml1.binding.encoding;

import java.io.IOException;

import org.apache.velocity.app.VelocityEngine;
import org.opensaml.common.binding.encoding.HTTPMessageEncoder;
import org.opensaml.common.binding.encoding.MessageEncoderBuilder;

/**
 * Builder of {@link HTTPPostEncoder}s.
 */
public class HTTPPostEncoderBuilder implements MessageEncoderBuilder<HTTPMessageEncoder> {

    /** Velocity engine used to evaluate the template when performing POST encoding. */
    private VelocityEngine velocityEngine;

    /** ID of the velocity template. */
    private String velocityTempalteId;

    /**
     * Constructor. Creates a builder that performs a FORM artifact encoding.
     * 
     * @param engine velocity engine used during POST encoding
     * @param templatePath classpath location of the POST encoding template
     * 
     * @throws IOException thrown if the template can not be read from the classpath
     */
    public HTTPPostEncoderBuilder(VelocityEngine engine, String templatePath) throws IOException {
        velocityEngine = engine;
        velocityTempalteId = templatePath;
    }

    /** {@inheritDoc} */
    public HTTPMessageEncoder buildEncoder() {
        HTTPPostEncoder encoder = new HTTPPostEncoder();
        encoder.setVelocityEngine(velocityEngine);
        encoder.setVelocityTemplateId(velocityTempalteId);
        return encoder;
    }
}