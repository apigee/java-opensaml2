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

import java.util.StringTokenizer;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.parsers.FactoryConfigurationError;

import org.opensaml.common.util.StringHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A helper class for working with W3C DOM objects.
 */
public class XMLHelper {
    
    /** JAXP DatatypeFactory */
    private static DatatypeFactory dataTypeFactory;
    
    /**
     * Gets a static instance of a JAXP DatatypeFactory.
     * 
     * @return the factory or null if the factory could not be created
     */
    public static DatatypeFactory getDataTypeFactory() {
        if(dataTypeFactory == null) {
            try {
                dataTypeFactory = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException e) {
                //do nothing
            }
        }
        
        return dataTypeFactory;
    }
    
    /**
     *  Shortcut for checking a DOM element node's namespace and local name
     *
     * @param  e            An element to compare against
     * @param  ns           An XML namespace to compare
     * @param  localName    A local name to compare
     * @return              true iff the element's local name and namespace match the
     *                          parameters
     */
    //TODO
    public static boolean isElementNamed(Element e, String ns, String localName) {
        return (e != null && StringHelper.safeEquals(ns, e.getNamespaceURI()) && StringHelper.safeEquals(localName, e.getLocalName()));
    }
    
    /**
     * Checks if the given element has an xsi:type defined for it
     * 
     * @param e the DOM element
     * 
     * @return true if there is a type, false if not
     */
    public static boolean hasXSIType(Element e) {
        if(e != null) {
            if(e.getAttributeNodeNS(XMLConstants.XSI_NS, "type") != null) {
                return true;
            }
        }
        
        return false;
        
    }
    
    /**
     * Gets the XSI type for a given element if it has one.
     * 
     * @param e the element
     * 
     * @return the type or null
     */
    public static QName getXSIType(Element e) {
        if(hasXSIType(e)){
            Attr attribute = e.getAttributeNodeNS(XMLConstants.XSI_NS, "type");
            String attributeValue = attribute.getTextContent().trim();
            StringTokenizer tokenizer = new StringTokenizer(attributeValue, ":");
            String prefix = null;
            String localPart;
            if(tokenizer.countTokens() > 1) {
                prefix = tokenizer.nextToken();
                localPart = tokenizer.nextToken();
            }else{
               localPart = tokenizer.nextToken(); 
            }
            
            return new QName(e.lookupNamespaceURI(prefix), localPart, prefix);
        }
        
        return null;
    }
    
    /**
     * Gets the QName for the given element.
     * 
     * @param e the element
     * 
     * @return the QName for the element or null if the element was null
     */
    public static QName getElementQName(Element e) {
        if(e != null) {
            return new QName(e.getNamespaceURI(), e.getLocalName(), e.getPrefix());
        }
        
        return null;
    }
    
    /**
     * Appends the child Element to the parent Element, adopting the child Element into the parent's Document if needed.
     * 
     * @param parentElement the parent Element
     * @param childElement the child Element
     */
    public static void appendChildElement(Element parentElement, Element childElement) {
        Document parentDocument = parentElement.getOwnerDocument();
        if(!(childElement.getOwnerDocument().equals(parentDocument))) {
            parentDocument.adoptNode(childElement);
        }
        
        parentElement.appendChild(childElement);
    }
    
    /**
     * Verifies that the XML parser used by the JVM supports DOM level 3 and JAXP 1.3.
     */
    public static void verifyUsableXmlParser() {
        try {
            Class.forName("javax.xml.validation.SchemaFactory");
            Element.class.getDeclaredMethod("setIdAttributeNS", new Class[]{String.class, String.class,
                    java.lang.Boolean.TYPE});
        } catch (NoSuchMethodException e) {
            throw new FactoryConfigurationError("OpenSAML requires an xml parser that supports DOM3 calls. "
                    + "Sun JAXP 1.3 has been included with this release and is strongly recommended. "
                    + "If you are using Java 1.4, make sure that you have enabled the Endorsed "
                    + "Standards Override Mechanism for this parser "
                    + "(see http://java.sun.com/j2se/1.4.2/docs/guide/standards/ for details).");
        } catch (ClassNotFoundException e) {
            throw new FactoryConfigurationError("OpenSAML requires an xml parser that supports JAXP 1.3. "
                    + "Sun JAXP 1.3 has been included with this release and is strongly recommended. "
                    + "If you are using Java 1.4, make sure that you have enabled the Endorsed "
                    + "Standards Override Mechanism for this parser "
                    + "(see http://java.sun.com/j2se/1.4.2/docs/guide/standards/ for details).");
        }
    }
}
