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

package org.opensaml.saml1.core.impl;

import java.util.GregorianCalendar;

import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.saml1.core.ResponseAbstractType;

/**
 * Abstract implementation of the (abstract) {@link org.opensaml.saml1.core.ResponseAbstractType} Object
 */
public abstract class ResponseAbstractTypeImpl extends AbstractSignableSAMLObject implements ResponseAbstractType {

    /**
     * Constructor
     *
     * @param namespaceURI
     * @param elementLocalName
     */
    protected ResponseAbstractTypeImpl(String namespaceURI, String elementLocalName) {
        super(namespaceURI, elementLocalName);
    }

    /** Contents of the InResponseTo attribute */
    private String inResponseTo = null;

    /** Minor Version of this element */
    private int minorVersion = 0;

    /** Contents of the Date attribute */
    private GregorianCalendar issueInstant = null;

    /** Contents of the recipient attribute */
    private String recipient = null;

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
    public GregorianCalendar getIssueInstant() {

        return issueInstant;
    }

    /*
     * @see org.opensaml.saml1.core.Response#setIssueInstant(java.util.Date)
     */
    public void setIssueInstant(GregorianCalendar date) {
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
}
