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

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.xml.validation.AbstractValidatingDOMCachingXMLObject;

/**
 * An abstract implementation of SAMLObject.
 */
public abstract class AbstractSAMLObject extends AbstractValidatingDOMCachingXMLObject{

    /** SAML version of this object */
    private SAMLVersion version;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected AbstractSAMLObject(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getVersion()
     */
    public SAMLVersion getVersion() {
        return version;
    }

    /**
     * Sets the SAML version for this object.
     * 
     * @param version the SAML version for this object
     */
    protected void setSAMLVersion(SAMLVersion version) {
        this.version = version;
    }
    
    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    // TODO - stop just calling the parent
    protected DateTime prepareForAssignment(DateTime oldValue, DateTime newValue){
        return super.prepareForAssignment(oldValue, newValue);
    }
}