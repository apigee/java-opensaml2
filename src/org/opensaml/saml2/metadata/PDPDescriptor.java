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

import java.util.Collection;

import javax.xml.namespace.QName;

import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.common.util.xml.XMLConstants;

/**
 * SAML 2.0 Metadata PDPDescriptor
 */
public interface PDPDescriptor extends RoleDescriptor, AssertionIDRequestDescriptorComp, NameIDFormatDescriptorComp {
    
    /** Local name, no namespace */
    public final static String LOCAL_NAME = "PDPDescriptor";
    
    /** QName for element */
    public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);

    /**
     * Gets an immutable list of authz service {@link Endpoint}s for this service.
     * 
     * @return list of authz service {@link Endpoint}s for this service
     */
	public UnmodifiableOrderedSet<Endpoint> getAuthzServices();
    
    /**
     * Adds an authz service {@link Endpoint} for this service.
     * 
     * @param service the service endpoint
     */
	public void addAuthzService(Endpoint service);
    
    /**
     * Removes an authz service {@link Endpoint} for this service.
     * 
     * @param service the service endpoint
     */
    public void removeAuthzService(Endpoint service);
    
    /**
     * Removes a list of authz service {@link Endpoint}s for this service.
     * 
     * @param services the service endpoint
     */
    public void removeAuthzServices(Collection<Endpoint> services);
    
    /**
     * Removes all the authz service endpoints from this service.
     *
     */
    public void removeAllAuthzServices();
}
