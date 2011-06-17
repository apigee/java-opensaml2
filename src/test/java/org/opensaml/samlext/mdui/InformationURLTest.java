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
import org.opensaml.saml2.metadata.LocalizedString;
import org.opensaml.samlext.saml2mdui.InformationURL;
import org.opensaml.samlext.saml2mdui.UIInfo;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationName}.
 */
public class InformationURLTest extends BaseSAMLObjectProviderTestCase {
    
    /** Expected name. */
    private LocalizedString expectName;
    
    /**
     * Constructor.
     */
    public InformationURLTest() {
        singleElementFile = "/data/org/opensaml/samlext/mdui/InformationURL.xml";
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        expectName = new LocalizedString("http://example.org/Info/URL", "infoUrlLang");
        
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        InformationURL url = (InformationURL) unmarshallElement(singleElementFile);
        
        assertEquals("URI was not expected value", expectName, url.getURI());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(UIInfo.MDUI_NS, 
                                InformationURL.DEFAULT_ELEMENT_LOCAL_NAME, 
                                UIInfo.MDUI_PREFIX);
        
        InformationURL url = (InformationURL) buildXMLObject(qname);
        
        url.setURI(expectName);

        assertEquals(expectedDOM, url);
    }
}