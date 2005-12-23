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

package org.opensaml.saml2.metadata.impl;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLObjectBuilderFactory;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallerFactory;
import org.opensaml.common.io.MarshallingException;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallerFactory;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.common.util.xml.XMLParserException;
import org.opensaml.saml2.metadata.AdditionalMetadataLocation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class AdditionalMetadataLocationTest extends BaseTestCase {
    
    /** Location of file containing a single EntitiesDescriptor element with NO optional attributes*/
    private static String singleElementFile = "/data/org/opensaml/saml2/metadata/impl/singleAdditionalMetadataLocation.xml";
    
    /** Expected value of namespace attribute */
    private String expectedNamespace = "http://example.org/xmlns";
    
    /** Expected value of element content */
    private String expectedContent = "http://example.org";
    
    /** The expected result of a marshalled single EntitiesDescriptor element with no optional attributes */
    private Document expectedDOM;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        expectedDOM = ppMgr
                .parse(new InputSource(EntitiesDescriptorTest.class.getResourceAsStream(singleElementFile)));
    }
    
    /**
     * Tests unmarshalling a document that contains a single EntitiesDescriptor element (no children) with no optional attributes.
     */
    public void testSingleElementUnmarshall() {
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        try {
            Document doc = ppMgr.parse(new InputSource(EntitiesDescriptorTest.class
                    .getResourceAsStream(singleElementFile)));
            Element locationElement = doc.getDocumentElement();

            Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(locationElement);
            if (unmarshaller == null) {
                fail("Unable to retrieve unmarshaller by DOM Element");
            }

            AdditionalMetadataLocation locationObj = (AdditionalMetadataLocation) unmarshaller
                    .unmarshall(locationElement);

            String location = locationObj.getLocationURI();
            assertEquals("Location URI was " + location + ", expected " + expectedContent, expectedContent, location);

            String namespace = locationObj.getNamespaceURI();
            assertEquals("Namepsace URI was " + namespace + ", expected " + expectedNamespace, expectedNamespace, namespace);
        } catch (XMLParserException e) {
            fail("Unable to parse file containing single EntitiesDescriptor element");
        } catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown but example element does not contain any unknown attributes: " + e);
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown but example element does not contain any child elements");
        } catch (UnmarshallingException e) {
            fail("Unmarshalling failed with the following error:" + e);
        }
    }
    
    /**
     * Tests marshalling the contents of a single EntitiesDescriptor element to a DOM document.
     * @throws Exception 
     */
    public void testSingleElementMarshall() throws Exception {
        SAMLObjectBuilder objectBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(AdditionalMetadataLocation.QNAME);
        AdditionalMetadataLocation location = (AdditionalMetadataLocation) objectBuilder.buildObject();
        location.setLocationURI(expectedContent);
        location.setNamespaceURI(expectedNamespace);
        
        Marshaller marshaller = MarshallerFactory.getInstance().getMarshaller(location);
        try {
            Element generatedDOM = marshaller.marshall(location);
            assertXMLEqual("Marshalled DOM was not the same as the expected DOM", expectedDOM, generatedDOM.getOwnerDocument());
        } catch (MarshallingException e) {
            fail("Marshalling failed with the following error: " + e);
        }
    }
}
