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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A singleton thread-safe manager for pools of XML parsers.
 * 
 */
public class ParserPoolManager {
	/** Logger */
	private final static Logger LOG = Logger.getLogger(ParserPoolManager.class);
	
	private static ParserPoolManager instance;

	/** Directory, on the classpath, schemas are located in */
	private String schemaDir = "/schema/";

	/** Extension Schems to the base XML and SAML schemas */
	private TreeMap <String,EntityResolver> schemaExtensions;

	/** Non-validating parser pool */
	private ParserPool parserPool;

	// Note that SAML 1.0 and 1.1 schemas are mutually exclusive
	/** XML Schema for validating SAML 1.0 and 2.0 */
	private Schema saml10Schema;

	/** XML Schema for validatin SAML 1.1 and 2.0 */
	private Schema saml11Schema;

	/**
	 * Constructor
	 */
	private ParserPoolManager() {
		parserPool = new ParserPool();
	}
	
	/**
	 * Gets the instance of this manager.
	 * 
	 * @return the instance of this manager
	 */
	public static ParserPoolManager getInstance(){
		if(instance == null){
			instance = new ParserPoolManager();
		}
		
		return instance;
	}

	/**
	 * Parses an XML file, in the given Source. Validation is performed against
	 * the SAML schema, and registered extension Schema, that correspond to the
	 * given SAML version identifier. If the version identifier is null, no
	 * validation is performed.
	 * 
	 * @param in
	 *            the XML file source
	 * 
	 * @return the XML Document
     * 
	 * @throws XMLParserException
	 * 
	 * @throws XMLParserException
	 *             thrown if there is a problem reading or parsing the XML document
	 */
	public Document parse(InputSource in) throws XMLParserException {
		return parserPool.parse(in);
	}

	/**
	 * Validates an SAML document against the SAML 1.1, SAML 2.0, and any
	 * registered extension schemas. User {@link #validate10(Document)} to
	 * validate SAML 1.0 documents.
	 * 
	 * @param samlDocument
	 *            the SAML document to validate
	 * 
	 * @throws XMLParserException
	 *             thrown if the approrpriate SAML schema can not be constructed
	 *             or the document does not validate
	 */
	public void validate(Document samlDocument) throws XMLParserException {
		if (saml11Schema == null) {
			SchemaFactory factory = SchemaFactory
					.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			try {
				saml10Schema = createSchema(factory, getXMLCoreSchemaSources(),
						getSAML11SchemaSources(), getExtensionSchemaSources());
			} catch (SAXException e) {
				LOG
						.error(
								"Unable to construct SAML 1.1/2.0 Schema object, can not perform validation",
								e);
				throw new XMLParserException(
						"Unable to construct SAML 1.1/2.0 Schema object, can not perform validation",
						e);
			}
		}

		validateSAMLDocument(samlDocument, saml11Schema);
	}

	/**
	 * Validates an SAML document against the SAML 1.0, SAML 2.0, and any
	 * registered extension schemas. User {@link #validate(Document)} to
	 * validate SAML 1.1 documents.
	 * 
	 * @param samlDocument
	 *            the SAML document to validate
	 * 
	 * @throws XMLParserException
	 *             thrown if the approrpriate SAML schema can not be constructed
	 *             or the document does not validate
	 */
	public void validate10(Document samlDocument) throws XMLParserException {
		if (saml10Schema == null) {
			SchemaFactory factory = SchemaFactory
					.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			try {
				saml10Schema = createSchema(factory, getXMLCoreSchemaSources(),
						getSAML10SchemaSources(), getExtensionSchemaSources());
			} catch (SAXException e) {
				LOG
						.error("Unable to construct SAML 1.0/2.0 Schema object, can not perform validation");
				throw new XMLParserException(
						"Unable to construct SAML 1.0/2.0 Schema object, can not perform validation");
			}
		}

		validateSAMLDocument(samlDocument, saml10Schema);
	}

	/**
	 * Creates a new XML document.
	 * 
	 * @return the XML document
	 * 
	 * @throws XMLParserException
	 *             thrown if the system can not create XML document, this
	 *             usually occurs because JAXP is not properly installed
	 */
	public Document newDocument() throws XMLParserException {
		return parserPool.newDocument();
	}

	/**
	 * Registers new schemas that extend the base XML and SAML schemas. These
	 * schemas will be added to previously registered extension schemas.
	 * Extensions must be ordered such that, when the collection is iterated
	 * over, schema documents required by the current schema document have
	 * already been loaded.
	 * 
	 * <p>
	 * <strong>WARNING</strong> This method causes all the currently pooled
	 * DocumentBuilders with schemas to be destroyed as there is no way to
	 * change the schema associated with the Builder onces it's created. Because
	 * of this calling this method can have an adverse effect on performance as
	 * the pools regrow.
	 * </p>
	 * 
	 * @param exts
	 *            extensions schemas with a location URI as the key and the
	 *            EntityResolver responsible for resolving the URI as the value
	 */
	public synchronized void registerSchemas(
			TreeMap <String,EntityResolver> exts) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Registering new extension schemas");
		}
		// Merge the new extensions into the existing ones set.
		if (exts != null) {
			if (schemaExtensions == null) {
				schemaExtensions = new TreeMap<String,EntityResolver>();
			}

			schemaExtensions.putAll(exts);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Destroying existing SAML pools");
		}

		// Destroy current schemas, they'll be created next time they're needed.
		saml10Schema = null;
		saml11Schema = null;
	}

	/**
	 * Gets a list of {@link Source}s for core XML schemas.
	 * 
	 * @return a list of {@link Source}s for core XML schemas
	 */
	private ArrayList<SAXSource> getXMLCoreSchemaSources() {
		ArrayList<SAXSource> sources = new ArrayList<SAXSource>();

		SAXSource xmlSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.XML_SCHEMA_ID));
		xmlSource.setSystemId(XMLConstants.XML_SCHEMA_ID);
		sources.add(xmlSource);

		SAXSource dsigSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.XMLSIG_SCHEMA_ID));
		dsigSource.setSystemId(XMLConstants.XMLSIG_SCHEMA_ID);
		sources.add(dsigSource);

		SAXSource soap11EnvSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SOAP11ENV_SCHEMA_ID));
		soap11EnvSource.setSystemId(XMLConstants.SOAP11ENV_SCHEMA_ID);
		sources.add(dsigSource);

		return sources;
	}

	/**
	 * Gets a list of {@link Source}s for SAML 1.0 schemas sources.
	 * 
	 * @return a list of SAML 1.0 schemas sources
	 */
	private ArrayList<SAXSource> getSAML10SchemaSources() {
        ArrayList<SAXSource> sources = new ArrayList<SAXSource>();

		// Start out with core XML schemas
		sources.addAll(getXMLCoreSchemaSources());

		SAXSource saml10Source = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML10_SCHEMA_ID));
		saml10Source.setSystemId(XMLConstants.SAML10_SCHEMA_ID);
		sources.add(saml10Source);

		SAXSource saml10PSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML10P_SCHEMA_ID));
		saml10PSource.setSystemId(XMLConstants.SAML10P_SCHEMA_ID);
		sources.add(saml10PSource);

		// Add in SAML 2.0 schema
		sources.addAll(getSAML20SchemaSources());

		return sources;
	}

	/**
	 * Gets a list of {@link Source}s for SAML 1.1 schemas sources.
	 * 
	 * @return a list of SAML 1.1 schemas sources
	 */
	private ArrayList<SAXSource> getSAML11SchemaSources() {
        ArrayList<SAXSource> sources = new ArrayList<SAXSource>();

		SAXSource saml11Source = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML11_SCHEMA_ID));
		saml11Source.setSystemId(XMLConstants.SAML11_SCHEMA_ID);
		sources.add(saml11Source);

		SAXSource saml11PSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML11P_SCHEMA_ID));
		saml11PSource.setSystemId(XMLConstants.SAML11P_SCHEMA_ID);
		sources.add(saml11PSource);

		// Add in SAML 2.0 schema
		sources.addAll(getSAML20SchemaSources());

		return sources;
	}

	/**
	 * Gets a list of {@link Source}s for SAML 2.0 schemas sources.
	 * 
	 * @return a list of SAML 2.0 schemas sources
	 */
	private ArrayList<SAXSource> getSAML20SchemaSources() {
        ArrayList<SAXSource> sources = new ArrayList<SAXSource>();

		SAXSource saml20Source = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML20_SCHEMA_ID));
		saml20Source.setSystemId(XMLConstants.SAML20_SCHEMA_ID);
		sources.add(saml20Source);

		SAXSource saml20PSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML20P_SCHEMA_ID));
		saml20PSource.setSystemId(XMLConstants.SAML20P_SCHEMA_ID);
		sources.add(saml20PSource);

		SAXSource saml20MDSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML20MD_SCHEMA_ID));
		saml20MDSource.setSystemId(XMLConstants.SAML20MD_SCHEMA_ID);
		sources.add(saml20MDSource);

		SAXSource saml20ACSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML20AC_SCHEMA_ID));
		saml20ACSource.setSystemId(XMLConstants.SAML20AC_SCHEMA_ID);
		sources.add(saml20ACSource);

		SAXSource saml20ECPSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML20ECP_SCHEMA_ID));
		saml20ECPSource.setSystemId(XMLConstants.SAML20ECP_SCHEMA_ID);
		sources.add(saml20ECPSource);

		SAXSource saml20DCESource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML20DCE_SCHEMA_ID));
		saml20DCESource.setSystemId(XMLConstants.SAML20DCE_SCHEMA_ID);
		sources.add(saml20DCESource);

		SAXSource saml20X500Source = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML20X500_SCHEMA_ID));
		saml20X500Source.setSystemId(XMLConstants.SAML20X500_SCHEMA_ID);
		sources.add(saml20X500Source);

		SAXSource saml20XACMLSource = new SAXSource(new InputSource(schemaDir
				+ XMLConstants.SAML20XACML_SCHEMA_ID));
		saml20XACMLSource.setSystemId(XMLConstants.SAML20XACML_SCHEMA_ID);
		sources.add(saml20XACMLSource);

		return sources;
	}

	/**
	 * Converts a list of schema extension system Ids and thier resolvers into
	 * schema {@link Source}s.
	 * 
	 * @param extensions
	 *            the extensions
	 * 
	 * @return the list of schema sources
	 */
	private ArrayList<SAXSource> getExtensionSchemaSources() {		
		ArrayList<SAXSource> extSources = new ArrayList<SAXSource>();

		EntityResolver extEntityResolver = null;
		String extSystemId = null;
		SAXSource extSource = null;
		for (Iterator i = schemaExtensions.entrySet().iterator(); i.hasNext();) {
			Entry entry = (Entry) i.next();
			try {
				extSystemId = (String) entry.getKey();
				extEntityResolver = (EntityResolver) entry.getValue();
				if (extEntityResolver != null) {
					extSource = new SAXSource(extEntityResolver.resolveEntity(
							null, extSystemId));
					extSources.add(extSource);
				} else {
					throw new SAXException(
							"Entity resolver for extension schema ("
									+ extSystemId + ") is null");
				}
			} catch (SAXException e) {
				LOG.error("Unable to obtain extension schema (" + extSystemId
						+ "): " + e);
			} catch (IOException e) {
				LOG.error("Unable to obtain extension schema (" + extSystemId
						+ "): " + e);
			}
		}

		return extSources;
	}

	/**
	 * Creates an XML Schema object for a particular version of SAML with
	 * extensions.
	 * 
	 * @param factory
	 *            the schema factory
	 * @param xmlCoreSchemaSources
	 *            the core XML schema sources
	 * @param samlSchemaSources
	 *            the schema sources for the particular version of SAML
	 * @param extensionSchemaSources
	 *            the schema sources for the extensions
	 * 
	 * @return the XML Schema object
	 * 
	 * @throws SAXException
	 *             thrown if there is a problem creating the schema
	 */
	private Schema createSchema(SchemaFactory factory,
			List<SAXSource> xmlCoreSchemaSources, List<SAXSource> samlSchemaSources,
			List<SAXSource> extensionSchemaSources) throws SAXException {
		ArrayList<SAXSource> schemaSources = new ArrayList<SAXSource>();
		schemaSources.addAll(xmlCoreSchemaSources);
		schemaSources.addAll(samlSchemaSources);
		schemaSources.addAll(extensionSchemaSources);
		return factory.newSchema(schemaSources
				.toArray(new Source[0]));
	}
	

	/**
	 * Validates a SAML document against a given SAML schema.
	 * 
	 * @param samlDocument
	 *            the SAML document
	 * @param samlSchema
	 *            the SAML schema
	 * 
	 * @throws XMLParserException
	 *             thrown if the document is not valid
	 */
	private void validateSAMLDocument(Document samlDocument, Schema samlSchema)
			throws XMLParserException {
		Validator validator = samlSchema.newValidator();
		try {
			validator.validate(new DOMSource(samlDocument));
		} catch (SAXException e) {
			throw new XMLParserException("Invalid Document", e);
		} catch (IOException e) {
			throw new XMLParserException("Unable to read document", e);
		}
	}
}
