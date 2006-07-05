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

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.resolver.MetadataFilter;

public abstract class AbstractMetadataProvider implements MetadataProvider {

    public EntityDescriptor getEntityDescriptor(String entityID) {
        // TODO Auto-generated method stub
        return null;
    }

    public EntityDescriptor getEntityDescriptor(String entityID, boolean requireValidMetadata) {
        // TODO Auto-generated method stub
        return null;
    }

    public MetadataFilter getMetadataFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<RoleDescriptor> getRole(String entityID, QName roleName) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<RoleDescriptor> getRole(String entityID, QName roleName, boolean requireValidMetadata) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<RoleDescriptor> getRole(String entityID, QName roleName, String supportedProtocol) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<RoleDescriptor> getRole(String entityID, QName roleName, String supportedProtocol,
            boolean requireValidMetadata) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean requireValidMetadata() {
        // TODO Auto-generated method stub
        return false;
    }

    public void setMetadataFilter(MetadataFilter newFilter) {
        // TODO Auto-generated method stub

    }

    public void setRequireValidMetadata(boolean requireValidMetadata) {
        // TODO Auto-generated method stub

    }

}
