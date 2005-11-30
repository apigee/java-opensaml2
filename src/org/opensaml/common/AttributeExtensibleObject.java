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
package org.opensaml.common;

import javax.xml.namespace.QName;

/**
 * An interface for those SAML elements that allows for additional attributes.
 * This interface only allows access to those attributes which are an extension 
 * to the SAML metadata, not those explicitly defined in the metadta.
 */
public interface AttributeExtensibleObject {

    /**
     * Gets the extended attribute with the given name.
     * 
     * @param attributeName the attribute name
     * 
     * @return the attribute value
     */
    public String getExtensionAttribute(QName attributeName);
    
    /**
     * Sets the extended attribute with the given name.  If the 
     * attribute does not yet exist it is created, if it does exist 
     * it is overwritten.
     * 
     * @param attributeName the attribute name
     * 
     * @param value the attribute value
     */
    public void setExtensionAttribute(QName attributeName, String value);
}
