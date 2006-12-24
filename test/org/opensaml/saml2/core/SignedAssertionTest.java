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

package org.opensaml.saml2.core;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.signature.XMLSignature;
import org.joda.time.DateTime;
import org.opensaml.common.SAMLObjectTestCaseConfigInitializer;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.impl.SAMLObjectContentReference;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.saml2.core.impl.AuthnStatementBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.opensaml.xml.validation.ValidationException;

public class SignedAssertionTest extends SAMLObjectTestCaseConfigInitializer {
    
    /** Key used for signing */
    private PrivateKey signingKey;

    /** Key used for verification */
    private PublicKey verificationKey;

    /** Verification key that should fail to verify signature */
    private PublicKey badVerificationKey;
    
    /** Builder of Assertions */
    private AssertionBuilder assertionBuilder;
    
    /** Builder of Issuers */
    private IssuerBuilder issuerBuilder;
    
    /** Builder of AuthnStatements */
    private AuthnStatementBuilder authnStatementBuilder;
    
    /** Builder of AuthnStatements */
    private SignatureBuilder signatureBuilder;
    
    /** Generator of element IDs */
    private SecureRandomIdentifierGenerator idGenerator;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        signingKey = keyPair.getPrivate();
        verificationKey = keyPair.getPublic();

        keyGen.initialize(1024);
        keyPair = keyGen.generateKeyPair();
        badVerificationKey = keyPair.getPublic();
        
        assertionBuilder = (AssertionBuilder) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
        issuerBuilder = (IssuerBuilder) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
        authnStatementBuilder = (AuthnStatementBuilder) builderFactory.getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
        signatureBuilder = (SignatureBuilder) builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME);
        
        idGenerator = new SecureRandomIdentifierGenerator();
    }
    
    /**
     * Creates a simple Assertion, signs it and then verifies the signature.
     * 
     * @throws MarshallingException thrown if the Assertion can not be marshalled into a DOM
     * @throws ValidationException thrown if the Signature does not validate
     */
    public void testAssertionSignature() throws MarshallingException, ValidationException{
        DateTime now = new DateTime();
        
        Assertion assertion = assertionBuilder.buildObject();
        assertion.setVersion(SAMLVersion.VERSION_20);
        assertion.setID(idGenerator.generateIdentifier());
        assertion.setIssueInstant(now);
        
        Issuer issuer = issuerBuilder.buildObject();
        issuer.setValue("urn:example.org:issuer");
        assertion.setIssuer(issuer);
        
        AuthnStatement authnStmt = authnStatementBuilder.buildObject();
        authnStmt.setAuthnInstant(now);
        assertion.getAuthnStatements().add(authnStmt);
        
        Signature signature = signatureBuilder.buildObject();
        signature.setSigningKey(signingKey);
        signature.setCanonicalizationAlgorithm(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        signature.setSignatureAlgorithm(XMLSignature.ALGO_ID_SIGNATURE_RSA);
        signature.getContentReferences().add(new SAMLObjectContentReference(assertion));
        assertion.setSignature(signature);
        
        Marshaller marshaller = marshallerFactory.getMarshaller(assertion);
        marshaller.marshall(assertion);
        Signer.signObject(signature);
        
        //TODO verify signature with new trust engine
    }
}