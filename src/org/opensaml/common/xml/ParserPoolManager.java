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

package org.opensaml.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import javolution.util.FastList;

import org.apache.log4j.Logger;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A singleton thread-safe manager for pools of XML parsers.
 */
public class ParserPoolManager {
    /** Logger */
    private final static Logger LOG = Logger.getLogger(ParserPoolManager.class);

    /** Instance of this pool manager */
    private static ParserPoolManager instance = new ParserPoolManager();

    /** Extension Schems to the base XML and SAML schemas */
    private TreeMap<String, EntityResolver> schemaExtensions;

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
        HashMap<String, Boolean> features = new HashMap<String, Boolean>();
        features.put("http://apache.org/xml/features/validation/schema/normalized-value", Boolean.FALSE);
        features.put("http://apache.org/xml/features/dom/defer-node-expansion", Boolean.FALSE);

        parserPool = new ParserPool(true, null, features);
        schemaExtensions = new TreeMap<String, EntityResolver>();
    }

    /**
     * Gets the instance of this manager.
     * 
     * @return the instance of this manager
     */
    public static ParserPoolManager getInstance() {
        return instance;
    }

    /**
     * Parses an XML file, in the given Source. Validation is performed against the SAML schema, and registered
     * extension Schema, that correspond to the given SAML version identifier. If the version identifier is null, no
     * validation is performed.
     * 
     * @param in the XML file source
     * 
     * @return the XML Document
     * 
     * @throws XMLParserException thrown if there is a problem reading or parsing the XML document
     */
    public Document parse(InputSource in) throws XMLParserException {
        return parserPool.parse(in);
    }
    
    /**
     * Parses an XML file, in the given Source. Validation is performed against the SAML schema, and registered
     * extension Schema, that correspond to the given SAML version identifier. If the version identifier is null, no
     * validation is performed.
     * 
     * @param in the XML file source
     * 
     * @return the XML Document
     * 
     * @throws XMLParserException thrown if there is a problem reading or parsing the XML document
     */
    public Document parse(InputStream in) throws XMLParserException{
        return parse(new InputSource(in));
    }

    /**
     * Validates an SAML document against the SAML 1.1, SAML 2.0, and any registered extension schemas. User
     * {@link #validate10(Document)} to validate SAML 1.0 documents.
     * 
     * @param samlDocument the SAML document to validate
     * 
     * @throws XMLParserException thrown if the approrpriate SAML schema can not be constructed or the document does not
     *             validate
     */
    public void validate(Document samlDocument) throws XMLParserException {
        if (saml11Schema == null) {
            SchemaFactory factory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);

            try {
                saml11Schema = createSchema(factory, getSAML11SchemaSources());
            } catch (SAXException e) {
                LOG.error("Unable to construct SAML 1.1/2.0 Schema object, can not perform validation", e);
                throw new XMLParserException(
                        "Unable to construct SAML 1.1/2.0 Schema object, can not perform validation", e);
            }
        }

        validateDocument(samlDocument, saml11Schema);
    }

    /**
     * Validates an SAML document against the SAML 1.0, SAML 2.0, and any registered extension schemas. User
     * {@link #validate(Document)} to validate SAML 1.1 documents.
     * 
     * @param samlDocument the SAML document to validate
     * 
     * @throws XMLParserException thrown if the approrpriate SAML schema can not be constructed or the document does not
     *             validate
     */
    public void validate10(Document samlDocument) throws XMLParserException {
        if (saml10Schema == null) {
            SchemaFactory factory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                saml10Schema = createSchema(factory, getSAML10SchemaSources());
            } catch (SAXException e) {
                LOG.error("Unable to construct SAML 1.0/2.0 Schema object, can not perform validation");
                throw new XMLParserException(
                        "Unable to construct SAML 1.0/2.0 Schema object, can not perform validation");
            }
        }

        validateDocument(samlDocument, saml10Schema);
    }

    /**
     * Creates a new XML document.
     * 
     * @return the XML document
     * 
     * @throws XMLParserException thrown if the system can not create XML document, this usually occurs because JAXP is
     *             not properly installed
     */
    public Document newDocument() throws XMLParserException {
        return parserPool.newDocument();
    }

    /**
     * Registers new schemas that extend the base XML and SAML schemas. These schemas will be added to previously
     * registered extension schemas. Extensions must be ordered such that, when the collection is iterated over, schema
     * documents required by the current schema document have already been loaded.
     * 
     * @param exts extensions schemas with a location URI as the key and the EntityResolver responsible for resolving
     *            the URI as the value
     */
    public synchronized void registerSchemas(TreeMap<String, EntityResolver> exts) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Registering new extension schemas");
        }
        // Merge the new extensions into the existing ones set.
        schemaExtensions.putAll(exts);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Destroying existing SAML pools");
        }

        // Destroy current schemas, they'll be created next time they're needed.
        saml10Schema = null;
        saml11Schema = null;
    }

    /**
     * Gets a list of {@link Source}s for SAML 1.0 schemas sources.
     * 
     * @return a list of SAML 1.0 schemas sources
     */
    private FastList<SAXSource> getSAML10SchemaSources() {
        FastList<SAXSource> sources = new FastList<SAXSource>();

        SAXSource saml10Source = getSchemaSource(SAMLConstants.SAML10_SCHEMA_LOCATION);
        sources.add(saml10Source);

        SAXSource saml10PSource = getSchemaSource(SAMLConstants.SAML10P_SCHEMA_LOCATION);
        sources.add(saml10PSource);

        return sources;
    }

    /**
     * Gets a list of {@link Source}s for SAML 1.1 schemas sources.
     * 
     * @return a list of SAML 1.1 schemas sources
     */
    private FastList<SAXSource> getSAML11SchemaSources() {
        FastList<SAXSource> sources = new FastList<SAXSource>();

        SAXSource saml11Source = getSchemaSource(SAMLConstants.SAML11_SCHEMA_LOCATION);
        sources.add(saml11Source);

        SAXSource saml11PSource = getSchemaSource(SAMLConstants.SAML11P_SCHEMA_LOCATION);
        sources.add(saml11PSource);

        return sources;
    }

    /**
     * Gets a list of {@link Source}s for core XML schemas.
     * 
     * @return a list of {@link Source}s for core XML schemas
     */
    private FastList<SAXSource> getXMLCoreSchemaSources() {
        FastList<SAXSource> sources = new FastList<SAXSource>();

        SAXSource xmlSource = getSchemaSource(SAMLConstants.XML_SCHEMA_LOCATION);
        sources.add(xmlSource);

        SAXSource dsigSource = getSchemaSource(SAMLConstants.XMLSIG_SCHEMA_LOCATION);
        sources.add(dsigSource);

        SAXSource xencSource = getSchemaSource(SAMLConstants.XMLENC_SCHEMA_LOCATION);
        sources.add(xencSource);

        return sources;
    }

    /**
     * Gets a list of {@link Source}s for SAML 2.0 schemas sources.
     * 
     * @return a list of SAML 2.0 schemas sources
     */
    private FastList<SAXSource> getSAML20SchemaSources() {
        FastList<SAXSource> sources = new FastList<SAXSource>();

        SAXSource saml20Source = getSchemaSource(SAMLConstants.SAML20_SCHEMA_LOCATION);
        sources.add(saml20Source);

        SAXSource saml20PSource = getSchemaSource(SAMLConstants.SAML20P_SCHEMA_LOCATION);
        sources.add(saml20PSource);

        SAXSource saml20MDSource = getSchemaSource(SAMLConstants.SAML20MD_SCHEMA_LOCATION);
        sources.add(saml20MDSource);

        SAXSource saml20ACSource = getSchemaSource(SAMLConstants.SAML20AC_SCHEMA_LOCATION);
        sources.add(saml20ACSource);

        SAXSource saml20ECPSource = getSchemaSource(SAMLConstants.SAML20ECP_SCHEMA_LOCATION);
        sources.add(saml20ECPSource);

        SAXSource saml20DCESource = getSchemaSource(SAMLConstants.SAML20DCE_SCHEMA_LOCATION);
        sources.add(saml20DCESource);

        SAXSource saml20X500Source = getSchemaSource(SAMLConstants.SAML20X500_SCHEMA_LOCATION);
        sources.add(saml20X500Source);

        SAXSource saml20XACMLSource = getSchemaSource(SAMLConstants.SAML20XACML_SCHEMA_LOCATION);
        sources.add(saml20XACMLSource);

        return sources;
    }

    /**
     * Converts a list of schema extension system Ids and thier resolvers into schema {@link Source}s.
     * 
     * @param extensions the extensions
     * 
     * @return the list of schema sources
     */
    private FastList<SAXSource> getExtensionSchemaSources() {
        FastList<SAXSource> extSources = new FastList<SAXSource>();

        EntityResolver extEntityResolver = null;
        String extSystemId = null;
        SAXSource extSource = null;

        for (Entry entry : schemaExtensions.entrySet()) {
            try {
                extSystemId = (String) entry.getKey();
                extEntityResolver = (EntityResolver) entry.getValue();
                if (extEntityResolver != null) {
                    extSource = getSchemaSource(extSystemId);
                    extSource.setSystemId(extSystemId);
                    extSources.add(extSource);
                } else {
                    throw new SAXException("Entity resolver for extension schema (" + extSystemId + ") is null");
                }
            } catch (SAXException e) {
                LOG.error("Unable to obtain extension schema (" + extSystemId + "): " + e);
            }
        }

        return extSources;
    }

    /**
     * Creates a schema input source from a file located on the classpath.
     * 
     * @param fileLocation the location of the schema file
     * 
     * @return the schema input source
     */
    private SAXSource getSchemaSource(String fileLocation) {
        SAXSource source = new SAXSource(new InputSource(ParserPoolManager.class.getResourceAsStream(fileLocation)));
        source.setSystemId(fileLocation);
        return source;
    }

    /**
     * Creates an XML Schema object for a particular version of SAML with extensions.
     * 
     * @param factory the schema factory
     * @param xmlCoreSchemaSources the core XML schema sources
     * @param samlSchemaSources the schema sources for the particular version of SAML
     * @param extensionSchemaSources the schema sources for the extensions
     * 
     * @return the XML Schema object
     * 
     * @throws SAXException thrown if there is a problem creating the schema
     */
    private Schema createSchema(SchemaFactory factory, List<SAXSource> samlSchemaSources) throws SAXException {
        FastList<SAXSource> schemaSources = new FastList<SAXSource>();
        schemaSources.addAll(getXMLCoreSchemaSources());
        schemaSources.addAll(samlSchemaSources);
        schemaSources.addAll(getSAML20SchemaSources());
        schemaSources.addAll(getExtensionSchemaSources());
        return factory.newSchema(schemaSources.toArray(new Source[0]));
    }

    /**
     * Validates a document against the given schema.
     * 
     * @param document the document to validate
     * @param samlSchema the schema to validate against
     * 
     * @throws XMLParserException thrown if the document is not valid
     */
    private void validateDocument(Document document, Schema schema) throws XMLParserException {
        Validator validator = schema.newValidator();
        try {
            validator.validate(new DOMSource(document));
        } catch (SAXException e) {
            throw new XMLParserException("Invalid Document", e);
        } catch (IOException e) {
            throw new XMLParserException("Unable to read document", e);
        }
    }
}
