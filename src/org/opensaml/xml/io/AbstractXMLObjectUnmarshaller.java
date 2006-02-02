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

package org.opensaml.xml.io;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.opensaml.xml.DOMCachingXMLObject;
import org.opensaml.xml.Namespace;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLConstants;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An thread safe abstract unmarshaller.
 */
public abstract class AbstractXMLObjectUnmarshaller implements Unmarshaller<XMLObject> {

    /** Logger */
    private static Logger log = Logger.getLogger(AbstractXMLObjectUnmarshaller.class);

    /** The target name and namespace for this unmarshaller. */
    private QName targetQName;

    /** Factory for XMLObjectBuilders */
    private XMLObjectBuilderFactory<QName, XMLObjectBuilder<XMLObject>> xmlObjectBuilderFactory;

    /** Factory for creating unmarshallers for child elements */
    private UnmarshallerFactory<QName, Unmarshaller<XMLObject>> unmarshallerFactory;

    /**
     * Constructor.
     * 
     * @param targetNamespaceURI the namespace URI of either the schema type QName or element QName of the elements this
     *            unmarshaller operates on
     * @param targetLocalName the local name of either the schema type QName or element QName of the elements this
     *            unmarshaller operates on
     * @param xmlObjectBuilderFactory the factory used to fetch builders for the XMLObjects elements are unmarshalled
     *            into
     * @param unmarshallerFactory the factory used to fetch unmarshallers for the XMLObjects
     * 
     * @throws NullPointerException if any of the arguments are null (or empty in the case of String parameters)
     */
    protected AbstractXMLObjectUnmarshaller(String targetNamespaceURI, String targetLocalName,
            XMLObjectBuilderFactory<QName, XMLObjectBuilder<XMLObject>> xmlObjectBuilderFactory,
            UnmarshallerFactory<QName, Unmarshaller<XMLObject>> unmarshallerFactory) throws IllegalArgumentException,
            NullPointerException {
        if (DatatypeHelper.isEmpty(targetNamespaceURI)) {
            throw new NullPointerException("Target Namespace URI may not be null or an empty");
        }

        if (DatatypeHelper.isEmpty(targetLocalName)) {
            throw new NullPointerException("Target Local Name may not be null or an empty");
        }
        targetQName = new QName(targetNamespaceURI, targetLocalName);

        if (xmlObjectBuilderFactory == null) {
            throw new NullPointerException("XMLObjectBuilderFactory must not be null");
        }
        this.xmlObjectBuilderFactory = xmlObjectBuilderFactory;

        if (unmarshallerFactory == null) {
            throw new NullPointerException("UnmarshallerFactory must not be null");
        }
        this.unmarshallerFactory = unmarshallerFactory;
    }

    /*
     * @see org.opensaml.common.io.Unmarshaller#unmarshall(org.w3c.dom.Element)
     */
    public XMLObject unmarshall(Element domElement) throws UnmarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Starting to unmarshall DOM element " + domElement.getLocalName());
        }

        checkElementIsTarget(domElement);

        XMLObject xmlObject = buildXMLObject(domElement);

        if (domElement.hasAttributes()) {
            unmarshallAttributes(domElement, xmlObject);
        }

        if (domElement.getTextContent() != null) {
            processElementContent(xmlObject, domElement.getTextContent());
        }

        unmarshallChildElements(domElement, xmlObject);

        if (xmlObject instanceof DOMCachingXMLObject) {
            ((DOMCachingXMLObject) xmlObject).setDOM(domElement);
        }
        return xmlObject;
    }

    /**
     * Checks that the given DOM Element's XSI type or namespace qualified element name matches the target QName of this
     * unmarshaller.
     * 
     * @param domElement the DOM element to check
     * 
     * @throws UnmarshallingException thrown if the DOM Element does not match the target of this unmarshaller
     */
    protected void checkElementIsTarget(Element domElement) throws UnmarshallingException {
        QName elementName = XMLHelper.getNodeQName(domElement);

        if (log.isDebugEnabled()) {
            log.debug("Checking that " + elementName + " meets target criteria.");
        }

        QName type = XMLHelper.getXSIType(domElement);

        if (type != null && type.equals(targetQName)) {
            if (log.isDebugEnabled()) {
                log.debug(elementName + " schema type matches target.");
            }
            return;
        } else {
            if (elementName.equals(targetQName)) {
                if (log.isDebugEnabled()) {
                    log.debug(elementName + " element name matches target.");
                }
                return;
            } else {
                String errorMsg = "This unmarshaller only operations on " + targetQName + " elements not "
                        + elementName;
                log.error(errorMsg);
                throw new UnmarshallingException(errorMsg);
            }
        }
    }

    /**
     * Constructs the XMLObject that the given DOM Element will be unmarshalled into. If the DOM element has an XML
     * Schema type defined this method will attempt to retrieve an XMLObjectBuilder, from the factory given at
     * construction time, using the schema type. If no schema type is present or no builder is registered with the
     * factory for the schema type, the elements QName is used. Once the builder is found the XMLObject is create by
     * invoking {@link XMLObjectBuilder#buildObject()}. Extending classes may wish to override this logic if more than
     * just schema type or element name (e.g. element attributes or content) need to be used to determine which
     * XMLObjectBuilder should be used to create the XMLObject.
     * 
     * @param domElement the DOM Element the created XMLObject will represent
     * 
     * @return the empty XMLObject that DOM Element can be unmarshalled into
     * 
     * @throws UnmarshallingException thrown if there is now XMLObjectBuilder registered for the given DOM Element
     */
    protected XMLObject buildXMLObject(Element domElement) throws UnmarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Building XMLObject for " + XMLHelper.getNodeQName(domElement));
        }
        XMLObjectBuilder<XMLObject> xmlObjectBuilder;

        QName schemaType = XMLHelper.getXSIType(domElement);
        xmlObjectBuilder = xmlObjectBuilderFactory.getBuilder(schemaType);
        if (xmlObjectBuilder == null) {
            QName elementName = XMLHelper.getNodeQName(domElement);
            xmlObjectBuilder = xmlObjectBuilderFactory.getBuilder(elementName);
            if (xmlObjectBuilder == null) {
                log.error("No XMLObjectBuilder was registered for element " + elementName);
                throw new UnmarshallingException("No XMLObjectBuilder was registered for element " + elementName);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Builder located based on element name " + elementName);
                }
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Builder located based on schema type " + schemaType);
            }
        }

        return xmlObjectBuilder.buildObject();
    }

    /**
     * Unmarshalls the attributes from the given DOM Element into the given XMLObject. If the attribute is an XML
     * namespace declaration the namespace is added to the given element via {@link XMLObject#addNamespace(Namespace)}.
     * If it is an schema type (xsi:type) the schema type is added to the element via
     * {@link XMLObject#setSchemaType(QName)}. All other attributes are passed to the
     * {@link #processAttribute(XMLObject, String, String)}
     * 
     * @param domElement the DOM Element whose attributes will be unmarshalled
     * @param xmlObject the XMLObject that will recieve information from the DOM attribute
     * 
     * @throws UnmarshallingException thrown if there is a problem unmarshalling an attribute
     */
    protected void unmarshallAttributes(Element domElement, XMLObject xmlObject) throws UnmarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Unmarshalling attributes for DOM Element " + domElement.getLocalName());
        }

        NamedNodeMap attributes = domElement.getAttributes();
        if (attributes == null) {
            if (log.isDebugEnabled()) {
                log.debug("No attributes to unmarshall");
            }
            return;
        }

        Node childNode;
        Attr attribute;
        for (int i = 0; i < attributes.getLength(); i++) {
            childNode = attributes.item(i);

            // The child node should always be an attribute, but just in case
            if (childNode.getNodeType() != Node.ATTRIBUTE_NODE) {
                if (log.isDebugEnabled()) {
                    log.debug("Encountered child node of type " + childNode.getNodeType()
                            + " in attribute list.  Ignoring it.");
                }
                continue;
            }

            attribute = (Attr) childNode;
            if (log.isDebugEnabled()) {
                log.debug("Pre-processing attribute " + XMLHelper.getNodeQName(attribute));
            }
            if (!DatatypeHelper.isEmpty(attribute.getNamespaceURI())) {
                if (attribute.getNamespaceURI().equals(XMLConstants.XMLNS_NS)) {
                    if (log.isDebugEnabled()) {
                        log.debug(XMLHelper.getNodeQName(attribute)
                                + " is a namespace declaration, adding it to the list of namespaces on the XMLObject");
                    }

                    xmlObject.addNamespace(new Namespace(attribute.getValue(), attribute.getLocalName()));
                    continue;
                } else if (attribute.getNamespaceURI().equals(XMLConstants.XSI_NS)
                        && attribute.getLocalName().equals("type")) {

                    if (log.isDebugEnabled()) {
                        log.debug(XMLHelper.getNodeQName(attribute)
                                + " is a schema type declaration, setting it as the schema type for the XMLObject");
                    }

                    xmlObject.setSchemaType(XMLHelper.getAttributeValueAsQName(attribute));
                    continue;
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Attribute " + XMLHelper.getNodeQName(attribute)
                        + " is neither a schema type nor namespace, calling processAttribute()");
            }
            processAttribute(xmlObject, attribute.getLocalName(), attribute.getValue());
        }
    }

    /**
     * Unmarshalls given Element's children. For each child an unmarshaller is retrieved using
     * {@link #getUnmarshaller(Element)}. The unmarshaller is then used to unmarshall the child element and the
     * resultant XMLObject is passed to {@link #processChildElement(XMLObject, XMLObject)} for further processing.
     * 
     * @param domElement the DOM Element whose children will be unmarshalled
     * @param xmlObject the parent object of the unmarshalled children
     * 
     * @throws UnmarshallingException thrown if an error occurs unmarshalling the chilren elements
     */
    protected void unmarshallChildElements(Element domElement, XMLObject xmlObject) throws UnmarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Unmarshalling child elements of Element " + XMLHelper.getNodeQName(domElement));
        }
        NodeList childNodes = domElement.getChildNodes();
        Node childNode;
        Element childElement;
        Unmarshaller unmarshaller;
        if (childNodes == null || childNodes.getLength() == 0) {
            if (log.isDebugEnabled()) {
                log.debug(XMLHelper.getNodeQName(domElement) + " had no children");
            }
            return;
        }

        for (int i = 0; i < childNodes.getLength(); i++) {
            childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                if (log.isDebugEnabled()) {
                    log.debug("Unmarshalling child element " + XMLHelper.getNodeQName(childNode));
                }
                childElement = (Element) childNode;
                unmarshaller = getUnmarshaller(childElement);
                processChildElement(xmlObject, unmarshaller.unmarshall(childElement));
            }
        }
    }

    /**
     * Gets the Unmarshaller for the given Element from the UnmarshallerFactory provided at construction time. If the
     * child element has an explicit XML Schema type that is used to fetch get the unmarshaller. If there is no
     * unmarshaller registered for the schema type, or the element does not have an explicit schema type, the element's
     * QName is used.
     * 
     * @param domElement the DOM Element to get the Unmarshaller for
     * 
     * @return the Unmarshaller for the given DOM Element
     * 
     * @throws UnmarshallingException thrown if no unmarshaller is available for the given DOM Element
     */
    protected Unmarshaller<XMLObject> getUnmarshaller(Element domElement) throws UnmarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Getting unmarshaller for Element " + XMLHelper.getNodeQName(domElement));
        }
        Unmarshaller<XMLObject> unmarshaller;

        // Try to get the unmarshaller based off the schema type
        QName schemaType = XMLHelper.getXSIType(domElement);
        unmarshaller = unmarshallerFactory.getUnmarshaller(schemaType);
        if(unmarshaller != null) {
            if (log.isDebugEnabled()) {
                log.debug("Unmarshaller " + unmarshaller.getClass() + " located based on schema type " + schemaType);
            }
            return unmarshaller;
        }
        
        // Since there was no unmarshaller registered for the schema type try to get one based off the element QName
        QName elementName = XMLHelper.getNodeQName(domElement);
        unmarshaller = unmarshallerFactory.getUnmarshaller(elementName);
        if(unmarshaller != null) {
            if (log.isDebugEnabled()) {
                log.debug("Unmarshaller " + unmarshaller.getClass() + " located based on element QName " + elementName);
            }
            return unmarshaller;
        }
        
        String errorMsg = "No unmarshaller registered for element " + elementName;
        log.error(errorMsg);
        throw new UnmarshallingException(errorMsg);
    }

    /**
     * Called after a child element has been unmarshalled so that it can be added to the parent XMLObject.
     * 
     * @param parentXMLObject the parent XMLObject
     * @param childXMLObject the child XMLObject
     * 
     * @throws UnmarshallingException thrown if there is a problem adding the child to the parent
     */
    protected abstract void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject)
            throws UnmarshallingException;

    /**
     * Called after an attribute has been unmarshalled so that it can be added to the XMLObject.
     * 
     * @param xmlObject the XMLObject
     * @param attributeName the attributes name
     * @param attributeValue the attributes value
     * 
     * @throws UnmarshallingException thrown if there is a problem adding the attribute to the XMLObject
     */
    protected abstract void processAttribute(XMLObject xmlObject, String attributeName, String attributeValue)
            throws UnmarshallingException;

    /**
     * Called if the element being unmarshalled contained textual content so that it can be added to the XMLObject.
     * 
     * @param xmlObject XMLObject the content will be given to
     * @param elementContent the Element's content
     */
    protected abstract void processElementContent(XMLObject xmlObject, String elementContent);
}