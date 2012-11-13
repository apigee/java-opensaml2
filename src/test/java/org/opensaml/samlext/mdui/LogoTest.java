/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
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
package org.opensaml.samlext.mdui;

import javax.xml.namespace.QName;

import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.samlext.saml2mdui.Logo;
import org.opensaml.samlext.saml2mdui.UIInfo;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationName}.
 */
public class LogoTest extends BaseSAMLObjectProviderTestCase {
    
    /** Expected URL. */
    private final String expectedURL;
    
    /** expected language. */
    private final String expectedLang;
    
    /** expected height. */
    private final Integer expectedHeight;
    
    /** expected width. */
    private final Integer expectedWidth;
    
    /**
     * Constructor.
     */
    public LogoTest() {
        singleElementFile = "/data/org/opensaml/samlext/mdui/Logo.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/samlext/mdui/LogoWithLang.xml";
        expectedURL = "http://exaple.org/Logo";
        expectedHeight = new Integer(10);
        expectedWidth = new Integer(23);
        expectedLang = "logoLang";
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();      
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        Logo logo = (Logo) unmarshallElement(singleElementFile);
        
        assertEquals("URL was not expected value", expectedURL, logo.getURL());
        assertEquals("height was not expected value", expectedHeight, logo.getHeight());
        assertEquals("width was not expected value", expectedWidth, logo.getWidth());
    }

    /** {@inheritDoc} */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Logo logo = (Logo) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("URL was not expected value", expectedURL, logo.getURL());
        assertEquals("height was not expected value", expectedHeight, logo.getHeight());
        assertEquals("width was not expected value", expectedWidth, logo.getWidth());
        assertEquals("xml:lang was not the expected value", expectedLang, logo.getXMLLang());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(UIInfo.MDUI_NS, 
                                Logo.DEFAULT_ELEMENT_LOCAL_NAME, 
                                UIInfo.MDUI_PREFIX);
        
        Logo logo = (Logo) buildXMLObject(qname);
        
        logo.setURL(expectedURL);
        logo.setWidth(expectedWidth);
        logo.setHeight(expectedHeight);

        assertEquals(expectedDOM, logo);
    }

    /** {@inheritDoc} */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(UIInfo.MDUI_NS, 
                                Logo.DEFAULT_ELEMENT_LOCAL_NAME, 
                                UIInfo.MDUI_PREFIX);
        
        Logo logo = (Logo) buildXMLObject(qname);
        
        logo.setURL(expectedURL);
        logo.setWidth(expectedWidth);
        logo.setHeight(expectedHeight);
        logo.setXMLLang(expectedLang);

        assertEquals(expectedOptionalAttributesDOM, logo);
    }
}