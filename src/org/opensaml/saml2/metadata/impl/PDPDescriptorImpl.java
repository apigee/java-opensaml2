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
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AuthzService;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.PDPDescriptor;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.PDPDescriptor}
 */
public class PDPDescriptorImpl extends RoleDescriptorImpl implements PDPDescriptor {

    /** AuthzService children */
    private XMLObjectChildrenList<AuthzService> authzServices;

    /** AssertionIDRequestService children */
    private XMLObjectChildrenList<AssertionIDRequestService> assertionIDRequestServices;

    /** NameIDFormat children */
    private XMLObjectChildrenList<NameIDFormat> nameIDFormats;

    /**
     * Constructor
     */
    public PDPDescriptorImpl() {
        super(SAMLConstants.SAML20MD_NS, PDPDescriptor.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);

        authzServices = new XMLObjectChildrenList<AuthzService>(this);
        assertionIDRequestServices = new XMLObjectChildrenList<AssertionIDRequestService>(this);
        nameIDFormats = new XMLObjectChildrenList<NameIDFormat>(this);
    }

    /*
     * @see org.opensaml.saml2.metadata.PDPDescriptor#getAuthzServices()
     */
    public List<AuthzService> getAuthzServices() {
        return authzServices;
    }

    /*
     * @see org.opensaml.saml2.metadata.PDPDescriptor#getAssertionIDRequestService()
     */
    public List<AssertionIDRequestService> getAssertionIDRequestService() {
        return assertionIDRequestServices;
    }

    /*
     * @see org.opensaml.saml2.metadata.PDPDescriptor#getNameIDFormats()
     */
    public List<NameIDFormat> getNameIDFormats() {
        return nameIDFormats;
    }

    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        children.addAll(super.getOrderedChildren());
        children.addAll(authzServices);
        children.addAll(assertionIDRequestServices);
        children.addAll(nameIDFormats);

        return children;
    }
}