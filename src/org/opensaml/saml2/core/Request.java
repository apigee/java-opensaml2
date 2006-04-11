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

package org.opensaml.saml2.core;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.xml.signature.SignableXMLObject;

/**
 * SAML 2.0 Core RequestAbstractType
 */
public interface Request extends SAMLObject, SignableXMLObject {
    
    /** ID attribute name */
    public final static String ID_ATTRIB_NAME = "ID";

    /** Version attribute name */
    public final static String VERSION_ATTRIB_NAME = "Version";
    
    /** IssueInstant attribute name */
    public final static String ISSUE_INSTANT_ATTRIB_NAME = "IssueInstant";
    
    /** Destination attribute name */
    public final static String DESTINATION_ATTRIB_NAME = "Destination";
    
    /** Consent attribute name */
    public final static String CONSENT_ATTRIB_NAME = "Consent";
    
    /**
     * Gets the SAML Version of this request.
     * 
     * @return the SAML Version of this request.
     */
    public SAMLVersion getVersion();
    
    /**
     * Sets the SAML Version of this request.
     * 
     * @param newVersion the SAML Version of this request
     */
    public void setVersion(SAMLVersion newVersion);
    
    /**
     * Gets the unique identifier of the request.
     * 
     * @return the unique identifier of the request
     */
    public String getID();
    
    /**
     * Sets the unique identifier of the request.
     * 
     * @param newID the unique identifier of the request
     */
    
    public void setID(String newID);
    
    /**
     * Gets the date/time the request was issued.
     * 
     * @return the date/time the request was issued
     */
    
    public DateTime getIssueInstant();
    
    /**
     * Sets the date/time the request was issued.
     * 
     * param newIssueInstant the date/time the request was issued
     */
    public void setIssueInstant(DateTime newIssueInstant);
    
    /**
     * Gets the URI of the destination of the request.
     * 
     * @return the URI of the destination of the request
     */
    public String getDestination();
    
    /**
     * Sets the URI of the destination of the request.
     * 
     * @param newDestination the URI of the destination of the request
     */
    public void setDestination(String newDestination);
    
    /**
     * Gets the consent obtained from the principal for sending this request.
     * 
     * @return the consent obtained from the principal for sending this request
     */
    public String getConsent();
    
    /**
     * Sets the consent obtained from the principal for sending this request.
     * 
     * @param newConsent the new consent obtained from the principal for sending this request
     */
    public void setConsent(String newConsent);
    
    /**
     * Gets the issuer of this request.
     * 
     * @return the issuer of this request
     */
    public Issuer getIssuer();
    
    /**
     * Sets the issuer of this request.
     * 
     * @param newIssuer the issuer of this request
     */
    public void setIssuer(Issuer newIssuer);
    
    /**
     * Gets the Extensions of this request.
     * 
     * @return the Status of this request 
     */
    public Extensions getExtensions();
    
    /**
     * Sets the Extensions of this request.
     * 
     * @param newExtensions the Extensions of this request
     */
    public void setExtensions(Extensions newExtensions);
    
}