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

/**
 * 
 */

package org.opensaml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml2.metadata.ManageNameIDService;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.SSODescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.SSODescriptor}.
 */
public abstract class SSODescriptorImpl extends RoleDescriptorImpl implements SSODescriptor {

    /** Supported artifact resolutions services */
    private XMLObjectChildrenList<ArtifactResolutionService> artifactResolutionServices;

    /** Logout services for this SSO entity */
    private XMLObjectChildrenList<SingleLogoutService> singleLogoutServices;

    /** Manage NameID services for this entity */
    private XMLObjectChildrenList<ManageNameIDService> manageNameIDServices;

    /** NameID formats supported by this entity */
    private XMLObjectChildrenList<NameIDFormat> nameIDFormats;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace URI of the element this saml object represents
     * @param localName the local name of the element this SAML object represents
     */
    public SSODescriptorImpl(String namespaceURI, String localName) {
        super(SAMLConstants.SAML20MD_NS, localName);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);

        artifactResolutionServices = new XMLObjectChildrenList<ArtifactResolutionService>(this);
        singleLogoutServices = new XMLObjectChildrenList<SingleLogoutService>(this);
        manageNameIDServices = new XMLObjectChildrenList<ManageNameIDService>(this);
        nameIDFormats = new XMLObjectChildrenList<NameIDFormat>(this);
    }

    /*
     * @see org.opensaml.saml2.metadata.SSODescriptor#getArtifactResolutionServices()
     */
    public List<ArtifactResolutionService> getArtifactResolutionServices() {
        return artifactResolutionServices;
    }

    /*
     * @see org.opensaml.saml2.metadata.SSODescriptor#getSingleLogoutServices()
     */
    public List<SingleLogoutService> getSingleLogoutServices() {
        return singleLogoutServices;
    }

    /*
     * @see org.opensaml.saml2.metadata.SSODescriptor#getManageNameIDServices()
     */
    public List<ManageNameIDService> getManageNameIDServices() {
        return manageNameIDServices;
    }

    /*
     * @see org.opensaml.saml2.metadata.SSODescriptor#getNameIDFormats()
     */
    public List<NameIDFormat> getNameIDFormats() {
        return nameIDFormats;
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        children.addAll(super.getOrderedChildren());
        children.addAll(artifactResolutionServices);
        children.addAll(singleLogoutServices);
        children.addAll(manageNameIDServices);
        children.addAll(nameIDFormats);
        
        return Collections.unmodifiableList(children);
    }
}