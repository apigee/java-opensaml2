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

package org.opensaml.saml1.core.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.ResponseAbstractType;
import org.opensaml.xml.XMLObject;

/**
 * Abstract implementation of the (abstract) {@link org.opensaml.saml1.core.ResponseAbstractType} Object
 */
public abstract class ResponseAbstractTypeImpl extends AbstractSignableProtocolSAMLObject implements
        ResponseAbstractType {

    /** Contains the ID */
    private String id;
    
   /** Contents of the InResponseTo attribute */
    private String inResponseTo = null;

    /** Contents of the Date attribute */
    private DateTime issueInstant = null;

    /** Contents of the recipient attribute */
    private String recipient = null;

    /**
     * Constructor. Sets namespace to {@link SAMLConstants#SAML1_NS} and prefix to {@link SAMLConstants#SAML1_PREFIX}.
     * Sets the SAML version as specified
     * 
     * @param localName the local name of the element
     * @param version the version to set
     */
    protected ResponseAbstractTypeImpl(String elementLocalName, SAMLVersion version) {
        super(elementLocalName, version);
    }

    /**
     * Constructor. Sets the SAML version to {@link SAMLVersion#VERSION_11}.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected ResponseAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix, SAMLVersion version) {
        super(namespaceURI, elementLocalName, namespacePrefix, version);
    }


    /*
     * @see org.opensaml.saml1.core.ResponseAbstractType#getID()
     */
    public String getID() {
        return id;
    }

    /*
     * @see org.opensaml.saml1.core.ResponseAbstractType#setID(java.lang.String)
     */
    public void setID(String id) {
        this.id = prepareForAssignment(this.id, id);
    }

    
    /*
     * @see org.opensaml.saml1.core.Response#getInResponseTo()
     */
    public String getInResponseTo() {
        return inResponseTo;
    }

    /*
     * @see org.opensaml.saml1.core.Response#setInResponseTo(java.lang.String)
     */
    public void setInResponseTo(String inResponseTo) {
        this.inResponseTo = prepareForAssignment(this.inResponseTo, inResponseTo);
    }

    /*
     * @see org.opensaml.saml1.core.Response#getMinorVersion()
     */
    public int getMinorVersion() {
        if (SAMLVersion.VERSION_11.equals(getVersion())) {
            return 1;
        } else {
            return 0;
        }
    }

    /*
     * @see org.opensaml.saml1.core.Response#getIssueInstant()
     */
    public DateTime getIssueInstant() {

        return issueInstant;
    }

    /*
     * @see org.opensaml.saml1.core.Response#setIssueInstant(java.util.Date)
     */
    public void setIssueInstant(DateTime date) {
        this.issueInstant = prepareForAssignment(this.issueInstant, date);
    }

    /*
     * @see org.opensaml.saml1.core.Response#getRecipient()
     */
    public String getRecipient() {
        return recipient;
    }

    /*
     * @see org.opensaml.saml1.core.Response#setRecipient(java.lang.String)
     */
    public void setRecipient(String recipient) {
        this.recipient = prepareForAssignment(this.recipient, recipient);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        // TODO Signature ?
        return null;
    }
    
    
}