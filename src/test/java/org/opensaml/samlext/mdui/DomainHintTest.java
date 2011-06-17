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
import org.opensaml.samlext.saml2mdui.DomainHint;
import org.opensaml.samlext.saml2mdui.UIInfo;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationName}.
 */
public class DomainHintTest extends BaseSAMLObjectProviderTestCase {
    
    /** Expected name. */
    private String expectedHint;
    
    /**
     * Constructor.
     */
    public DomainHintTest() {
        singleElementFile = "/data/org/opensaml/samlext/mdui/DomainHint.xml";
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        expectedHint = ".ed.ac.uk";
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        DomainHint hint = (DomainHint) unmarshallElement(singleElementFile);
        
        assertEquals("Name was not expected value", expectedHint, hint.getHint());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(UIInfo.MDUI_NS, 
                                DomainHint.DEFAULT_ELEMENT_LOCAL_NAME, 
                                UIInfo.MDUI_PREFIX);
        
        DomainHint hint = (DomainHint) buildXMLObject(qname);
        
        hint.setHint(expectedHint);

        assertEquals(expectedDOM, hint);
    }
}