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
import org.joda.time.DateTimeZone;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.saml1.core.RespondWith;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete (but abstract) class for <code> org.opensaml.saml1.core.RequestAbstractType </code>
 */
public abstract class RequestAbstractTypeImpl extends AbstractSignableProtocolSAMLObject implements RequestAbstractType {

    /** Contains the ID */
    private String id;
    
    /** Contains the minor version */
    private int version;

    /** Containt the IssueInstant */
    public DateTime issueInstant;

    /** Contains the respondWiths */
    public final List<RespondWith> respondWiths;

    /**
     * Constructor. Sets namespace to {@link SAMLConstants#SAML1_NS} and prefix to {@link SAMLConstants#SAML1_PREFIX}.
     * Sets the SAML version to {@link SAMLVersion#VERSION_11}.
     * 
     * @param localName the local name of the element
     */
    protected RequestAbstractTypeImpl(String elementLocalName) {
        super(elementLocalName);
        respondWiths = new XMLObjectChildrenList<RespondWith>(this);
    }

    /**
     * Constructor. Sets the SAML version to {@link SAMLVersion#VERSION_11}.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected RequestAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        respondWiths = new XMLObjectChildrenList<RespondWith>(this);
    }
    

    /*
     * @see org.opensaml.saml1.core.RequestAbstractType#getID()
     */
    public String getID() {
        return id;
    }

    /*
     * @see org.opensaml.saml1.core.RequestAbstractType#setID(java.lang.String)
     */
    public void setID(String id) {
        this.id = prepareForAssignment(this.id, id);
    }


    /*
     * @see org.opensaml.saml1.core.RequestAbstractType#getMinorVersion()
     */
    public int getMinorVersion() {
        return version;
    }

    /*
     * @see org.opensaml.saml1.core.RequestAbstractType#setMinorVersion(int)
     */
    public void setMinorVersion(int version) {
        if (this.version != version) {
            releaseThisandParentDOM();
            this.version = version;
        }
    }

    /*
     * @see org.opensaml.saml1.core.RequestAbstractType#getIssueInstant()
     */
    public DateTime getIssueInstant() {
        return issueInstant;
    }

    /*
     * @see org.opensaml.saml1.core.RequestAbstractType#setIssueInstant(org.joda.time.DateTime)
     */
    public void setIssueInstant(DateTime instant) {
        if (instant != null) {
            instant = instant.withZone(DateTimeZone.UTC);
        }
        this.issueInstant = prepareForAssignment(this.issueInstant, instant);
    }

    /*
     * @see org.opensaml.saml1.core.RequestAbstractType#getRespondWiths()
     */
    public List<RespondWith> getRespondWiths() {
        return respondWiths;
    }
}