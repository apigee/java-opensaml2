/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
package org.opensaml.saml1.core.impl;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml1.core.StatusMessage;

/**
 *
 */
public class StatusMessageTest extends SAMLObjectBaseTestCase {

    private final String contents;

    /**
     * Constructor
     *
     */
    public StatusMessageTest() {
        super();

        contents = "Nibble a Happy Warthog";
        singleElementFile = "/data/org/opensaml/saml1/singleStatusMessage.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/FullStatusMessage.xml";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        StatusMessage statusMessage = (StatusMessage) unmarshallElement(singleElementFile);
        assertNull("Contents", statusMessage.getMessage());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        StatusMessage statusMessage = (StatusMessage) unmarshallElement(singleElementOptionalAttributesFile);
        assertEquals("Contents", contents, statusMessage.getMessage());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        StatusMessage statusMessage = new StatusMessageImpl();
        
        assertEquals(expectedDOM, statusMessage);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        StatusMessage statusMessage = new StatusMessageImpl();

        statusMessage.setMessage(contents);
        assertEquals(expectedOptionalAttributesDOM, statusMessage);
    }
}
