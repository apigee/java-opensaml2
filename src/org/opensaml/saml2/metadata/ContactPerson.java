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

package org.opensaml.saml2.metadata;

import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.saml2.common.ExtensionsExtensibleSAMLObject;

/**
 * SAML 2.0 Metadata ContactPerson
 */
public interface ContactPerson extends SAMLObject, ExtensionsExtensibleSAMLObject{

    /** Element name, no namespace */
    public final static String LOCAL_NAME = "ContactPerson";
    
    /** QName for this element */
    public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /** "contactType" attribute's local name */
    public final static String CONTACT_TYPE_ATTRIB_NAME = "affiliationOwnerID";
    
    /** "contactType" attribute's QName */
    public final static QName CONTACT_TYPE_ATTRIB_QNAME = new QName(XMLConstants.SAML20MD_NS, CONTACT_TYPE_ATTRIB_NAME, XMLConstants.SAML20MD_PREFIX);

    /**
     * Gets the type of contact this person.
     * 
     * @return the type of contact this person
     */
	public ContactPersonType getContactPersonType();
    
    /**
     * Sets the type of contact this person.
     * 
     * @param type the type of contact this person
     */
    public void setType(ContactPersonType type);

    /**
     * Gets the company this contact person is associated with.
     * 
     * @return the company this contact person is associated with
     */
	public String getCompany();
    
    /**
     * Sets the company this contact person is associated with.
     * 
     * @param company the company this contact person is associated with
     */
    public void setCompany(String company);

    /**
     * Gets the given name for this person.
     * 
     * @return the given name for this person
     */
	public String getGivenName();
    
    /**
     * Sets the given name for this person.
     * 
     * @param name the given name for this person
     */
    public void setGivenName(String name);

	/**
     * Gets the surname for this person.
     *  
     * @return the surname for this person
	 */
    public String getSurName();
    
    /**
     * Sets the surname for this person.
     * @param name the surname for this person
     */
    public void setSurName(String name);

    /**
     * Gets an immutable list of email addresses for this person.
     * 
     * @return list of email addresses for this person
     */
	public Set /* <String> */getEmailAddresses();
    
    /**
     * Adds an email address to this person.
     * 
     * @param address email address to add
     */
    public void addEmailAddress(String address);
    
    /**
     * Adds a list of email addresses to this person.
     * 
     * @param addresses email addresses to add
     */
    public void addEmailAddresses(Set /*<String>*/ addresses);
    
    /**
     * Removes an email address to this person.
     * 
     * @param address email address to remove
     */
    public void removeEmailAddress(String address);
    
    /**
     * Removes a list of email addresses to this person.
     * 
     * @param addresses email addresses to remove
     */
    public void removeEmailAddresses(Set /*<String>*/ addresses);
    
    /**
     * Removes all the email addresses for this person.
     */
    public void removeAllEmailAddresses();
    
    /**
     * Gets an immutable list of telephone numbers for this person.
     * 
     * @return list of telephone numbers for this person
     */
	public Set /* <String> */getTelephoneNumbers();
    
    /**
     * Adds a telephone number to this person.
     * 
     * @param number telephone number to add
     */
    public void addTelephoneNumber(String number);
    
    /**
     * Adds a list of telephone numbers to this person.
     * 
     * @param numbers telephone numbers to add
     */
    public void addTelephoneNumbers(Set /*<String>*/ numbers);
    
    /**
     * Removes a telephone number to this person.
     * 
     * @param number telephone number to remove
     */
    public void removeTelephoneNumber(String number);
    
    /**
     * Removes a list of telephone numbers to this person.
     * 
     * @param numbers telephone numbers to remove
     */
    public void removeTelephoneNumbers(Set /*<String>*/ numbers);
    
    /**
     * Removes all the telephone numbers for this person.
     */
    public void removeAllTelephoneNumbers();
}
