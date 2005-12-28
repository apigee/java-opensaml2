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

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml1.core.AudienceRestrictionCondition;

/**
 * Test class for data.org.opensaml.saml1.AudienceRestrictionCondition
 */
public class AudienceRestrictionConditionTest extends SAMLObjectBaseTestCase {


    /**
     * Constructor
     */
    public AudienceRestrictionConditionTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleAudienceRestrictionCondition.xml";
        singleElementOptionalAttributesFile =
            "/data/org/opensaml/saml1/AudienceRestrictionConditionWithChildren.xml";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        AudienceRestrictionCondition audienceRestrictionCondition;
        
        audienceRestrictionCondition = (AudienceRestrictionCondition) unmarshallElement(singleElementFile);
        
        assertEquals("Count of child Audience elements", 0, audienceRestrictionCondition.getAudiences().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     * 
     * We over,load this to test the children case (there are no attributes)
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {

        AudienceRestrictionCondition audienceRestrictionCondition;
        
        audienceRestrictionCondition = (AudienceRestrictionCondition) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Count of child Audience elements", 2, audienceRestrictionCondition.getAudiences().size());

    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {

        AudienceRestrictionCondition audienceRestrictionCondition;
        
        audienceRestrictionCondition = new AudienceRestrictionConditionImpl();
        
        assertEquals(expectedDOM, audienceRestrictionCondition);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        AudienceRestrictionCondition audienceRestrictionCondition;
        
        audienceRestrictionCondition = new AudienceRestrictionConditionImpl();
        try {
            audienceRestrictionCondition.addAudience(new AudienceImpl());
            audienceRestrictionCondition.addAudience(new AudienceImpl());        
        } catch (IllegalAddException e) {
            fail("Threw IllegalAddException");
           e.printStackTrace();
        }
        
        assertEquals(expectedOptionalAttributesDOM, audienceRestrictionCondition);
    }

}
