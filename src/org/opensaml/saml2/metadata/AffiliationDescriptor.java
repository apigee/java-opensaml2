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
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.SignableXMLObject;

/**
 * SAML 2.0 Metadata AffiliationDescriptorType
 */
public interface AffiliationDescriptor extends SAMLObject, SignableXMLObject, TimeBoundSAMLObject, CacheableSAMLObject, KeyDescriptorDescriptorComp{
	
	/** Element name, no namespace */
	public final static String LOCAL_NAME = "AffiliationDescriptor";
	
	/** "affiliationOwnerID" attribute's local name */
	public final static String OWNER_ID_ATTRIB_NAME = "affiliationOwnerID";

	/**
	 * Gets the ID of the owner of this affiliation.  The owner may, or may not, be a memeber of the affiliation.
	 * 
	 * @return the ID of the owner of this affiliation
	 */
	public String getOwnerID();

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
     * @throws IllegalAddException thrown if the given extensions Object is already a child of another SAMLObject 
     */
    public void setExtensions(Extensions extensions) throws IllegalAddException;
    
	/**
	 * Sets the ID of the owner of this affiliation.
	 * 
	 * @param ownerID the ID of the owner of this affiliation
	 */
	public void setOwnerID(String ownerID);

	/**
	 * Checks to see if a given entity is a member of this affiliation.
	 * 
	 * @param id the entity's id
	 * 
	 * @return true if the entity is a member, false if not
	 */
	public boolean isMember(String id);
	
    /**
     * Gets an immutable list of the members of this affiliation.
     * 
     * @return a list of affiliate members
     */
	public List<AffiliateMember> getMembers();
	
    /**
     * Adds a member to this affiliation.
     * 
     * @param member the member to add
     * 
     * @throws IllegalAddException thrown if the given member is already a child of another SAMLObject
     */
    public void addMember(AffiliateMember member) throws IllegalAddException;
	
	/**
	 * Removes the given member from this affiliation.
	 * 
	 * @param member the member to remove
	 */
    public void removeMember(AffiliateMember member);
    
	/**
	 * Removes the given list of member {@link URI}s from this affiliation.
	 * 
	 * @param members the list of members to be removed
	 */
    public void removeMemebers(Collection<AffiliateMember> members);
	
	/**
	 * Removes all the members from this Affiliation.
	 */
	public void removeAllMembers();
}