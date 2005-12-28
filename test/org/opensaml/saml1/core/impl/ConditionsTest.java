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

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.common.util.xml.XMLHelper;
import org.opensaml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml1.core.Condition;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.DoNotCacheCondition;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test class for org.opensaml.saml1.core.Conditions
 */
public class ConditionsTest extends SAMLObjectBaseTestCase {

    /** A file with an Conditions object with kids */

    private final String fullElementsFile;

    /** The expected result of a marshalled multiple element */

    private Document expectedFullDOM;

    /**
     * Representation of NotBefore in test file.
     */

    private final GregorianCalendar notBeforeDate;

    /**
     * Representation of NotOnOrAfter in test file.
     */

    private final GregorianCalendar notOnOfAfter;

    /**
     * Constructor
     * 
     */
    public ConditionsTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleConditions.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleConditionsAttributes.xml";
        fullElementsFile = "/data/org/opensaml/saml1/ConditionsWithChildren.xml";
        //
        // NotBefore="1970-01-01T01:00:00.123Z"
        //
        notBeforeDate = new GregorianCalendar(1970, 0, 01, 01, 00, 00);
        notBeforeDate.set(Calendar.MILLISECOND, 123);
        //
        // NotOnOrAfter="1970-01-01T00:00:01.000Z"
        //
        notOnOfAfter = new GregorianCalendar(1970, 0, 01, 00, 00, 01);
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();

        expectedFullDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                .getResourceAsStream(fullElementsFile)));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        Conditions conditions;

        conditions = (Conditions) unmarshallElement(singleElementFile);

        GregorianCalendar date = conditions.getNotBefore();
        assertNull("NotBefore attribute has a value of " + date + ", expected no value", date);

        date = conditions.getNotOnOrAfter();
        assertNull("NotOnOrAfter attribute has a value of " + date + ", expected no value", date);
        
        
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        Conditions conditions;

        conditions = (Conditions) unmarshallElement(singleElementOptionalAttributesFile);

        GregorianCalendar date = conditions.getNotBefore();
        assertEquals("NotBefore attribute ", XMLHelper.calendarToString(notBeforeDate),XMLHelper.calendarToString(date));

        date = conditions.getNotOnOrAfter();
        assertEquals("NotOnOrAfter attribute ", XMLHelper.calendarToString(notOnOfAfter), XMLHelper.calendarToString(date));
    }

    /*
     * Test an XML file with children
     */

    public void testFullElementsUnmarshall() {
        Conditions conditions;

        conditions = (Conditions) unmarshallElement(fullElementsFile);

        assertEquals("Number of AudienceRestrictionCondition elements", 3, conditions
                .getAudienceRestrictionConditions().size());
        assertEquals("Number of DoNotCacheCondition children", 2, conditions.getDoNotCacheConditions().size());
        assertEquals("Wrong number of Condition children", 1, conditions.getConditions().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {

        Conditions conditions = (Conditions) buildSAMLObject(Conditions.QNAME);

        assertEquals(expectedDOM, conditions);

    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        Conditions conditions = (Conditions) buildSAMLObject(Conditions.QNAME);

        conditions.setNotBefore(notBeforeDate);
        conditions.setNotOnOrAfter(notOnOfAfter);

        assertEquals(expectedOptionalAttributesDOM, conditions);
    }

    /**
     * Test an XML file with Children
     */
    
    public void testFullElementsMarshall() {
        Conditions conditions = (Conditions) buildSAMLObject(Conditions.QNAME);

        AudienceRestrictionCondition audienceRestrictionCondition = null;
        DoNotCacheCondition doNotCacheCondition = null;
        Condition condition = null;

        try {
            throw new IllegalAddException();
            //conditions.addAudienceRestrictionCondition(audienceRestrictionCondition);
            //conditions.addDoNotCacheCondition(doNotCacheCondition);
            //conditions.addCondition(condition);

            // conditions.addAudienceRestrictionCondition(audienceRestrictionCondition);
            // conditions.addDoNotCacheCondition(doNotCacheCondition);
            // conditions.addCondition(condition);
            //           
            // conditions.addAudienceRestrictionCondition(audienceRestrictionCondition);
            // conditions.addDoNotCacheCondition(doNotCacheCondition);
            // conditions.addAudienceRestrictionCondition(audienceRestrictionCondition);
        } catch (IllegalAddException e) {
            fail("Exception " + e + " while adding members");
            e.printStackTrace();
        }

        assertEquals(expectedFullDOM, conditions);

    }

}
