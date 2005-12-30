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

package org.opensaml.saml2.metadata.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AffiliateMember;

public class AffiliateMemberTest extends SAMLObjectBaseTestCase {
    
    protected String expectedMemberID;
    
    public AffiliateMemberTest(){
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/AffiliateMember.xml";
    }
    
    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        expectedMemberID = "urn:example.org:members:foo";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AffiliateMember member = (AffiliateMember)unmarshallElement(singleElementFile);
        
        String memberID = member.getID();
        assertEquals("Affiliation memeber ID was " + memberID + ", expected " + expectedMemberID, expectedMemberID, memberID);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        //DO NOTHING; no optional attributes
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AffiliateMember.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        AffiliateMember member = (AffiliateMember) buildSAMLObject(qname);
        
        member.setID(expectedMemberID);
        
        assertEquals(expectedDOM, member);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        //DO NOTHING; no optional attributes
    }
}