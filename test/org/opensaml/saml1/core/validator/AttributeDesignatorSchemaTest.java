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

import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AttributeDesignator;

/**
 * Test case for {@link org.opensaml.saml1.core.validator.AttributeDesignatorValidator}.
 */
public abstract class AttributeDesignatorSchemaTest extends SAMLObjectValidatorBaseTestCase {

    /** Constructor */
    public AttributeDesignatorSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML1_NS, AttributeDesignator.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        validator = new AttributeDesignatorValidator();

    }

    /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();

        AttributeDesignator attributeDesignator = (AttributeDesignator) target;

        attributeDesignator.setAttributeName("Jimmy");
        attributeDesignator.setAttributeNamespace("Glaswegan");
    }
    
    public void testMissingName() {
        AttributeDesignator attributeDesignator = (AttributeDesignator) target;

        attributeDesignator.setAttributeName("");
        assertValidationFail("No AttributeName attribute");
    }

    public void testMissingNameSpace() {
        AttributeDesignator attributeDesignator = (AttributeDesignator) target;

        attributeDesignator.setAttributeNamespace(null);
        assertValidationFail("No AttributeNamespace attribute");
    }
}