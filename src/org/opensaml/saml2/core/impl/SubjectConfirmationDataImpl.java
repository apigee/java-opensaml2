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

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.SubjectConfirmationData;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.SubjectConfirmationData}
 */
public class SubjectConfirmationDataImpl extends AbstractSAMLObject implements SubjectConfirmationData {

    /** Constructor */
    public SubjectConfirmationDataImpl() {
        super(SAMLConstants.SAML20_NS, SubjectConfirmationData.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);   
    }

    /** NotBefore of the Confirmation Data */
    private DateTime notBefore;

    /** NotOnOrAfter of the Confirmation Data */
    private DateTime notOnOrAfter;

    /** Recipient of the Confirmation Data */
    private String recipient;

    /** InResponseTo of the Confirmation Data */
    private String inResponseTo;

    /** Address of the Confirmation Data */
    private String address;

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#getNotBefore()
     */
    public DateTime getNotBefore() {
        return notBefore;
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#setNotBefore(java.util.Date)
     */
    public void setNotBefore(DateTime newNotBefore) {
        this.notBefore = prepareForAssignment(this.notBefore, newNotBefore.withZone(DateTimeZone.UTC));
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#getNotOnOrAfter()
     */
    public DateTime getNotOnOrAfter() {
        return notOnOrAfter;
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#setNotOnOrAfter(java.util.Date)
     */
    public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
        this.notOnOrAfter = prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter.withZone(DateTimeZone.UTC));
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#getRecipient()
     */
    public String getRecipient() {
        return recipient;
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#setRecipient(java.lang.String)
     */
    public void setRecipient(String newRecipient) {
        this.recipient = prepareForAssignment(this.recipient, newRecipient);
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#getInResponseTo()
     */
    public String getInResponseTo() {
        return inResponseTo;
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#setInResponseTo(java.lang.String)
     */
    public void setInResponseTo(String newInResponseTo) {
        this.inResponseTo = prepareForAssignment(this.inResponseTo, newInResponseTo);
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#getAddress()
     */
    public String getAddress() {
        return address;
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmationData#setAddress(java.lang.String)
     */
    public void setAddress(String newAddress) {
        this.address = prepareForAssignment(this.address, newAddress);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }
}