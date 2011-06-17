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

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.samlext.saml2mdui.Keywords;
import org.opensaml.samlext.saml2mdui.UIInfo;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationName}.
 */
public class KeywordsTest extends BaseSAMLObjectProviderTestCase {
    
    /** Expected Keywords. */
    private final List<String> expectedWords;
    /** Expected Language.*/
    private final String expectedLang;
    
    /**
     * Constructor.
     */
    public KeywordsTest() {
        singleElementFile = "/data/org/opensaml/samlext/mdui/Keywords.xml";
        String[] contents = {"This", "is", "a", "six", "element", "keyword"}; 
        expectedWords = new ArrayList(contents.length);
        for (String s : contents) {
            expectedWords.add(s);
        }
        expectedLang = "en";
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        Keywords name = (Keywords) unmarshallElement(singleElementFile);
        
        assertEquals("Keyworks were not expected value", expectedWords, name.getKeywords());
        assertEquals("Language was not expected value", expectedLang, name.getXMLLang());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(UIInfo.MDUI_NS, 
                                Keywords.DEFAULT_ELEMENT_LOCAL_NAME, 
                                UIInfo.MDUI_PREFIX);
        
        Keywords keywords = (Keywords) buildXMLObject(qname);
        keywords.setXMLLang(expectedLang);
        keywords.setKeywords(expectedWords);

        assertEquals(expectedDOM, keywords);
    }
}