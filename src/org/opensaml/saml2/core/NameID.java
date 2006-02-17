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
 * SAML 2.0 Core NameID
 */
public interface NameID extends Identifier, SAMLObject {
    
    /** Element local name */
    public final static String LOCAL_NAME = "NameID";
    
    /** NameQualifier attribute name */
    public final static String NAME_QUALIFIER_ATTRIB_NAME = "NameQualifier";
    
    /** SPNameQualifier attribute name */
    public final static String SP_NAME_QUALIFIER_ATTRIB_NAME = "SPNameQualifier";
    
    /** Format attribute name */
    public final static String FORMAT_ATTRIB_NAME = "Format";
    
    /** SPProviderID attribute name*/
    public final static String SPPROVIDER_ID_ATTRIB_NAME = "SPProviderID";

    /**
     * Gets the NameQualifier value.
     * 
     * @return the NameQualifier value
     */
    public String getNameQualifier();

    /**
     * Sets the NameQualifier value.
     * 
     * @param newNameQualifier the NameQualifier value
     */
    public void setNameQualifier(String newNameQualifier);

    /**
     * Gets the SPNameQualifier value.
     * 
     * @return the SPNameQualifier value
     */
    public String getSPNameQualifier();

    /**
     * Sets the SPNameQualifier value.
     * 
     * @param newSPNameQualifier the SPNameQualifier value
     */
    public void setSPNameQualifier(String newSPNameQualifier);

    /**
     * Gets the format of the NameID.
     * 
     * @return the format of the NameID
     */
    public String getFormat();

    /**
     * Sets the format of the NameID.
     * 
     * @param newFormat the format of the NameID
     */
    public void setFormat(String newFormat);

    /**
     * Gets the SPProivderID of this NameID.
     * 
     * @return the SPProivderID of this NameID
     */
    public String getSPProviderID();

    /**
     * Sets the SPProivderID of this NameID.
     * 
     * @param newSPProviderID the SPProivderID of this NameID
     */
    public void setSPProviderID(String newSPProviderID);
}