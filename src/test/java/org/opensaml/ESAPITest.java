/*
 * Copyright 2011 University Corporation for Advanced Internet Development, Inc.
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

package org.opensaml;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.SecurityConfiguration;

import junit.framework.TestCase;

/**
 * Test that OWASPI ESAPI is initialized properly by the default bootstrap process.
 */
public class ESAPITest extends TestCase {

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        DefaultBootstrap.bootstrap();
    }
    
    /**
     *  Tests that basic initialization has happened.
     */
    public void testInit() {
        SecurityConfiguration sc = ESAPI.securityConfiguration();
        assertNotNull("ESAPI SecurityConfiguration was null", sc);
        
        Encoder encoder = ESAPI.encoder();
        assertNotNull("ESAPI Encoder was null", encoder);
    }

}
