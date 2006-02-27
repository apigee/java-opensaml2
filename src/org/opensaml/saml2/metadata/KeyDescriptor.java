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

import org.opensaml.common.SAMLObject;
import org.opensaml.xml.signature.KeyInfo;

public interface KeyDescriptor extends SAMLObject {

    /** Element name, no namespace */
    public final static String LOCAL_NAME = "KeyDescriptor";

    /** "use" attribute's local name */
    public final static String USE_ATTRIB_NAME = "use";

    /**
     * Gets the use of this key.
     * 
     * @return the use of this key
     */
    public KeyUseType getUse();

    /**
     * Sets the use of this key.
     * 
     * @param newType the use of this key
     */
    public void setUse(KeyUseType newType);

    /**
     * Gets information about the key, including the key itself.
     * 
     * @return information about the key, including the key itself
     */
    public KeyInfo getKeyInfo();

    /**
     * Sets information about the key, including the key itself.
     * 
     * @param newKeyInfo information about the key, including the key itself
     */
    public void setKeyInfo(KeyInfo newKeyInfo);

    // TOOD Add encryption method once encryption interfaces are available
}