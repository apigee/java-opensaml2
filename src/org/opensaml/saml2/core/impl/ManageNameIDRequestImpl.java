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

/**
 * 
 */

package org.opensaml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.saml2.core.EncryptedID;
import org.opensaml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NewEncryptedID;
import org.opensaml.saml2.core.NewID;
import org.opensaml.saml2.core.Terminate;
import org.opensaml.xml.XMLObject;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.ManageNameIDRequest}
 */
public class ManageNameIDRequestImpl extends RequestImpl implements ManageNameIDRequest {

    // TODO EncryptedID and NewEncryptedID stuff may change, pending Chad's encryption implementation

    /** NameID child element */
    private NameID nameID;

    /** EncryptedID child element */
    private EncryptedID encryptedID;

    /** NewID child element */
    private NewID newID;

    /** NameID child element */
    private NewEncryptedID newEncryptedID;

    /** Terminate child element */
    private Terminate terminate;

    /**
     * Constructor
     * 
     */
    protected ManageNameIDRequestImpl() {
        super(ManageNameIDRequest.LOCAL_NAME);
    }

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected ManageNameIDRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#getNameID()
     */
    public NameID getNameID() {
        return this.nameID;
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#setNameID(org.opensaml.saml2.core.NameID)
     */
    public void setNameID(NameID newNameID) {
        this.nameID = prepareForAssignment(this.nameID, newNameID);
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#getEncryptedID()
     */
    public EncryptedID getEncryptedID() {
        return this.encryptedID;
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#setEncryptedID(org.opensaml.saml2.core.EncryptedID)
     */
    public void setEncryptedID(EncryptedID newEncryptedID) {
        this.encryptedID = prepareForAssignment(this.encryptedID, newEncryptedID);
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#getNewID()
     */
    public NewID getNewID() {
        return this.newID;
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#setNewID(org.opensaml.saml2.core.NewID)
     */
    public void setNewID(NewID newNewID) {
        this.newID = prepareForAssignment(this.newID, newNewID);
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#getNewEncryptedID()
     */
    public NewEncryptedID getNewEncryptedID() {
        return this.newEncryptedID;
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#setNewEncryptedID(org.opensaml.saml2.core.NewEncryptedID)
     */
    public void setNewEncryptedID(NewEncryptedID newNewEncryptedID) {
        this.newEncryptedID = prepareForAssignment(this.newEncryptedID, newNewEncryptedID);
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#getTerminate()
     */
    public Terminate getTerminate() {
        return this.terminate;
    }

    /**
     * @see org.opensaml.saml2.core.ManageNameIDRequest#setTerminate(org.opensaml.saml2.core.Terminate)
     */
    public void setTerminate(Terminate newTerminate) {
        this.terminate = prepareForAssignment(this.terminate, newTerminate);
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());
        if (nameID != null)
            children.add(nameID);
        if (encryptedID != null)
            children.add(encryptedID);
        if (newID != null)
            children.add(newID);
        if (newEncryptedID != null)
            children.add(newEncryptedID);
        if (terminate != null)
            children.add(terminate);

        if (children.size() == 0)
            return null;

        return Collections.unmodifiableList(children);
    }

}
