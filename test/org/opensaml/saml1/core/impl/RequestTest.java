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

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AssertionArtifact;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.AttributeQuery;
import org.opensaml.saml1.core.Request;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test in and around the {@link org.opensaml.saml1.core.Request} interface
 */
public class RequestTest extends SAMLObjectBaseTestCase {

    private final GregorianCalendar expectedIssueInstant;
    private final String expectedIssueInstantAsString;

    private final int expectedMinorVersion;

    public RequestTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleRequest.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleRequestAttributes.xml";
        expectedIssueInstant = new GregorianCalendar(1970, 0, 1, 0, 0, 0);
        expectedIssueInstant.set(Calendar.MILLISECOND, 100);
        expectedIssueInstantAsString = DatatypeHelper.calendarToString(expectedIssueInstant, DatatypeHelper.UTC_TIMEZONE);
        expectedMinorVersion = 1;
    }
    
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        Request request = (Request) unmarshallElement(singleElementFile);

        GregorianCalendar date = request.getIssueInstant();
        assertNull("IssueInstant attribute has a value of " + DatatypeHelper.calendarToString(date, 0)
                + ", expected no value", date);

        int minorVersion = request.getMinorVersion();
        
        assertTrue("MinorVersion has value " + minorVersion + " expected no value", minorVersion == 0);
        assertNull("Query has value", request.getQuery());
        assertEquals("AssertionArtifact present", 0, request.getAssertionArtifacts().size());
        assertEquals("AssertionIDReferences present", 0, request.getAssertionIDReferences().size());
        assertNull("IssueInstance has value", request.getIssueInstant());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        Request request = (Request) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("MinorVersion", expectedMinorVersion, request.getMinorVersion());
        assertEquals("IssueInstant", expectedIssueInstantAsString, 
                                     DatatypeHelper.calendarToString(request.getIssueInstant(), DatatypeHelper.UTC_TIMEZONE));
        
    }
    
    /**
     * Test a few Requests with children 
     */
    
    public void testSingleElementChildrenUnmarshall() {
        Request request; 
        
        request = (Request) unmarshallElement("/data/org/opensaml/saml1/RequestWithAssertionArtifact.xml");
        
        assertNull("Query is not null", request.getQuery());
        assertEquals("AssertionId count", 0, request.getAssertionIDReferences().size());
        assertEquals("AssertionArtifact count", 2, request.getAssertionArtifacts().size());
        
        request = (Request) unmarshallElement("/data/org/opensaml/saml1/RequestWithQuery.xml");
        
        assertNotNull("Query is not null", request.getQuery());
        assertEquals("AssertionId count", 0, request.getAssertionIDReferences().size());
        assertEquals("AssertionArtifact count", 0, request.getAssertionArtifacts().size());
        
        request = (Request) unmarshallElement("/data/org/opensaml/saml1/RequestWithAssertionIDReference.xml");
        assertNull("Query is not null", request.getQuery());
        assertNotNull("AssertionId", request.getAssertionIDReferences());
        assertEquals("AssertionId count", 3, request.getAssertionIDReferences().size());
        assertEquals("AssertionArtifact count", 0, request.getAssertionArtifacts().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, Request.LOCAL_NAME);
        Request request= (Request) buildSAMLObject(qname);

        assertEquals(expectedDOM, request);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, Request.LOCAL_NAME);
        Request request = (Request) buildSAMLObject(qname);

        request.setIssueInstant(expectedIssueInstant);
        request.setMinorVersion(expectedMinorVersion);
        assertEquals(expectedOptionalAttributesDOM, request);
    }

    /**
     * Test a few Requests with children 
     */
    
    public void testSingleElementChildrenMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, Request.LOCAL_NAME);
        QName oqname;

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        Request request; 
        Document dom;
                
        
        try {
            dom = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                        .getResourceAsStream("/data/org/opensaml/saml1/RequestWithAssertionArtifact.xml")));
            request = (Request) buildSAMLObject(qname); 
            oqname = new QName(SAMLConstants.SAML1P_NS, AssertionArtifact.LOCAL_NAME);
            request.getAssertionArtifacts().add((AssertionArtifact) buildSAMLObject(oqname));
            request.getAssertionArtifacts().add((AssertionArtifact) buildSAMLObject(oqname));
            assertEquals(dom, request);
          
            dom = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                    .getResourceAsStream("/data/org/opensaml/saml1/RequestWithAssertionIDReference.xml")));
            request = (Request) buildSAMLObject(qname); 
            oqname = new QName(SAMLConstants.SAML1_NS, AssertionIDReference.LOCAL_NAME);
            request.getAssertionIDReferences().add((AssertionIDReference) buildSAMLObject(oqname));
            request.getAssertionIDReferences().add((AssertionIDReference) buildSAMLObject(oqname));
            request.getAssertionIDReferences().add((AssertionIDReference) buildSAMLObject(oqname));
            assertEquals(dom, request);

            dom = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                    .getResourceAsStream("/data/org/opensaml/saml1/RequestWithQuery.xml")));
            request = (Request) buildSAMLObject(qname); 
            oqname = new QName(SAMLConstants.SAML1P_NS, AttributeQuery.LOCAL_NAME);
            request.setQuery((AttributeQuery) buildSAMLObject(oqname));
            assertEquals(dom, request);

        } catch (XMLParserException e) {
            fail(e.toString());
        }
    }

}
