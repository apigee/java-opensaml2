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
package org.opensaml.saml2.core.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.StatusDetail;
import org.opensaml.xml.schema.impl.XSAnyBuilder;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.core.impl.StatusDetailImpl}.
 */
public class StatusDetailTest extends BaseSAMLObjectProviderTestCase {

    /**
     * Constructor.
     *
     */
    public StatusDetailTest() {
       singleElementFile = "/data/org/opensaml/saml2/core/impl/StatusDetail.xml";
       childElementsFile = "/data/org/opensaml/saml2/core/impl/StatusDetailChildElements.xml";
    }
    
    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, StatusDetail.DEFAULT_ELEMENT_LOCAL_NAME);
        StatusDetail statusDetail = (StatusDetail) buildXMLObject(qname);
        
        assertEquals(expectedDOM, statusDetail);
    }

    /** {@inheritDoc} */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, StatusDetail.DEFAULT_ELEMENT_LOCAL_NAME);
        StatusDetail statusDetail = (StatusDetail) buildXMLObject(qname);
        QName childQname = new QName("http://www.example.org/testObjects", "SimpleElement", "test");
        
        XSAnyBuilder xsAnyBuilder = new XSAnyBuilder();
        
        statusDetail.getUnknownXMLObjects().add(xsAnyBuilder.buildObject(childQname));
        statusDetail.getUnknownXMLObjects().add(xsAnyBuilder.buildObject(childQname));
        statusDetail.getUnknownXMLObjects().add(xsAnyBuilder.buildObject(childQname));
        
        assertEquals(expectedChildElementsDOM, statusDetail);
    }



    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        StatusDetail statusDetail = (StatusDetail) unmarshallElement(singleElementFile);
        
        assertNotNull(statusDetail);
    }

    /** {@inheritDoc} */
    public void testChildElementsUnmarshall() {
        StatusDetail statusDetail = (StatusDetail) unmarshallElement(childElementsFile);
        
        assertNotNull(statusDetail);
        assertEquals(3, statusDetail.getUnknownXMLObjects().size());
    }
    
}