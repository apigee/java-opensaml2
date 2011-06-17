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

package org.opensaml.samlext.saml2mdattr;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;

/** Unit test for {@link EntityAttributes}. */
public class EntityAttributesTest extends BaseSAMLObjectProviderTestCase {

    /** Constructor. */
    public EntityAttributesTest() {
        singleElementFile = "/data/org/opensaml/samlext/saml2mdattr/EntityAttributes.xml";
        childElementsFile = "/data/org/opensaml/samlext/saml2mdattr/EntityAttributesChildElements.xml";
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        EntityAttributes attributes = (EntityAttributes) unmarshallElement(singleElementFile);
        assertNotNull(attributes);
        assertTrue(attributes.getAssertions().isEmpty());
        assertTrue(attributes.getAttributes().isEmpty());
    }

    /** {@inheritDoc} */
    public void testChildElementsUnmarshall() {
        EntityAttributes attributes = (EntityAttributes) unmarshallElement(childElementsFile);
        assertNotNull(attributes);

        assertEquals(2, attributes.getAssertions().size());
        assertEquals(3, attributes.getAttributes().size());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        EntityAttributes attributes = (EntityAttributes) buildXMLObject(EntityAttributes.DEFAULT_ELEMENT_NAME);

        assertEquals(expectedDOM, attributes);
    }

    /** {@inheritDoc} */
    public void testChildElementsMarshall() {
        Assertion assertion1 = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        assertion1.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 0, DateTimeZone.UTC));
        Assertion assertion2 = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        assertion2.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 0, DateTimeZone.UTC));
        
        Attribute attrib1 = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
        attrib1.setName("attrib1");
        Attribute attrib2 = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
        attrib2.setName("attrib2");
        Attribute attrib3 = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
        attrib3.setName("attrib3");
        
        EntityAttributes attributes = (EntityAttributes) buildXMLObject(EntityAttributes.DEFAULT_ELEMENT_NAME);
        attributes.getAssertions().add(assertion1);
        attributes.getAttributes().add(attrib1);
        attributes.getAssertions().add(assertion2);
        attributes.getAttributes().add(attrib2);
        attributes.getAttributes().add(attrib3);

        assertEquals(5, attributes.getOrderedChildren().size());
        assertEquals(expectedChildElementsDOM, attributes);
    }
}