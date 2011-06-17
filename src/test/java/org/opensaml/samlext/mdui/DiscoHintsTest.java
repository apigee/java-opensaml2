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
import org.opensaml.samlext.saml2mdui.DiscoHints;
import org.opensaml.samlext.saml2mdui.DomainHint;
import org.opensaml.samlext.saml2mdui.GeolocationHint;
import org.opensaml.samlext.saml2mdui.IPHint;
import org.opensaml.samlext.saml2mdui.UIInfo;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationName}.
 */
public class DiscoHintsTest extends BaseSAMLObjectProviderTestCase {
    
    /** Expected count of &lt;IPHint/&gt;. */
    private final int expectedIPHintCount = 2;
    
    /** Expected count of &lt;DomainHint/&gt;. */
    private final int expectedDomainHintsCount = 3;
    
    /** Expected count of &lt;GeolocationHint/&gt;. */
    private final int expectedGeolocationHintsCount = 1;
    
    /**
     * Constructor.
     */
    public DiscoHintsTest() {
        singleElementFile = "/data/org/opensaml/samlext/mdui/DiscoHints.xml";
        childElementsFile = "/data/org/opensaml/samlext/mdui/DiscoHintsChildElements.xml";
    }
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();      
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        DiscoHints hints = (DiscoHints) unmarshallElement(singleElementFile);
        //
        // Shut up warning
        //
        hints.toString();
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(UIInfo.MDUI_NS, 
                                DiscoHints.DEFAULT_ELEMENT_LOCAL_NAME, 
                                UIInfo.MDUI_PREFIX);
        
        DiscoHints hints = (DiscoHints) buildXMLObject(qname);
        
        assertEquals(expectedDOM, hints);
    }

    /** {@inheritDoc} */
    public void testChildElementsUnmarshall(){
        DiscoHints hints = (DiscoHints) unmarshallElement(childElementsFile);
        
        assertEquals("<IPHint> count", expectedIPHintCount, hints.getIPHints().size());
        assertEquals("<DomainHint> count", expectedDomainHintsCount, hints.getDomainHints().size());
        assertEquals("<GeolocationHint> count", expectedGeolocationHintsCount, hints.getGeolocationHints().size());
    }

    /** {@inheritDoc} */
    public void testChildElementsMarshall(){
        QName qname = new QName(UIInfo.MDUI_NS, 
                DiscoHints.DEFAULT_ELEMENT_LOCAL_NAME, 
                UIInfo.MDUI_PREFIX);
        DiscoHints hints = (DiscoHints) buildXMLObject(qname);
        
        QName iPHintQname = new QName(UIInfo.MDUI_NS, 
                                      IPHint.DEFAULT_ELEMENT_LOCAL_NAME, 
                                      UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedIPHintCount; i++) {
            hints.getIPHints().add((IPHint) buildXMLObject(iPHintQname));
        }

        QName domainHintQname = new QName(UIInfo.MDUI_NS, 
                                           DomainHint.DEFAULT_ELEMENT_LOCAL_NAME, 
                                           UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedDomainHintsCount; i++) {
            hints.getDomainHints().add((DomainHint) buildXMLObject(domainHintQname));
        }

        QName geolocationHintQname = new QName(UIInfo.MDUI_NS, 
                                    GeolocationHint.DEFAULT_ELEMENT_LOCAL_NAME, 
                                    UIInfo.MDUI_PREFIX);
        for (int i = 0; i < expectedGeolocationHintsCount; i++) {
            hints.getGeolocationHints().add((GeolocationHint) buildXMLObject(geolocationHintQname));
        }

        assertEquals(expectedChildElementsDOM, hints);   
    }

}