/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml2.encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.joda.time.DateTime;
import org.opensaml.common.BaseTestCase;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.encryption.EncryptionConstants;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.encryption.EncryptionParameters;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.validation.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *  Tests that decryption of an Assertion does not invalidate the signature of a containing object (Response).
 */
public class DecryptionPlusSigningTest extends BaseTestCase {
    
    private KeyInfoCredentialResolver keyResolver;
    
    private String encURI;
    private EncryptionParameters encParams;
    
    private Encrypter encrypter;
    
    private Credential signingCred;
    
    /**
     * Constructor.
     *
     */
    public DecryptionPlusSigningTest() {
        super();
        
        encURI = EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128;
        
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        Credential encCred = SecurityHelper.generateKeyAndCredential(encURI);
        encCred.getSecretKey();
        keyResolver = new StaticKeyInfoCredentialResolver(encCred);
        encParams = new EncryptionParameters();
        encParams.setAlgorithm(encURI);
        encParams.setEncryptionCredential(encCred);
        
        encrypter = new Encrypter(encParams);
        
        KeyPair kp = SecurityHelper.generateKeyPair("RSA", 1024, null);
        signingCred = SecurityHelper.getSimpleCredential(kp.getPublic(), kp.getPrivate());
        
    }
    
    /**
     * Test decryption of an EncryptedAssertion and validation of the signature on the enclosing Response.
     *  
     * @throws XMLParserException  thrown if there is an error parsing the control XML file
     * @throws EncryptionException  thrown if there is an error encrypting the control XML
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws SecurityException 
     * @throws MarshallingException 
     * @throws SignatureException 
     * @throws UnmarshallingException 
     */
    public void testEncryptedAssertionInResponse() throws XMLParserException, EncryptionException, 
            NoSuchAlgorithmException, NoSuchProviderException, SecurityException, MarshallingException, 
            SignatureException, UnmarshallingException {
        
        //Build encrypted Assertion
        String filename = "/data/org/opensaml/saml2/encryption/Assertion.xml";
        Document targetDOM = getDOM(filename);
        
        Assertion assertion = (Assertion) unmarshallElement(filename);
        EncryptedAssertion encryptedAssertion = encrypter.encrypt(assertion);
        
        // Build Response container
        Response response = (Response) buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
        response.setID("def456");
        response.setIssueInstant(new DateTime());
        
        Issuer issuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue("urn:string:issuer");
        response.setIssuer(issuer);
        
        response.getEncryptedAssertions().add(encryptedAssertion);
        
        // Sign Response
        Signature responseSignature = (Signature) buildXMLObject(Signature.DEFAULT_ELEMENT_NAME);
        responseSignature.setSigningCredential(signingCred);
        response.setSignature(responseSignature);
        SecurityHelper.prepareSignatureParams(responseSignature, signingCred, null, null);
        
        marshallerFactory.getMarshaller(response).marshall(response);
        
        Signer.signObject(responseSignature);
        
        // Marshall Response and re-parse, for good measure
        Element marshalledResponse = marshallerFactory.getMarshaller(response).marshall(response);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLHelper.writeNode(marshalledResponse, baos);
        
        //System.out.println(XMLHelper.prettyPrintXML(marshalledResponse));
        
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Document parsedDoc = parser.parse(bais);
        Element parsedResponse = parsedDoc.getDocumentElement();
        
        Response newResponse = 
            (Response) unmarshallerFactory.getUnmarshaller(parsedResponse).unmarshall(parsedResponse);
        
        // Validate Response signature first time
        SignatureValidator firstSigValidator = new SignatureValidator(signingCred);
        try {
            firstSigValidator.validate(newResponse.getSignature());
        } catch (ValidationException e1) {
            fail("First Response signature validation failed");
        }
        
        // Decrypt Assertion
        EncryptedAssertion newEncryptedAssertion = newResponse.getEncryptedAssertions().get(0);
        
        Decrypter decrypter = new Decrypter(keyResolver, null, null);
        decrypter.setRootInNewDocument(true);
        
        Assertion decryptedAssertion = null;
        try {
            decryptedAssertion = decrypter.decrypt(newEncryptedAssertion);
        } catch (DecryptionException e) {
            fail("Error on decryption of EncryptedAssertion: " + e);
        }
        
        assertNotNull("Decrypted Assertion was null", decryptedAssertion);
        
        assertEquals(targetDOM, decryptedAssertion);
        
        // Validate Response signature second time
        SignatureValidator secondSigValidator = new SignatureValidator(signingCred);
        try {
            secondSigValidator.validate(newResponse.getSignature());
        } catch (ValidationException e1) {
            fail("Second Response signature validation failed");
        }
        
    }
    
    /**
     * Parse the XML file and return the DOM Document.
     * 
     * @param filename file containing control XML
     * @return parsed Document
     * @throws XMLParserException if parser encounters an error
     */
    private Document getDOM(String filename) throws XMLParserException {
        Document targetDOM = parser.parse(DecryptionPlusSigningTest.class.getResourceAsStream(filename));
        return targetDOM;
    }
    
}
