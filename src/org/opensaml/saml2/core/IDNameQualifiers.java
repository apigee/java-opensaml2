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

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Core IDNameQualifiers
 */
public interface IDNameQualifiers extends SAMLObject {

    /** Local Name of IDNameQualifiers */
    public final static String LOCAL_NAME = "IDNameQualifiers";

    /** Local Name of the NameQualifier attribute */
    public final static String NAME_QUALIFIER_ATTRIB_NAME = "NameQualifier";

    /** Local Name of the SPNameQualifier attribute */
    public final static String SPNAME_QUALIFIER_ATTRIB_NAME = "SPNameQualifier";

    /**
     * Gets the Name Qualifier.
     * 
     * @return IDNameQualifiers NameQualifier
     */
    public String getNameQualifier();

    /**
     * Sets the Name Qualifier.
     * 
     * @param newNameQualifier the Name Qualifier
     */
    public void setNameQualifier(String newNameQualifier);

    /**
     * Gets the SP Name Qualifier.
     * 
     * @return IDNameQualifiers SPNameQualifier
     */
    public String getSPNameQualifier();

    /**
     * Sets the SP Name Qualifier.
     * 
     * @param newSPNameQualifier the SP Name Qualifier
     */
    public void setSPNameQualifier(String newSPNameQualifier);
}