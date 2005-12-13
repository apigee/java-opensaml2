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

package org.opensaml.common.io.impl;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLObjectBuilderFactory;
import org.opensaml.common.SignableObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.impl.DOMCachingSAMLObject;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallerFactory;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.util.StringHelper;
import org.opensaml.common.util.xml.DigitalSignatureHelper;
import org.opensaml.common.util.xml.Namespace;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.common.util.xml.XMLHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An thread safe abstract unmarshaller. This abstract marshaller only works with
 * {@link org.opensaml.common.impl.AbstractSAMLObject}.
 */
public abstract class AbstractUnmarshaller implements Unmarshaller {

    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(AbstractUnmarshaller.class);

    /**
     * QName of the element this unmarshaller is for
     */
    private QName target;

    /**
     * 
     * Constructor
     * 
     * @param target the QName of the type or elment this unmarshaller operates on
     */
    protected AbstractUnmarshaller(QName target) {
        this.target = target;
    }

    /*
     * @see org.opensaml.common.io.Unmarshaller#unmarshall(org.w3c.dom.Element)
     */
    public SAMLObject unmarshall(Element domElement) throws UnmarshallingException, UnknownAttributeException,
            UnknownElementException {
        if (log.isDebugEnabled()) {
            log.debug("Starting to unmarshall DOM element " + domElement.getLocalName());
        }

        checkElementIsTarget(domElement);

        SAMLObject samlObject = buildSAMLObject(domElement);

        unmarshallAttributes(domElement, samlObject);

        unmarshallElementContent(samlObject, domElement.getTextContent());

        unmarshallChildElements(domElement, samlObject);
        
        unmarshallDigitalSignature(domElement, samlObject);

        if (samlObject instanceof DOMCachingSAMLObject) {
            ((DOMCachingSAMLObject) samlObject).setDOM(domElement);
        }
        return samlObject;
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
        String domElementNamespace = domElement.getNamespaceURI();
        String domElementLocalName = domElement.getLocalName();
        QName type = XMLHelper.getXSIType(domElement);

        if (type != null) {
            if (!type.getNamespaceURI().equals(target.getNamespaceURI())
                    || !type.getLocalPart().equals(target.getLocalPart())) {
                throw new UnmarshallingException("Can not unmarshall DOM element of type " + type.getNamespaceURI()
                        + ":" + type.getLocalPart() + ".  This unmarshaller only operations on DOM elements of type "
                        + target.getNamespaceURI() + ":" + target.getLocalPart());
            }
        } else {
            if (!domElementNamespace.equals(target.getNamespaceURI())
                    || !domElementLocalName.equals(target.getLocalPart())) {
                throw new UnmarshallingException("Can not unmarshall DOM element " + domElementNamespace + ":"
                        + domElementLocalName + ".  This unmarshaller only operations on DOM element "
                        + target.getNamespaceURI() + ":" + target.getLocalPart());
            }
        }
    }

    /**
     * Constructs the SAMLObject that the given DOM Element will be unmarshalled into. The returned object has the XSI
     * schema type declared and namespace and prefix, declared by the given DOM Element, already set.
     * 
     * @param domElement the DOM Element the created SAMLObject will represent
     * 
     * @return the SAMLObject
     * 
     * @throws UnmarshallingException thrown if there is now SAMLObjectBuilder registered for the given DOM Element
     */
    protected SAMLObject buildSAMLObject(Element domElement) throws UnmarshallingException {
        SAMLObjectBuilder samlObjectBuilder;
        SAMLObject samlObject;
        QName type = XMLHelper.getXSIType(domElement);

        if (type != null) {
            if (log.isDebugEnabled()) {
                log.debug("DOM element " + domElement.getLocalName() + " is of type " + type
                        + " retrieving builder based on that type.");
            }
            samlObjectBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(type);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("DOM element " + domElement.getLocalName()
                        + " does not have an explicit type retrieving builder based on element qname.");
            }
            samlObjectBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(XMLHelper.getNodeQName(domElement));
        }

        if (samlObjectBuilder == null) {
            throw new UnmarshallingException("No SAMLObjectBuilder was registered for element "
                    + domElement.getLocalName());
        }

        samlObject = samlObjectBuilder.buildObject();
        samlObject.setSchemaType(type);
        samlObject.setElementNamespaceAndPrefix(domElement.getNamespaceURI(), domElement.getPrefix());
        return samlObject;
    }

    /**
     * Unmarshalls the attributes from the given DOM Element into the given SAMLObject. If the attribute is an XML
     * namespace declaration the namespace is added to the given element, if it is an xsi:type it is ingored, anything
     * else is passed to the {@link #processAttribute(AbstractSAMLObject, String, String)} to be added to the given
     * element.
     * 
     * @param samlObject the SAML element that will recieve information from the DOM attribute
     * @param attribute the DOM attribute
     * 
     * @throws UnmarshallingException thrown if the given attribute is not an allowable attribute on this SAML element
     * @throws UnknownAttributeException thrown if an attribute that the unmarshaller does not understand is encountered
     */
    protected void unmarshallAttributes(Element domElement, SAMLObject samlObject) throws UnmarshallingException,
            UnknownAttributeException {
        if (log.isDebugEnabled()) {
            log.debug("Unmarshalling attributes for DOM Element " + domElement.getLocalName());
        }

        NamedNodeMap attributes = domElement.getAttributes();
        Node childNode;
        Attr attribute;
        if (attributes == null) {
            return;
        }
        for (int i = 0; i < attributes.getLength(); i++) {
            childNode = attributes.item(i);

            // The child node should always be an attribute, but just in case
            if (childNode.getNodeType() != Node.ATTRIBUTE_NODE) {
                continue;
            }

            attribute = (Attr) childNode;
            if (!StringHelper.isEmpty(attribute.getNamespaceURI())) {
                if (attribute.getNamespaceURI().equals(XMLConstants.XMLNS_NS)) {
                    if (log.isDebugEnabled()) {
                        log
                                .debug("Attribute "
                                        + attribute.getName()
                                        + " is a namespace declaration, adding it to the list of namespaces on the SAML element");
                    }

                    samlObject.addNamespace(new Namespace(attribute.getValue(), attribute.getLocalName()));
                    continue;
                } else if (attribute.getNamespaceURI().equals(XMLConstants.XSI_NS)
                        && attribute.getLocalName().equals("type")) {

                    if (log.isDebugEnabled()) {
                        log.debug("Attribute " + attribute.getName()
                                + " is an xsi:type, this has already been dealth with, ignoring attribute");
                    }
                    continue;
                }
            }

            // Attribute is an element specific attribute
            processAttribute(samlObject, attribute.getLocalName(), attribute.getValue());
        }
    }

    /**
     * Unmarshalls the child elements, of the given DOM Element, into the given SAMLObject. Each child element is
     * unmarshalled into it's own SAMLObject and then the given SAMLObject and the child are passed to
     * {@link #processChildElement(SAMLObject, SAMLObject)} so that the child element may be added to the parent object.
     * 
     * @param domElement the DOM Element whose children will be unmarshalled
     * @param samlObject the parent object of the unmarshalled children
     * 
     * @throws UnmarshallingException thrown if there
     * @throws UnknownElementException
     */
    protected void unmarshallChildElements(Element domElement, SAMLObject samlObject) throws UnmarshallingException,
            UnknownElementException {
        if (log.isDebugEnabled()) {
            log.debug("Unmarshalling child elements of DOM Element " + domElement.getLocalName());
        }
        NodeList childNodes = domElement.getChildNodes();
        Node childNode;
        Element childElement;
        Unmarshaller unmarshaller;
        if (childNodes == null || childNodes.getLength() == 0) {
            return;
        }

        for (int i = 0; i < childNodes.getLength(); i++) {
            childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                childElement = (Element) childNode;
                unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(childElement);
                
                if (unmarshaller == null) {
                    if (!SAMLConfig.ignoreUnknownElements()) {
                        log.error("No unmarshaller registered for child element, "
                                + childElement.getLocalName() + ", of DOM element " + domElement.getLocalName());
                        throw new UnknownElementException("No unmarshaller registered for child element, "
                                + childElement.getLocalName() + ", of DOM element " + domElement.getLocalName());
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("Ingored child element, " + childElement.getLocalName() + ", of element "
                                    + domElement.getLocalName() + " because it had no registered unmarshaller.");
                        }
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Child element " + childElement.getTagName()
                                + " being unmarshalled and added to SAML Object");
                    }
                    processChildElement(samlObject, unmarshaller.unmarshall(childElement));
                }
            }
        }
    }
    
    /**
     * Unmarshalls the digital signature, if present, into a SigningContext and adds it to the SAMLObject.
     * 
     * @param domElement the DOM element that may contain a signature
     * @param samlObject the SAMLObject that will recieve the SigningContext
     */
    protected void unmarshallDigitalSignature(Element domElement, SAMLObject samlObject) {
        if(samlObject instanceof SignableObject) {
            ((SignableObject)samlObject).setSigningContext(DigitalSignatureHelper.buildFromSignature(domElement));
        }
    }

    /**
     * Called after this unmarshaller has unmarshalled a child element in order to add that child to the parent element.
     * 
     * @param parentElement the parent element
     * @param childElement the child element
     * 
     * @throws UnmarshallingException thrown if the child element is not a valid child of the parent
     * @throws UnknownElementException thrown if an element that the unmarshaller does not understand is encountered
     */
    protected abstract void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException;

    /**
     * Called after this unmarshaller has unmarshalled an attribute in order to add it to the SAML element
     * 
     * @param samlElement the SAML element
     * @param attributeName the attributes name
     * @param attributeValue the attributes value
     * 
     * @throws UnmarshallingException thrown if the given attribute is not a valid attribute for this SAML element
     * @throws UnknownAttributeException thrown if an attribute that the unmarshaller does not understand is encountered
     */
    protected abstract void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException;

    /**
     * Called to process the content of a DOM element
     * 
     * @param samlElement SAML object the content will be given to
     * @param elementContent the DOM element content
     */
    protected void unmarshallElementContent(SAMLObject samlElement, String elementContent) {
        // Vast majority of elements don't have textual content, let the few that do override this.
    }

}
