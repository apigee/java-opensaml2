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

package org.opensaml.saml2.metadata;

import org.opensaml.saml2.core.Attribute;

/**
 * SAML 2.0 Metadata RequestedAttribute
 *
 */
public interface RequestedAttribute extends Attribute {

    /** Local name, no namespace */
    public final static String LOCAL_NAME = "RequestedAttribute";
    
    /** "isRequired" attribute's local name */
    public final static String IS_REQUIRED_ATTRIB_NAME = "isRequired";
    
    /**
     * Checks to see if this requested attribute is also required.
     * 
     * @return true if this attribute is required
     */
    public boolean isRequired();
    
    /**
     * Sets if this requested attribute is also required.
     * 
     * @param isRequired true if this attribute is required
     */
    public void setIsRequired(boolean isRequired);
}
