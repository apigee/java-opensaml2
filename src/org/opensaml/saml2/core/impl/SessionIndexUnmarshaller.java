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

/**
 * 
 */
package org.opensaml.saml2.core.impl;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.SessionIndex;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.SessionIndex}
 * objects.
 */
public class SessionIndexUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     *
     */
    public SessionIndexUnmarshaller() {
        super(SAMLConstants.SAML20P_NS, SessionIndex.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#unmarshallElementContent(org.opensaml.common.SAMLObject, java.lang.String)
     */
    protected void unmarshallElementContent(SAMLObject samlObject, String elementContent) {
        SessionIndex si = (SessionIndex) samlObject;
        
        si.setSessionIndex(elementContent);
    }

}
