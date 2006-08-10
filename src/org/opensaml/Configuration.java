/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

import org.apache.xml.security.Init;
import org.opensaml.common.SAMLObjectTestCaseConfigInitializer;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.xml.XMLConfigurator;
import org.w3c.dom.Document;

/**
 * OpenSAML configuration singleton.  This 
 */
public class Configuration extends org.opensaml.xml.Configuration {

    public static synchronized void init() {
        Init.init();
        
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();

        Class clazz = SAMLObjectTestCaseConfigInitializer.class;
        try {
            XMLConfigurator configurator = new XMLConfigurator();
            
            // Common Object Provider Configuration
            Document commonConfig = ppMgr.parse(clazz.getResourceAsStream("/common-config.xml"));
            configurator.load(commonConfig);

            // SAML 1.X Assertion Object Provider Configuration
            Document saml1AssertionConfig = ppMgr.parse(clazz.getResourceAsStream("/saml1-assertion-config.xml"));
            configurator.load(saml1AssertionConfig);

            // SAML 1.X Protocol Object Provider Configuration
            Document saml1ProtocolConfig = ppMgr.parse(clazz.getResourceAsStream("/saml1-protocol-config.xml"));
            configurator.load(saml1ProtocolConfig);
            
            // SAML 1.X Core (Asserion + Protocol) Validation Configuration
            Document saml1ValidationConfig = ppMgr.parse(clazz.getResourceAsStream("/saml1-core-validation-config.xml"));
            configurator.load(saml1ValidationConfig);

            // SAML 2.0 Assertion Object Provider Configuration
            Document saml2assertionConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-assertion-config.xml"));
            configurator.load(saml2assertionConfig);

            // SAML 2.0 Protocol Object Provider Configuration
            Document saml2protocolConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-protocol-config.xml"));
            configurator.load(saml2protocolConfig);
            
            // SAML 2.0 Core (Asserion + Protocol) Validation Configuration
            Document saml2ValidationConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-core-validation-config.xml"));
            configurator.load(saml2ValidationConfig);
            
            // SAML 2.0 Metadata Object Provider Configuration
            Document saml2mdConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-metadata-config.xml"));
            configurator.load(saml2mdConfig);
            
            // SAML 2.0 Metadata Validation Configuration
            Document saml2mdValidationConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-metadata-validation-config.xml"));
            configurator.load(saml2mdValidationConfig);
        } catch (Exception e) {
            System.err.println("Unable to configure OpenSAML: " + e);
        }
    }
}