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

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.StatusDetail;
import org.opensaml.saml2.core.StatusMessage;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Status}
 */
public class StatusImpl extends AbstractSAMLObject implements Status {

    /** StatusCode element */
    private StatusCode statusCode;

    /** StatusMessage element */
    private StatusMessage statusMessage;

    /** StatusDetail element */
    private StatusDetail statusDetail;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected StatusImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /**
     * @see org.opensaml.saml2.core.Status#getStatusCode()
     */
    public StatusCode getStatusCode() {
        return this.statusCode;
    }

    /**
     * @see org.opensaml.saml2.core.Status#setStatusCode(org.opensaml.saml2.core.StatusCode)
     */
    public void setStatusCode(StatusCode newStatusCode) {
        this.statusCode = prepareForAssignment(this.statusCode, newStatusCode);

    }

    /**
     * @see org.opensaml.saml2.core.Status#getStatusMessage()
     */
    public StatusMessage getStatusMessage() {
        return this.statusMessage;
    }

    /**
     * @see org.opensaml.saml2.core.Status#setStatusMessage(org.opensaml.saml2.core.StatusMessage)
     */
    public void setStatusMessage(StatusMessage newStatusMessage) {
        this.statusMessage = prepareForAssignment(this.getStatusMessage(), newStatusMessage);
    }

    /**
     * @see org.opensaml.saml2.core.Status#getStatusDetail()
     */
    public StatusDetail getStatusDetail() {
        return this.statusDetail;
    }

    /**
     * @see org.opensaml.saml2.core.Status#setStatusDetail(org.opensaml.saml2.core.StatusDetail)
     */
    public void setStatusDetail(StatusDetail newStatusDetail) {
        this.statusDetail = prepareForAssignment(this.statusDetail, newStatusDetail);
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.add(statusCode);
        if (statusMessage != null)
            children.add(statusMessage);
        if (statusDetail != null)
            children.add(statusDetail);
        return Collections.unmodifiableList(children);
    }
}