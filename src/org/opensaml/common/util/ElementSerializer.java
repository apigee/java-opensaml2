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

import org.opensaml.common.SAMLException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * A utility class for writting out SAML and DOM elements.
 */
public class ElementSerializer {
    
    /** DOM 3 LS seralizer */
    private static LSSerializer lsSerializer;

    public static String serialize(SAMLObject element) throws SerializationException {
        Marshaller elementMarshaller = MarshallerFactory.getInstance().getMarshaller(element.getElementQName());
        try {
            return serialize(elementMarshaller.marshall(element));
        } catch (SAMLException e) {
            throw new SerializationException(e);
        }
    }

    public static String serialize(Element element) throws SerializationException {
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
