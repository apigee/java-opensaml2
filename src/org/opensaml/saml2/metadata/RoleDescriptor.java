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
import java.util.Collection;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.xml.signature.SignableXMLObject;

/**
 * SAML 2.0 Metadata RoleDescriptor
 */
public interface RoleDescriptor extends SAMLObject, SignableXMLObject, TimeBoundSAMLObject, CacheableSAMLObject {

    /** Element name, no namespace */
    public final static String LOCAL_NAME = "RoleDescriptor";

    /** "ID" attribute's local name */
    public final static String ID_ATTRIB_NAME = "ID";

    /** "protocolEnumeration" attribute's local name */
    public final static String PROTOCOL_ENUMERATION_ATTRIB_NAME = "protocolSupportEnumeration";

    /** "errorURL" attribute's local name */
    public final static String ERROR_URL_ATTRIB_NAME = "errorURL";

    /**
     * Gets the ID of this role descriptor.
     * 
     * @return the ID of this role descriptor
     */
    public String getID();

    /**
     * Sets the ID of this role descriptor.
     * 
     * @param newID the ID of this role descriptor
     */
    public void setID(String newID);

    /**
     * Gets an immutable list of protocol {@link URI}s supported by this role.
     * 
     * @return list of protocol {@link URI}s supported by this role
     */
    public List<String> getSupportedProtocols();

    /**
     * Chckes to see if the given protocol is supported by this role.
     * 
     * @param protocol the protocol
     * 
     * @return true if the protocol is supported, false if not
     */
    public boolean isSupportedProtocol(String protocol);

    /**
     * Adds a protocol to the list of supported protocols for this role.
     * 
     * @param protocol the protocol
     */
    public void addSupportedProtocol(String protocol);

    /**
     * Removes a protocol to the list of supported protocols for this role.
     * 
     * @param protocol the protocol
     */
    public void removeSupportedProtocol(String protocol);

    /**
     * Removes a list of protocols to the list of supported protocols for this role.
     * 
     * @param protocols the protocol
     */
    public void removeSupportedProtocols(Collection<String> protocols);

    /**
     * Removes all the supported protocols from this role.
     * 
     */
    public void removeAllSupportedProtocols();

    /**
     * Gets the URI users should be sent to in the event of an error.
     * 
     * @return the URI users should be sent to in the event of an error
     */
    public String getErrorURL();

    /**
     * Sets the URI users should be sent to in the event of an error.
     * 
     * @param errorURL the URI users should be sent to in the event of an error
     */
    public void setErrorURL(String errorURL);

    /**
     * Gets the Extensions child of this object.
     * 
     * @return the Extensions child of this object
     */
    public Extensions getExtensions();

    /**
     * Sets the Extensions child of this object.
     * 
     * @param extensions the Extensions child of this object
     * 
     * @throws IllegalArgumentException thrown if the given extensions Object is already a child of another SAMLObject
     */
    public void setExtensions(Extensions extensions) throws IllegalArgumentException;

    /**
     * Gets the key descriptors for this role.
     * 
     * @return the key descriptors for this role
     */
    public List<KeyDescriptor> getKeyDescriptors();

    /**
     * Gets the organization responsible for this role.
     * 
     * @return the organization responsible for this role
     */
    public Organization getOrganization();

    /**
     * Sets the organization responsible for this role.
     * 
     * @param organization the organization responsible for this role
     * 
     * @throws IllegalArgumentException thrown if the given organization is owned by another element
     */
    public void setOrganization(Organization organization) throws IllegalArgumentException;

    /**
     * Gets an immutable list of {@link ContactPerson}s for this role.
     * 
     * @return list of {@link ContactPerson}s for this role
     */
    public List<ContactPerson> getContactPersons();
}
