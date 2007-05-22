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
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

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
import org.opensaml.xml.encryption.impl.CarriedKeyNameBuilder;
import org.opensaml.xml.encryption.impl.DataReferenceBuilder;
import org.opensaml.xml.encryption.impl.ReferenceListBuilder;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.KeyName;
import org.opensaml.xml.signature.RetrievalMethod;
import org.opensaml.xml.signature.impl.KeyInfoBuilder;
import org.opensaml.xml.signature.impl.KeyNameBuilder;
import org.opensaml.xml.signature.impl.RetrievalMethodBuilder;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Document;

// TODO more documentation needed.
// TODO need logging
/**
 * Encrypter for SAML 2 SAMLObjects which has specific options for generating instances of subtypes of
 * {@link org.opensaml.saml2.core.EncryptedElementType}.
 */
public class Encrypter extends org.opensaml.xml.encryption.Encrypter {

    /**
     * Options for where to place the resulting EncryptedKey elements with respect
     * to the associated EncryptedData element.
     */
    public enum KeyPlacement {
        /** Place the EncryptedKey element(s) as a peer to the EncryptedData inside the EncryptedElementType. */
        PEER,
    
        /** Place the EncryptedKey element(s) within the KeyInfo of the EncryptedData. */
        INLINE
    }
    
    /** Factory for building XMLObject instances. */
    private XMLObjectBuilderFactory builderFactory;
    
    /** Builder for KeyInfo objects. */
    private KeyInfoBuilder keyInfoBuilder;
    
    /** Builder for DataReference objects. */
    private DataReferenceBuilder dataReferenceBuilder;
    
    /** Builder for ReferenceList objects. */
    private ReferenceListBuilder referenceListBuilder;
    
    /** Builder for RetrievalMethod objects. */
    private RetrievalMethodBuilder retrievalMethodBuilder;
    
    /** Builder for KeyName objects. */
    private KeyNameBuilder keyNameBuilder;
    
    /** Builder for CarriedKeyName objects. */
    private CarriedKeyNameBuilder carriedKeyNameBuilder;
    
    /** Generator for XML ID attribute values. */
    private IdentifierGenerator idGenerator;
    
    /** The parameters to use for encrypting the data. */
    private EncryptionParameters encParams;
    
    /** The parameters to use for encrypting (wrapping) the data encryption key. */
    private List<KeyEncryptionParameters> kekParamsList;
    
    /** The option for where to place the generated EncryptedKey elements. */
    private KeyPlacement keyPlacement;

    /** Class logger. */
    private Logger log = Logger.getLogger(Encrypter.class);

    
    /**
     * Constructor.
     *
     * @param dataEncParams the data encryption parameters
     * @param keyEncParams the key encryption parameters
     */
    public Encrypter(EncryptionParameters dataEncParams, List<KeyEncryptionParameters> keyEncParams) {
        super();
        
        this.encParams = dataEncParams;
        this.kekParamsList = keyEncParams;
        
        init();
    }
 
    /**
     * Constructor.
     *
     * @param dataEncParams the data encryption parameters
     * @param keyEncParam the key encryption parameter
     */
    public Encrypter(EncryptionParameters dataEncParams, KeyEncryptionParameters keyEncParam) {
        super();
        
        List<KeyEncryptionParameters> keks = new ArrayList<KeyEncryptionParameters>();
        keks.add(keyEncParam);
        
        this.encParams = dataEncParams;
        this.kekParamsList = keks;
        
        init();
    }
    
    /**
     * Constructor.
     *
     * @param dataEncParams the data encryption parameters
     */
    public Encrypter(EncryptionParameters dataEncParams) {
        super();
        
        List<KeyEncryptionParameters> keks = new ArrayList<KeyEncryptionParameters>();
        
        this.encParams = dataEncParams;
        this.kekParamsList = keks;
        
        init();
    }
    
    /**
     * Helper method for constructors.
     */
    private void init() {
        builderFactory = Configuration.getBuilderFactory();
        keyInfoBuilder = 
            (KeyInfoBuilder) builderFactory.getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
        dataReferenceBuilder = 
            (DataReferenceBuilder) builderFactory.getBuilder(DataReference.DEFAULT_ELEMENT_NAME);
        referenceListBuilder = 
            (ReferenceListBuilder) builderFactory.getBuilder(ReferenceList.DEFAULT_ELEMENT_NAME);
        retrievalMethodBuilder = 
            (RetrievalMethodBuilder) builderFactory.getBuilder(RetrievalMethod.DEFAULT_ELEMENT_NAME);
        keyNameBuilder = 
            (KeyNameBuilder) builderFactory.getBuilder(KeyName.DEFAULT_ELEMENT_NAME);
        carriedKeyNameBuilder = 
            (CarriedKeyNameBuilder) builderFactory.getBuilder(CarriedKeyName.DEFAULT_ELEMENT_NAME);
        
        idGenerator = new SecureRandomIdentifierGenerator();
        
        keyPlacement = KeyPlacement.PEER;
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
    public KeyPlacement getKeyPlacement() {
        return this.keyPlacement;
    }

    /**
     * Set the key placement option.
     * 
     * @param newKeyPlacement The new key placement option to set
     */
    public void setKeyPlacement(KeyPlacement newKeyPlacement) {
        this.keyPlacement = newKeyPlacement;
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
     * Encrypt the specified Assertion, treating as an identifier and returning
     * an EncryptedID.
     * 
     * @param assertion the Assertion to encrypt
     * @return an EncryptedID 
     * @throws EncryptionException thrown when encryption generates an error
     */
    public EncryptedID encryptAsID(Assertion assertion) throws EncryptionException {
        return (EncryptedID) encrypt(assertion, EncryptedID.DEFAULT_ELEMENT_NAME);
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
    private EncryptedElementType encrypt(XMLObject xmlObject, QName encElementName) throws EncryptionException {
        
        checkParams(encParams, kekParamsList);
       
        EncryptedElementType encElement = 
            (EncryptedElementType) builderFactory.getBuilder(encElementName).buildObject(encElementName);
        
        // Marshall the containing element, we will need its Document context to pass 
        // to the key encryption method
        checkAndMarshall(encElement);
        Document ownerDocument = encElement.getDOM().getOwnerDocument();
        
        String encryptionAlgorithmURI = encParams.getAlgorithm();
        Key encryptionKey = SecurityHelper.extractEncryptionKey(encParams.getEncryptionCredential());
        if (encryptionKey == null) {
            encryptionKey = generateEncryptionKey(encryptionAlgorithmURI);
        }
        
        EncryptedData encryptedData = encryptElement(xmlObject, encryptionKey, encryptionAlgorithmURI, false);
        if (encParams.getKeyInfoGenerator() != null) {
            KeyInfoGenerator generator = encParams.getKeyInfoGenerator();
            try {
                encryptedData.setKeyInfo( generator.generate(encParams.getEncryptionCredential()) );
            } catch (SecurityException e) {
                throw new EncryptionException("Error generating EncryptedData KeyInfo", e);
            }
        }
        
        List<EncryptedKey> encryptedKeys = new ArrayList<EncryptedKey>();
        if (kekParamsList != null && ! kekParamsList.isEmpty()) {
            encryptedKeys.addAll( encryptKey(encryptionKey, kekParamsList, ownerDocument) );
        }
        
        return processElements(encElement, encryptedData, encryptedKeys);
    }

    /**
     * Handle post-processing of generated EncryptedData and EncryptedKey(s) and storage in the appropriate
     * EncryptedElementType instance.
     * 
     * @param encElement the EncryptedElementType instance which will hold the encrypted data and keys
     * @param encData the EncryptedData object
     * @param encKeys the list of EncryptedKey objects
     * @return the processed EncryptedElementType instance
     * 
     * @throws EncryptionException thrown when processing encounters an error
     */
    protected EncryptedElementType processElements(EncryptedElementType encElement,
            EncryptedData encData, List<EncryptedKey> encKeys) throws EncryptionException {
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
            encData.setKeyInfo(keyInfoBuilder.buildObject());
        }
        
        for (EncryptedKey encKey : encKeys) {
            if (encKey.getID() == null) {
                encKey.setID(idGenerator.generateIdentifier());
            }
        }
        
        switch (keyPlacement) {
            case INLINE:
                return placeKeysInline(encElement, encData, encKeys);
            case PEER:
                return placeKeysAsPeers(encElement, encData, encKeys);
            default:
                //Shouldn't be able to get here, but just in case...
                throw new EncryptionException("Unsupported key placement option was specified: " + keyPlacement);
        }
    }

    /**
     * Place the EncryptedKey elements inside the KeyInfo element within the EncryptedData element.
     * 
     * Although operationally trivial, this method is provided so that subclasses may 
     * override or augment as desired.
     * 
     * @param encElement the EncryptedElementType instance which will hold the encrypted data and keys
     * @param encData the EncryptedData object
     * @param encKeys the list of EncryptedKey objects
     * @return the processed EncryptedElementType instance
     */
    protected EncryptedElementType placeKeysInline(EncryptedElementType encElement,
            EncryptedData encData, List<EncryptedKey> encKeys) {
        
        encData.getKeyInfo().getEncryptedKeys().addAll(encKeys);
        encElement.setEncryptedData(encData);
        return encElement;
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
    protected EncryptedElementType placeKeysAsPeers(EncryptedElementType encElement,
            EncryptedData encData, List<EncryptedKey> encKeys) {
        
        for (EncryptedKey encKey : encKeys) {
            if (encKey.getReferenceList() == null) {
                encKey.setReferenceList(referenceListBuilder.buildObject());
            }
        }
        
        // If there is only 1 EncryptedKey we have a simple forward reference (RetrievalMethod) 
        // and back reference (ReferenceList/DataReference) requirement.
        // Multiple "multicast" keys use back reference + CarriedKeyName
        if (encKeys.size() == 1) {
            linkSinglePeerKey(encData, encKeys.get(0));
        } else if (encKeys.size() > 1) {
            linkMultiplePeerKeys(encData, encKeys);
        }
        
        encElement.setEncryptedData(encData);
        encElement.getEncryptedKeys().addAll(encKeys);
        
        return encElement;
    }
    
    /**
     * Link a single EncryptedKey to the EncryptedData according to guidelines in SAML Errata E43.
     * 
     * @param encData the EncryptedData
     * @param encKey the EncryptedKey
     */
    protected void linkSinglePeerKey(EncryptedData encData, EncryptedKey encKey) {
        // Forward reference from EncryptedData to the EncryptedKey
        RetrievalMethod rm = retrievalMethodBuilder.buildObject();
        rm.setURI("#" + encKey.getID());
        rm.setType(EncryptionConstants.TYPE_ENCRYPTED_KEY);
        encData.getKeyInfo().getRetrievalMethods().add(rm);
        
        // Back reference from the EncryptedKey to the EncryptedData
        DataReference dr = dataReferenceBuilder.buildObject();
        dr.setURI("#" + encData.getID());
        encKey.getReferenceList().getDataReferences().add(dr);
    }

    /**
     * Link multiple "multicast" EncryptedKeys to the EncryptedData according 
     * to guidelines in SAML Errata E43.
     * 
     * @param encData the EncryptedData
     * @param encKeys the list of EncryptedKeys
     */
    protected void linkMultiplePeerKeys(EncryptedData encData, List<EncryptedKey> encKeys) {
        // Get the name of the data encryption key
        List<KeyName> dataEncKeyNames = encData.getKeyInfo().getKeyNames();
        String carriedKeyNameValue;
        if (dataEncKeyNames.size() == 0  || DatatypeHelper.isEmpty(dataEncKeyNames.get(0).getValue()) ) {
            // If there isn't one, autogenerate a random key name.
            // TODO - should we do this, or just not use CarriedKeyName at all - what are SAML recs?
            String keyNameValue = idGenerator.generateIdentifier();
            
            KeyName keyName = dataEncKeyNames.get(0);
            if (keyName == null) {
                keyName = keyNameBuilder.buildObject();
                dataEncKeyNames.add(keyName);
            }
            keyName.setValue(keyNameValue);
            carriedKeyNameValue = keyNameValue;
        } else {
            carriedKeyNameValue = dataEncKeyNames.get(0).getValue();
        }
        
        // Set carried key name of the multicast key in each EncryptedKey
        for (EncryptedKey encKey : encKeys) {
            if (encKey.getCarriedKeyName() == null) {
                encKey.setCarriedKeyName(carriedKeyNameBuilder.buildObject());
            }
            encKey.getCarriedKeyName().setValue(carriedKeyNameValue);
            
            // Back reference from the EncryptedKeys to the EncryptedData
            DataReference dr = dataReferenceBuilder.buildObject();
            dr.setURI("#" + encData.getID());
            encKey.getReferenceList().getDataReferences().add(dr);
            
        }
    }

}
