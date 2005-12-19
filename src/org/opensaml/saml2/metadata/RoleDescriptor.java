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

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableObject;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.ExtensionsExtensibleSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;

/**
 * SAML 2.0 Metadata RoleDescriptor
 */
public interface RoleDescriptor extends SAMLObject, SignableObject, TimeBoundSAMLObject, CacheableSAMLObject, ExtensionsExtensibleSAMLObject, KeyDescriptorDescriptorComp{
    
    /** Element name, no namespace */
    public final static String LOCAL_NAME = "RoleDescriptor";
    
    /** QName for this element */
    public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /** "protocolEnumeration" attribute's local name */
    public final static String PROTOCOL_ENUMERATION_ATTRIB_NAME = "protocolEnumeration";
    
    /** "protocolEnumeration" attribute's QName */
    public final static QName PROTOCOL_ENUMERATION_ATTRIB_QNAME = new QName(XMLConstants.SAML20MD_NS, PROTOCOL_ENUMERATION_ATTRIB_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /** "errorURL" attribute's local name */
    public final static String ERROR_URL_ATTRIB_NAME = "errorURL";
    
    /** "errorURL" attribute's QName */
    public final static QName ERROR_URL_ATTRIB_QNAME = new QName(XMLConstants.SAML20MD_NS, ERROR_URL_ATTRIB_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /**
     * Gets an immutable list of protocol {@link URI}s supported by this role.
     * 
     * @return list of protocol {@link URI}s supported by this role
     */
	public UnmodifiableOrderedSet<String> getSupportedProtocols();

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
     * @throws IllegalAddException thrown if the given organization is owned by another element
     */
    public void setOrganization(Organization organization) throws IllegalAddException;
    
    /**
     * Gets an immutable list of {@link ContactPerson}s for this role.
     * 
     * @return list of {@link ContactPerson}s for this role
     */
    public UnmodifiableOrderedSet<ContactPerson> getContactPersons();
    
    /**
     * Adds a contact person to the list of contact people for this role.
     * 
     * @param person the contact person
     * 
     * @throws IllegalAddException thrown if the given contact person is owned by another element
     */
    public void addContactPerson(ContactPerson person) throws IllegalAddException;
    
    /**
     * Removes a contact person from the list of contact people for this role.
     * 
     * @param person the contact person
     */
    public void removeContactPerson(ContactPerson person);
    
    /**
     * Removes a list of contact persons from the list of contact people for this role.
     * 
     * @param persons the list of contact persons
     */
    public void removeContactPersons(Collection<ContactPerson> persons);
    
    /**
     * Removes all the contact persons from this role.
     */
    public void removeAllContactPersons();
}
