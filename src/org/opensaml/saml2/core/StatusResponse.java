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
package org.opensaml.saml2.core;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.xml.signature.SignableXMLObject;

/**
 * SAML 2.0 Core StatusResponseType
 */
public interface StatusResponse extends SAMLObject,  SignableXMLObject {
    
    /** ID attribute name */
    public static final String ID_ATTRIB_NAME = "ID";
    
    /** InResponseTo attribute name */
    public static final String IN_RESPONSE_TO_ATTRIB_NAME = "InResponseTo";
    
    /** Version attribute name */
    public static final String VERSION_ATTRIB_NAME = "Version";
    
    /** IssueInstant attribute name */
    public static final String ISSUE_INSTANT_ATTRIB_NAME = "IssueInstant";
    
    /** Destination attribute name */
    public static final String DESTINATION_ATTRIB_NAME = "Destination";
    
    /** Consent attribute name */
    public static final String CONSENT_ATTRIB_NAME = "Consent";
    
    /**
     * Gets the SAML Version of this response.
     * 
     * @return the SAML Version of this response.
     */
    public SAMLVersion getVersion();
    
    /**
     * Sets the SAML Version of this response.
     * 
     * @param newVersion the SAML Version of this response
     */
    public void setVersion(SAMLVersion newVersion);
    
    /**
     * Gets the unique identifier of the response.
     * 
     * @return the unique identifier of the response 
     */
    public String getID();
    
    /**
     * Sets the unique identifier of the response.
     * 
     * @param newID the unique identifier of the response 
     */
    
    public void setID(String newID);
    
    /**
     * Gets the unique request identifier for which this is a response
     * 
     * @return the unique identifier of the originating request
     */
    public String getInResponseTo();
    
    /**
     * Sets the unique request identifier for which this is a response
     * 
     * @param newInResponseTo the unique identifier of the originating request
     */
    
    public void setInResponseTo(String newInResponseTo);

    /**
     * Gets the date/time the response was issued.
     * 
     * @return the date/time the response was issued
     */
    public DateTime getIssueInstant();
    
    /**
     * Sets the date/time the response was issued.
     * 
     * param newIssueInstant the date/time the response was issued
     */
    public void setIssueInstant(DateTime newIssueInstant);
    
    /**
     * Gets the URI of the destination of the response.
     * 
     * @return the URI of the destination of the response
     */
    public String getDestination();
    
    /**
     * Sets the URI of the destination of the response.
     * 
     * @param newDestination the URI of the destination of the response
     */
    public void setDestination(String newDestination);
    
    /**
     * Gets the consent obtained from the principal for sending this response.
     * 
     * @return the consent obtained from the principal for sending this response
     */
    public String getConsent();
    
    /**
     * Sets the consent obtained from the principal for sending this response.
     * 
     * @param newConsent the consent obtained from the principal for sending this response
     */
    public void setConsent(String newConsent);
    
    /**
     * Gets the issuer of this response.
     * 
     * @return the issuer of this response
     */
    public Issuer getIssuer();
    
    /**
     * Sets the issuer of this response.
     * 
     * @param newIssuer the issuer of this response
     */
    public void setIssuer(Issuer newIssuer);
    
    /**
     * Gets the Status of this response.
     * 
     * @return the Status of this response
     */
    public Status getStatus();
    
    /**
     * Sets the Status of this response.
     * 
     * @param newStatus the Status of this response
     */
    public void setStatus(Status newStatus);
    
    /**
     * Gets the Extensions of this response.
     * 
     * @return the Status of this response
     */
    public Extensions getExtensions();
    
    /**
     * Sets the Extensions of this response.
     * 
     * @param newExtensions the Extensions of this response
     */
    public void setExtensions(Extensions newExtensions);
    
}
