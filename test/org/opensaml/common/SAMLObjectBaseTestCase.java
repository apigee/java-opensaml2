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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Base test case for all OpenSAML tests that work with {@link org.opensaml.common.SAMLObject}s.
 */
public abstract class SAMLObjectBaseTestCase extends BaseTestCase {

    /** Location of file containing a single element with NO optional attributes */
    protected String singleElementFile;

    /** Location of file containing a single element with all optional attributes */
    protected String singleElementOptionalAttributesFile;

    /** The expected result of a marshalled single element with no optional attributes */
    protected Document expectedDOM;

    /** The expected result of a marshalled single element with all optional attributes */
    protected Document expectedOptionalAttributesDOM;

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        if (singleElementFile != null) {
            expectedDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                    .getResourceAsStream(singleElementFile)));
        }

        if (singleElementOptionalAttributesFile != null) {
            expectedOptionalAttributesDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                    .getResourceAsStream(singleElementOptionalAttributesFile)));
        }
    }

    /*
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Asserts a given SAMLObject is equal to an expected DOM. The SAMLObject is marshalled and the resulting DOM object
     * is compared against the expected DOM object for equality.
     * 
     * @param expectedDOM the expected DOM
     * @param samlObject the SAMLObject to be marshalled and compared against the expected DOM
     */
    public void assertEquals(Document expectedDOM, SAMLObject samlObject) {
        assertEquals("Marshalled DOM was not the same as the expected DOM", expectedDOM, samlObject);
    }

    /**
     * Asserts a given SAMLObject is equal to an expected DOM. The SAMLObject is marshalled and the resulting DOM object
     * is compared against the expected DOM object for equality.
     * 
     * @param failMessage the message to display if the DOMs are not equal
     * @param expectedDOM the expected DOM
     * @param samlObject the SAMLObject to be marshalled and compared against the expected DOM
     */
    public void assertEquals(String failMessage, Document expectedDOM, SAMLObject samlObject) {
        Marshaller marshaller = MarshallerFactory.getInstance().getMarshaller(samlObject);
        try {
            Element generatedDOM = marshaller.marshall(samlObject);
            assertXMLEqual(failMessage, expectedDOM, generatedDOM.getOwnerDocument());
        } catch (MarshallingException e) {
            fail("Marshalling failed with the following error: " + e);
        }
    }

    /**
     * Builds an empty object for a given QName
     * 
     * @param objectQName the objects QName
     * 
     * @return the empty SAMLObject
     */
    public SAMLObject buildSAMLObject(QName objectQName) {
        SAMLObjectBuilder objectBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(objectQName);
        if (objectBuilder == null) {
            fail("No object build available for object with QName of " + objectQName);
        }
        return objectBuilder.buildObject();
    }

    /**
     * Unmarshalls an element file into its SAMLObject.
     * 
     * @return the SAMLObject from the file
     */
    protected SAMLObject unmarshallElement(String elementFile) {
        try {
            ParserPoolManager ppMgr = ParserPoolManager.getInstance();
            Document doc = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class.getResourceAsStream(elementFile)));
            Element samlElement = doc.getDocumentElement();

            Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(samlElement);
            if (unmarshaller == null) {
                fail("Unable to retrieve unmarshaller by DOM Element");
            }

            return unmarshaller.unmarshall(samlElement);
        } catch (XMLParserException e) {
            fail("Unable to parse element file " + elementFile);
        } catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown when parsing element file " + elementFile + ": " + e);
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown when parsing element file " + elementFile + ": " + e);
        } catch (UnmarshallingException e) {
            fail("Unmarshalling failed when parsing element file " + elementFile + ": " + e);
        }

        return null;
    }

    /**
     * Tests unmarshalling a document that contains a single element (no children) with no optional attributes.
     */
    public abstract void testSingleElementUnmarshall();

    /**
     * Tests unmarshalling a document that contains a single element (no children) with all that element's optional
     * attributes.
     */
    public abstract void testSingleElementOptionalAttributesUnmarshall();

    /**
     * Tests marshalling the contents of a single element, with no optional attributes, to a DOM document.
     */
    public abstract void testSingleElementMarshall();

    /**
     * Tests marshalling the contents of a single element, with all optional attributes, to a DOM document.
     */
    public abstract void testSingleElementOptionalAttributesMarshall();
}