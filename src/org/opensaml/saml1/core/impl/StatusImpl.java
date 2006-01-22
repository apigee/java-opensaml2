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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Status;
import org.opensaml.saml1.core.StatusCode;
import org.opensaml.saml1.core.StatusDetail;
import org.opensaml.saml1.core.StatusMessage;

/**
 * Concrete Implementation {@link org.opensaml.saml1.core.Status}
 */
public class StatusImpl extends AbstractSAMLObject implements Status {

    /** Representation of the StatusMessage element. */
    private StatusMessage statusMessage;

    /** Representation of the StatusCode element. */
    private StatusCode statusCode;

    /** Representation of the StatusDetail element. */
    private StatusDetail statusDetail;

    /**
     * Constructor.
     */
    public StatusImpl() {
        super(SAMLConstants.SAML1P_NS, Status.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1P_PREFIX);
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
    public void setStatusMessage(StatusMessage statusMessage) throws IllegalArgumentException {
        this.statusMessage = prepareForAssignment(this.statusMessage, statusMessage);
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
    public void setStatusCode(StatusCode statusCode) throws IllegalArgumentException {
        this.statusCode = prepareForAssignment(this.statusCode, statusCode);
    }

    /*
     * @see org.opensaml.saml1.core.Status#getStatusDetail()
     */
    public StatusDetail getStatusDetail() {
        return statusDetail;
    }

    /*
     * @see org.opensaml.saml1.core.Status#setStatusDetail(org.opensaml.common.SAMLObject)
     */
    public void setStatusDetail(StatusDetail statusDetail) throws IllegalArgumentException {
        this.statusDetail = prepareForAssignment(this.statusDetail, statusDetail);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>(3);

        if (statusCode != null) {
            children.add(statusCode);
        }

        if (statusMessage != null) {
            children.add(statusMessage);
        }

        if (statusDetail != null) {
            children.add(statusDetail);
        }

        if (children.size() == 0) {
            return null;
        }
        
        return Collections.unmodifiableList(children);
    }
}