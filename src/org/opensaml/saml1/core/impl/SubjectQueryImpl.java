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

package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Subject;
import org.opensaml.saml1.core.SubjectQuery;
import org.opensaml.xml.XMLObject;

/**
 * Concrete (but abstract) implementation of {@link org.opensaml.saml1.core.SubjectQuery} abstract type
 */
public abstract class SubjectQueryImpl extends AbstractProtocolSAMLObject implements SubjectQuery {

    /** Contains the Subject subelement */
    private Subject subject;
    
    /**
     * Constructor. Sets namespace to {@link SAMLConstants#SAML1P_NS} and prefix to
     * {@link SAMLConstants#SAML1P_PREFIX}.  Sets the SAML version to {@link SAMLVersion#VERSION_11}.
     * 
     * @param localName the local name of the element
     */
    protected SubjectQueryImpl(String elementLocalName) {
        super(elementLocalName);
    }
    
    /**
     * Constructor.  Sets the SAML version to {@link SAMLVersion#VERSION_11}.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected SubjectQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml1.core.SubjectQuery#getSubject()
     */
    public Subject getSubject() {
        return subject;
    }

    /*
     * @see org.opensaml.saml1.core.SubjectQuery#setSubject(org.opensaml.saml1.core.Subject)
     */
    public void setSubject(Subject subject) {
        this.subject = prepareForAssignment(this.subject, subject);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        if (subject == null) {
            return null;
        }
        
        List<XMLObject> children = new ArrayList<XMLObject>();
        children.add(subject);
        return Collections.unmodifiableList(children);
    }
    
}