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
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Request;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Request}
 */
public abstract class RequestImpl extends AbstractSignableSAMLObject implements Request {

    /** SAML Version of the request */
    private SAMLVersion version;
    
    /** Unique identifier of the request */
    private String id;

    /** Date/time request was issued */
    private DateTime issueInstant;

    /** URI of the request destination */
    private String destination;

    /** URI of the SAML user consent type */
    private String consent;

    /** URI of the SAML user consent type */
    private Issuer issuer;

    /** Extensions child element */
    private Extensions extensions;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected RequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml2.core.Request#getVersion()
     */
    public SAMLVersion getVersion() {
        return version;
    }

    /*
     * @see org.opensaml.saml2.core.Request#setVersion(org.opensaml.common.SAMLVersion)
     */
    public void setVersion(SAMLVersion newVersion) {
        this.version = prepareForAssignment(this.version, newVersion);
    }
    
    /**
     * @see org.opensaml.saml2.core.Request#getID()
     */
    public String getID() {
        return id;
    }

    /**
     * @see org.opensaml.saml2.core.Request#setID(java.lang.String)
     */
    public void setID(String newID) {
        this.id = prepareForAssignment(this.id, newID);
    }

    /**
     * @see org.opensaml.saml2.core.Request#getIssueInstant()
     */
    public DateTime getIssueInstant() {
        return issueInstant;
    }

    /**
     * @see org.opensaml.saml2.core.Request#setIssueInstant(org.joda.time.DateTime)
     */
    public void setIssueInstant(DateTime newIssueInstant) {
        this.issueInstant = prepareForAssignment(this.issueInstant, newIssueInstant);
    }

    /**
     * @see org.opensaml.saml2.core.Request#getDestination()
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @see org.opensaml.saml2.core.Request#setDestination(java.lang.String)
     */
    public void setDestination(String newDestination) {
        this.destination = prepareForAssignment(this.destination, newDestination);
    }

    /**
     * @see org.opensaml.saml2.core.Request#getConsent()
     */
    public String getConsent() {
        return consent;
    }

    /**
     * @see org.opensaml.saml2.core.Request#setConsent(java.lang.String)
     */
    public void setConsent(String newConsent) {
        this.consent = prepareForAssignment(this.consent, newConsent);
    }

    /**
     * @see org.opensaml.saml2.core.Request#getIssuer()
     */
    public Issuer getIssuer() {
        return issuer;
    }

    /**
     * @see org.opensaml.saml2.core.Request#setIssuer(org.opensaml.saml2.core.Issuer)
     */
    public void setIssuer(Issuer newIssuer) {
        this.issuer = prepareForAssignment(this.issuer, newIssuer);
    }

    /**
     * @see org.opensaml.saml2.core.Request#getExtensions()
     */
    public Extensions getExtensions() {
        return this.extensions;
    }

    /**
     * @see org.opensaml.saml2.core.Request#setExtensions(org.opensaml.saml2.core.Extensions)
     */
    public void setExtensions(Extensions newExtensions) {
        this.extensions = prepareForAssignment(this.extensions, newExtensions);
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if (issuer != null)
            children.add(issuer);
        // TODO Signature ??? necessary? if so, get from superclass ?
        if (extensions != null)
            children.add(extensions);

        if (children.size() == 0)
            return null;

        return Collections.unmodifiableList(children);
    }
}