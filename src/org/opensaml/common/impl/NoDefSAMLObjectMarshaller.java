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

package org.opensaml.common.impl;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallingException;
import org.opensaml.common.io.impl.AbstractMarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Marshaller for {@link org.opensaml.common.impl.NoDefSAMLObject} objects.
 *
 */
public class NoDefSAMLObjectMarshaller implements Marshaller {

    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(AbstractMarshaller.class);
    
    /*
     * @see org.opensaml.common.io.Marshaller(SAMLObject)
     */
    public Element marshall(SAMLObject samlElement) throws MarshallingException {
        if(samlElement instanceof DOMCachingSAMLObject) {
            return ((DOMCachingSAMLObject)samlElement).getDOM();
        }else {
            log.error("Marshaller only operates on SAMLObjects of type " + DOMCachingSAMLObject.class + ", not " + samlElement.getClass());
            throw new MarshallingException("Marshaller only operates on SAMLObjects of type " + DOMCachingSAMLObject.class + ", not " + samlElement.getClass());
        }
        
    }

    /*
     * @see org.opensaml.common.io.Marshaller(SAMLObject, Document)
     */
    public Element marshall(SAMLObject samlElement, Document document) throws MarshallingException {
        return marshall(samlElement);
    }

}
