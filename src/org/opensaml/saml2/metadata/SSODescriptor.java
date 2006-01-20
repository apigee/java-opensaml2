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
 * SAML 2.0 Metadata SSODescriptor
 */
public interface SSODescriptor extends RoleDescriptor {

    /** Element name, no namespace */
    public final static String LOCAL_NAME = "SSODescriptor";
    
    /**
     * Gets a list of artifact resolution services for this service.
     * 
     * @return list of artifact resolution services for this service
     */
	public List<ArtifactResolutionService> getArtifactResolutionServices();

    /**
     * Gets a list of single logout services for this service.
     * 
     * @return list of single logout services for this service
     */
	public List<SingleLogoutService> getSingleLogoutServices();

    /**
     * Gets a list of manage NameId services for this service.
     * 
     * @return list of manage NameId services for this service
     */
	public List<ManageNameIDService> getManageNameIDServices();
    
    /**
     * Gets the list of NameID formats this service supports.
     * 
     * @return NameID formats this service supports
     */
    public List<NameIDFormat> getNameIDFormats();
}
