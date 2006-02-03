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

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml1.core.Attribute;
import org.opensaml.saml1.core.AttributeStatement;

/**
 * Test for {@link org.opensaml.saml1.core.AttributeStatement}
 */
public class AttributeStatementTest extends SAMLObjectBaseTestCase {

    /**
     * Constructor
     */
    public AttributeStatementTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleAttributeStatement.xml";
        childElementsFile = "/data/org/opensaml/saml1/AttributeStatementWithChildren.xml";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        AttributeStatement attributeStatement = (AttributeStatement) unmarshallElement(singleElementFile);

        assertNull("<Subject> element present", attributeStatement.getSubject());
        assertEquals("Non zero count of <Attribute> elements", 0, attributeStatement.getAttributes().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    @Override
    public void testChildElementsUnmarshall() {
        AttributeStatement attributeStatement = (AttributeStatement) unmarshallElement(childElementsFile);

        assertNotNull("<Subject> element not present", attributeStatement.getSubject());
        assertNotNull("<AuthorityBinding> elements not present", attributeStatement.getAttributes());
        assertEquals("count of <AuthorityBinding> elements", 5, attributeStatement.getAttributes().size());

        Attribute attribute = attributeStatement.getAttributes().get(0);
        attributeStatement.getAttributes().remove(attribute);
        assertEquals("count of <AttributeStatement> elements after single remove", 4, attributeStatement
                .getAttributes().size());

        ArrayList<Attribute> list = new ArrayList<Attribute>(2);

        list.add(attributeStatement.getAttributes().get(0));
        list.add(attributeStatement.getAttributes().get(2));

        attributeStatement.getAttributes().removeAll(list);

        assertEquals("count of <AttributeStatement> elements after double remove", 2, attributeStatement
                .getAttributes().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, new AttributeStatementImpl());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {

        AttributeStatement attributeStatement = new AttributeStatementImpl();

        attributeStatement.setSubject(new SubjectImpl());

        for (int i = 0; i < 5; i++) {
            attributeStatement.getAttributes().add(new AttributeImpl());
        }

        assertEquals(expectedChildElementsDOM, attributeStatement);
    }
}
