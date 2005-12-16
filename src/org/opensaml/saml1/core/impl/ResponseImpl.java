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

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.util.StringHelper;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.Status;
/**
 *
 */
public class ResponseImpl extends AbstractSAMLObject implements Response {

    private static final long serialVersionUID = 6224869049066163659L;

    /*
     * Local state
     */
    
    private String responseID = null;
    private String inResponseTo = null;
    private int minorVersion = 0;
    private Date issueInstant = null;
    private String recipient = null;
    private Status status = null;
    private Assertion assertion = null;

    protected ResponseImpl() {
        super();
        setQName(Response.QNAME);
    }
    
    /*
     * @see org.opensaml.saml1.core.Response#getResponseID()
     */
    public String getResponseID() {
        
        return responseID;
    }

    /*
     * @see org.opensaml.saml1.core.Response#setResponseID(java.lang.String)
     */
    public void setResponseID(String responseID) {

        
        this.responseID = assignString(this.responseID, responseID);
        
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

        this.inResponseTo = assignString(this.inResponseTo, inResponseTo);
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
    public Date getIssueInstant() {
        
        return issueInstant;
    }

    /*
     * @see org.opensaml.saml1.core.Response#setIssueInstant(java.util.Date)
     */
    public void setIssueInstant(Date date) {

        if (issueInstant == null && date == null) {
            // no change - return
            return;
        }
        
        if (issueInstant == null || !issueInstant.equals(date)) {
            releaseThisandParentDOM();
            issueInstant = date;
        }
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
      
        this.recipient = assignString(this.recipient, recipient);
    }

    /*
     * @see org.opensaml.saml1.core.Response#getStatus()
     */
    public Status getStatus() {

        return status;
    }

    /*
     * @see org.opensaml.saml1.core.Response#getStatus(org.opensaml.saml1.core.Status)
     */
    public void setStatus(Status status) throws IllegalAddException {
        
        this.status = assignSAMLObject(this.status, status);
    }

    /*
     * @see org.opensaml.saml1.core.Response#getAssertions()
     */
    public Assertion getAssertion() {

        return assertion;    
    }

    /*
     * @see org.opensaml.saml1.core.Response#addAssertion(org.opensaml.saml1.core.Assertion)
     */
    public void setAssertion(Assertion assertion) throws IllegalAddException {
        this.assertion = assignSAMLObject(this.assertion, assertion) ;
    }

    /*
     * @see org.opensaml.saml1.core.Response#removeAssertion(org.opensaml.saml1.core.Assertion)
     */

    public boolean equals(SAMLObject element) {
        if (element instanceof ResponseImpl){
            
            Response other = (ResponseImpl)element;
            
            return StringHelper.safeEquals(other.getResponseID(), this.responseID);
        }
        
        return false;
    }
 }
