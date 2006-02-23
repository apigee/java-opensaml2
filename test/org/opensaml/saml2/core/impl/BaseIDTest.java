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

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.BaseID;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.BaseIDImpl}.
 */
public class BaseIDTest extends SAMLObjectBaseTestCase {

    /** Expected NameQualifier value */
    private String expectedNameQualifier;

    /** Expected SPNameQualifier value */
    private String expectedSPNameQualifier;

    /** Constructor */
    public BaseIDTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/BaseID.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/BaseIDOptionalAttributes.xml";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        expectedNameQualifier = "nq";
        expectedSPNameQualifier = "spnq";
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        BaseID baseID = (BaseID) unmarshallElement(singleElementFile);

        String nameQualifier = baseID.getNameQualifier();
        assertEquals("NameQualifier not as expected", nameQualifier, expectedNameQualifier);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        BaseID baseID = (BaseID) unmarshallElement(singleElementOptionalAttributesFile);
        String nameQualifier = baseID.getNameQualifier();
        assertEquals("NameQualifier not as expected", nameQualifier, expectedNameQualifier);

        String spNameQualifier = baseID.getSPNameQualifier();
        assertEquals("SPNameQualifier not as expected", spNameQualifier, expectedSPNameQualifier);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, BaseID.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        BaseID baseID = (BaseID) buildSAMLObject(qname);

        baseID.setNameQualifier(expectedNameQualifier);
        assertEquals(expectedDOM, baseID);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, BaseID.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        BaseID baseID = (BaseID) buildSAMLObject(qname);

        baseID.setNameQualifier(expectedNameQualifier);
        baseID.setSPNameQualifier(expectedSPNameQualifier);
        assertEquals(expectedOptionalAttributesDOM, baseID);
    }
}