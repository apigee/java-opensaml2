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

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Conditions;

/**
 * Test class for org.opensaml.saml1.core.Conditions
 */
public class ConditionsTest extends SAMLObjectBaseTestCase {

    /**
     * Representation of NotBefore in test file.
     */
    private final DateTime expectedNotBeforeDate;

    /**
     * Representation of NotOnOrAfter in test file.
     */
    private final DateTime expectedNotOnOfAfter;

    /**
     * Constructor
     * 
     */
    public ConditionsTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleConditions.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleConditionsAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml1/ConditionsWithChildren.xml";
        //
        // NotBefore="1970-01-01T01:00:00.123Z"
        //
        expectedNotBeforeDate = new DateTime(1970, 1, 01, 01, 00, 00, 123, ISOChronology.getInstanceUTC());
        //
        // NotOnOrAfter="1970-01-01T00:00:01.000Z"
        //
        expectedNotOnOfAfter = new DateTime(1970, 1, 01, 00, 00, 01, 0, ISOChronology.getInstanceUTC());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Conditions conditions;

        conditions = (Conditions) unmarshallElement(singleElementFile);

        DateTime date = conditions.getNotBefore();
        assertNull("NotBefore attribute has a value of " + date + ", expected no value", date);

        date = conditions.getNotOnOrAfter();
        assertNull("NotOnOrAfter attribute has a value of " + date + ", expected no value", date);

    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Conditions conditions;

        conditions = (Conditions) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("NotBefore attribute ", expectedNotBeforeDate, conditions.getNotBefore());
        assertEquals("NotOnOrAfter attribute ", expectedNotOnOfAfter, conditions.getNotOnOrAfter());
    }

    /*
     * Test an XML file with children
     */
    public void testChildElementsUnmarshall() {
        Conditions conditions;

        conditions = (Conditions) unmarshallElement(childElementsFile);

        assertEquals("Number of AudienceRestrictionCondition elements", 3, conditions
                .getAudienceRestrictionConditions().size());
        assertEquals("Number of DoNotCacheCondition children", 1, conditions.getDoNotCacheConditions().size());
        assertEquals("Wrong number of Condition children", 4, conditions.getConditions().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML1_NS, Conditions.LOCAL_NAME);
        Conditions conditions = (Conditions) buildXMLObject(qname, null);

        assertEquals(expectedDOM, conditions);

    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML1_NS, Conditions.LOCAL_NAME);
        Conditions conditions = (Conditions) buildXMLObject(qname, null);

        conditions.setNotBefore(expectedNotBeforeDate);
        conditions.setNotOnOrAfter(expectedNotOnOfAfter);

        assertEquals(expectedOptionalAttributesDOM, conditions);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML1_NS, Conditions.LOCAL_NAME);
        Conditions conditions = (Conditions) buildXMLObject(qname, null);

        conditions.getConditions().add(new AudienceRestrictionConditionImpl(null));
        conditions.getConditions().add(new DoNotCacheConditionImpl(null));
        // conditions.addCondition(condition);

        conditions.getConditions().add(new AudienceRestrictionConditionImpl(null));
        // conditions.addCondition(condition);
        //           
        conditions.getConditions().add(new AudienceRestrictionConditionImpl(null));

        assertEquals(expectedChildElementsDOM, conditions);

    }

}
