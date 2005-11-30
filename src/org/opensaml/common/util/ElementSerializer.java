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

package org.opensaml.common.util;

import java.io.StringWriter;
import java.security.SignatureException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.opensaml.common.SAMLException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallerFactory;
import org.opensaml.common.io.MarshallingException;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * A utility class for writting out SAML and DOM elements.
 */
public class ElementSerializer {

    /** JAXP transformer factory */
    private static TransformerFactory transformerFactory;
    
    /** DOM 3 LS seralizer */
    private static LSSerializer lsSerializer;
    
    /**
     * Writes a given SAMLElement out as a string.
     * 
     * @param element the SAML element
     * @param prettyPrint whether the resulting XML should be pretty printed
     * 
     * @return the SAMLElement as a String containing the XML representation of the element
     * @throws MarshallingException thrown if the SAML element, or any of it's children, can not be marshalled to a DOM
     *             element
     * @throws SignatureException thrown if the SAML element, or any of it's children, can not be signed as instructured
     */
    public static String transformSerialize(SAMLObject element, boolean prettyPrint) throws SerializationException {
        Marshaller elementMarshaller = MarshallerFactory.getInstance().getMarshaller(element.getElementQName());
        try {
            return transformSerialize(elementMarshaller.marshall(element), prettyPrint);
        } catch (SAMLException e) {
            throw new SerializationException(e);
        }
    }

    /**
     * Writes a given DOM element out as a string.
     * 
     * @param element the DOM element
     * @param prettyPrint whether the resulting XML should be pretty printed
     * 
     * @return the DOM element as a String containing the XML representation of the element
     */
    public static String transformSerialize(Element element, boolean prettyPrint) throws SerializationException{
        if(transformerFactory == null) {
            transformerFactory = TransformerFactory.newInstance();
        }
        Transformer transformer = null;
        DOMSource source = new DOMSource(element);
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            if (prettyPrint) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            } else {
                transformer.setOutputProperty(OutputKeys.INDENT, "no");
            }
        } catch (TransformerConfigurationException e) {
            throw new SerializationException(e);
        }
        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        try {
            transformer.transform(source, result);
        } catch (TransformerException e1) {
            throw new SerializationException(e1);
        }
        return stringWriter.toString();
    }

    public static String lsSerialize(SAMLObject element) throws SerializationException {
        Marshaller elementMarshaller = MarshallerFactory.getInstance().getMarshaller(element.getElementQName());
        try {
            return lsSerialize(elementMarshaller.marshall(element));
        } catch (SAMLException e) {
            throw new SerializationException(e);
        }
    }

    public static String lsSerialize(Element element) throws SerializationException {
        try {
            if(lsSerializer == null) {
                DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
                DOMImplementationLS domImplLS = (DOMImplementationLS) registry.getDOMImplementation("LS 3.0");
                lsSerializer = domImplLS.createLSSerializer();
            }

            return lsSerializer.writeToString(element);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }
}
