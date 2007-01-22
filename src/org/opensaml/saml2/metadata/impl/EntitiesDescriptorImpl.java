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
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.EntitiesDescriptor}.
 */
public class EntitiesDescriptorImpl extends AbstractSignableSAMLObject implements EntitiesDescriptor {

    /** Name of this descriptor group */
    private String name;

    /** ID attribute */
    private String id;

    /** validUntil attribute */
    private DateTime validUntil;

    /** cacheDurection attribute */
    private Long cacheDuration;

    /** Extensions child */
    private Extensions extensions;

    /** Ordered set of child Entity/Entities Descriptors */
    private final IndexedXMLObjectChildrenList<SAMLObject> orderedDescriptors;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected EntitiesDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        orderedDescriptors = new IndexedXMLObjectChildrenList<SAMLObject>(this);
    }

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    public void setName(String newName) {
        this.name = prepareForAssignment(this.name, newName);
    }

    /** {@inheritDoc} */
    public String getID() {
        return id;
    }

    /** {@inheritDoc} */
    public void setID(String newID) {
        String oldID = this.id;
        this.id = prepareForAssignment(this.id, newID);
        registerOwnID(oldID, this.id);
    }

    /** {@inheritDoc} */
    public boolean isValid() {
        return validUntil.isBeforeNow();
    }

    /** {@inheritDoc} */
    public DateTime getValidUntil() {
        return validUntil;
    }

    /** {@inheritDoc} */
    public void setValidUntil(DateTime validUntil) {
        this.validUntil = prepareForAssignment(this.validUntil, validUntil);
    }

    /** {@inheritDoc} */
    public Long getCacheDuration() {
        return cacheDuration;
    }

    /** {@inheritDoc} */
    public void setCacheDuration(Long duration) {
        cacheDuration = prepareForAssignment(cacheDuration, duration);
    }

    /** {@inheritDoc} */
    public Extensions getExtensions() {
        return extensions;
    }

    /** {@inheritDoc} */
    public void setExtensions(Extensions extensions) throws IllegalArgumentException {
        this.extensions = prepareForAssignment(this.extensions, extensions);
    }

    /** {@inheritDoc} */
    public List<EntitiesDescriptor> getEntitiesDescriptors() {
        return (List<EntitiesDescriptor>) orderedDescriptors.subList(EntitiesDescriptor.ELEMENT_QNAME);
    }

    /** {@inheritDoc} */
    public List<EntityDescriptor> getEntityDescriptors() {
        return (List<EntityDescriptor>) orderedDescriptors.subList(EntityDescriptor.ELEMENT_QNAME);
    }
    
    /** {@inheritDoc} */
    public String getSignatureReferenceID(){
        return id;
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if(getSignature() != null){
            children.add(getSignature());
        }
        
        children.add(getExtensions());
        children.addAll(orderedDescriptors);

        return Collections.unmodifiableList(children);
    }
}