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
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.EntitiesDescriptor}.
 */
public class EntitiesDescriptorImpl extends AbstractSignableMetadataSAMLObject implements EntitiesDescriptor {

    /** Name of this descriptor group */
    private String name;

    /** validUntil attribute */
    private DateTime validUntil;

    /** cacheDurection attribute */
    private Long cacheDuration;

    /** Extensions child */
    private Extensions extensions;

    /**
     * Ordered set of child Entity/Entities Descriptors
     */
    private IndexedXMLObjectChildrenList<SAMLObject> orderedDescriptors;

    /**
     * Constructor
     */
    protected EntitiesDescriptorImpl() {
        super(EntitiesDescriptor.LOCAL_NAME);

        orderedDescriptors = new IndexedXMLObjectChildrenList<SAMLObject>(this);
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
        return validUntil.isBeforeNow();
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundSAMLObject#getValidUntil()
     */
    public DateTime getValidUntil() {
        return validUntil;
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundSAMLObject#setValidUntil(java.util.GregorianCalendar)
     */
    public void setValidUntil(DateTime validUntil) {
        this.validUntil = prepareForAssignment(this.validUntil, validUntil.withZone(DateTimeZone.UTC));
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
    public void setExtensions(Extensions extensions) throws IllegalArgumentException {
        this.extensions = prepareForAssignment(this.extensions, extensions);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getEntitiesDescriptors()
     */
    public List<EntitiesDescriptor> getEntitiesDescriptors() {
        return (List<EntitiesDescriptor>) orderedDescriptors.subList(EntitiesDescriptor.ELEMENT_QNAME);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getEntityDescriptors()
     */
    public List<EntityDescriptor> getEntityDescriptors() {
        return (List<EntityDescriptor>) orderedDescriptors.subList(EntityDescriptor.ELEMENT_QNAME);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.add(getExtensions());
        children.addAll(orderedDescriptors);

        return Collections.unmodifiableList(children);
    }
}