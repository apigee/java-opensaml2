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

import java.util.List;

import org.joda.time.DateTime;

/**
 * SAML 2.0 Core LogoutRequest
 */
public interface LogoutRequest extends Request {
    
    /** Element local name */
    public static final String LOCAL_NAME = "LogoutRequest";
    
    /** Reason attribute name */
    public static final String REASON_ATTRIB_NAME = "Reason";
    
    /** NotOnOrAfter attribute name */
    public static final String NOT_ON_OR_AFTER_ATTRIB_NAME = "NotOnOrAfter";
    
    /**
     * Get the Reason attrib value of the request
     * 
     * @return the Reason value of the request
     */
    public String getReason();

    /**
     * Set the Reason attrib value of the request
     * 
     * @param newReason the new Reason value of the request
     */
    public void setReason(String newNameID);
    
    /**
     * Get the NotOnOrAfter attrib value of the request
     * 
     * @return the NotOnOrAfter value of the request
     */
    public DateTime getNotOnOrAfter();

    /**
     * Set the NotOnOrAfter attrib value of the request
     * 
     * @param newNotOnOrAfter the new NotOnOrAfter value of the request
     */
    public void setNotOnOrAfter (DateTime newNotOnOrAfter);
    
    /**
     * Get the Identifier of the request
     * 
     * @return the Identifier of the request
     */
    public Identifier getIdentifier();

    /**
     * Set the Identifier of the request
     * 
     * @param newIdentifier the new Identifier of the request
     */
    public void setIdentifier(Identifier newIdentifier);
   
    /**
     *  Get the list of SessionIndexes for the request
     * 
     * 
     * @return the list of SessionIndexes
     */
    public List<SessionIndex> getSessionIndexes();


}
