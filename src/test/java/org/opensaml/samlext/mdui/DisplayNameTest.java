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
import org.opensaml.samlext.saml2mdui.DisplayName;
import org.opensaml.samlext.saml2mdui.UIInfo;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationName}.
 */
public class DisplayNameTest extends BaseSAMLObjectProviderTestCase {
    
    /** Expected name. */
    private LocalizedString expectName;
    
    /**
     * Constructor.
     */
    public DisplayNameTest() {
        singleElementFile = "/data/org/opensaml/samlext/mdui/DisplayName.xml";
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        expectName = new LocalizedString("Prifysgol Caerdydd", "cy");
        
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        DisplayName name = (DisplayName) unmarshallElement(singleElementFile);
        
        assertEquals("Name was not expected value", expectName, name.getName());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(UIInfo.MDUI_NS, 
                                DisplayName.DEFAULT_ELEMENT_LOCAL_NAME, 
                                UIInfo.MDUI_PREFIX);
        
        DisplayName name = (DisplayName) buildXMLObject(qname);
        
        name.setName(expectName);

        assertEquals(expectedDOM, name);
    }
}