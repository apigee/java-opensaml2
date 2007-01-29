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

package org.opensaml.saml2.encryption;

import java.security.Key;
import java.util.List;

import javolution.util.FastList;

import org.opensaml.common.BaseTestCase;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.EncryptedAttribute;
import org.opensaml.saml2.core.EncryptedID;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NewEncryptedID;
import org.opensaml.saml2.core.NewID;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.EncryptionConstants;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.encryption.EncryptionParameters;
import org.opensaml.xml.encryption.EncryptionTestHelper;
import org.opensaml.xml.encryption.KeyEncryptionParameters;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.KeyName;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Simple tests for encryption.
 */
public class SimpleEncryptionTest extends BaseTestCase {
    
    private Encrypter encrypter;
    private EncryptionParameters encParams;
    private List<KeyEncryptionParameters> kekParams;
    
    private KeyInfo keyInfo;
    
    private String algoURI;
    private String expectedKeyName;

    /**
     * Constructor.
     *
     */
    public SimpleEncryptionTest() {
        super();
        
        expectedKeyName = "SuperSecretKey";
        algoURI = EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128;
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        Key encKey = EncryptionTestHelper.generateKey(algoURI);
        encParams = new EncryptionParameters();
        encParams.setAlgorithm(algoURI);
        encParams.setEncryptionKey(encKey);
        
        kekParams = new FastList<KeyEncryptionParameters>();
        
        keyInfo = (KeyInfo) buildXMLObject(org.opensaml.xml.signature.KeyInfo.DEFAULT_ELEMENT_NAME);
        
    }

    /**
     *  Test basic encryption with symmetric key, no key wrap,
     *  set key name in passed KeyInfo object.
     */
    public void testAssertion() {
        Assertion target = (Assertion) unmarshallElement("/data/org/opensaml/saml2/encryption/Assertion.xml");
        
        KeyName keyName = (KeyName) buildXMLObject(org.opensaml.xml.signature.KeyName.DEFAULT_ELEMENT_NAME);
        keyName.setValue(expectedKeyName);
        keyInfo.getKeyNames().add(keyName);
        encParams.setKeyInfo(keyInfo);
        
        encrypter = new Encrypter(encParams, kekParams);
        
        EncryptedAssertion encTarget = null;
        XMLObject encObject = null;
        try {
            encObject = encrypter.encrypt(target);
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertNotNull("Encrypted object was null", encObject);
        assertTrue("Encrypted object was not an instance of the expected type", 
                encObject instanceof EncryptedAssertion);
        encTarget = (EncryptedAssertion) encObject;
        
        assertEquals("Type attribute", EncryptionConstants.TYPE_ELEMENT, encTarget.getEncryptedData().getType());
        assertEquals("Algorithm attribute", algoURI, 
                encTarget.getEncryptedData().getEncryptionMethod().getAlgorithm());
        assertNotNull("KeyInfo", encTarget.getEncryptedData().getKeyInfo());
        assertEquals("KeyName", expectedKeyName, 
                encTarget.getEncryptedData().getKeyInfo().getKeyNames().get(0).getValue());
        
        assertEquals("Number of EncryptedKeys", 0, 
                encTarget.getEncryptedData().getKeyInfo().getEncryptedKeys().size());
        
        assertFalse("EncryptedData ID attribute was empty",
                DatatypeHelper.isEmpty(encTarget.getEncryptedData().getID()));
    }
    
    /**
     *  Test basic encryption with symmetric key, no key wrap,
     *  set key name in passed KeyInfo object.
     */
    public void testNameID() {
        Assertion assertion = (Assertion) unmarshallElement("/data/org/opensaml/saml2/encryption/Assertion.xml");
        NameID target = assertion.getSubject().getNameID();
        
        KeyName keyName = (KeyName) buildXMLObject(org.opensaml.xml.signature.KeyName.DEFAULT_ELEMENT_NAME);
        keyName.setValue(expectedKeyName);
        keyInfo.getKeyNames().add(keyName);
        encParams.setKeyInfo(keyInfo);
        
        encrypter = new Encrypter(encParams, kekParams);
        
        EncryptedID encTarget = null;
        XMLObject encObject = null;
        try {
            encObject = encrypter.encrypt(target);
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertNotNull("Encrypted object was null", encObject);
        assertTrue("Encrypted object was not an instance of the expected type", 
                encObject instanceof EncryptedID);
        encTarget = (EncryptedID) encObject;
        
        assertEquals("Type attribute", EncryptionConstants.TYPE_ELEMENT, encTarget.getEncryptedData().getType());
        assertEquals("Algorithm attribute", algoURI, 
                encTarget.getEncryptedData().getEncryptionMethod().getAlgorithm());
        assertNotNull("KeyInfo", encTarget.getEncryptedData().getKeyInfo());
        assertEquals("KeyName", expectedKeyName, 
                encTarget.getEncryptedData().getKeyInfo().getKeyNames().get(0).getValue());
        
        assertEquals("Number of EncryptedKeys", 0, 
                encTarget.getEncryptedData().getKeyInfo().getEncryptedKeys().size());
        
        assertFalse("EncryptedData ID attribute was empty",
                DatatypeHelper.isEmpty(encTarget.getEncryptedData().getID()));
    }
    
    /**
     *  Test basic encryption with symmetric key, no key wrap,
     *  set key name in passed KeyInfo object.
     */
    public void testAttribute() {
        Assertion assertion = (Assertion) unmarshallElement("/data/org/opensaml/saml2/encryption/Assertion.xml");
        Attribute target = assertion.getAttributeStatements().get(0).getAttributes().get(0);
        
        
        KeyName keyName = (KeyName) buildXMLObject(org.opensaml.xml.signature.KeyName.DEFAULT_ELEMENT_NAME);
        keyName.setValue(expectedKeyName);
        keyInfo.getKeyNames().add(keyName);
        encParams.setKeyInfo(keyInfo);
        
        encrypter = new Encrypter(encParams, kekParams);
        
        EncryptedAttribute encTarget = null;
        XMLObject encObject = null;
        try {
            encObject = encrypter.encrypt(target);
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertNotNull("Encrypted object was null", encObject);
        assertTrue("Encrypted object was not an instance of the expected type", 
                encObject instanceof EncryptedAttribute);
        encTarget = (EncryptedAttribute) encObject;
        
        assertEquals("Type attribute", EncryptionConstants.TYPE_ELEMENT, encTarget.getEncryptedData().getType());
        assertEquals("Algorithm attribute", algoURI, 
                encTarget.getEncryptedData().getEncryptionMethod().getAlgorithm());
        assertNotNull("KeyInfo", encTarget.getEncryptedData().getKeyInfo());
        assertEquals("KeyName", expectedKeyName, 
                encTarget.getEncryptedData().getKeyInfo().getKeyNames().get(0).getValue());
        
        assertEquals("Number of EncryptedKeys", 0, 
                encTarget.getEncryptedData().getKeyInfo().getEncryptedKeys().size());
        
        assertFalse("EncryptedData ID attribute was empty",
                DatatypeHelper.isEmpty(encTarget.getEncryptedData().getID()));
    }
    
    /**
     *  Test basic encryption with symmetric key, no key wrap,
     *  set key name in passed KeyInfo object.
     */
    public void testNewID() {
        NewID target = (NewID) buildXMLObject(NewID.DEFAULT_ELEMENT_NAME);
        target.setNewID("SomeNewID");
        
        KeyName keyName = (KeyName) buildXMLObject(org.opensaml.xml.signature.KeyName.DEFAULT_ELEMENT_NAME);
        keyName.setValue(expectedKeyName);
        keyInfo.getKeyNames().add(keyName);
        encParams.setKeyInfo(keyInfo);
        
        encrypter = new Encrypter(encParams, kekParams);
        
        NewEncryptedID encTarget = null;
        XMLObject encObject = null;
        try {
            encObject = encrypter.encrypt(target);
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertNotNull("Encrypted object was null", encObject);
        assertTrue("Encrypted object was not an instance of the expected type", 
                encObject instanceof NewEncryptedID);
        encTarget = (NewEncryptedID) encObject;
        
        assertEquals("Type attribute", EncryptionConstants.TYPE_ELEMENT, encTarget.getEncryptedData().getType());
        assertEquals("Algorithm attribute", algoURI, 
                encTarget.getEncryptedData().getEncryptionMethod().getAlgorithm());
        assertNotNull("KeyInfo", encTarget.getEncryptedData().getKeyInfo());
        assertEquals("KeyName", expectedKeyName, 
                encTarget.getEncryptedData().getKeyInfo().getKeyNames().get(0).getValue());
        
        assertEquals("Number of EncryptedKeys", 0, 
                encTarget.getEncryptedData().getKeyInfo().getEncryptedKeys().size());
        
        assertFalse("EncryptedData ID attribute was empty",
                DatatypeHelper.isEmpty(encTarget.getEncryptedData().getID()));
        
    }
    
    /** Test that valid reuse is allowed, i.e. when no KeyInfo is passed in the encryption parameters. */
    public void testValidReuse() {
        Assertion assertion = (Assertion) unmarshallElement("/data/org/opensaml/saml2/encryption/Assertion.xml");
        
        Attribute target = assertion.getAttributeStatements().get(0).getAttributes().get(0);
        Attribute target2 = assertion.getAttributeStatements().get(0).getAttributes().get(1);
        
        encrypter = new Encrypter(encParams, kekParams);
        
        assertTrue("Encrypter is not reusable, it should be", encrypter.isReusable());
        
        XMLObject encObject = null;
        try {
            encObject = encrypter.encrypt(target);
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertNotNull("Encrypted object was null", encObject);
        assertTrue("Encrypted object was not an instance of the expected type", 
                encObject instanceof EncryptedAttribute);
        
        XMLObject encObject2 = null;
        try {
            encObject2 = encrypter.encrypt(target2);
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertNotNull("Encrypted object was null", encObject2);
        assertTrue("Encrypted object was not an instance of the expected type", 
                encObject2 instanceof EncryptedAttribute);
    }
    
    /** Test that invalid reuse is disallowed, i.e. when a KeyInfo is passed in the encryption parameters. */
    public void testInvalidReuse() {
        Assertion assertion = (Assertion) unmarshallElement("/data/org/opensaml/saml2/encryption/Assertion.xml");
        
        Attribute target = assertion.getAttributeStatements().get(0).getAttributes().get(0);
        Attribute target2 = assertion.getAttributeStatements().get(0).getAttributes().get(1);
        
        KeyName keyName = (KeyName) buildXMLObject(org.opensaml.xml.signature.KeyName.DEFAULT_ELEMENT_NAME);
        keyName.setValue(expectedKeyName);
        keyInfo.getKeyNames().add(keyName);
        encParams.setKeyInfo(keyInfo);
        
        encrypter = new Encrypter(encParams, kekParams);
        
        assertFalse("Encrypter is reusable, it shouldn't be", encrypter.isReusable());
        
        XMLObject encObject = null;
        try {
            encObject = encrypter.encrypt(target);
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertNotNull("Encrypted object was null", encObject);
        assertTrue("Encrypted object was not an instance of the expected type", 
                encObject instanceof EncryptedAttribute);
        
        // This should fail
        XMLObject encObject2 = null;
        try {
            encObject2 = encrypter.encrypt(target2);
            fail("Second call to Encrypter with passed KeyInfo should have failed due to invalid reuse");
        } catch (EncryptionException e) {
            //do nothing, this should fail
        }
    }
    
    /** Test that a data encryption key is auto-generated if it is not supplied. */
    public void testAutoKeyGen() {
       Assertion target = (Assertion) unmarshallElement("/data/org/opensaml/saml2/encryption/Assertion.xml");
       
       encParams.setEncryptionKey(null);
        
        encrypter = new Encrypter(encParams, kekParams);
        
        XMLObject encObject = null;
        try {
            encObject = encrypter.encrypt(target);
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertNotNull("Encrypted object was null", encObject);
        assertTrue("Encrypted object was not an instance of the expected type", 
                encObject instanceof EncryptedAssertion);
    }
    
    /** Tests proper operation of the reuseDataEncryptionKey parameter. */
    public void testKeyResuse() {
       Assertion assertion = (Assertion) unmarshallElement("/data/org/opensaml/saml2/encryption/Assertion.xml");
        
        Attribute target = assertion.getAttributeStatements().get(0).getAttributes().get(0);
        Attribute target2 = assertion.getAttributeStatements().get(0).getAttributes().get(1);
        
        encrypter = new Encrypter(encParams, kekParams);
        
        encrypter.setReuseDataEncryptionKey(true);
        assertTrue("Data encryption key is not reusable, it should be", encrypter.reuseDataEncryptionKey());
        
        Key keyOrig = encParams.getEncryptionKey();
        Key key1 = null, key2 = null;
        XMLObject encObject = null;
        try {
            encObject = encrypter.encrypt(target);
            key1 = encParams.getEncryptionKey();
            encObject = encrypter.encrypt(target);
            key2 = encParams.getEncryptionKey();
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertEquals("Data encryption key wasn't reused properly", keyOrig, key1);
        assertEquals("Data encryption key wasn't reused properly", keyOrig, key2);
        
        encrypter.setReuseDataEncryptionKey(false);
        assertFalse("Data encryption key is reusable, it shouldn't be", encrypter.reuseDataEncryptionKey());
        
        try {
            encObject = encrypter.encrypt(target);
            key1 = encParams.getEncryptionKey();
            encObject = encrypter.encrypt(target);
            key2 = encParams.getEncryptionKey();
        } catch (EncryptionException e) {
            fail("Object encryption failed: " + e);
        }
        
        assertFalse("Data encryption key was reused improperly", keyOrig.equals(key1));
        assertFalse("Data encryption key was reused improperly", keyOrig.equals(key2));
    }
}
