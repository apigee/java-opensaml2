/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.common;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallerFactory;
import org.opensaml.common.io.MarshallingException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallerFactory;
import org.opensaml.common.util.ElementSerializer;
import org.opensaml.common.util.SerializationException;
import org.opensaml.common.util.xml.DigitalSignatureHelper;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class SAMLObjectSigningTest extends BaseTestCase {

    private static String signedElementFile = "/data/signedElement.xml";

    /** Signing key */
    private PrivateKey signingKey;

    /** Public key sed to validate signature */
    private PublicKey publicKey;

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyGen.generateKeyPair();
        signingKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    /**
     * Tests that a SAML object can be signed and have it's signature validated.
     * @throws SerializationException 
     */
    public void testSAMLObjectSigning() throws SerializationException {
        SAMLObjectBuilder edBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(EntitiesDescriptor.QNAME);
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor) edBuilder.buildObject();

        IdentifierGenerator idGen = new SecureRandomIdentifierGenerator();
        SigningContext dsigCtx = new SigningContext("ID", idGen.generateIdentifier());
        dsigCtx.setSigningKey(signingKey);
        dsigCtx.setPublicKey(publicKey);
        entitiesDescriptor.setSigningContext(dsigCtx);

        Marshaller marshaller = MarshallerFactory.getInstance().getMarshaller(entitiesDescriptor);
        try {
            Element dom = marshaller.marshall(entitiesDescriptor);
            
            System.out.println(ElementSerializer.serialize(dom));

            DigitalSignatureHelper.verifySignature(dom);
        } catch (MarshallingException e) {
            fail("Marshalling failed with the following error: " + e);
        } catch (SignatureException e) {
            fail("XML Digital Signature did not validate: " + e);
        }
    }

    public void testUnmarshallingSignedObject() throws Exception {
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        Document doc = ppMgr.parse(new InputSource(SAMLObjectSigningTest.class.getResourceAsStream(signedElementFile)));
        
        Element signedElement = doc.getDocumentElement();
        Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(signedElement);
        if (unmarshaller == null) {
            fail("Unable to retrieve unmarshaller by DOM Element");
        }

        SignableObject signableSAMLObject = (SignableObject) unmarshaller.unmarshall(signedElement);
        
        assertNotNull(signableSAMLObject.getIdAttributeValue());
        assertNotNull(signableSAMLObject.getSigningContext());
    }
}