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

package org.opensaml.saml1.core.validator;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Attribute;
import org.opensaml.saml1.core.AttributeValue;

/**
 * Test case for {@link org.opensaml.saml1.core.validator.AttributeValidator}.
 */
public class AttributeSchemaTest extends AttributeDesignatorSchemaTest {

    /** Constructor */
    public AttributeSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML1_NS, Attribute.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        validator = new AttributeValidator();

    }

    /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();

        Attribute attribute = (Attribute) target;
        QName qname = new QName(SAMLConstants.SAML1_NS, AttributeValue.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        
        attribute.getAttributeValues().add((AttributeValue)buildXMLObject(qname));
    }
    
    public void testMissingValue() {
        Attribute attribute = (Attribute) target;
        attribute.getAttributeValues().clear();
        
        assertValidationFail("No AttributeValue elements");
    }

}