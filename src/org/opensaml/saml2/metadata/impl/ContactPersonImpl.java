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

package org.opensaml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.List;

import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.metadata.Company;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.ContactPersonType;
import org.opensaml.saml2.metadata.EmailAddress;
import org.opensaml.saml2.metadata.GivenName;
import org.opensaml.saml2.metadata.SurName;
import org.opensaml.saml2.metadata.TelephoneNumber;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.ContactPerson}
 */
public class ContactPersonImpl extends AbstractMetadataSAMLObject implements ContactPerson {

    /** Contact person type */
    private ContactPersonType type;

    /** Extensions child object */
    private Extensions extensions;

    /** Company child element */
    private Company company;

    /** GivenName child objectobject */
    private GivenName givenName;

    /** SurName child object */
    private SurName surName;

    /** Child email address */
    private XMLObjectChildrenList<EmailAddress> emailAddresses;

    /** Child telephone numbers */
    private XMLObjectChildrenList<TelephoneNumber> telephoneNumbers;

    /**
     * Constructor
     */
    protected ContactPersonImpl() {
        super(ContactPerson.LOCAL_NAME);

        emailAddresses = new XMLObjectChildrenList<EmailAddress>(this);
        telephoneNumbers = new XMLObjectChildrenList<TelephoneNumber>(this);
    }

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected ContactPersonImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#getContactPersonType()
     */
    public ContactPersonType getType() {
        return type;
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#setType(org.opensaml.saml2.metadata.ContactPersonType)
     */
    public void setType(ContactPersonType type) {
        this.type = prepareForAssignment(this.type, type);
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#getExtensions()
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#setExtensions(org.opensaml.saml2.core.Extensions)
     */
    public void setExtensions(Extensions extensions) throws IllegalArgumentException {
        this.extensions = prepareForAssignment(this.extensions, extensions);
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#getCompany()
     */
    public Company getCompany() {
        return company;
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#setCompany(org.opensaml.saml2.metadata.Company)
     */
    public void setCompany(Company company) {
        this.company = prepareForAssignment(this.company, company);
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#getGivenName()
     */
    public GivenName getGivenName() {
        return givenName;
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#setGivenName(org.opensaml.saml2.metadata.GivenName)
     */
    public void setGivenName(GivenName name) {
        givenName = prepareForAssignment(givenName, name);
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#getSurName()
     */
    public SurName getSurName() {
        return surName;
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#setSurName(org.opensaml.saml2.metadata.SurName)
     */
    public void setSurName(SurName name) {
        surName = prepareForAssignment(surName, name);
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#getEmailAddresses()
     */
    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    /*
     * @see org.opensaml.saml2.metadata.ContactPerson#getTelephoneNumbers()
     */
    public List<TelephoneNumber> getTelephoneNumbers() {
        return telephoneNumbers;
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.add(extensions);
        children.add(company);
        children.add(givenName);
        children.add(surName);
        children.addAll(emailAddresses);
        children.addAll(telephoneNumbers);

        return children;
    }
}