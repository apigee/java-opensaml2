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


import java.net.URI;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.xml.XMLConstants;

/**
 * SAML 2.0 Metadata AttributeAuthorityDescriptor
 */
public interface AttributeAuthorityDescriptor extends SAMLObject, RoleDescriptor, AssertionIDRequestDescriptorComp, NameIDFormatDescriptorComp, AttributeProfileDescriptorComp, AttributeDescriptorComp {
	
	/** Element name, no namespace */
	public final static String LOCAL_NAME = "AttributeAuthorityDescriptor";
	
	/** QName for this element */
	public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);

    /**
     * Gets an immutable list of attribute service {@link Endpoint}s for this authority.
     * 
     * @return list of attributes services
     */
    public Set /*<Endpoint>*/getAttributeServices();

    /**
     * Gets an immutable list of attribute request {@link Endpoint}s that support the given binding.
     * 
     * @param bindingURI the binding URI
     * 
     * @return list of {@link Endpoint}s that support the given binding
     */
    public Set /*<Endpoint>*/getAttributeServicesByBinding(URI bindingURI);

    /**
     * Adds an attribute service {@link Endpoint} for this authority.
     * 
     * @param service the attribute service
     */
    public void addAttributeService(Endpoint service);

    /**
     * Adds a list of attribute service {@link Endpoint}s for this authority.
     * 
     * @param services the list of attribute service
     */
    public void addAttributeServices(Set /*<Endpoint>*/services);

    /**
     * Removes an attribute service {@link Endpoint} for this authority.
     * 
     * @param service the attribute service
     */
    public void removeAttributeService(Endpoint service);

    /**
     * Removes a list of attribute service {@link Endpoint} for this authority.
     * 
     * @param services the list of attribute service
     */
    public void removeAttributeServices(Set /*<Endpoint>*/services);

    /**
     * Removes all the attribute service {@link Endpoint}s for this authority.
     */
    public void removeAllAttributeServices();
}