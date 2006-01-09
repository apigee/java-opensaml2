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
package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Attribute;
import org.opensaml.saml1.core.AttributeValue;
import org.opensaml.saml1.core.Subject;
import org.opensaml.xml.IllegalAddException;

/**
 * A Concrete implementation of the {@link org.opensaml.saml1.core.Attribute} Interface
 */
public class AttributeImpl extends AbstractSAMLObject implements Attribute {

    /** Contains the AttributeValues */
    private final List <AttributeValue> attributeValues;
    
    /** Contains the Subject subelement */
    private Subject subject;

   /**
     * Constructor
     */
    public AttributeImpl() {
        super(SAMLConstants.SAML1_NS, Attribute.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
        attributeValues = new ArrayList<AttributeValue>();
    }

    /*
     * @see org.opensaml.saml1.core.Attribute#getAttributeValues()
     */
    public List<AttributeValue> getAttributeValues() {
        if (attributeValues.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(attributeValues);
    }

    /*
     * @see org.opensaml.saml1.core.Attribute#addAttributeValue(org.opensaml.saml1.core.AttributeValue)
     */
    public void addAttributeValue(AttributeValue attributeValue) throws IllegalAddException {
        addXMLObject(attributeValues, attributeValue);
    }

    /*
     * @see org.opensaml.saml1.core.Attribute#removeAttributeValue(org.opensaml.saml1.core.AttributeValue)
     */
    public void removeAttributeValue(AttributeValue attributeValue) {
        removeXMLObject(attributeValues, attributeValue);
    }

    /*
     * @see org.opensaml.saml1.core.Attribute#removeAttributeValues(java.util.Collection)
     */
    public void removeAttributeValues(Collection<AttributeValue> attributeValues) {
       for (AttributeValue attributeValue : attributeValues) {
           removeXMLObject(this.attributeValues, attributeValue);
       }
    }

    /*
     * @see org.opensaml.saml1.core.Attribute#removeAllAttributeValues()
     */
    public void removeAllAttributeValues() {
        for (AttributeValue attributeValue : attributeValues) {
            removeXMLObject(attributeValues, attributeValue);
        }
    }
    /*
     * @see org.opensaml.saml1.core.SubjectStatement#getSubject()
     */
    public Subject getSubject() {
        return subject;
    }

    /*
     * @see org.opensaml.saml1.core.SubjectStatement#setSubject(org.opensaml.saml1.core.Subject)
     */
    public void setSubject(Subject subject) throws IllegalAddException {
        this.subject = prepareForAssignment(this.subject, subject);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {

        List<SAMLObject> list = new ArrayList<SAMLObject>(attributeValues.size() + 1);
        if (subject != null) {
            list.add(subject);
        }
        list.addAll(attributeValues);
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

}
