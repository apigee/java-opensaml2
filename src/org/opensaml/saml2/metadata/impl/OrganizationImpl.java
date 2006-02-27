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

import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml2.metadata.OrganizationName;
import org.opensaml.saml2.metadata.OrganizationURL;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.Organization}
 */
public class OrganizationImpl extends AbstractMetadataSAMLObject implements Organization {

    /** element extensions */
    private Extensions extensions;

    /** OrganizationName children */
    private XMLObjectChildrenList<OrganizationName> names;

    /** OrganizationDisplayName children */
    private XMLObjectChildrenList<OrganizationDisplayName> displayNames;

    /** OrganizationURL children */
    private XMLObjectChildrenList<OrganizationURL> urls;

    /**
     * Constructor
     */
    protected OrganizationImpl() {
        super(Organization.LOCAL_NAME);

        names = new XMLObjectChildrenList<OrganizationName>(this);
        displayNames = new XMLObjectChildrenList<OrganizationDisplayName>(this);
        urls = new XMLObjectChildrenList<OrganizationURL>(this);
    }

    /*
     * @see org.opensaml.saml2.metadata.Organization#getExtensions()
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /*
     * @see org.opensaml.saml2.metadata.Organization#setExtensions(org.opensaml.saml2.core.Extensions)
     */
    public void setExtensions(Extensions extensions) throws IllegalArgumentException {
        this.extensions = prepareForAssignment(this.extensions, extensions);
    }

    /*
     * @see org.opensaml.saml2.metadata.Organization#getOrganizationNames()
     */
    public List<OrganizationName> getOrganizationNames() {
        return names;
    }

    /*
     * @see org.opensaml.saml2.metadata.Organization#getDisplayNames()
     */
    public List<OrganizationDisplayName> getDisplayNames() {
        return displayNames;
    }

    /*
     * @see org.opensaml.saml2.metadata.Organization#getURLs()
     */
    public List<OrganizationURL> getURLs() {
        return urls;
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.add(extensions);
        children.addAll(names);
        children.addAll(displayNames);
        children.addAll(urls);

        return children;
    }
}