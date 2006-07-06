/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.metadata.provider;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import javolution.util.FastList;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;

/**
 * An abstract, base, implementation of a metadata provider.
 */
public abstract class AbstractMetadataProvider implements MetadataProvider {
    
    /** Whether metadata is required to be valid */
    private boolean requireValidMetadata;
    
    /** Filter applied to all metadata */
    private MetadataFilter mdFilter;

    /** {@inheritDoc} */
    public abstract EntityDescriptor getEntityDescriptor(String entityID);

    /** {@inheritDoc} */
    public List<RoleDescriptor> getRole(String entityID, QName roleName) {
        EntityDescriptor entity = getEntityDescriptor(entityID);
        return entity.getRoleDescriptors(roleName);
    }

    /** {@inheritDoc} */
    public List<RoleDescriptor> getRole(String entityID, QName roleName, String supportedProtocol) { 
        Iterator<RoleDescriptor> roles = getRole(entityID, roleName).iterator();
        RoleDescriptor role;
        FastList<RoleDescriptor> protocolSupportingRoles = new FastList<RoleDescriptor>();
        while(roles.hasNext()){
            role = roles.next();
            if(role.getSupportedProtocols().contains(supportedProtocol)){
                protocolSupportingRoles.add(role);
            }
        }
        
        return protocolSupportingRoles;
    }

    /** {@inheritDoc} */
    public boolean requireValidMetadata() {
        return requireValidMetadata;
    }
 
    /** {@inheritDoc} */
    public void setRequireValidMetadata(boolean requireValidMetadata) {
        this.requireValidMetadata = requireValidMetadata;
    }

    /** {@inheritDoc} */
    public MetadataFilter getMetadataFilter() {
        return mdFilter;
    }

    /** {@inheritDoc} */
    public void setMetadataFilter(MetadataFilter newFilter) {
        mdFilter = newFilter;
    }
}