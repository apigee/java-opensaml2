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

import java.util.List;

import javax.xml.namespace.QName;

import javolution.util.FastList;

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.BaseID;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.EncryptedAttribute;
import org.opensaml.saml2.core.EncryptedElementType;
import org.opensaml.saml2.core.EncryptedID;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NewEncryptedID;
import org.opensaml.saml2.core.NewID;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.encryption.CarriedKeyName;
import org.opensaml.xml.encryption.DataReference;
import org.opensaml.xml.encryption.EncryptedData;
import org.opensaml.xml.encryption.EncryptedKey;
import org.opensaml.xml.encryption.EncryptionConstants;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.encryption.EncryptionParameters;
import org.opensaml.xml.encryption.KeyEncryptionParameters;
import org.opensaml.xml.encryption.ReferenceList;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.KeyName;
import org.opensaml.xml.signature.RetrievalMethod;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Class which implements SAML2-specific options for {@link org.opensaml.saml2.core.EncryptedElementType} objects.
 */
public class Encrypter {
    
    // TODO support KEK input as list of recipient(s) + resolver(s) + algorithm(s) + ?
    // - need more key resolver work
    
    // TODO refactor key placement constants as a (nested?) enum? 
    
    // TODO data enc key reuse issue
    // TODO related - KeyInfo etc, object reuse
    
    // TODO need unit test
    
    /** Place the EncryptedKey element(s) as a peer to the EncryptedData inside the EncryptedElementType. */
    public static final int KEY_PLACEMENT_PEER = 0;
    
    /** Place the EncryptedKey element(s) within the KeyInfo of the EncryptedData. */
    public static final int KEY_PLACEMENT_INLINE = 1;
    
    /** Factory for building XMLObject instances. */
    private XMLObjectBuilderFactory builderFactory;
    
    /** Generator for XML ID attribute values. */
    private IdentifierGenerator idGenerator;
    
    /** The parameters to use for encrypting the data. */
    private EncryptionParameters encParams;
    
    /** The parameters to use for encrypting (wrapping) the data encryption key. */
    private List<KeyEncryptionParameters> kekParams;
    
    /** The option for where to place the generated EncryptedKey elements. */
    private int keyPlacement;
    
    /** The XML encrypter instance to use. */
    private org.opensaml.xml.encryption.Encrypter xmlEncrypter;
    
    /** Specifies whether to reuse a generated data encryption key 
     * across multiple calls to a given Encrypter instance. */
    private boolean reuseDataEncryptionKey;

    /** Class logger. */
    private Logger log = Logger.getLogger(Encrypter.class);

    
    /**
     * Constructor.
     *
     * @param dataEncParams the data encryption parameters
     * @param keyEncParams the key encryption parameters
     */
    public Encrypter(EncryptionParameters dataEncParams, List<KeyEncryptionParameters> keyEncParams) {
        init();
        
        this.encParams = dataEncParams;
        this.kekParams = keyEncParams;
    }
 
    /**
     * Constructor.
     *
     * @param dataEncParams the data encryption parameters
     * @param keyEncParam the key encryption parameter
     */
    public Encrypter(EncryptionParameters dataEncParams, KeyEncryptionParameters keyEncParam) {
        init();
        
        List<KeyEncryptionParameters> keks = new FastList<KeyEncryptionParameters>();
        keks.add(keyEncParam);
        
        this.encParams = dataEncParams;
        this.kekParams = keks;
    }
    
    /**
     * Helper method for constructors.
     */
    private void init() {
        builderFactory = Configuration.getBuilderFactory();
        idGenerator = new SecureRandomIdentifierGenerator();
        xmlEncrypter = new org.opensaml.xml.encryption.Encrypter();
        
        keyPlacement = KEY_PLACEMENT_PEER;
        reuseDataEncryptionKey = false;
    }
    
    /**
     * Set the generator to use when creating XML ID attribute values.
     * 
     * @param newIDGenerator the new IdentifierGenerator to use
     */
    public void setIDGenerator(IdentifierGenerator newIDGenerator) {
        this.idGenerator = newIDGenerator;
    }

    /**
     * Get the current key placement option.
     * 
     * @return returns the key placement option.
     */
    public int getKeyPlacement() {
        return this.keyPlacement;
    }

    /**
     * Set the key placement option.
     * 
     * @param newKeyPlacement The new key placement option to set
     */
    public void setKeyPlacement(int newKeyPlacement) {
        this.keyPlacement = newKeyPlacement;
    }

    /**
     * Get the flag determining data encryption key reuse.
     * 
     * @return Returns the data encryption key reuse option
     */
    public boolean reuseDataEncryptionKey() {
        return this.reuseDataEncryptionKey;
    }

    /**
     * Set the flag determining data encryption key reuse.
     * 
     * @param newReuseDataEncryptionKey The new reuseDataEncryptionKey option to set
     */
    public void setReuseDataEncryptionKey(boolean newReuseDataEncryptionKey) {
        this.reuseDataEncryptionKey = newReuseDataEncryptionKey;
    }

    /**
     * Encrypt the specified Assertion.
     * 
     * @param assertion the Assertion to encrypt
     * @return an EncryptedAssertion 
     * @throws EncryptionException thrown when encryption generates an error
     */
    public EncryptedAssertion encrypt(Assertion assertion) throws EncryptionException {
        return (EncryptedAssertion) encrypt(assertion, EncryptedAssertion.DEFAULT_ELEMENT_NAME);
    }

    /**
     * Encrypt the specified Attribute.
     * 
     * @param attribute the Attribute to encrypt
     * @return an EncryptedAttribute
     * @throws EncryptionException thrown when encryption generates an error
     */
    public EncryptedAttribute encrypt(Attribute attribute) throws EncryptionException {
        return (EncryptedAttribute) encrypt(attribute, EncryptedAttribute.DEFAULT_ELEMENT_NAME);
    }

    /**
     * Encrypt the specified NameID.
     * 
     * @param nameID the NameID to encrypt
     * @return an EncryptedID
     * @throws EncryptionException thrown when encryption generates an error
     */
    public EncryptedID encrypt(NameID nameID) throws EncryptionException {
        return (EncryptedID) encrypt(nameID, EncryptedID.DEFAULT_ELEMENT_NAME);
    }

    /**
     * Encrypt the specified BaseID.
     * 
     * @param baseID the BaseID to encrypt
     * @return an EncryptedID
     * @throws EncryptionException thrown when encryption generates an error
     */
    public EncryptedID encrypt(BaseID baseID) throws EncryptionException {
        return (EncryptedID) encrypt(baseID, EncryptedID.DEFAULT_ELEMENT_NAME);
    }

    /**
     * Encrypt the specified NewID.
     * 
     * @param newID the NewID to encrypt
     * @return a NewEncryptedID
     * @throws EncryptionException thrown when encryption generates an error
     */
    public NewEncryptedID encrypt(NewID newID) throws EncryptionException {
        return (NewEncryptedID) encrypt(newID, NewEncryptedID.DEFAULT_ELEMENT_NAME);
    }
    
    /**
     * Encrypt the specified XMLObject, and return it as an instance of the specified QName,
     * which should be one of the types derived from {@link org.opensaml.saml2.core.EncryptedElementType}.
     * 
     * @param xmlObject the XMLObject to encrypt
     * @param encElementName the QName of the specialization of EncryptedElementType to return
     * @return a specialization of {@link org.opensaml.saml2.core.EncryptedElementType}
     * @throws EncryptionException thrown when encryption generates an error
     */
    protected EncryptedElementType encrypt(XMLObject xmlObject, QName encElementName) throws EncryptionException {
        EncryptedElementType encElement = 
            (EncryptedElementType) builderFactory.getBuilder(encElementName).buildObject(encElementName);
        
        try {
            if (encParams.getEncryptionKey() == null) {
                encParams.setEncryptionKey(xmlEncrypter.generateEncryptionKey(encParams.getAlgorithm()));
            }
        } catch (EncryptionException e) {
            log.error("Encrypter could not generate the data encryption key: ", e);
            throw e;
        }
        
        EncryptedData encData = encryptElement(xmlObject);
        List<EncryptedKey> encKeys = encryptKeys();
        
        if (!reuseDataEncryptionKey()) {
            encParams.setEncryptionKey(null);
        }
        
        // First ensure certain elements/attributes are non-null, common to all cases.
        if (encData.getID() == null) {
            encData.setID(idGenerator.generateIdentifier());
        }
        
        // If not doing key wrapping, just return the encrypted element
        if (encKeys.isEmpty()) {
            encElement.setEncryptedData(encData);
            return encElement;
        }
        
        if (encData.getKeyInfo() == null) {
            KeyInfo dataKeyInfo = 
                (KeyInfo) builderFactory
                    .getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME).buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
            encData.setKeyInfo(dataKeyInfo);
        }
        
        for (EncryptedKey encKey : encKeys) {
            if (encKey.getID() == null) {
                encKey.setID(idGenerator.generateIdentifier());
            }
        }
        
        switch (keyPlacement) {
            case KEY_PLACEMENT_INLINE:
                // If key placement is inline, just embed the keys inline and return the new element.
                encData.getKeyInfo().getEncryptedKeys().addAll(encKeys);
                encElement.setEncryptedData(encData);
                return encElement;
            case KEY_PLACEMENT_PEER:
                // If key placement is peer, follow SAML Errata E43 guidelines for forward and back referencing
                // between encrypted keys and encrytped data.
                return placeKeysAsPeers(encElement, encData, encKeys);
            default:
                //TODO 
                return null;
        }
        
    }
    
    /**
     * Store the specified EncryptedData and EncryptedKey(s) in the specified instance of EncryptedElementType
     * as peer elements, following SAML 2 Errata E43 guidelines for forward and back referencing between the
     * EncryptedData and EncryptedKey(s).
     * 
     * @param encElement a specialization of EncryptedElementType to store the encrypted data and keys
     * @param encData the EncryptedData to store
     * @param encKeys the EncryptedKey(s) to store
     * @return the resulting specialization of EncryptedElementType
     */
    protected EncryptedElementType placeKeysAsPeers(EncryptedElementType encElement, EncryptedData encData, List<EncryptedKey> encKeys) {
        
        for (EncryptedKey encKey : encKeys) {
            if (encKey.getReferenceList() == null) {
                ReferenceList rl = 
                    (ReferenceList) builderFactory
                        .getBuilder(ReferenceList.DEFAULT_ELEMENT_NAME).buildObject(ReferenceList.DEFAULT_ELEMENT_NAME);
                encKey.setReferenceList(rl);
            }
        }
        
        // If there is only 1 EncryptedKey we have a simple forward reference (RetrievalMethod) 
        // and back reference (ReferenceList/DataReference) requirement.
        if (encKeys.size() == 1) {
            EncryptedKey encKey = encKeys.get(0);
            
            // Forward reference from EncryptedData to the EncryptedKey
            RetrievalMethod rm = 
                (RetrievalMethod) builderFactory
                    .getBuilder(RetrievalMethod.DEFAULT_ELEMENT_NAME).buildObject(RetrievalMethod.DEFAULT_ELEMENT_NAME);
            rm.setURI("#" + encKey.getID());
            rm.setType(EncryptionConstants.TYPE_ENCRYPTED_KEY);
            encData.getKeyInfo().getRetrievalMethods().add(rm);
            
            // Back reference from the EncryptedKey to the EncryptedData
            DataReference dr = 
                (DataReference) builderFactory
                    .getBuilder(DataReference.DEFAULT_ELEMENT_NAME).buildObject(DataReference.DEFAULT_ELEMENT_NAME);
            dr.setURI("#" + encData.getID());
            encKey.getReferenceList().getDataReferences().add(dr);
            
        } else if (encKeys.size() > 1) {
            // Get the name of the data encryption key
            List<KeyName> dataEncKeyNames = encData.getKeyInfo().getKeyNames();
            String carriedKeyNameValue;
            if (dataEncKeyNames.size() == 0  || DatatypeHelper.isEmpty(dataEncKeyNames.get(0).getValue()) ) {
                // TODO - should we do this, or just not use CarriedKeyName at all - what are SAML recs?
                //
                // If there isn't one, autogenerate a random key name
                String keyNameValue = idGenerator.generateIdentifier();
                
                KeyName keyName = dataEncKeyNames.get(0);
                if (keyName == null) {
                    keyName = (KeyName) builderFactory
                        .getBuilder(KeyName.DEFAULT_ELEMENT_NAME).buildObject(KeyName.DEFAULT_ELEMENT_NAME);
                    dataEncKeyNames.add(keyName);
                }
                keyName.setValue(keyNameValue);
                carriedKeyNameValue = keyNameValue;
            } else {
                carriedKeyNameValue = dataEncKeyNames.get(0).getValue();
            }
            
            for (EncryptedKey encKey : encKeys) {
                // Set carried key name of the multicast key
                if (encKey.getCarriedKeyName() == null) {
                    CarriedKeyName ckn = (CarriedKeyName) builderFactory
                        .getBuilder(CarriedKeyName.DEFAULT_ELEMENT_NAME)
                        .buildObject(CarriedKeyName.DEFAULT_ELEMENT_NAME);
                    encKey.setCarriedKeyName(ckn);
                }
                encKey.getCarriedKeyName().setValue(carriedKeyNameValue);
                
                // Back reference from the EncryptedKeys to the EncryptedData
                DataReference dr = 
                    (DataReference) builderFactory
                    .getBuilder(DataReference.DEFAULT_ELEMENT_NAME).buildObject(DataReference.DEFAULT_ELEMENT_NAME);
                dr.setURI("#" + encData.getID());
                encKey.getReferenceList().getDataReferences().add(dr);
                
            }
        }
        
        encElement.setEncryptedData(encData);
        encElement.getEncryptedKeys().addAll(encKeys);
        
        return encElement;
    }

    /**
     * Encrypt the passed XMLObject, using the data encryption parameters previously specified.
     * @param xmlObject the object to encrypt
     * @return the EncryptedData representing the encrypted XMLObject
     * @throws EncryptionException thrown when the encrypter encounters an error
     */
    protected EncryptedData encryptElement(XMLObject xmlObject) throws EncryptionException {
        EncryptedData encData =  null;
        try {
            encData =  xmlEncrypter.encryptElement(xmlObject, encParams, null);
        } catch (EncryptionException e) {
            log.error("Encrypter could not encrypt the XMLObject: ", e);
            throw e;
        }
        
        return encData;
    }
    
    /**
     * Generate the wrapped encryption keys, using the data encryption key and key encryption
     * parameters previously specified.
     * 
     * @return the list of EncryptedKey's
     * @throws EncryptionException thrown when the encrypter encounters an error
     */
    protected List<EncryptedKey> encryptKeys() throws EncryptionException {
        List<EncryptedKey> encKeys = new FastList<EncryptedKey>();
        
        //TODO need/check document context
        for (KeyEncryptionParameters kekParam: kekParams) {
            EncryptedKey encKey = null;
            try {
                encKey = xmlEncrypter.encryptKey(encParams.getEncryptionKey(), kekParam, null);
            } catch (EncryptionException e) {
                log.error("Encrypter could not encrypt the data encryption key: ", e);
                throw e;
            }
            encKeys.add(encKey);
        }
        return encKeys;
    }

}
