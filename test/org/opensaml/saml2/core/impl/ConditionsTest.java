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

package org.opensaml.saml2.core.impl;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Conditions;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.ConditionsImpl}.
 */
public class ConditionsTest extends SAMLObjectBaseTestCase {

    /** Expected NotBefore value */
    private DateTime expectedNotBefore;

    /** Expected NotOnOrAfter value */
    private DateTime expectedNotOnOrAfter;

    /** Count of Condition subelements */
    private int conditionCount = 7;

    /** Count of AudienceRestriction subelements */
    private int audienceRestrictionCount = 3;

    /** Count of ProxyRestriction subelements */
    private int proxyRestrictionCount = 2;

    /** Constructor */
    public ConditionsTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/Conditions.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/ConditionsOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/ConditionsChildElements.xml";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedNotBefore = new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC());
        expectedNotOnOrAfter = new DateTime(1984, 8, 26, 10, 11, 30, 43, ISOChronology.getInstanceUTC());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Conditions conditions = (Conditions) unmarshallElement(singleElementFile);

        DateTime notBefore = conditions.getNotBefore();
        assertEquals("NotBefore was " + notBefore + ", expected " + expectedNotBefore, expectedNotBefore, notBefore);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Conditions conditions = (Conditions) unmarshallElement(singleElementOptionalAttributesFile);

        DateTime notBefore = conditions.getNotBefore();
        assertEquals("NotBefore was " + notBefore + ", expected " + expectedNotBefore, expectedNotBefore, notBefore);

        DateTime notOnOrAfter = conditions.getNotOnOrAfter();
        assertEquals("NotOnOrAfter was " + notOnOrAfter + ", expected " + expectedNotOnOrAfter, expectedNotOnOrAfter,
                notOnOrAfter);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Conditions.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Conditions conditions = (Conditions) buildSAMLObject(qname);

        conditions.setNotBefore(expectedNotBefore);
        assertEquals(expectedDOM, conditions);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Conditions.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Conditions conditions = (Conditions) buildSAMLObject(qname);

        conditions.setNotBefore(expectedNotBefore);
        conditions.setNotOnOrAfter(expectedNotOnOrAfter);

        assertEquals(expectedOptionalAttributesDOM, conditions);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        Conditions conditions = (Conditions) unmarshallElement(childElementsFile);
        assertEquals("Condition count not as expected", conditionCount, conditions.getConditions().size());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Conditions.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Conditions conditions = (Conditions) buildSAMLObject(qname);

        conditions.getConditions().add(new OneTimeUseImpl());
        for (int i = 0; i < audienceRestrictionCount; i++) {
            conditions.getAudienceRestrictions().add(new AudienceRestrictionImpl());
        }
        conditions.setOneTimeUse(new OneTimeUseImpl());
        for (int i = 0; i < proxyRestrictionCount; i++) {
            conditions.getProxyRestrictions().add(new ProxyRestrictionImpl());
        }
        assertEquals(expectedChildElementsDOM, conditions);
    }
}