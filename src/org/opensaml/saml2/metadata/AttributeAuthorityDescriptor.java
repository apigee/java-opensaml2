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


import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.core.Attribute;

/**
 * SAML 2.0 Metadata AttributeAuthorityDescriptor
 */
public interface AttributeAuthorityDescriptor extends SAMLObject, RoleDescriptor {
	
	/** Element name, no namespace */
	public final static String LOCAL_NAME = "AttributeAuthorityDescriptor";

    /**
     * Gets a list of attribute service {@link Endpoint}s for this authority.
     * 
     * @return list of attributes services
     */
    public List<AttributeService> getAttributeServices();
    
    /**
     * Gets a list of Assertion ID request services.
     * 
     * @return list of Assertion ID request services
     */
    public List<AssertionIDRequestService> getAssertionIDRequestServices();
    
    /**
     * Gets a list of NameID formats supported by this authority.
     * 
     * @return list of NameID formats supported by this authority
     */
    public List<NameIDFormat> getNameIDFormats();
    
    /**
     * Gets a list of Attribute profiles supported by this authority.
     * 
     * @return list of Attribute profiles supported by this authority
     */
    public List<AttributeProfile> getAttributeProfiles();
    
    /**
     * Gets the list of attribute available from this authority.
     * 
     * @return list of attribute available from this authority
     */
    public List<Attribute> getAttributes();
}