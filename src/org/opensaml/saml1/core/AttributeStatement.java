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

/**
 * This interface defines how the object representing a SAML 1 <code> AttributeStatement </code> element behaves.
 */
public interface AttributeStatement extends SAMLObject, SubjectStatement {

    /** Element name, no namespace. */

    public final static String LOCAL_NAME = "AttributeStatement";

    /** Get all the subsiduary Attribute elements */
    public List <Attribute> getAttributes();

    /** Add an Attribute */
    public void addAttribute(Attribute attribute);
    
    /** Remove an attribute */
    public void removeAttribute(Attribute attribute);
    
    /** Remove several attributes */
    public void removeAttributes(Collection<Attribute> attributes);
    
    /** Remove all attributes */
    public void removeAllAttributes();
    
}
