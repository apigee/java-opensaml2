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

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.ContactPersonType;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.impl.ContactPersonImpl}.
 */
public class ContactPersonTest extends SAMLObjectBaseTestCase {
    
    /** Expected company name */
    protected ContactPersonType expectedPersonType;
    
    /** Count of EmailAddress subelements */
    protected int emailAddressCount = 2;
    
    /** Count of TelephoneNumber subelements */
    protected int telephoneNumberCount = 3;

    /**
     * Constructor
     */
    public ContactPersonTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/ContactPerson.xml";
        childElementsFile = "/data/org/opensaml/saml2/metadata/impl/ContactPersonChildElements.xml";
    }
    
    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedPersonType = ContactPersonType.TECHNICAL;
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        ContactPerson person = (ContactPerson) unmarshallElement(singleElementFile);
        
        assertEquals("Contact type was not expected value", expectedPersonType, person.getType());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    @Override
    public void testChildElementsUnmarshall()
    {
        ContactPerson person = (ContactPerson) unmarshallElement(childElementsFile);
        
        assertNotNull("Extension Element not present", person.getExtensions());
        assertNotNull("Company Element not present", person.getCompany());
        assertNotNull("GivenName not present", person.getGivenName());
        assertEquals("Email address count", emailAddressCount, person.getEmailAddresses().size());
        assertEquals("Telephone Number count", telephoneNumberCount, person.getTelephoneNumbers().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, ContactPerson.LOCAL_NAME);
        ContactPerson person = (ContactPerson) buildSAMLObject(qname);
        
        person.setType(expectedPersonType);

        assertEquals(expectedDOM, person);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall()
    {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, ContactPerson.LOCAL_NAME);
        ContactPerson person = (ContactPerson) buildSAMLObject(qname);
        
        person.setType(expectedPersonType);

        person.setExtensions(new ExtensionsImpl());
        person.setCompany(new CompanyImpl());
        person.setGivenName(new GivenNameImpl());
        person.setSurName(new SurNameImpl());
        for (int i = 0; i < telephoneNumberCount; i++) {
            person.getTelephoneNumbers().add(new TelephoneNumberImpl());
        }
        for (int i = 0; i < emailAddressCount; i++) {
            person.getEmailAddresses().add(new EmailAddressImpl());
        }
        assertEquals(expectedChildElementsDOM, person);
    }
}