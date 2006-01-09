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

package org.opensaml.saml1.core;


import java.util.Collection;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.xml.IllegalAddException;

/**
 * This interface defines how the object representing a SAML 1 <code> Attribute </code> element behaves.
 */
public interface Attribute extends SAMLObject, SubjectStatement {

    /** Element name, no namespace. */

    public final static String LOCAL_NAME = "Attribute";

    /** Get all the subsiduary AttributeValue elements */
    
    public List <AttributeValue> getAttributeValues();
    
    /** Add a single AttributeValue element 
     * @throws IllegalAddException */

    public void addAttributeValue(AttributeValue attributeValue) throws IllegalAddException;
    
    /** Remove a single AttributeValue element */
    
    public void removeAttributeValue(AttributeValue attributeValue);
    
    /** Remove several AttributeValue elements */

    public void removeAttributeValues(Collection<AttributeValue> attributeValues);
    
    /** Remove all AttributeValue elements */
    public void removeAllAttributeValues();
    
}
