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

package org.opensaml.saml1.core;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;

/**
 * Interface to define how a <code> AuthorityBinding  <\code> element behaves
 */
public interface AuthorityBinding extends SAMLObject {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "AuthorityBinding";
    
    /** Name for the AuthorityKind attribute  */
    public final static String AUTHORITYKIND_ATTRIB_NAME = "AuthorityKind";
    
    /** Name for the Location attribute  */
    public final static String LOCATION_ATTRIB_NAME = "Location";

    /** Name for the Binding attribute  */
    public final static String BINDING_ATTRIB_NAME = "Binding";

    /** Getter for AuthorityKind */
    public QName getAuthorityKind();

    /** Setter for AuthorityKind */
    public void setAuthorityKind(QName authorityKind);
    
    /** Getter for Location */
    public String getLocation();

    /** Setter for Location */
    public void setLocation(String location);
    
    /** Getter for Binding */
    public String getBinding();

    /** Setter for Binding */
    public void setBinding(String binding);
    


}
