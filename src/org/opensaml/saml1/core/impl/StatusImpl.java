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
import java.util.LinkedHashSet;
import java.util.Set;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml1.core.Status;
import org.opensaml.saml1.core.StatusCode;
import org.opensaml.saml1.core.StatusMessage;

/**
 *
 */
public class StatusImpl extends AbstractSAMLObject implements Status {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private StatusMessage statusMessage;
    private StatusCode statusCode;
    private SAMLObject statusDetail;
    
    /**
     * Constructor
     *
     */
    public StatusImpl() {
        super();
        setQName(Status.QNAME);
    }

    /*
     * @see org.opensaml.saml1.core.Status#getStatusMessage()
     */
    public StatusMessage getStatusMessage() {

        return statusMessage;
    }

    /*
     * @see org.opensaml.saml1.core.Status#setStatusMessage(org.opensaml.saml1.core.StatusMessage)
     */
    public void setStatusMessage(StatusMessage statusMessage) throws IllegalAddException {

        this.statusMessage = (StatusMessage) assignSAMLObject(this.statusMessage, statusMessage, null);
    }

    /*
     * @see org.opensaml.saml1.core.Status#getStatusCode()
     */
    public StatusCode getStatusCode() {

        return statusCode;    
    }

    /*
     * @see org.opensaml.saml1.core.Status#getStatusCode(org.opensaml.saml1.core.StatusCode)
     */
    public void setStatusCode(StatusCode statusCode) throws IllegalAddException {
        
        this.statusCode = (StatusCode) assignSAMLObject(this.statusCode, statusCode, null);
    }

    /*
     * @see org.opensaml.saml1.core.Status#getStatusDetail()
     */
    public SAMLObject getStatusDetail() {

        return statusDetail;
    }

    /*
     * @see org.opensaml.saml1.core.Status#setStatusDetail(org.opensaml.common.SAMLObject)
     */
    public void setStatusDetail(SAMLObject statusDetail) throws IllegalAddException {

        this.statusDetail = assignSAMLObject(this.statusDetail, statusDetail, null);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public Set<SAMLObject> getOrderedChildren() {
        Set <SAMLObject> set = new LinkedHashSet<SAMLObject>(3);
        
        if (statusCode != null) {
            set.add(statusCode);
        }
        
        if (statusMessage != null) {
            set.add(statusMessage);
        }
        
        if (statusDetail != null) {
            set.add(statusDetail);
        }
        
        return Collections.unmodifiableSet(set);
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {

        if (!(element instanceof Status)) {
            
            return false;
        }
        
        //
        // TODO what are equal statuses and why would we care
        //
        
        Status status = (Status) element;
         
        if (statusCode == null && status.getStatusCode() == null) {
            return true;
        }
        
        if (statusCode != null) {
            return statusCode.equals(status.getStatusCode());
        }
        
        return false;
    }

}
