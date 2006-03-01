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

/**
 * Type safe enumeration of key types for use in {@link org.opensaml.saml2.metadata.KeyDescriptor}.
 */
public class KeyUseType {

    /** Encryption key type */
    public final static KeyUseType ENCRYPTION = new KeyUseType("encryption");
    
    /** Signing key type */
    public final static KeyUseType SIGNING = new KeyUseType("signing");
    
    /** The key type */
    private String type;
    
    /**
     * Constructor
     * 
     * @param type the contact type
     */
    private KeyUseType(String type) {
        this.type = type;
    }

    /**
     * Gets the contact type as a string.
     * 
     * @return the contact type
     */
    public String toString() {
        return type;
    }
}