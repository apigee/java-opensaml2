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
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Request;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Request}
 */
public abstract class RequestImpl extends AbstractSignableSAMLObject implements Request {
    
    //TODO how is the typesafe versioning handled?
    /* SAML version */
    //private String version;
    
    /* Date/time request was issued */
    private DateTime issueInstant;
    
    /* URI of the request destination */
    private String destination;

    /* URI of the SAML user consent type */
    private String consent;

    /* URI of the SAML user consent type */
    private Issuer issuer;


    /**
     * Constructor
     *
     * @param namespaceURI
     * @param elementLocalName
     */
    public RequestImpl() {
        super(SAMLConstants.SAML20P_NS, Request.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);
        //TODO need to set namespace prefix ?
    }

    /*
     * @see org.opensaml.saml2.core.Request#getIssueInstant()
     */
    public DateTime getIssueInstant() {
        return issueInstant;
    }

    /*
     * @see org.opensaml.saml2.core.Request#setIssueInstant(org.joda.time.DateTime)
     */
    public void setIssueInstant(DateTime newIssueInstant) {
        this.issueInstant = prepareForAssignment(this.issueInstant, newIssueInstant);
    }

    /*
     * @see org.opensaml.saml2.core.Request#getDestination()
     */
    public String getDestination() {
        return destination;
    }

    /*
     * @see org.opensaml.saml2.core.Request#setDestination(java.lang.String)
     */
    public void setDestination(String newDestination) {
        this.destination = prepareForAssignment(this.destination, newDestination);
    }

    /*
     * @see org.opensaml.saml2.core.Request#getConsent()
     */
    public String getConsent() {
        return consent;
    }

    /*
     * @see org.opensaml.saml2.core.Request#setConsent(java.lang.String)
     */
    public void setConsent(String newConsent) {
        this.consent = prepareForAssignment(this.consent, newConsent);
    }

    /*
     * @see org.opensaml.saml2.core.Request#getIssuer()
     */
    public Issuer getIssuer() {
        return issuer;
    }

    /*
     * @see org.opensaml.saml2.core.Request#setIssuer(org.opensaml.saml2.core.Issuer)
     */
    public void setIssuer(Issuer newIssuer) {
        this.issuer = prepareForAssignment(this.issuer, newIssuer);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
       
        if (issuer != null)
            children.add(issuer);
        //TODO Signature ??? necessary?  if so, get from superclass ?
        //TODO Extensions
        
        if (children.size() == 0)
            return null;
        
        return Collections.unmodifiableList(children);
    }
    
    //TODO Extensions
    

}
