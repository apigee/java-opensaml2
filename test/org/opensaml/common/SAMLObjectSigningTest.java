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

import javax.xml.namespace.QName;

import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.xml.SignableXMLObject;
import org.opensaml.xml.SigningContext;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.util.DigitalSignatureHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Testing SAML object signing and singature verification functionality.
 */
public class SAMLObjectSigningTest extends BaseTestCase {

    private static String signedElementFile = "/data/org/opensaml/common/signedElement.xml";

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
     * 
     * @throws SerializationException
     */
    public void testSAMLObjectSigning(){
        QName entitiesDescriptorQName = new QName(SAMLConstants.SAML20MD_NS, EntitiesDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        SAMLObjectBuilder edBuilder = SAMLObjectManager.getBuilder(entitiesDescriptorQName);
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor) edBuilder.buildObject();

        IdentifierGenerator idGen = new SecureRandomIdentifierGenerator();
        SigningContext dsigCtx = new SigningContext("ID", idGen.generateIdentifier());
        dsigCtx.setSigningKey(signingKey);
        dsigCtx.setPublicKey(publicKey);
        entitiesDescriptor.setSigningContext(dsigCtx);

        Marshaller marshaller = SAMLObjectManager.getMarshaller(entitiesDescriptor);
        try {
            Element dom = marshaller.marshall(entitiesDescriptor, ParserPoolManager.getInstance().newDocument());
            DigitalSignatureHelper.verifySignature(dom);
        } catch (MarshallingException e) {
            fail("Marshalling failed with the following error: " + e);
        } catch (SignatureException e) {
            fail("XML Digital Signature did not validate: " + e);
        } catch (Exception e) {
            fail("Could not create DOM Document to root marshalled element in: " + e);
        }
    }

    /**
     * Tests unmarshalling an element and verifying the signature on it.
     */
    public void testUnmarshallingSignedObject(){
        try {
            ParserPoolManager ppMgr = ParserPoolManager.getInstance();
            Document doc = ppMgr.parse(new InputSource(SAMLObjectSigningTest.class.getResourceAsStream(signedElementFile)));
            
            Element signedElement = doc.getDocumentElement();
            Unmarshaller unmarshaller = SAMLObjectManager.getUnmarshaller(signedElement);
            if (unmarshaller == null) {
                fail("Unable to retrieve unmarshaller by DOM Element");
            }

            SignableXMLObject signableSAMLObject = (SignableXMLObject) unmarshaller.unmarshall(signedElement);

            assertNotNull(signableSAMLObject.getSigningContext());
            assertNotNull(signableSAMLObject.getSigningContext().getIdAttributeValue());
        } catch (XMLParserException e) {
            fail("Unable to parse file containing single EntitiesDescriptor element");
        } catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown but example element does not contain any unknown attributes: " + e);
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown but example element does not contain any child elements");
        } catch (UnmarshallingException e) {
            fail("Unmarshalling failed with the following error:" + e);
        }
    }
}