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

import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableObject;
import org.opensaml.common.ValidatingObject;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.ExtensionsExtensibleSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;

/**
 * SAML 2.0 Metadata EntitiesDescriptor.
 * 
 * @author Chad La Joie
 */
public interface EntitiesDescriptor extends SignableObject, TimeBoundSAMLObject, CacheableSAMLObject, ExtensionsExtensibleSAMLObject, ValidatingObject{
    
	/** Element name, no namespace */
	public final static String LOCAL_NAME = "EntitiesDescriptor";
	
	/** QName for this element */
	public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);
	
	/** "Name" attribute name */
	public final static String NAME_ATTRIB_NAME = "Name";
	
	/** "Name" attribute's QName */
	public final static QName NAME_ATTRIB_QNAME = new QName(XMLConstants.SAML20MD_NS, NAME_ATTRIB_NAME, XMLConstants.SAML20MD_PREFIX);
	
	/**
	 * Gets the name of this entity group.
	 * 
	 * @return the name of this entity group
	 */
	public String getName();
	
	/**
	 * Sets the name of this entity group.
	 * 
	 * @param name the name of this entity group
	 */
	public void setName(String name);
	
	/**
     * Gets an immutable list of child {@link EntitiesDescriptor}s.
     * 
     * @return list of descriptors
     */
    public Set<EntitiesDescriptor> getEntitiesDescriptors();
    
    /**
     * Adds the given descriptor to the list of child {@link EntitiesDescriptor}s for this descriptor.
     * 
     * @param descriptor the child descriptor
     * 
     * @throws IllegalAddException thrown if the given descriptor is already a descendant of another descriptor
     */
    public void addEntitiesDescriptor(EntitiesDescriptor descriptor) throws IllegalAddException;
    
    /**
     * Removes the given descriptor from the list of child {@link EntitiesDescriptor}s for this descriptor.
     * 
     * @param descriptor the child descriptor
     */
    public void removeEntitiesDescriptor(EntitiesDescriptor descriptor);
    
    /**
     * Removes the given list of {@link EntitiesDescriptor}s for this descriptor.
     * 
     * @param descriptors the child descriptors
     */
    public void removeEntitiesDescriptors(Set<EntitiesDescriptor> descriptors);
    
    /**
     * Removes all the {@link EntitiesDescriptor}s from this descriptor.
     */
    public void removeAllEntitiesDescriptors();

    /**
     * Gets an immutable list of child {@link EntityDescriptor}s.
     * 
     * @return list of child descriptors
     */
	public Set<EntityDescriptor> getEntityDescriptors();
    
    /**
     * Adds the given descriptor to the list of child {@link EntityDescriptor}s for this descriptor.
     * 
     * @param descriptor the child descriptors
     * 
     * @throws IllegalAddException thrown if the given descriptor is already a descendant of another descriptor
     */
    public void addEntityDescriptor(EntityDescriptor descriptor) throws IllegalAddException;
    
    /**
     * Removes the given descriptor to the list of child {@link EntityDescriptor}s for this descriptor.
     * 
     * @param descriptor the child descriptors
     */
    public void removeEntityDescriptor(EntityDescriptor descriptor);
    
    /**
     * Adds the list of {@link EntityDescriptor}s from this descriptor.
     * 
     * @param descriptors the child descriptors
     */
    public void removeEntityDescriptors(Set<EntityDescriptor> descriptors);
    
    /**
     * Removes all the child {@link EntityDescriptor}s from this descriptor.
     */
    public void removeAllEntityDescriptors();
    
    /**
     * Gets the {@link EntitiesDescriptor} and {@link EntityDescriptor}s as a single bag in the 
     * order that they were added to this descriptor.
     * 
     * @return the {@link EntitiesDescriptor} and {@link EntityDescriptor}s as a single bag in the 
     * order that they were added to this descriptor
     */
    public Set<SAMLObject> getOrderedChildDescriptors();
}