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

package org.opensaml.common;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.XMLTestCase;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Intermediate class that serves to initialize the configuration environment for other base test classes.
 */
public abstract class SAMLObjectTestCaseConfigInitializer extends XMLTestCase {

    /** Logger */
    private static Logger log = Logger.getLogger(SAMLObjectTestCaseConfigInitializer.class);
    
    /** XMLObject builder factory */
    protected static XMLObjectBuilderFactory builderFactory;

    /** XMLObject marshaller factory */
    protected static MarshallerFactory marshallerFactory;

    /** XMLObject unmarshaller factory */
    protected static UnmarshallerFactory unmarshallerFactory;
    
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
    /**
     * Asserts a given XMLObject is equal to an expected DOM. The XMLObject is marshalled and the resulting DOM object
     * is compared against the expected DOM object for equality.
     * 
     * @param expectedDOM the expected DOM
     * @param xmlObject the XMLObject to be marshalled and compared against the expected DOM
     */
    public void assertEquals(Document expectedDOM, XMLObject xmlObject) {
        assertEquals("Marshalled DOM was not the same as the expected DOM", expectedDOM, xmlObject);
    }

    /**
     * Asserts a given XMLObject is equal to an expected DOM. The XMLObject is marshalled and the resulting DOM object
     * is compared against the expected DOM object for equality.
     * 
     * @param failMessage the message to display if the DOMs are not equal
     * @param expectedDOM the expected DOM
     * @param xmlObject the XMLObject to be marshalled and compared against the expected DOM
     */
    public void assertEquals(String failMessage, Document expectedDOM, XMLObject xmlObject) {
        Marshaller marshaller = marshallerFactory.getMarshaller(xmlObject);
        if(marshaller == null){
            fail("Unable to locate marshaller for " + xmlObject.getElementQName() + " can not perform equality check assertion");
        }
        
        try {
            Element generatedDOM = marshaller.marshall(xmlObject, ParserPoolManager.getInstance().newDocument());
            if(log.isDebugEnabled()) {
                log.debug("Marshalled DOM was " + XMLHelper.nodeToString(generatedDOM));
            }
            assertXMLEqual(failMessage, expectedDOM, generatedDOM.getOwnerDocument());
        } catch (Exception e) {
            fail("Marshalling failed with the following error: " + e);
        }
    }
    
    /**
     * Builds the requested XMLObject.
     * 
     * @param objectQName name of the XMLObject
     * 
     * @return the build XMLObject
     */
    public XMLObject buildXMLObject(QName objectQName){
        XMLObjectBuilder builder = Configuration.getBuilderFactory().getBuilder(objectQName);
        if(builder == null){
            fail("Unable to retrieve builder for object QName " + objectQName);
        }
        return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
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
            
            // SAML 1.X Core (Asserion + Protocol) Validation Configuration
            Document saml1ValidationConfig = ppMgr.parse(clazz.getResourceAsStream("/saml1-core-validation-config.xml"));
            Configuration.load(saml1ValidationConfig);

            // SAML 2.0 Assertion Object Provider Configuration
            Document saml2assertionConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-assertion-config.xml"));
            Configuration.load(saml2assertionConfig);

            // SAML 2.0 Protocol Object Provider Configuration
            Document saml2protocolConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-protocol-config.xml"));
            Configuration.load(saml2protocolConfig);
            
            // SAML 2.0 Core (Asserion + Protocol) Validation Configuration
            Document saml2ValidationConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-core-validation-config.xml"));
            Configuration.load(saml2ValidationConfig);
            
            // SAML 2.0 Metadata Object Provider Configuration
            Document saml2mdConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-metadata-config.xml"));
            Configuration.load(saml2mdConfig);
            
            // SAML 2.0 Metadata Validation Configuration
            Document saml2mdValidationConfig = ppMgr.parse(clazz.getResourceAsStream("/saml2-metadata-validation-config.xml"));
            Configuration.load(saml2mdValidationConfig);
            
            builderFactory = Configuration.getBuilderFactory();
            marshallerFactory = Configuration.getMarshallerFactory();
            unmarshallerFactory = Configuration.getUnmarshallerFactory();

        } catch (Exception e) {
            System.err.println("Unable to configure OpenSAML: " + e);
        }
    }
}