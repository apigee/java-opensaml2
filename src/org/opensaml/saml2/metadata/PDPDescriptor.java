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

/**
 * SAML 2.0 Metadata PDPDescriptor
 */
public interface PDPDescriptor extends RoleDescriptor {
    
    /** Local name, no namespace */
    public final static String LOCAL_NAME = "PDPDescriptor";

    /**
     * Gets an list of authz services for this service.
     * 
     * @return list of authz services for this service
     */
	public List<AuthzService> getAuthzServices();
    
    /**
     * Gets the list of assertion ID request services for this PDP.
     * 
     * @return list of assertion ID request services for this PDP
     */
    public List<AssertionIDRequestService> getAssertionIDRequestServices();
    
    /**
     * Gets the list of NameID formats this service supports.
     * 
     * @return NameID formats this service supports
     */
    public List<NameIDFormat> getNameIDFormats();
}