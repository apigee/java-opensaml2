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

    /** Expected source ID value */
    private String expectedValue;

    /** Constructor */
    public GeneratedKeyTest() {
        super();
        singleElementFile = "/data/org/opensaml/samlext/samlec/impl/GeneratedKey.xml";
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();

        expectedValue = "AGeneratedKey";
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        GeneratedKeyBuilder builder = (GeneratedKeyBuilder) builderFactory.getBuilder(GeneratedKey.DEFAULT_ELEMENT_NAME);

        GeneratedKey key = builder.buildObject();
        key.setValue(expectedValue);

        assertEquals(expectedDOM, key);
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        GeneratedKey key = (GeneratedKey) unmarshallElement(singleElementFile);

        assertNotNull(key);
        assertEquals(expectedValue, key.getValue());
    }
}