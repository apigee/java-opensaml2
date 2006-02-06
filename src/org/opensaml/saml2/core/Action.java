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
 * SAML 2.0 Core Action
 */
public interface Action extends SAMLObject {
    
    /** Element local name */
    public final static String LOCAL_NAME = "Action";
    
    /** Name of the Namespace attribute */
    public final static String NAMEPSACE_ATTRIB_NAME = "Namespace";

    /**
     * Gets the namespace scope of the specified action.
     * 
     * @return the namespace scope of the specified action
     */
    public String getNamespace();

    /**
     * Sets the namespace scope of the specified action.
     * 
     * @param newNamespace the namespace scope of the specified action
     */
    public void setNamespace(String newNamespace);

    /**
     * Gets the URI of the action to be performed.
     * 
     * @return the URI of the action to be performed
     */
    public String getAction();

    /**
     * Sets the URI of the action to be performed.
     * 
     * @param newAction the URI of the action to be performed
     */
    public void setAction(String newAction);
}