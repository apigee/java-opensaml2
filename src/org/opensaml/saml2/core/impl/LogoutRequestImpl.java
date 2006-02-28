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
import org.opensaml.saml2.core.Identifier;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.SessionIndex;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.LogoutRequest}
 */
public class LogoutRequestImpl extends RequestImpl implements LogoutRequest {
    
    /** Reason attribute */
    private String reason;
    
    /** NotOnOrAfter attribute */
    private DateTime notOnOrAfter;
    
    /** Identifier child element */
    private Identifier identifier;
    
    /** SessionIndex child elements */
    private XMLObjectChildrenList<SessionIndex> sessionIndexes;
    

    /**
     * Constructor
     *
     */
    protected LogoutRequestImpl() {
        super(LogoutRequest.LOCAL_NAME);
        sessionIndexes = new XMLObjectChildrenList<SessionIndex>(this);
    }

    /**
     * @see org.opensaml.saml2.core.LogoutRequest#getReason()
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * @see org.opensaml.saml2.core.LogoutRequest#setReason(java.lang.String)
     */
    public void setReason(String newReason) {
        this.reason = prepareForAssignment(this.reason, newReason);
    }

    /**
     * @see org.opensaml.saml2.core.LogoutRequest#getNotOnOrAfter()
     */
    public DateTime getNotOnOrAfter() {
        return this.notOnOrAfter;
    }

    /**
     * @see org.opensaml.saml2.core.LogoutRequest#setNotOnOrAfter(org.joda.time.DateTime)
     */
    public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
        this.notOnOrAfter = prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter);
    }

    /**
     * @see org.opensaml.saml2.core.LogoutRequest#getIdentifier()
     */
    public Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * @see org.opensaml.saml2.core.LogoutRequest#setIdentifier(org.opensaml.saml2.core.Identifier)
     */
    public void setIdentifier(Identifier newIdentifier) {
        this.identifier = prepareForAssignment(this.identifier, newIdentifier);

    }

    /**
     * @see org.opensaml.saml2.core.LogoutRequest#getSessionIndexes()
     */
    public List<SessionIndex> getSessionIndexes() {
        return sessionIndexes;
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        
        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());
        
        if (identifier != null)
            children.add(identifier);
        children.addAll(sessionIndexes);
        
        if (children.size() == 0)
            return null;
        
        return Collections.unmodifiableList(children);
    }

}
