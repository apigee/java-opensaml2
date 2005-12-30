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

package org.opensaml.xml.parse;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A thread safe pool of {@link javax.xml.parsers.DocumentBuilder}s.
 */
public class ParserPool {

    /** logger */
    private static Logger log = Logger.getLogger(ParserPool.class);

    /** JAXP factory used to create DocumentBuilders */
    private DocumentBuilderFactory docBuilderFactory = null;

    /** FIFO stack for parsers */
    private Stack<SoftReference<DocumentBuilder>> parserPool;

    /**
     * Constructor
     */
    public ParserPool(boolean namespaceAware, Schema schema, Map<String, Boolean> features) {

        if (log.isDebugEnabled()) {
            log.debug("Creating DocumentBuilderFactory");
        }
        docBuilderFactory = DocumentBuilderFactory.newInstance();

        if (log.isDebugEnabled()) {
            log.debug("Setting document builder factory namespace aware setting to " + namespaceAware);
        }
        docBuilderFactory.setNamespaceAware(namespaceAware);

        if (schema != null) {
            if (log.isDebugEnabled()) {
                log.debug("Setting document builder factory validating setting to true");
            }
            docBuilderFactory.setValidating(true);
            docBuilderFactory.setSchema(schema);
        }

        if (features != null && features.size() > 0) {
            try {
                String featureId;
                boolean isOn;
                for (Entry<String, Boolean> feature : features.entrySet()) {
                    featureId = feature.getKey();
                    isOn = feature.getValue().booleanValue();
                    if (log.isDebugEnabled()) {
                        log.debug("Setting document builder factory feature " + featureId + " to " + isOn);
                    }
                    docBuilderFactory.setFeature(featureId, isOn);
                }
            } catch (ParserConfigurationException e) {
                log.error("Document builder configuration error" + e);
            }
        }

        parserPool = new Stack<SoftReference<DocumentBuilder>>();
    }

    /**
     * Gets the number of parsers currently pooled.
     * 
     * @return the number of parsers currently pooled
     */
    public int size() {
        return parserPool.size();
    }

    /**
     * Creates a new document using a parser from this pool.
     * 
     * @return new XML document
     * 
     * @throws XMLParserException thrown if a DocumentBuilder can not be created.
     */
    public Document newDocument() throws XMLParserException {
        DocumentBuilder docBuilder = checkoutBuilder();
        Document newDoc = docBuilder.newDocument();
        checkinBuilder(docBuilder);
        return newDoc;
    }

    /**
     * Parses a document using a pooled parser with the proper settings
     * 
     * @param in A stream containing the content to be parsed
     * 
     * @return The DOM document resulting from the parse
     * 
     * @exception XMLParserException thrown if there was a problem reading, parsing, or validating the XML
     */
    public Document parse(InputSource in) throws XMLParserException {
        DocumentBuilder documentBuilder = checkoutBuilder();
        try {
            Document doc = documentBuilder.parse(in);
            return doc;
        } catch (SAXException e) {
            throw new XMLParserException("Unable to parse XML", e);
        } catch (IOException e) {
            throw new XMLParserException("Unable to read XML source", e);
        } finally {
            checkinBuilder(documentBuilder);
        }
    }

    /**
     * Gets a DocumentBuilder from the pool.
     * 
     * @return
     * 
     * @throws XMLParserException
     */
    private DocumentBuilder checkoutBuilder() throws XMLParserException {
        DocumentBuilder builder = null;

        synchronized (parserPool) {
            if (parserPool.isEmpty()) {
                builder = createDocumentBuilder();
            } else {
                SoftReference builderRef = parserPool.pop();
                builder = (DocumentBuilder) builderRef.get();
                // Check to see if this reference has been garbage collected
                if (builder == null) {
                    builder = createDocumentBuilder();
                }
            }
        }

        return builder;
    }

    /**
     * Returns the Builder to the pool.
     * 
     * @param builder the builder to return to this pool
     */
    private void checkinBuilder(DocumentBuilder builder) {
        parserPool.push(new SoftReference<DocumentBuilder>(builder));
    }

    /**
     * Creates a new {@link DocumentBuilder} that validates documents against the schema for the given SAML version and
     * any registered extensions or without validation if no version is given.
     * 
     * @param samlVersion the SAML version to validate against or null for no validation
     * 
     * @return the DocumentBuilder
     * 
     * @throws XMLParserException thrown if the give SAML version is not recognized or if JAXP is unable to create an
     *             XML parser
     */
    private DocumentBuilder createDocumentBuilder() throws XMLParserException {
        DocumentBuilder docBuilder;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();

            // Register our simple, logging, error handler
            docBuilder.setErrorHandler(new SAXErrorHandler());

            // Short-circut URI resolution
            docBuilder.setEntityResolver(new NoOpEntityResolver());
            return docBuilder;

        } catch (ParserConfigurationException e) {
            log.error("Unable to obtain usable XML parser from environment");
            throw new XMLParserException("Unable to obtain usable XML parser from environment", e);
        }
    }
}
