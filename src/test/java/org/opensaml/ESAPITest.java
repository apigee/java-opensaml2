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

package org.opensaml;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.SecurityConfiguration;

import junit.framework.TestCase;

/**
 * Test that OWASPI ESAPI is initialized properly by the default bootstrap process.
 */
public class ESAPITest extends TestCase {
    
    private String systemPropertyKey = "org.owasp.esapi.SecurityConfiguration";
    private String opensamlConfigImpl = ESAPISecurityConfig.class.getName();

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        DefaultBootstrap.bootstrap();
    }
    
    /**
     *  Tests that basic initialization has happened.
     */
    public void testInit() {
        assertEquals(opensamlConfigImpl, System.getProperty(systemPropertyKey));
        
        SecurityConfiguration sc = ESAPI.securityConfiguration();
        assertNotNull("ESAPI SecurityConfiguration was null", sc);
        
        assertTrue(sc instanceof ESAPISecurityConfig);
        
        Encoder encoder = ESAPI.encoder();
        assertNotNull("ESAPI Encoder was null", encoder);
    }

}
