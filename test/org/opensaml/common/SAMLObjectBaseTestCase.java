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

import java.util.HashMap;

import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Base test case for all OpenSAML tests that work with {@link org.opensaml.common.SAMLObject}s.
 */
public abstract class SAMLObjectBaseTestCase extends  SAMLObjectTestCaseConfigInitializer {

    /** Location of file containing a single element with NO optional attributes */
    protected String singleElementFile;

    /** Location of file containing a single element with all optional attributes */
    protected String singleElementOptionalAttributesFile;

    /** Location of file containing a single element with child elements */
    protected String childElementsFile;

    /** The expected result of a marshalled single element with no optional attributes */
    protected Document expectedDOM;

    /** The expected result of a marshalled single element with all optional attributes */
    protected Document expectedOptionalAttributesDOM;

    /** The expected result of a marshalled single element with child elements */
    protected Document expectedChildElementsDOM;

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

        if (childElementsFile != null) {
            expectedChildElementsDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                    .getResourceAsStream(childElementsFile)));
        }
    }

    /*
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Unmarshalls an element file into its SAMLObject.
     * 
     * @return the SAMLObject from the file
     */
    protected XMLObject unmarshallElement(String elementFile) {
        try {
            ParserPoolManager ppMgr = ParserPoolManager.getInstance();
            Document doc = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class.getResourceAsStream(elementFile)));
            Element samlElement = doc.getDocumentElement();

            Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
            if (unmarshaller == null) {
                fail("Unable to retrieve unmarshaller by DOM Element");
            }

            return unmarshaller.unmarshall(samlElement, new HashMap<String, Object>());
        } catch (XMLParserException e) {
            fail("Unable to parse element file " + elementFile);
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
    public void testSingleElementOptionalAttributesUnmarshall() {
        assertNull("No testSingleElementOptionalAttributesUnmarshall present", singleElementOptionalAttributesFile);
    }

    /**
     * Tests unmarshalling a document that contains a single element with children.
     */
    public void testChildElementsUnmarshall() {
        assertNull("No testSingleElementChildElementsUnmarshall present", childElementsFile);
    }

    /**
     * Tests marshalling the contents of a single element, with no optional attributes, to a DOM document.
     */
    public abstract void testSingleElementMarshall();

    /**
     * Tests marshalling the contents of a single element, with all optional attributes, to a DOM document.
     */
    public void testSingleElementOptionalAttributesMarshall() {
        assertNull("No testSingleElementOptionalAttributesMarshall", expectedOptionalAttributesDOM);
    }

    /**
     * Tests marshalling the contents of a single element with child elements to a DOM document.
     */
    public void testChildElementsMarshall() {
        assertNull("No testSingleElementChildElementsMarshall", expectedChildElementsDOM);
    }

}