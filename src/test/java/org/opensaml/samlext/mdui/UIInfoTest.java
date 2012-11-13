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
import org.opensaml.samlext.saml2mdui.Description;
import org.opensaml.samlext.saml2mdui.DisplayName;
import org.opensaml.samlext.saml2mdui.InformationURL;
import org.opensaml.samlext.saml2mdui.Keywords;
import org.opensaml.samlext.saml2mdui.Logo;
import org.opensaml.samlext.saml2mdui.PrivacyStatementURL;
import org.opensaml.samlext.saml2mdui.UIInfo;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationName}.
 */
public class UIInfoTest extends BaseSAMLObjectProviderTestCase {
    
    /** Expected count of &lt;DisplayName&gt;. */
    private final int expectedDisplayNamesCount = 3;
    
    /** Expected count of &lt;Description&gt;. */
    private final int expectedDescriptionsCount = 1;
    
    /** Expected count of &lt;Keywords&gt;. */
    private final int expectedKeywordsCount = 2;
    
    /** Expected count of &lt;Logo&gt;. */
    private final int expectedLogosCount = 0;
    
    /** Expected count of &lt;InformationURL&gt;. */
    private final int expectedInformationURLsCount = 1;
    
    /** Expected count of &lt;PrivacyStatementURL&gt;. */
    private final int expectedPrivacyStatementURLsCount =1;
    
    /**
     * Constructor.
     */
    public UIInfoTest() {
        singleElementFile = "/data/org/opensaml/samlext/mdui/UIInfo.xml";
        childElementsFile = "/data/org/opensaml/samlext/mdui/UIInfoChildElements.xml";
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();      
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        unmarshallElement(singleElementFile);
        //
        // No contents sanity to check
        //
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(UIInfo.MDUI_NS, 
                                UIInfo.DEFAULT_ELEMENT_LOCAL_NAME, 
                                UIInfo.MDUI_PREFIX);
        
        UIInfo uiinfo = (UIInfo) buildXMLObject(qname);
        
        assertEquals(expectedDOM, uiinfo);
    }

    /** {@inheritDoc} */
    public void testChildElementsUnmarshall(){
        UIInfo uiinfo = (UIInfo) unmarshallElement(childElementsFile);
        
        assertEquals("<DisplayName> count", expectedDisplayNamesCount, uiinfo.getDisplayNames().size());
        assertEquals("<Descriptions> count", expectedDescriptionsCount, uiinfo.getDescriptions().size());
        assertEquals("<Logos> count", expectedLogosCount, uiinfo.getLogos().size());
        assertEquals("<Keywords> count", expectedKeywordsCount, uiinfo.getKeywords().size());
        assertEquals("<InformationURLs> count", expectedInformationURLsCount, uiinfo.getInformationURLs().size());
        assertEquals("<PrivacyStatementURLs> count", expectedPrivacyStatementURLsCount, 
                                                     uiinfo.getPrivacyStatementURLs().size());
       
    }

    /** {@inheritDoc} */
    public void testChildElementsMarshall(){
        QName qname = new QName(UIInfo.MDUI_NS, 
                UIInfo.DEFAULT_ELEMENT_LOCAL_NAME, 
                UIInfo.MDUI_PREFIX);
        UIInfo uiinfo = (UIInfo) buildXMLObject(qname);
        
        QName displayNameQname = new QName(UIInfo.MDUI_NS, 
                                      DisplayName.DEFAULT_ELEMENT_LOCAL_NAME, 
                                      UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedDisplayNamesCount; i++) {
            uiinfo.getDisplayNames().add((DisplayName) buildXMLObject(displayNameQname));
        }

        QName descriptionQname = new QName(UIInfo.MDUI_NS, 
                Description.DEFAULT_ELEMENT_LOCAL_NAME, 
                UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedDescriptionsCount; i++) {
            uiinfo.getDescriptions().add((Description) buildXMLObject(descriptionQname));
        }

        QName logoQname = new QName(UIInfo.MDUI_NS, 
                Logo.DEFAULT_ELEMENT_LOCAL_NAME, 
                UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedLogosCount; i++) {
            uiinfo.getLogos().add((Logo) buildXMLObject(logoQname));
        }

        QName keywordsQname = new QName(UIInfo.MDUI_NS, 
                Keywords.DEFAULT_ELEMENT_LOCAL_NAME, 
                UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedKeywordsCount; i++) {
            uiinfo.getKeywords().add((Keywords) buildXMLObject(keywordsQname));
        }

        QName informationURLQname = new QName(UIInfo.MDUI_NS, 
                InformationURL.DEFAULT_ELEMENT_LOCAL_NAME, 
                UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedInformationURLsCount; i++) {
            uiinfo.getInformationURLs().add((InformationURL) buildXMLObject(informationURLQname));
        }

        QName privacyStatementURLQname = new QName(UIInfo.MDUI_NS, 
                PrivacyStatementURL.DEFAULT_ELEMENT_LOCAL_NAME, 
                UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedPrivacyStatementURLsCount; i++) {
            uiinfo.getPrivacyStatementURLs().add((PrivacyStatementURL) buildXMLObject(privacyStatementURLQname));
        }

        assertEquals(expectedChildElementsDOM, uiinfo);   
    }

}