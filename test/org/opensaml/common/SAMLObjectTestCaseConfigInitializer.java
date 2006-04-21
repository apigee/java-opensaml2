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

package org.opensaml.common;

import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObjectBaseTestCase;
import org.w3c.dom.Document;

/**
 * Intermediate class that serves to initialize the configuration environment for other base test classes.
 */
public abstract class SAMLObjectTestCaseConfigInitializer extends XMLObjectBaseTestCase {

    /**
     * Constructor
     * 
     */
    public SAMLObjectTestCaseConfigInitializer() {
        super();
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    static {
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();

        Class clazz = SAMLObjectTestCaseConfigInitializer.class;
        try {
            // Common Object Provider Configuration
            Document commonConfig = ppMgr.parse(clazz.getResourceAsStream("/common-config.xml"));
            Configuration.load(commonConfig);

            // SAML 1.X Assertion Object Provider Configuration
            Document saml1AssertionConfig = ppMgr.parse(clazz.getResourceAsStream("/saml1-assertion-config.xml"));
            Configuration.load(saml1AssertionConfig);

            // SAML 1.X Protocol Object Provider Configuration
            Document saml1ProtocolConfig = ppMgr.parse(clazz.getResourceAsStream("/saml1-protocol-config.xml"));
            Configuration.load(saml1ProtocolConfig);
            
            // SAML 1.X Protocol Object Provider Configuration
            Document saml1ValidationConfig = ppMgr.parse(clazz.getResourceAsStream("/saml1-validation-config.xml"));
            Configuration.load(saml1ValidationConfig);

            // SAML 2.0 Metadata Object Provider Configuration
            Document saml2mdConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-metadata-config.xml"));
            Configuration.load(saml2mdConfig);

            // SAML 2.0 Assertion Object Provider Configuration
            Document saml2assertionConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-assertion-config.xml"));
            Configuration.load(saml2assertionConfig);

            // SAML 2.0 Protocol Object Provider Configuration
            Document saml2protocolConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-protocol-config.xml"));
            Configuration.load(saml2protocolConfig);

        } catch (Exception e) {
            System.err.println("Unable to configure OpenSAML: " + e);
        }
    }
}