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
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectBuilderFactory;
import org.opensaml.common.impl.DOMCachingSAMLObject;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallerFactory;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.util.NamespaceComparator;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.common.util.xml.XMLHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An thread safe abstract unmarshaller.  This abstract marshaller only works with 
 * {@link org.opensaml.common.impl.AbstractSAMLObject}.
 */
public abstract class AbstractUnmarshaller implements Unmarshaller {
    
    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(AbstractUnmarshaller.class);
    
    /** Compartor for Namespaces */
    private static final NamespaceComparator nsCompare = new NamespaceComparator();
    
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
    protected AbstractUnmarshaller(QName target){
        this.target = target;
    }

    /*
     * @see org.opensaml.common.io.Unmarshaller#unmarshall(org.w3c.dom.Element)
     */
    public SAMLObject unmarshall(Element domElement) throws UnmarshallingException{
        if(log.isDebugEnabled()) {
            log.debug("Staring to log DOM element " + domElement.getLocalName());
        }
        
        SAMLObjectBuilder elemBuilder;
        SAMLObject samlElement;
        QName type = XMLHelper.getXSIType(domElement);
        
        //Check to make sure the given element type or QName matches the given target QName
        //If so, create the SAML element everything will be unmarshalled in to
        if(type != null) {
            if(nsCompare.compare(type, target) != 0) {
                throw new UnmarshallingException("Can not unmarshall DOM element of type " + type.getNamespaceURI() + ":" + type.getLocalPart() + 
                        ".  This unmarshaller only operations on DOM elements of type " + target.getNamespaceURI() + ":" + target.getLocalPart());
            }
            if(log.isDebugEnabled()) {
                log.debug("DOM element " + domElement.getLocalName() + " is of type " + type + " retrieving builder based on that type.");
            }
            elemBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(type);
            samlElement = elemBuilder.buildObject();
            samlElement.setSchemaType(type);
        }else {
            if(log.isDebugEnabled()) {
                log.debug("DOM element " + domElement.getLocalName() + " does not have an explicit type retrieving builder based on element qname.");
            }
            String domElementNamespace = domElement.getNamespaceURI();
            String domElementLocalName = domElement.getLocalName();
            if(!domElementNamespace.equals(target.getNamespaceURI()) && !domElementLocalName.equals(target.getLocalPart())){
                throw new UnmarshallingException("Can not unmarshall DOM element " + domElementNamespace + ":" + domElementLocalName + 
                        ".  This unmarshaller only operations on DOM element " + target.getNamespaceURI() + ":" + target.getLocalPart());
            }
            
            elemBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(XMLHelper.getElementQName(domElement));
            samlElement = elemBuilder.buildObject();
        }
        
        samlElement.setElementNamespaceAndPrefix(domElement.getNamespaceURI(), domElement.getPrefix());
        
        if(log.isDebugEnabled()) {
            log.debug("Empty SAML element object created based on builder.  Processing DOM attributes and child elements.");
        }
        
        //Process any attributes and child elements it may have
        NodeList childNodes = domElement.getChildNodes();
        Node childNode;
        Attr childAttribute;
        Element childElement;
        Unmarshaller unmarshaller;
        if(childNodes != null) {
            for(int i = 0; i < childNodes.getLength(); i++) {
                childNode = childNodes.item(i);
                if(childNode.getNodeType() == Node.ATTRIBUTE_NODE) {
                    childAttribute = (Attr) childNode;
                    processAttribute(samlElement, childAttribute);
                }else if(childNode.getNodeType() == Node.ELEMENT_NODE) {
                    childElement = (Element) childNode;
                    
                    if(log.isDebugEnabled()) {
                        log.debug("Child element " + childElement.getTagName() + " being unmarshalled and added to SAML element");
                    }
                    unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(childElement);
                    processChildElement(samlElement, unmarshaller.unmarshall(childElement));
                }
            }
        }
        
        if(samlElement instanceof DOMCachingSAMLObject) {
            ((DOMCachingSAMLObject)samlElement).setDOM(domElement);
        }
        return samlElement;
    }
    
    /**
     * Pre-process the given attribute.  If the attribute is an XML namespace declaration the namespace is added
     * to the given element, if it is an xsi:type it is ingored, anything is passed to the {@link #processAttribute(AbstractSAMLObject, String, String)}
     * to be added to the given element.
     * 
     * @param rootElement the SAML element that will recieve information from the DOM attribute
     * @param attribute the DOM attribute
     * 
     * @throws UnmarshallingException thrown if the given attribute is not an allowable attribute on this SAML element
     */
    protected void processAttribute(SAMLObject rootElement, Attr attribute) throws UnmarshallingException {
        if(log.isDebugEnabled()) {
            log.debug("Processing attribute " + attribute.getName());
        }
        if(attribute.getNamespaceURI().equals(XMLConstants.XMLNS_NS)) {
            if(log.isDebugEnabled()) {
                log.debug("Attribute " + attribute.getName() + " is a namespace declaration, adding it to the list of namespaces on the SAML element");
            }
            // Attribute is a namespace
            rootElement.addNamespace(new QName(attribute.getValue(), "", attribute.getPrefix()));
        }else if(attribute.getNamespaceURI().equals(XMLConstants.XSI_NS) && attribute.getLocalName().equals("type")){
            // Attribute is an XSI type, we've already handled this above
            if(log.isDebugEnabled()) {
                log.debug("Attribute " + attribute.getName() + " is an xsi:type, this has already been dealth with, ignoring attribute");
            }
            return;
        }else {
            // Attribute is an element specific attribute
            processAttribute(rootElement, attribute.getLocalName(), attribute.getValue());
        }
    }
    
    /**
     * Called after this unmarshaller has unmarshalled a child element in order to add that child to the parent element.
     * 
     * @param parentElement the parent element
     * @param childElement the child element
     * 
     * @throws UnmarshallingException thrown if the child element is not a valid child of the parent
     */
    protected abstract void processChildElement(SAMLObject parentElement, SAMLObject childElement) throws UnmarshallingException;
    
    /**
     * Called after this unmarshaller has unmarshalled an attribute in order to add it to the SAML element
     * 
     * @param samlElement the SAML element
     * @param attributeName the attributes name
     * @param attributeValue the attributes value
     * 
     * @throws UnmarshallingException thrown if the given attribute is not a valid attribute for this SAML element
     */
    protected abstract void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue) throws UnmarshallingException;
    
    /**
     * Called to process the content of a DOM element
     * 
     * @param samlElement SAML object the content will be given to
     * @param elementContent the DOM element content
     */
    protected void processElementContent(SAMLObject samlElement, String elementContent) {
        //Vast majority of elements don't have textual content, let the few that do override this.
    }

}
