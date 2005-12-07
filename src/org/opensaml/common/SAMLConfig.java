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

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallerFactory;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallerFactory;
import org.opensaml.common.util.StringHelper;
import org.opensaml.common.util.xml.NoOpEntityResolver;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.common.util.xml.XMLHelper;
import org.opensaml.common.util.xml.XMLParserException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Configuration class that initializes the library and provides access to the configuration.
 */
public class SAMLConfig {
    
    /** Logger */
    private final static Logger log = Logger.getLogger(SAMLConfig.class);
    
    /** Default configuration file location */
    private final static String DEFAULT_CONFIGURATION = "/conf/opensaml2-config.xml";
    
    /** Whether the library has been intialized or not */
    private static boolean initialized = false;
    
    /** Whether to ignore unknown attributes when they are encountered */
    private static boolean ignoreUnknownAttributes = true;
    
    /** Whether to ignore unknown elements when they are ecnountered */
    private static boolean ignoreUnknownElements = true;
    
    /** Object provider configuration elements indexed by QName */
    private static HashMap<QName, Element> configuredObjectProviders = new HashMap<QName, Element>();
    
    /**
     * Intializes the OpenSAML library.  This consists, many, of reading in the configuration file
     * and setting up everything stipulated in that file.
     * 
     * @throws InitializationException thrown if there is a problem intializing the library, if the 
     * library has already been initialized, or the configuration file can not be read
     */
    public synchronized static void initializeOpenSAML() throws InitializationException{
        if(initialized){
            log.error("Attempt to initialize library after it has already been initialized.");
            throw new InitializationException("Library has already been initialized");
        }
        
        if(log.isInfoEnabled()){
            log.info("Intializing OpenSAML library with default configuration file " + DEFAULT_CONFIGURATION);
        }
        InputStream configInputStream = SAMLConfig.class.getResourceAsStream(DEFAULT_CONFIGURATION);
        ParserPoolManager xmlParserPool = ParserPoolManager.getInstance();
        
        try{
            Document configuration = xmlParserPool.parse(new InputSource(configInputStream));
            initializeOpenSAML(configuration);
        }catch(XMLParserException xpe){
            log.fatal("Unable to parse OpenSAML configuration file", xpe);
            throw new InitializationException("Unable to parse OpenSAML configuration file:", xpe);
        }
    }
    
    /**
     * Intializes the OpenSAML library.  This consists, many, of reading in the configuration file
     * and setting up everything stipulated in that file.
     * 
     * @param configuration root element of the OpenSAML configuration document to initialize the library with
     * 
     * @throws InitializationException thrown if there is a problem intializing the library, or if the 
     * library has already been initialized
     */
    public synchronized static void initializeOpenSAML(Document configuration) throws InitializationException{
        if(initialized){
            log.error("Attempt to initialize library after it has already been initialized.");
            throw new InitializationException("Library has already been initialized");
        }
        
        if(log.isInfoEnabled()){
            log.info("Intializing OpenSAML library with from cofiguration document");
        }
        
        try{
            validateConfigurationDocument(configuration);
            
            Element rootElement = configuration.getDocumentElement();
            
            if(log.isDebugEnabled()){
                log.debug("Configuring Object providers");
            }
            NodeList objectProviders = rootElement.getElementsByTagNameNS(XMLConstants.OPENSAML_CONFIG_NS, "ObjectProviders");
            initializeObjectProviders((Element) objectProviders.item(0));
            if(log.isDebugEnabled()){
                log.debug("Completed configuring all object providers");
            }
            
            
            initialized = true;
        }catch(XMLParserException xpe){
            log.fatal("Configuration document validation failed", xpe);
            throw new InitializationException("Configuration document validation failed", xpe);
        }
    }
    
    /**
     * Checks if the OpenSAML library has been sucessfully initialized.
     * 
     * @return true if the library has been initialized, false if not
     */
    public static boolean isInitialized(){
        return initialized;
    }
    
    /**
     * Checks if, during the unmarshalling process, attributes that are not expected by the unmarshaller should be ignored.
     * If they are not ignored a 
     * 
     * @return true if unexpected attributes should be ignored, false if not
     */
    public static boolean ignoreUnknownAttributes(){
        return ignoreUnknownAttributes;
    }
    
    public static boolean ignoreUnknownElements(){
        return ignoreUnknownElements;
    }
    
    /**
     * Gets a clone of the configuration element for a qualified element.  Note that this configuration 
     * reflects that state of things as they were when the library was initialized, applications may have 
     * programmatically removed builder, marshallers, and unmarshallers during runtime.
     * 
     * @param qualifedName the namespace qualifed element name of the schema type of the object provider
     *  
     * @return the object provider configuration element or null if no object provider is configured with that name
     */
    public static Element getObjectProviderConfiguration(QName qualifedName){
        return (Element) configuredObjectProviders.get(qualifedName).cloneNode(true);
    }
    
    /**
     * Intializes the object providers defined in the configuration file.
     * 
     * @param objectProviders
     * @throws InitializationException
     */
    private static void initializeObjectProviders(Element objectProviders) throws InitializationException{
        
        String ignoreAttributesAttr = objectProviders.getAttributeNS(null, "ignoreUnknownAttributes");
        if(!StringHelper.isEmpty(ignoreAttributesAttr)){
            ignoreUnknownAttributes = Boolean.parseBoolean(ignoreAttributesAttr);
        }
        
        String ignoreElementsAttr = objectProviders.getAttributeNS(null, "ignoreUnknownElements");
        if(!StringHelper.isEmpty(ignoreElementsAttr)){
            ignoreUnknownElements = Boolean.parseBoolean(ignoreElementsAttr);
        }
        
        // Process ObjectProvider child elements
        Element objectProvider;
        Attr qNameAttrib;
        QName objectProviderName;
        Element builderConfiguration;
        Element marshallerConfiguration;
        Element unmarshallerConfiguration;
        
        NodeList providerList = objectProviders.getElementsByTagNameNS(XMLConstants.OPENSAML_CONFIG_NS, "ObjectProvider");
        for(int i = 0; i < providerList.getLength(); i++){
            objectProvider = (Element) providerList.item(i);
            
            // Get the element name of type this object provider is for
            qNameAttrib = objectProvider.getAttributeNodeNS(null, "qualifiedName");
            objectProviderName = XMLHelper.getAttributeValueAsQName(qNameAttrib);
            
            if(log.isDebugEnabled()){
                log.debug("Initializing object provider " + objectProviderName);
            }
            
            try{
                builderConfiguration = (Element) objectProvider.getElementsByTagNameNS(XMLConstants.OPENSAML_CONFIG_NS, "BuilderClass").item(0);
                initalizeObjectProviderBuilderClass(objectProviderName, builderConfiguration);
                
                marshallerConfiguration = (Element) objectProvider.getElementsByTagNameNS(XMLConstants.OPENSAML_CONFIG_NS, "MarshallingClass").item(0);
                initalizeObjectProviderMarshallerClass(objectProviderName, marshallerConfiguration);
                
                unmarshallerConfiguration = (Element) objectProvider.getElementsByTagNameNS(XMLConstants.OPENSAML_CONFIG_NS, "UnmarshallingClass").item(0);
                initalizeObjectProviderUnmarshallerClass(objectProviderName, unmarshallerConfiguration);
                
                configuredObjectProviders.put(objectProviderName, objectProvider);
                
                if(log.isDebugEnabled()){
                    log.debug(objectProviderName + " intialized and configuration cached");
                }
            }catch(InitializationException e){
                log.fatal("Error initializing object provier " + objectProvider, e);
                //clean up any parts of the object provider that might have been registered before the failure
                deregisterObjectProvider(objectProviderName);
                throw e;
            }
        }
    }
    
    /**
     * Intializes the builder class for the given object provider.
     * 
     * @param objectProviderName the name of the object provider
     * @param builderConfiguration the BuilderClass configuration element
     * 
     * @throws InitializationException thrown if the given builder can not be instantiated
     */
    private static void initalizeObjectProviderBuilderClass(QName objectProviderName, Element builderConfiguration) throws InitializationException{
        String builderClassName = builderConfiguration.getAttributeNS(null, "className");
        if(log.isDebugEnabled()){
            log.debug("Initializing builder " + builderClassName + " for object provider" + objectProviderName);
        }

        try {
            SAMLObjectBuilder objectBuilder = (SAMLObjectBuilder) createClassInstance(builderClassName);
            SAMLObjectBuilderFactory.getInstance().registerBuilder(objectProviderName, objectBuilder);
        } catch (InstantiationException e) {
            log.fatal("Unable to create instance of builder class " + builderClassName + " for object provider " + objectProviderName);
            throw new InitializationException("Unable to create instance of builder class " + builderClassName + " for object provider " + objectProviderName, e);
        }
    }
    
    /**
     * Intializes the marshaller class for the given object provider.
     * 
     * @param objectProviderName the name of the object provider
     * @param marshallerConfiguration the Marshaller configuration element
     * 
     * @throws InitializationException thrown if the given marshaller can not be instantiated
     */
    private static void initalizeObjectProviderMarshallerClass(QName objectProviderName, Element marshallerConfiguration) throws InitializationException{
        String marshallerClassName = marshallerConfiguration.getAttributeNS(null, "className");
        if(log.isDebugEnabled()){
            log.debug("Initializing marshaller " + marshallerClassName + " for Object " + objectProviderName);
        }
        
        try{
            Marshaller objectMarshaller = (Marshaller) createClassInstance(marshallerClassName);
            MarshallerFactory.getInstance().registerMarshaller(objectProviderName, objectMarshaller);
        } catch (InstantiationException e) {
            log.fatal("Unable to create instance of marshaller class " + marshallerClassName + " for object provider " + objectProviderName);
            throw new InitializationException("Unable to create instance of unmarshaller class " + marshallerClassName + " for object provider " + objectProviderName, e);
        }
    }
    
    /**
     * Intializes the unmarshaller class for the given object provider.
     * 
     * @param objectProviderName the name of the object provider
     * @param marshallerClass the Marshaller configuration element
     * 
     * @throws InitializationException thrown if the given marshaller can not be instantiated
     */
    private static void initalizeObjectProviderUnmarshallerClass(QName objectProviderName, Element unmarshallerConfiguration) throws InitializationException{
        String unmarshallerClassName = unmarshallerConfiguration.getAttributeNS(null, "className");
        if(log.isDebugEnabled()){
            log.debug("Initializing unmarshaller " + unmarshallerClassName + " for Object " + objectProviderName);
        }
        
        try{
            Unmarshaller objectUnmarshaller = (Unmarshaller) createClassInstance(unmarshallerClassName);
            UnmarshallerFactory.getInstance().registerUnmarshaller(objectProviderName, objectUnmarshaller);
        } catch (InstantiationException e) {
            log.fatal("Unable to create instance of unmarshaller class " + unmarshallerClassName + " for object provider " + objectProviderName);
            throw new InitializationException("Unable to create instance of unmarshaller class " + unmarshallerClassName + " for object provider " + objectProviderName, e);
        }
    }
    
    /**
     * Removes the all the information about an object provider from the system.  This deregisters the 
     * builder, marshaller, and unmarshaller from their respective factories and removes the cached 
     * configuration.
     * 
     * @param objectProviderName the name of the object provider to remove from the system
     */
    private static void deregisterObjectProvider(QName objectProviderName){
        SAMLObjectBuilderFactory.getInstance().deregisterBuilder(objectProviderName);
        MarshallerFactory.getInstance().deregisterMarshaller(objectProviderName);
        UnmarshallerFactory.getInstance().deregisterUnmarshaller(objectProviderName);
        configuredObjectProviders.remove(objectProviderName);
    }
    
    /**
     * Validates a configuration document against the OpenSAML configuration file schema
     * 
     * @param configuration the configuration document
     * 
     * @throws XMLParserException thrown if the document fails validation
     */
    private static void validateConfigurationDocument(Document configuration) throws XMLParserException{
        if(log.isDebugEnabled()){
            log.debug("Validating configuration document");
        }
        ParserPoolManager xmlParserPool = ParserPoolManager.getInstance();
        TreeMap<String, EntityResolver> configSchemaInfo = new TreeMap<String, EntityResolver>();
        configSchemaInfo.put(XMLConstants.OPENSAML_CONFIG_SCHEMA_LOCATION, new NoOpEntityResolver());
        xmlParserPool.registerSchemas(configSchemaInfo);
        xmlParserPool.validate(configuration);
    }
    
    /**
     * Constructs an instance of the given class.
     * 
     * @param className the class's name
     * 
     * @return an instance of the given class
     * 
     * @throws InstantiationException 
     */
    private static Object createClassInstance(String className) throws InstantiationException{
        try{
            Class clazz = Class.forName(className);
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
        }catch(ClassNotFoundException e){
            log.error("Can not create instance of " + className + ", class not found on classpath", e);
            throw new InstantiationException("Can not create instance of " + className + ", class not found on classpath");
        }catch(NoSuchMethodException e){
            log.error(className + " does not have a default constructor, can not create instance", e);
            throw new InstantiationException(className + " does not have a default constructor, can not create instance");
        } catch (IllegalArgumentException e) {
            // No arguments in default constructor
        } catch (IllegalAccessException e) {
            log.error("Unable to execute constructor for " + className + " do to security restriction", e);
            throw new InstantiationException("Unable to execute constructor for " + className + " do to security restriction");
        } catch (InvocationTargetException e) {
            log.error("Constructor for " + className + " through the following error when executed", e);
            throw new InstantiationException("Constructor for " + className + " through the following error when executed" + e);
        }
        
        return null;
    }
}
