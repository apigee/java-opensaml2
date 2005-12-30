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

package org.opensaml.xml;

/**
 * Data structure for representing XML namespace attributes
 */
public class Namespace {

    /** URI of the namespace */
    private String namespaceURI;
    
    /** Prefix of the namespace */
    private String namespacePrefix;
    
    /**
     * Constructor
     */
    public Namespace() {
        
    }
    
    /**
     * Constructor
     *
     * @param uri the URI of the namespace
     * @param prefix the prefix of the namespace
     */
    public Namespace(String uri, String prefix) {
        namespaceURI = uri;
        namespacePrefix = prefix;
    }

    /**
     * Gets the prefix of the namespace.
     * 
     * @return the prefix of the namespace
     */
    public String getNamespacePrefix() {
        return namespacePrefix;
    }

    /**
     * Sets the prefix of the namespace.
     * 
     * @param namespacePrefix the prefix of the namespace
     */
    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }

    /**
     * Gets the URI of the namespace.
     * 
     * @return the URI of the namespace
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

    /**
     * Sets the URI of the namespace.
     * 
     * @param namespaceURI the URI of the namespace
     */
    public void setNamespaceURI(String namespaceURI) {
        this.namespaceURI = namespaceURI;
    }
    
    /**
     * Checks if the given object is the same as this Namespace.  This is true if:
     * <ul>
     *    <li>The given object is of type {@link Namespace}</li>
     *    <li>The given object's namespace URI is the same as this object's namespace URI</li>
     *    <li>The given object's namespace prefix is the same as this object's namespace prefix</li>
     * </ul>
     */
    public boolean equals(Object obj) {
        if(obj instanceof Namespace) {
            Namespace otherNamespace = (Namespace)obj;
            if(otherNamespace.getNamespaceURI().equals(getNamespaceURI())) {
                return otherNamespace.getNamespacePrefix().equals(getNamespacePrefix());
            }
        }
        
        return false;
    }
}
