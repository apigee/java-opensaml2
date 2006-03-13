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

import org.joda.time.DateTime;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusResponse;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.StatusResponse}
 */
public abstract class StatusResponseImpl extends AbstractSignableProtocolSAMLObject implements StatusResponse {

    /** ID attribute */
    private String id;

    /** InResponseTo attribute */
    private String inResponseTo;

    /** IssueInstant attribute */
    private DateTime issueInstant;

    /** Destination attribute */
    private String destination;

    /** Consent attribute */
    private String consent;

    /** Issuer child element */
    private Issuer issuer;

    /** Extensions child element */
    private Extensions extensions;

    /** Status child element */
    private Status status;

    /**
     * Constructor
     * 
     * @param elementLocalName
     */
    protected StatusResponseImpl(String elementLocalName) {
        super(elementLocalName);
    }

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected StatusResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#getID()
     */
    public String getID() {
        return this.id;
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#setID(java.lang.String)
     */
    public void setID(String newID) {
        this.id = prepareForAssignment(this.id, newID);
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#getInResponseTo()
     */
    public String getInResponseTo() {
        return this.inResponseTo;
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#setInResponseTo(java.lang.String)
     */
    public void setInResponseTo(String newInResponseTo) {
        this.inResponseTo = prepareForAssignment(this.inResponseTo, newInResponseTo);
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#getIssueInstant()
     */
    public DateTime getIssueInstant() {
        return this.issueInstant;
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#setIssueInstant(org.joda.time.DateTime)
     */
    public void setIssueInstant(DateTime newIssueInstant) {
        this.issueInstant = prepareForAssignment(this.issueInstant, newIssueInstant);
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#getDestination()
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#setDestination(java.lang.String)
     */
    public void setDestination(String newDestination) {
        this.destination = prepareForAssignment(this.destination, newDestination);
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#getConsent()
     */
    public String getConsent() {
        return this.consent;
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#setConsent(java.lang.String)
     */
    public void setConsent(String newConsent) {
        this.consent = prepareForAssignment(this.consent, newConsent);
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#getIssuer()
     */
    public Issuer getIssuer() {
        return this.issuer;
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#setIssuer(org.opensaml.saml2.core.Issuer)
     */
    public void setIssuer(Issuer newIssuer) {
        this.issuer = prepareForAssignment(this.issuer, newIssuer);
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#getExtensions()
     */
    public Extensions getExtensions() {
        return this.extensions;
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#setExtensions(org.opensaml.saml2.core.Extensions)
     */
    public void setExtensions(Extensions newExtensions) {
        this.extensions = prepareForAssignment(this.extensions, newExtensions);
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#getStatus()
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * @see org.opensaml.saml2.core.StatusResponse#setStatus(org.opensaml.saml2.core.Status)
     */
    public void setStatus(Status newStatus) {
        this.status = prepareForAssignment(this.status, newStatus);
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if (issuer != null)
            children.add(issuer);
        // TODO Signature
        if (extensions != null)
            children.add(extensions);
        if (status != null)
            children.add(status);

        if (children.size() == 0)
            return null;

        return Collections.unmodifiableList(children);
    }
}