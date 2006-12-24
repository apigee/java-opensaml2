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

package org.opensaml.security;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import javolution.util.FastList;

import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.security.KeyInfoSource;
import org.opensaml.xml.security.UsageType;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A {@link KeyInfoSource} the extract keying information from an entity's role descriptor with in a SAML 2 metadata
 * document.
 */
public class MetadataKeyInfoSource implements KeyInfoSource {

    /** Provider of metadata. */
    private MetadataProvider metadataProvider;

    /** ID of the entity to get the metadata for. */
    private String entityId;

    /** Role the entity is acting in. */
    private QName entityRole;

    /** Protocol the entity is using. */
    private String entityProtocol;

    /** Usage type of the key info to return. */
    private List<UsageType> keyUsages;

    /**
     * Constructor.
     *
     * @param keyUsageTypes types of key information to return
     * @param metadata source of metadata information
     * @param id entity ID whose metadata will be retrieved
     * @param role role the entity is acting
     */
    public MetadataKeyInfoSource(List<UsageType> keyUsageTypes, MetadataProvider metadata, String id, QName role) {
        keyUsages = new FastList<UsageType>();
        keyUsages.addAll(keyUsageTypes);
        metadataProvider = metadata;
        entityId = id;
        entityRole = role;
    }

    /**
     * Constructor.
     *
     * @param keyUsageTypes types of key information to return
     * @param metadata source of metadata information
     * @param id entity ID whose metadata will be retrieved
     * @param role role the entity is acting
     * @param protocol protocol being used by the entity
     */
    public MetadataKeyInfoSource(List<UsageType> keyUsageTypes, MetadataProvider metadata, String id, QName role,
            String protocol) {
        keyUsages = new FastList<UsageType>();
        keyUsages.addAll(keyUsageTypes);
        entityId = id;
        entityRole = role;
        entityProtocol = protocol;
    }

    /** {@inheritDoc} */
    public String getName() {
        return entityId;
    }

    /** {@inheritDoc} */
    public Iterator<KeyInfo> iterator() {
        List<RoleDescriptor> roleDescriptors;

        try {
            if (!DatatypeHelper.isEmpty(entityProtocol)) {
                roleDescriptors = new FastList<RoleDescriptor>();
                roleDescriptors.add(metadataProvider.getRole(entityId, entityRole, entityProtocol));
            } else {
                roleDescriptors = metadataProvider.getRole(entityId, entityRole);
            }
        } catch (MetadataProviderException e) {
            return null;
        }

        List<KeyInfo> keyInfos = new FastList<KeyInfo>();
        List<KeyDescriptor> keyDescriptors;
        for (RoleDescriptor roleDescriptor : roleDescriptors) {
            keyDescriptors = roleDescriptor.getKeyDescriptors();
            if (keyDescriptors != null) {
                for (KeyDescriptor keyDescriptor : keyDescriptors) {
                    if (keyUsages.contains(keyDescriptor.getUse())) {
                        keyInfos.add(keyDescriptor.getKeyInfo());
                    }
                }
            }
        }

        return keyInfos.iterator();
    }
}