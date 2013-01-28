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
import org.opensaml.samlext.samlec.EncType;

/**
 * Tests {@link EncTypeImpl}
 */
public class EncTypeTest extends BaseSAMLObjectProviderTestCase {

    /** Expected source ID value */
    private String expectedValue;

    /** Constructor */
    public EncTypeTest() {
        super();
        singleElementFile = "/data/org/opensaml/samlext/samlec/impl/EncType.xml";
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();

        expectedValue = "des-cbc-crc";
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        EncTypeBuilder builder = (EncTypeBuilder) builderFactory.getBuilder(EncType.DEFAULT_ELEMENT_NAME);

        EncType et = builder.buildObject();
        et.setValue(expectedValue);

        assertEquals(expectedDOM, et);
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        EncType et = (EncType) unmarshallElement(singleElementFile);

        assertNotNull(et);
        assertEquals(expectedValue, et.getValue());
    }
}