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

package org.opensaml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.xml.IllegalAddException;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.EntitiesDescriptor}.
 */
public class EntitiesDescriptorImpl extends AbstractSignableSAMLObject implements EntitiesDescriptor {

    /** Name of this descriptor group */
    private String name;

    /** validUntil attribute */
    private GregorianCalendar validUntil;

    /** cacheDurection attribute */
    private Long cacheDuration;

    /** Extensions child */
    private Extensions extensions;

    /**
     * Ordered set of child Entity/Entities Descriptors
     */
    private ArrayList<SAMLObject> orderedDescriptors = new ArrayList<SAMLObject>();

    /**
     * Ordered set of EntitiesDescriptors
     */
    private ArrayList<EntitiesDescriptor> entitiesDescriptors = new ArrayList<EntitiesDescriptor>();

    /**
     * Ordered set of EntityDescriptors
     */
    private ArrayList<EntityDescriptor> entityDescriptors = new ArrayList<EntityDescriptor>();

    /**
     * Constructor
     */
    public EntitiesDescriptorImpl() {
        super(EntitiesDescriptor.LOCAL_NAME);
        setElementNamespaceAndPrefix(SAMLConstants.SAML20MD_NS, SAMLConstants.SAML20MD_PREFIX);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getName()
     */
    public String getName() {
        return name;
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#setName(java.lang.String)
     */
    public void setName(String newName) {
        this.name = prepareForAssignment(this.name, newName);
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundSAMLObject#isValid()
     */
    public boolean isValid() {
        return validUntil.before(GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC")));
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundSAMLObject#getValidUntil()
     */
    public GregorianCalendar getValidUntil() {
        return validUntil;
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundSAMLObject#setValidUntil(java.util.GregorianCalendar)
     */
    public void setValidUntil(GregorianCalendar validUntil) {
        this.validUntil = prepareForAssignment(this.validUntil, validUntil);
    }

    /*
     * @see org.opensaml.saml2.common.CacheableSAMLObject#getCacheDuration()
     */
    public Long getCacheDuration() {
        return cacheDuration;
    }

    /*
     * @see org.opensaml.saml2.common.CacheableSAMLObject#setCacheDuration(java.lang.Long)
     */
    public void setCacheDuration(Long duration) {
        cacheDuration = prepareForAssignment(cacheDuration, duration);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getExtensions()
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#setExtensions(org.opensaml.saml2.core.Extensions)
     */
    public void setExtensions(Extensions extensions) throws IllegalAddException {
        this.extensions = prepareForAssignment(this.extensions, extensions);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getEntitiesDescriptors()
     */
    public List<EntitiesDescriptor> getEntitiesDescriptors() {
        return Collections.unmodifiableList(entitiesDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#addEntitiesDescriptor(org.opensaml.saml2.metadata.EntitiesDescriptor)
     */
    public void addEntitiesDescriptor(EntitiesDescriptor descriptor) throws IllegalAddException {
        if (addXMLObject(entitiesDescriptors, descriptor)) {
            orderedDescriptors.add(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntitiesDescriptor(org.opensaml.saml2.metadata.EntitiesDescriptor)
     */
    public void removeEntitiesDescriptor(EntitiesDescriptor descriptor) {
        if (removeXMLObject(entitiesDescriptors, descriptor)) {
            orderedDescriptors.remove(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntitiesDescriptors(java.util.List)
     */
    public void removeEntitiesDescriptors(Collection<EntitiesDescriptor> desciptors) {
        for (EntitiesDescriptor descriptor : desciptors) {
            removeEntitiesDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeAllEntitiesDescriptors()
     */
    public void removeAllEntitiesDescriptors() {
        for (EntitiesDescriptor descriptor : entitiesDescriptors) {
            removeEntitiesDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getEntityDescriptors()
     */
    public List<EntityDescriptor> getEntityDescriptors() {
        return Collections.unmodifiableList(entityDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#addEntityDescriptor(org.opensaml.saml2.metadata.EntityDescriptor)
     */
    public void addEntityDescriptor(EntityDescriptor descriptor) throws IllegalAddException {
        if (addXMLObject(entityDescriptors, descriptor)) {
            orderedDescriptors.add(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntityDescriptor(org.opensaml.saml2.metadata.EntityDescriptor)
     */
    public void removeEntityDescriptor(EntityDescriptor descriptor) {
        if (removeXMLObject(entityDescriptors, descriptor)) {
            orderedDescriptors.remove(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntityDescriptors(java.util.List)
     */
    public void removeEntityDescriptors(Collection<EntityDescriptor> descriptors) {
        for (EntityDescriptor descriptor : descriptors) {
            removeEntityDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeAllEntityDescriptors()
     */
    public void removeAllEntityDescriptors() {
        for (EntityDescriptor descriptor : entityDescriptors) {
            removeEntityDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.common.impl.AbstractSAMLElement#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();

        children.add(getExtensions());

        children.addAll(getOrderedChildDescriptors());

        return Collections.unmodifiableList(children);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getOrderedChildDescriptors()
     */
    public List<SAMLObject> getOrderedChildDescriptors() {
        return Collections.unmodifiableList(orderedDescriptors);
    }
}