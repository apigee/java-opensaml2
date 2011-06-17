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

import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.samlext.saml2delrestrict.Delegate;
import org.opensaml.samlext.saml2delrestrict.DelegationRestrictionType;
import org.opensaml.xml.Configuration;

/**
 * Test case for creating, marshalling, and unmarshalling {@link Delegate}.
 */
public class DelegationRestrictionTypeTest extends BaseSAMLObjectProviderTestCase {

    private int expectedDelegateChildren;
    

    /** Constructor */
    public DelegationRestrictionTypeTest() {
        singleElementFile = "/data/org/opensaml/samlext/saml2delrestrict/impl/DelegationRestrictionType.xml";
        childElementsFile = "/data/org/opensaml/samlext/saml2delrestrict/impl/DelegationRestrictionTypeChildElements.xml";
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        expectedDelegateChildren = 3;
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        DelegationRestrictionType drt = (DelegationRestrictionType) unmarshallElement(singleElementFile);

        assertNotNull(drt);
    }
    
    /** {@inheritDoc} */
    public void testChildElementsUnmarshall() {
        DelegationRestrictionType drt = (DelegationRestrictionType) unmarshallElement(childElementsFile);
        
        assertNotNull(drt);
        
        assertEquals("Incorrect # of Delegate Children", expectedDelegateChildren, drt.getDelegates().size());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        SAMLObjectBuilder<DelegationRestrictionType> builder =
            (SAMLObjectBuilder<DelegationRestrictionType>) Configuration.getBuilderFactory().getBuilder(DelegationRestrictionType.TYPE_NAME);
        
        DelegationRestrictionType drt = builder.buildObject();

        assertEquals(expectedDOM, drt);
    }


    /** {@inheritDoc} */
    public void testChildElementsMarshall() {
        SAMLObjectBuilder<DelegationRestrictionType> builder =
            (SAMLObjectBuilder<DelegationRestrictionType>) Configuration.getBuilderFactory().getBuilder(DelegationRestrictionType.TYPE_NAME);
        
        DelegationRestrictionType drt = builder.buildObject();
        
        drt.getDelegates().add((Delegate) buildXMLObject(Delegate.DEFAULT_ELEMENT_NAME));
        drt.getDelegates().add((Delegate) buildXMLObject(Delegate.DEFAULT_ELEMENT_NAME));
        drt.getDelegates().add((Delegate) buildXMLObject(Delegate.DEFAULT_ELEMENT_NAME));
        
        assertEquals(expectedChildElementsDOM, drt);
    }
}