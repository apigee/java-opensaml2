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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.ResponseAbstractType;

/**
 * Abstract implementation of the (abstract) {@link org.opensaml.saml1.core.ResponseAbstractType} Object
 */
public abstract class ResponseAbstractTypeImpl extends AbstractSignableProtocolSAMLObject implements
        ResponseAbstractType {

    /** Contains the ID */
    private String id;
    
   /** Contents of the InResponseTo attribute */
    private String inResponseTo = null;

    /** Minor Version of this element */
    private int minorVersion = 0;

    /** Contents of the Date attribute */
    private DateTime issueInstant = null;

    /** Contents of the recipient attribute */
    private String recipient = null;

    /**
     * Constructor. Sets namespace to {@link SAMLConstants#SAML1_NS} and prefix to {@link SAMLConstants#SAML1_PREFIX}.
     * Sets the SAML version to {@link SAMLVersion#VERSION_11}.
     * 
     * @param localName the local name of the element
     */
    protected ResponseAbstractTypeImpl(String elementLocalName) {
        super(elementLocalName);
    }

    /**
     * Constructor. Sets the SAML version to {@link SAMLVersion#VERSION_11}.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected ResponseAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }


    public String getID() {
        return id;
    }

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
        return minorVersion;
    }

    /*
     * @see org.opensaml.saml1.core.Response#setMinorVersion(int)
     */
    public void setMinorVersion(int version) {
        if (version != minorVersion) {
            releaseThisandParentDOM();
            minorVersion = version;
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
        this.issueInstant = prepareForAssignment(this.issueInstant, date.withZone(DateTimeZone.UTC));
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
}