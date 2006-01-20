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

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.SignableXMLObject;

/**
 * SAML 2.0 Metadata EntitiesDescriptor.
 * 
 * @author Chad La Joie
 */
public interface EntitiesDescriptor extends SAMLObject, SignableXMLObject, TimeBoundSAMLObject, CacheableSAMLObject{
    
	/** Element name, no namespace */
	public final static String LOCAL_NAME = "EntitiesDescriptor";
	
	/** "Name" attribute name */
	public final static String NAME_ATTRIB_NAME = "Name";
	
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
     * Gets a list of child {@link EntitiesDescriptor}s.
     * 
     * @return list of descriptors
     */
    public List<EntitiesDescriptor> getEntitiesDescriptors();

    /**
     * Gets a list of child {@link EntityDescriptor}s.
     * 
     * @return list of child descriptors
     */
	public List<EntityDescriptor> getEntityDescriptors();
    
    /**
     * Gets the {@link EntitiesDescriptor} and {@link EntityDescriptor}s as a single bag in the 
     * order that they were added to this descriptor.
     * 
     * @return the {@link EntitiesDescriptor} and {@link EntityDescriptor}s as a single bag in the 
     * order that they were added to this descriptor
     */
    public List<SAMLObject> getOrderedChildDescriptors();
}