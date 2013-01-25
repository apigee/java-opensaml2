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
package org.opensaml.samlext.saml2aslo.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.samlext.saml2aslo.Asynchronous;

/**
 *
 */
public class AsynchronousTest extends BaseSAMLObjectProviderTestCase {

    /**
     * Constructor
     *
     */
    public AsynchronousTest() {
        singleElementFile = "/data/org/opensaml/samlext/saml2aslo/Asynchronous.xml";
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        Asynchronous term = (Asynchronous) unmarshallElement(singleElementFile);
        
        assertNotNull("Asynchronous", term);
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20PASLO_NS, Asynchronous.DEFAULT_ELEMENT_LOCAL_NAME);
        Asynchronous term = (Asynchronous) buildXMLObject(qname);
        
        assertEquals(expectedDOM, term);
    }
}