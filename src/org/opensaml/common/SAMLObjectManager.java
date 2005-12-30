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

import java.util.Map;

import javax.xml.namespace.QName;

import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

public class SAMLObjectManager {

    /** Singleton instance */
    private static final SAMLObjectManager instance = new SAMLObjectManager();;
    
    /** Factory for XMLObjectBuilders */
    private XMLObjectBuilderFactory<QName, SAMLObjectBuilder> objectBuilderFactory;
    
    /** Factory for Marshallers */
    private MarshallerFactory<QName, SAMLObjectMarshaller> marshallerFactory;
    
    /** Factory for Unmarshallers */
    private UnmarshallerFactory<QName, SAMLObjectUnmarshaller> unmarshallerFactory;
    
    /**
     * Constructor
     */
    private SAMLObjectManager(){
        objectBuilderFactory = new XMLObjectBuilderFactory<QName, SAMLObjectBuilder>();
        marshallerFactory = new MarshallerFactory<QName, SAMLObjectMarshaller>();
        unmarshallerFactory = new UnmarshallerFactory<QName, SAMLObjectUnmarshaller>();
    }
    
    /**
     * Gets the singleton instance of this class.
     * 
     * @return the singleton instance of this class
     */
    public static SAMLObjectManager getInstance(){
        return instance;
    }
    
    /**
     * Retrieves an {@link XMLObjectBuilder} using the key it was registered with.
     * 
     * @param key the key used to register the builder
     * 
     * @return the builder
     */
    public static SAMLObjectBuilder getBuilder(QName objectTypeOrName) {
        return getInstance().getObjectBuilderFactory().getBuilder(objectTypeOrName);
    }
    
    /**
     * Gets the builder for the given DOM Element.  If the element has a schema type associated 
     * with it then the builder associated with the type will be returned, if one is registered. 
     * If there is no schema type, or no builder registered for the schema type, the builder 
     * assoicated with the element's QName will be returned.  If there is not builder registered 
     * for that QName, null is returned.
     * 
     * @param domElement the DOM Element to fetch the builder for
     * 
     * @return the builder or null
     */
    public static SAMLObjectBuilder getBuilder(Element domElement){
        SAMLObjectBuilder builder;
        
        QName type = XMLHelper.getXSIType(domElement);
        if(type != null){
            builder = getBuilder(type);
            if(builder != null){
                return builder;
            }
        }
        
        QName elementQName = XMLHelper.getNodeQName(domElement);
        return getBuilder(elementQName);
    }

    /**
     * Gets an immutable list of all the builders currently registered.
     * 
     * @return list of all the builders currently registered
     */
    public Map<QName, SAMLObjectBuilder> getBuilders() {
        return objectBuilderFactory.getBuilders();
    }

    /**
     * Registers a new builder for the given name.
     * 
     * @param builderKey the key used to retrieve this builder later
     * @param builder the builder
     */
    public void registerBuilder(QName objectTypeOrName, SAMLObjectBuilder builder) {
        objectBuilderFactory.registerBuilder(objectTypeOrName, builder);
    }

    /**
     * Deregisters a builder.
     * 
     * @param builderKey the key for the builder to be deregistered
     */
    public XMLObjectBuilder deregisterBuilder(QName objectTypeOrName) {
        return objectBuilderFactory.deregisterBuilder(objectTypeOrName);
    }
    
    /**
     * Gets the marshaller for the given element schema type or element name
     * 
     * @param objectTypeOrName element schema type or element name
     * 
     * @return the marshaller for the element
     */
    public static SAMLObjectMarshaller getMarshaller(QName objectTypeOrName){
        return getInstance().getMarshallerFactory().getMarshaller(objectTypeOrName);
    }
    
    /**
     * Gets the marshaller for the given SAMLObject.  If the object has a schema type associated 
     * with it then the marshaller associated with the type will be returned, if one is registered. 
     * If there is no schema type, or no marshaller registered for the schema type, the marshaller 
     * assoicated with the object's element QName will be returned.  If there is not marshaller registered 
     * for that QName, null is returned.
     * 
     * @param samlObject the SAMLObject to fetch the marshaller for
     * 
     * @return the marshaller or null
     */
    public static SAMLObjectMarshaller getMarshaller(SAMLObject samlObject){
        SAMLObjectMarshaller marshaller;
        if(samlObject.getSchemaType() != null){
            marshaller = getMarshaller(samlObject.getSchemaType());
            if(marshaller != null){
                return marshaller;
            }
        }
        
        return getMarshaller(samlObject.getElementQName());
    }
    
    /**
     * Gets an immutable listing of all the Marshallers currently registered.
     * 
     * @return a listing of all the Marshallers currently registered
     */
    public Map<QName, SAMLObjectMarshaller> getMarshallers() {
        return marshallerFactory.getMarshallers();
    }

    /**
     * Registers a Marshaller with this factory. If a Marshaller exist for the element name given it is replaced with
     * the given marshaller.
     * 
     * @param objectTypeOrName the type or element the Marshaller operates on
     * @param marshaller the Marshaller
     */
    public void registerMarshaller(QName objectTypeOrName, SAMLObjectMarshaller marshaller) {
        marshallerFactory.registerMarshaller(objectTypeOrName, marshaller);
    }

    /**
     * Deregisters the marshaller for the given element.
     * 
     * @param objectTypeOrName the type or element name
     * 
     * @return the Marshaller previously registered or null
     */
    public Marshaller deregisterMarshaller(QName objectTypeOrName) {
        return marshallerFactory.deregisterMarshaller(objectTypeOrName);
    }
    
    
    /**
     * Gets the unmarshaller for the given element schema type or element name
     * 
     * @param objectTypeOrName element schema type or element name
     * 
     * @return the unmarshaller for the element
     */
    public static SAMLObjectUnmarshaller getUnmarshaller(QName objectTypeOrName){
        return getInstance().getUnmarshallerFactory().getUnmarshaller(objectTypeOrName);
    }
    
    /**
     * Gets the unmarshaller for the given DOM Element.  If the element has a schema type associated 
     * with it then the unmarshaller associated with the type will be returned, if one is registered. 
     * If there is no schema type, or no unmarshaller registered for the schema type, the unmarshaller 
     * assoicated with the element's QName will be returned.  If there is not an unmarshaller registered 
     * for that QName, null is returned.
     * 
     * @param domElement the DOM Element to fetch the unmarshaller for
     * 
     * @return the unmarshaller or null
     */
    public static SAMLObjectUnmarshaller getUnmarshaller(Element domElement){
        SAMLObjectUnmarshaller unmarshaller;
        
        QName type = XMLHelper.getXSIType(domElement);
        if(type != null){
            unmarshaller = getUnmarshaller(type);
            if(unmarshaller != null){
                return unmarshaller;
            }
        }
        
        QName elementQName = XMLHelper.getNodeQName(domElement);
        return getUnmarshaller(elementQName);
    }
    
    /**
     * Gets an immutable listing of all the Unarshallers currently registered.
     * 
     * @return a listing of all the Unmarshallers currently registered
     */
    public Map<QName, SAMLObjectUnmarshaller> getUnmarshallers() {
        return unmarshallerFactory.getUnmarshallers();
    }

    /**
     * Registers an Unmarshaller with this factory. If an Unmarshaller exist for the Qname given it is replaced with the
     * given unmarshaller.
     * 
     * @param objectTypeOrName the type or element the Unmarshaller operates on
     * @param unmarshaller the Unmarshaller
     */
    public void registerUnmarshaller(QName objectTypeOrName, SAMLObjectUnmarshaller unmarshaller) {
        unmarshallerFactory.registerUnmarshaller(objectTypeOrName, unmarshaller);
    }

    /**
     * Deregisters the unmarshaller for the given element.
     * 
     * @param objectTypeOrName the type or element's name
     * 
     * @return the Unmarshaller previously registered or null
     */
    public Unmarshaller deregisterUnmarshaller(QName objectTypeOrName) {
        return unmarshallerFactory.deregisterUnmarshaller(objectTypeOrName);
    }
    
    /**
     * Gets the XMLObjectBuilderFactory this manager uses to create SAMLObjectBuilders
     * 
     * @return the XMLObjectBuilderFactory
     */
    protected XMLObjectBuilderFactory<QName, SAMLObjectBuilder> getObjectBuilderFactory(){
        return objectBuilderFactory;
    }
    
    /**
     * Gets the MarshallerFactory this manager uses to create SAMLObjectMarshallers
     * 
     * @return the MarshallerFactory
     */
    protected MarshallerFactory<QName, SAMLObjectMarshaller> getMarshallerFactory(){
        return marshallerFactory;
    }
    
    /**
     * Gets the UnmarshallerFactory this manager uses to create SAMLObjectUnmarshallers
     * 
     * @return the UnmarshallerFactory
     */
    protected UnmarshallerFactory<QName, SAMLObjectUnmarshaller> getUnmarshallerFactory(){
        return unmarshallerFactory;
    }
}