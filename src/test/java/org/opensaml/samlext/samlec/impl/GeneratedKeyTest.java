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

package org.opensaml.samlext.samlec.impl;

import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.samlext.samlec.GeneratedKey;

/**
 * Tests {@link GeneratedKeyImpl}
 */
public class GeneratedKeyTest extends BaseSAMLObjectProviderTestCase {

    private String expectedValue;

    private String expectedSOAP11Actor;
    
    private Boolean expectedSOAP11MustUnderstand;
    
    /** Constructor */
    public GeneratedKeyTest() {
        super();
        singleElementFile = "/data/org/opensaml/samlext/samlec/impl/GeneratedKey.xml";
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();

        expectedValue = "AGeneratedKey";
        expectedSOAP11Actor = "https://soap11actor.example.org";
        expectedSOAP11MustUnderstand = true;
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        GeneratedKeyBuilder builder = (GeneratedKeyBuilder) builderFactory.getBuilder(GeneratedKey.DEFAULT_ELEMENT_NAME);

        GeneratedKey key = builder.buildObject();
        key.setSOAP11Actor(expectedSOAP11Actor);
        key.setSOAP11MustUnderstand(expectedSOAP11MustUnderstand);
        key.setValue(expectedValue);

        assertEquals(expectedDOM, key);
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        GeneratedKey key = (GeneratedKey) unmarshallElement(singleElementFile);

        assertNotNull(key);
        assertEquals(expectedValue, key.getValue());
        assertEquals("SOAP mustUnderstand had unxpected value", expectedSOAP11MustUnderstand, key.isSOAP11MustUnderstand());
        assertEquals("SOAP actor had unxpected value", expectedSOAP11Actor, key.getSOAP11Actor());
    }
}