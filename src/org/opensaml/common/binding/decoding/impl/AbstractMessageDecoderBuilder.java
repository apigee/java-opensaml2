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

package org.opensaml.common.binding.decoding.impl;

import org.opensaml.common.binding.decoding.MessageDecoderBuilder;
import org.opensaml.common.binding.security.SAMLSecurityPolicy;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.SecurityPolicyFactory;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.security.trust.TrustEngine;

/**
 * Base class for message decoder builders.
 * 
 * @param <MessageDecoderType> type of decoder created by this builder
 */
public abstract class AbstractMessageDecoderBuilder<MessageDecoderType extends AbstractMessageDecoder> implements
        MessageDecoderBuilder<MessageDecoderType> {

    /** Security policy to apply to the request and payload. */
    private SecurityPolicyFactory policyFactory;

    /** Trust engine used to validate request credentials. */
    private TrustEngine trustEngine;

    /** Pool of parsers used to parse XML messages. */
    private ParserPool parser;

    /** Metadata provider used to lookup information about the issuer. */
    private MetadataProvider metadataProvider;

    /**
     * Gets the metadata provider used to lookup information about the issuer.
     * 
     * @return metadata provider used to lookup information about the issuer
     */
    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    /**
     * Sets the metadata provider used to lookup information about the issuer.
     * 
     * @param provider metadata provider used to lookup information about the issuer
     */
    public void setMetadataProvider(MetadataProvider provider) {
        metadataProvider = provider;
    }

    /**
     * Gets the parser pool used to parse messages.
     * 
     * @return parser pool used to parse messages
     */
    public ParserPool getParser() {
        return parser;
    }

    /**
     * Sets the parser pool used to parse messages.
     * 
     * @param pool parser pool used to parse messages
     */
    public void setParser(ParserPool pool) {
        parser = pool;
    }

    /**
     * Gets the policy factory used to create security policy instances.
     * 
     * @return policy factory used to create security policy instances
     */
    public SecurityPolicyFactory getPolicyFactory() {
        return policyFactory;
    }

    /**
     * Sets the policy factory used to create security policy instances.
     * 
     * @param factory policy factory used to create security policy instances
     */
    public void setPolicyFactory(SecurityPolicyFactory factory) {
        policyFactory = factory;
    }

    /**
     * Gets the trust engine used to evaluate message trustworthiness.
     * 
     * @return trust engine used to evaluate message trustworthiness
     */
    public TrustEngine getTrustEngine() {
        return trustEngine;
    }

    /**
     * Sets the trust engine used to evaluate message trustworthiness.
     * 
     * @param engine trust engine used to evaluate message trustworthiness
     */
    public void setTrustEngine(TrustEngine engine) {
        trustEngine = engine;
    }

    /** {@inheritDoc} */
    public MessageDecoderType buildDecoder() {
        MessageDecoderType decoder = doBuildEncoder();
        decoder.setMetadataProvider(metadataProvider);
        decoder.setParserPool(parser);
        decoder.setTrustEngine(trustEngine);

        if (policyFactory != null) {
            decoder.setSecurityPolicy((SAMLSecurityPolicy) policyFactory.createPolicyInstance());
        }

        return decoder;
    }

    /**
     * Builds the message decoder.
     * 
     * @return the message decoder
     */
    protected abstract MessageDecoderType doBuildEncoder();
}