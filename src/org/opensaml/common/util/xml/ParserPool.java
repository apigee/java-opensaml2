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

package org.opensaml.common.util.xml;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * A thread safe pool of expensive to create XMLParsers.
 */
public class ParserPool {

	/** Logger */
	private static Logger LOG = Logger.getLogger(ParserPool.class);

	/** JAXP factory used to create DocumentBuilders */
	private DocumentBuilderFactory docBuilderFactory = null;

	/** FIFO stack for parsers */
	private Stack<SoftReference<DocumentBuilder>> parserPool;

	/**
	 * Constructor
	 */
	public ParserPool() {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Creating DocumentBuilderFactory");
		}
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setNamespaceAware(true);
		try {
			if (LOG.isDebugEnabled()) {
				LOG
						.debug("Setting Schema Value Normalization feature on DocumentBuilderFactory");
			}
			docBuilderFactory
					.setFeature(
							"http://apache.org/xml/features/validation/schema/normalized-value",
							false);
		} catch (ParserConfigurationException e) {
			LOG
					.warn("Unable to turn off data normalization in parser, supersignatures may fail with Xerces-J: "
							+ e);
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
	 * Registers a new validation schema to be used by this pool.
	 * <p>
	 * Because the schema can not be changed once a DocumentBuilder is created
	 * all the existing Builders must be destroyed.
	 * </p>
	 */
	public void registerSchema(Schema newValidationSchema) {
		if (LOG.isDebugEnabled()) {
			LOG
					.debug("Registering new validation schema with DocumentBuilderFactory, existing pooled parsers will be destroyed");
		}

		// Ensure no pool operatons occur as we clear it
		synchronized (parserPool) {
			parserPool.clear();
		}

	}

	/**
	 * Creates a new document using a parser from this pool.
	 * 
	 * @return new XML document
	 * 
	 * @throws XMLParserException
	 *             thrown if a DocumentBuilder can not be created.
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
	 * @param in
	 *            A stream containing the content to be parsed
	 * 
	 * @return The DOM document resulting from the parse
	 * 
	 * @exception XMLParserException
	 *                thrown if there was a problem reading, parsing, or
	 *                validating the XML
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
				// underneath us
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
	 * @param builder
	 *            the builder to return to this pool
	 */
	private void checkinBuilder(DocumentBuilder builder) {
		parserPool.push(new SoftReference<DocumentBuilder>(builder));
	}

	/**
	 * Creates a new {@link DocumentBuilder} that validates documents against
	 * the schema for the given SAML version and any registered extensions or
	 * without validation if no version is given.
	 * 
	 * @param samlVersion
	 *            the SAML version to validate against or null for no validation
	 * 
	 * @return the DocumentBuilder
	 * 
	 * @throws XMLParserException
	 *             thrown if the give SAML version is not recognized or if JAXP
	 *             is unable to create an XML parser
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
			LOG.error("Unable to obtain usable XML parser from environment");
			throw new XMLParserException(
					"Unable to obtain usable XML parser from environment", e);
		}
	}

	/**
	 * A simple error handler for SAX parsers.
	 * 
	 */
	private class SAXErrorHandler implements ErrorHandler {
		public SAXErrorHandler() {

		}

		/**
		 * Called by parser if a fatal error is detected, does nothing
		 * 
		 * @param exception
		 *            Describes the error
		 * @exception SAXException
		 *                Can be raised to indicate an explicit error
		 */
		public void fatalError(SAXParseException e) throws SAXException {
			throw e;
		}

		/**
		 * Called by parser if an error is detected, currently just throws e
		 * 
		 * @param e
		 *            Description of Parameter
		 * @exception SAXParseException
		 *                Can be raised to indicate an explicit error
		 */
		public void error(SAXParseException e) throws SAXParseException {
			throw e;
		}

		/**
		 * Called by parser if a warning is issued, currently logs the condition
		 * 
		 * @param e
		 *            Describes the warning
		 * @exception SAXParseException
		 *                Can be raised to indicate an explicit error
		 */
		public void warning(SAXParseException e){
			LOG.warn("Parser warning: line = " + e.getLineNumber()
					+ " : uri = " + e.getSystemId());
			LOG.warn("Parser warning (root cause): " + e.getMessage());
		}
	}

	/**
	 * An entity resolver that returns a dummy source to shortcut resolution
	 * attempts.
	 * 
	 * During parsing, this should not be called with a systemId corresponding
	 * to any known externally resolvable entity. It prevents "accidental"
	 * resolution of external entities via URI resolution. Network based
	 * retrieval of resources is NOT allowable and should really be something
	 * the parser can block globally. We also can't return null, because that
	 * signals URI resolution.
	 */
	private class NoOpEntityResolver implements EntityResolver {
		public NoOpEntityResolver() {

		}

		public InputSource resolveEntity(String publicId, String systemId){
			return new InputSource();
			// Hopefully this will fail the parser
			// and not be treated as null.
		}
	}
}
