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
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.saml1.core.ResponseAbstractType;
import org.opensaml.xml.XMLObject;

/**
 * Abstract implementation of {@link org.opensaml.saml1.core.ResponseAbstractType} Object
 */
public abstract class ResponseAbstractTypeImpl extends AbstractSignableSAMLObject implements ResponseAbstractType {

    /** Contains the ID */
    private String id;

    private SAMLVersion version;

    /** Contents of the InResponseTo attribute */
    private String inResponseTo = null;

    /** Contents of the Date attribute */
    private DateTime issueInstant = null;

    /** Contents of the recipient attribute */
    private String recipient = null;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected ResponseAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        version = SAMLVersion.VERSION_11;
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
        return version.getMinorVersion();
    }

    /*
     * @see org.opensaml.saml1.core.ResponseAbstractType#getMajorVersion()
     */
    public int getMajorVersion() {
        return version.getMajorVersion();
    }

    /*
     * @see org.opensaml.saml1.core.ResponseAbstractType#setVersion(org.opensaml.common.SAMLVersion)
     */
    public void setVersion(SAMLVersion newVersion) {
        version = prepareForAssignment(version, newVersion);
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