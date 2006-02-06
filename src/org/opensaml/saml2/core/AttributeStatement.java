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

package org.opensaml.saml2.core;

import java.util.List;

/**
 * SAML 2.0 Core AttributeStatement
 */
public interface AttributeStatement extends Statement {
    
    /** Element local name */
    public final static String LOCAL_NAME = "AttributeStatement";

    /**
     * Gets the attribtues expressed in this statement.
     * 
     * @return the attribtues expressed in this statement
     */
    public List<Attribute> getAttributes();

    // TODO EncryptedAttribute
}