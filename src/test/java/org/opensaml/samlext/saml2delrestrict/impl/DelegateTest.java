/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.samlext.saml2delrestrict.impl;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.saml2.core.NameID;
import org.opensaml.samlext.saml2delrestrict.Delegate;

/**
 * Test case for creating, marshalling, and unmarshalling {@link Delegate}.
 */
public class DelegateTest extends BaseSAMLObjectProviderTestCase {

    private DateTime expectedDelegationInstant;
    
    private String expectedConfirmationMethod;
    

    /** Constructor */
    public DelegateTest() {
        singleElementFile = "/data/org/opensaml/samlext/saml2delrestrict/impl/Delegate.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/samlext/saml2delrestrict/impl/DelegateOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/samlext/saml2delrestrict/impl/DelegateChildElements.xml";
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        expectedDelegationInstant = new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC());
        expectedConfirmationMethod = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        Delegate delegate = (Delegate) unmarshallElement(singleElementFile);

        assertNotNull(delegate);
    }

    /** {@inheritDoc} */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Delegate delegate = (Delegate) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertNotNull(delegate);

        DateTime instant = delegate.getDelegationInstant();
        assertEquals("DelegationInstant was unexpected value", expectedDelegationInstant, instant);

        String cm = delegate.getConfirmationMethod();
        assertEquals("ConfirmationMethod was unexpected value", expectedConfirmationMethod, cm);
    }
    
    /** {@inheritDoc} */
    public void testChildElementsUnmarshall() {
        Delegate delegate = (Delegate) unmarshallElement(childElementsFile);
        
        assertNotNull(delegate);
        
        assertNotNull("NameID was null", delegate.getNameID());
        assertNull("BaseID was non-null", delegate.getBaseID());
        assertNull("EncryptedID was non-null", delegate.getEncryptedID());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        Delegate delegate = (Delegate) buildXMLObject(Delegate.DEFAULT_ELEMENT_NAME);

        assertEquals(expectedDOM, delegate);
    }

    /** {@inheritDoc} */
    public void testSingleElementOptionalAttributesMarshall() {
        Delegate delegate = (Delegate) buildXMLObject(Delegate.DEFAULT_ELEMENT_NAME);
        
        delegate.setConfirmationMethod(expectedConfirmationMethod);
        delegate.setDelegationInstant(expectedDelegationInstant);

        assertEquals(expectedOptionalAttributesDOM, delegate);
    }



    /** {@inheritDoc} */
    public void testChildElementsMarshall() {
        Delegate delegate = (Delegate) buildXMLObject(Delegate.DEFAULT_ELEMENT_NAME);
        
        delegate.setNameID((NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME));
        
        assertEquals(expectedChildElementsDOM, delegate);
    }
}