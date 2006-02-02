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

import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.opensaml.xml.DOMCachingXMLObject;
import org.opensaml.xml.Namespace;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A thread safe, abstract implementation of the {@link org.opensaml.common.io.Marshaller} interface that handles most
 * of the boilerplate code:
 * <ul>
 * <li>Ensuring elements to be marshalled are of either the correct xsi:type or element QName</li>
 * <li>Setting the appropriate namespace and prefix for the marshalled element</li>
 * <li>Setting the xsi:type for the element if the element has an explicit type</li>
 * <li>Setting namespaces attributes declared for the element</li>
 * <li>Marshalling of child elements</li>
 * <li>Caching of created DOM for elements that implement {@link org.opensaml.xml.DOMCachingXMLObject}
 * </ul>
 */
public abstract class AbstractXMLObjectMarshaller implements Marshaller<XMLObject> {

    /** Logger */
    private static Logger log = Logger.getLogger(AbstractXMLObjectMarshaller.class);

    /** The target name and namespace for this marshaller. */
    private QName targetQName;

    /** Factory for XMLObject Marshallers */
    private MarshallerFactory<QName, Marshaller<XMLObject>> marshallerFactory;

    /**
     * 
     * Constructor
     *
     * @param targetNamespaceURI the namespace URI of either the schema type QName or element QName of the elements this
     *            unmarshaller operates on
     * @param targetLocalName the local name of either the schema type QName or element QName of the elements this
     *            unmarshaller operates on
     * @param marshallerFactory the factory used to create Marshallers for child objects
     * 
     * @throws NullPointerException if any of the arguments are null (or empty in the case of String parameters)
     */
    protected AbstractXMLObjectMarshaller(String targetNamespaceURI, String targetLocalName,
            MarshallerFactory<QName, Marshaller<XMLObject>> marshallerFactory) throws IllegalArgumentException {
        if (DatatypeHelper.isEmpty(targetNamespaceURI)) {
            throw new NullPointerException("Target Namespace URI may not be null or an empty");
        }

        if (DatatypeHelper.isEmpty(targetLocalName)) {
            throw new NullPointerException("Target Local Name may not be null or an empty");
        }
        targetQName = new QName(targetNamespaceURI, targetLocalName);

        if (marshallerFactory == null) {
            throw new NullPointerException("Marshaller factory must not be null");
        }
        this.marshallerFactory = marshallerFactory;
    }

    /*
     * @see org.opensaml.xml.io.Marshaller#marshall(T, org.w3c.dom.Document)
     */
    public Element marshall(XMLObject xmlObject, Document document) throws MarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Starting to marshall " + xmlObject.getElementQName());
        }

        if (xmlObject instanceof DOMCachingXMLObject) {
            DOMCachingXMLObject domCachingObject = (DOMCachingXMLObject) xmlObject;
            if (domCachingObject.getDOM() != null) {
                log.debug("XMLObject " + xmlObject.getElementQName() + " has a cached DOM, using it.");
                return domCachingObject.getDOM();
            }
        }
        
        if(log.isDebugEnabled()) {
            log.debug("Creating Element to marshall " + xmlObject.getElementQName() + " into");
        }
        Element domElement = document.createElementNS(xmlObject.getElementQName().getNamespaceURI(), xmlObject.getElementQName().getLocalPart());
        
        if(log.isDebugEnabled()) {
            log.debug("Setting namespace prefix for " + xmlObject.getElementQName().getPrefix() + " for XMLObject " + xmlObject.getElementQName());
        }
        domElement.setPrefix(xmlObject.getElementQName().getPrefix());

        // Plant the element as the document root if this XMLObject is at the top of tree
        if (xmlObject.getParent() == null) {
            if(log.isDebugEnabled()) {
                log.debug("XMLObject " + xmlObject.getElementQName() + " is the root of the tree, planting it at the Document root");
            }
            Element docElement = document.getDocumentElement();
            if (document.getDocumentElement() != null) {
                document.replaceChild(domElement, docElement);
            } else {
                document.appendChild(domElement);
            }
        }

        marshallNamespaces(xmlObject, domElement);

        marshallAttributes(xmlObject, domElement);

        marshallChildElements(xmlObject, domElement);

        marshallElementContent(xmlObject, domElement);

        marshallElementType(xmlObject, domElement);

        if (xmlObject instanceof DOMCachingXMLObject) {
            if(log.isDebugEnabled()) {
                log.debug("Caching DOM for XMLObject " + xmlObject.getElementQName());
            }
            ((DOMCachingXMLObject) xmlObject).setDOM(domElement);
        }

        return domElement;
    }

    /**
     * Checks to make sure the given XMLObject's schema type or element QName matches the target parameters given at
     * marshaller construction time.
     * 
     * @param xmlObject the XMLObject to marshall
     */
    protected void checkXMLObjectIsTarget(XMLObject xmlObject) throws MarshallingException {
        if(log.isDebugEnabled()) {
            log.debug("Checking that " + xmlObject.getElementQName() + " meets target criteria");
        }
        
        QName type = xmlObject.getSchemaType();
        if (type != null && type.equals(targetQName)) {
            if(log.isDebugEnabled()) {
                log.debug(xmlObject.getElementQName() + " schema type matches target");
            }
            return;
        }else {
            QName elementQName = xmlObject.getElementQName();
            if(elementQName.equals(targetQName)) {
                if(log.isDebugEnabled()) {
                    log.debug(xmlObject.getElementQName() + " element QName matches target");
                }
                return;
            }else {
                String errorMsg = "This marshaller only operations on " + targetQName + " elements not "
                + elementQName;
                log.error(errorMsg);
                throw new MarshallingException(errorMsg);
            }
        }
    }

    protected Marshaller<XMLObject> getMarshaller(XMLObject xmlObject) throws MarshallingException {
        if(log.isDebugEnabled()) {
            log.debug("Getting Marshalelr for XMLObject " + xmlObject.getElementQName());
        }
        
        Marshaller<XMLObject> marshaller;

        // Try to get the marshaller based off the schema type
        marshaller = marshallerFactory.getMarshaller(xmlObject.getSchemaType());
        if (marshaller != null) {
            if(log.isDebugEnabled()) {
                log.debug("Marshaller" + marshaller.getClass() + " located based on schema type " + xmlObject.getSchemaType());
            }
            return marshaller;
        }

        // Since there was no marshaller registered for the schema type try to get one based off the element QName
        marshaller = marshallerFactory.getMarshaller(xmlObject.getElementQName());
        if (marshaller != null) {
            if(log.isDebugEnabled()) {
                log.debug("Marshaller" + marshaller.getClass() + " located based on element QName " + xmlObject.getElementQName());
            }
            return marshaller;
        }

        String errorMsg = "No marshaller registered for XMLObject " + xmlObject.getElementQName();
        log.error(errorMsg);
        throw new MarshallingException(errorMsg);
    }

    /**
     * Marshalls the child elements of the given XMLObject.
     * 
     * @param xmlObject the XMLObject whose children will be marshalled
     * @param domElement the DOM element that will recieved the marshalled children
     * 
     * @throws MarshallingException thrown if there is a problem marshalling a child element
     */
    protected void marshallChildElements(XMLObject xmlObject, Element domElement) throws MarshallingException {

        if (log.isDebugEnabled()) {
            log.debug("Marshalling child elements for XMLObject " + xmlObject.getElementQName());
        }

        List<XMLObject> childXMLObjects = (List<XMLObject>) xmlObject.getOrderedChildren();
        if (childXMLObjects != null && childXMLObjects.size() > 0) {
            for (XMLObject childXMLObject : childXMLObjects) {
                if (childXMLObject == null) {
                    continue;
                }

                if(log.isDebugEnabled()) {
                    log.debug("Getting marshaller for child XMLObject " + childXMLObject.getElementQName());
                }
                Marshaller<XMLObject> marshaller = getMarshaller(childXMLObject);
                
                if(log.isDebugEnabled()) {
                    log.debug("Marshalling "+ childXMLObject.getElementQName() + " and adding it to DOM");
                }
                domElement.appendChild(marshaller.marshall(childXMLObject, domElement.getOwnerDocument()));
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("No child elements to marshall for XMLObject " + xmlObject.getElementQName());
            }
        }
    }

    /**
     * Creates an xsi:type attribute, corresponding to the given type of the XMLObject, on the DOM element.
     * 
     * @param xmlObject the XMLObject
     * @param domElement the DOM element
     * 
     * @throws MarshallingException thrown if the type on the XMLObject is does contain a local name, local name
     *             prefix, and namespace URI
     */
    protected void marshallElementType(XMLObject xmlObject, Element domElement) throws MarshallingException {
        QName type = xmlObject.getSchemaType();
        if (type != null) {
            if (log.isDebugEnabled()) {
                log.debug("Setting xsi:type attribute with for XMLObject " + xmlObject.getElementQName());
            }
            String typeLocalName = type.getLocalPart();
            String typePrefix = type.getPrefix();

            if (typeLocalName == null) {
                throw new MarshallingException("The type QName on XMLObject "
                        + xmlObject.getElementQName() + " may not have a null local name");
            }

            if (typePrefix == null) {
                throw new MarshallingException("The type QName on XMLObject "
                        + xmlObject.getElementQName() + " may not have a null prefix");
            }

            if (type.getNamespaceURI() == null) {
                throw new MarshallingException("The type URI QName on XMLObject "
                        + xmlObject.getElementQName() + " may not have a null namespace URI");
            }

            domElement.setAttributeNS(XMLConstants.XSI_NS, XMLConstants.XSI_PREFIX + ":type", typePrefix + ":"
                    + typeLocalName);
            
            if(log.isDebugEnabled()) {
                log.debug("Adding XSI namespace to list of namespaces used by XMLObject " + xmlObject.getElementQName());
            }
            xmlObject.addNamespace(new Namespace(XMLConstants.XSI_NS, XMLConstants.XSI_PREFIX));
        }
    }

    /**
     * Creates the xmlns attributes for any namespaces set on the given XMLObject.
     * 
     * @param xmlObject the XMLObject
     * @param domElement the DOM element the namespaces will be added to
     */
    protected void marshallNamespaces(XMLObject xmlObject, Element domElement) throws MarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Marshalling namespace attributes for XMLObject " + xmlObject.getElementQName());
        }
        Set<Namespace> namespaces = xmlObject.getNamespaces();
        for (Namespace namespace : namespaces) {
            domElement.setAttribute(XMLConstants.XMLNS_PREFIX + ":" + namespace.getNamespacePrefix(), namespace
                    .getNamespaceURI());
        }
    }
    
    /**
     * Marshalls a given XMLObject into a W3C Element. The given signing context should be blindly passed to the
     * marshaller for child elements. The XMLObject passed to this method is guaranteed to be of the target name
     * specified during this unmarshaller's construction.
     * 
     * @param xmlObject the XMLObject to marshall
     * @param domElement the W3C DOM element
     * 
     * @throws MarshallingException thrown if there is a problem marshalling the element
     */
    protected abstract void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException;
    
    /**
     * Marshalls data from the XMLObject into content of the DOM Element.
     * 
     * @param xmlObject the XMLObject
     * @param domElement the DOM element recieving the content
     */
    protected abstract void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException;
}