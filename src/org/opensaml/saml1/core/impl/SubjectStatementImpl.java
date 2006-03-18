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
import org.opensaml.saml1.core.SubjectStatement;
import org.opensaml.xml.XMLObject;

/**
 * Abstract type to implement SubjectStatementType
 */
public abstract class SubjectStatementImpl extends AbstractAssertionSAMLObject implements SubjectStatement {

    /** Contains the Subject subelement */
    private Subject subject;

    /**
     * Constructor. Sets namespace to {@link SAMLConstants#SAML1_NS} and prefix to
     * {@link SAMLConstants#SAML1_PREFIX}.
     * 
     * @param localName the local name of the element
     * @param version the version to set
     */
    protected SubjectStatementImpl(String elementLocalName, SAMLVersion version) {
        super(elementLocalName, version);
    }
    
    /**
     * Constructor.  
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
    * @param version the version to set
      */
    protected SubjectStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix, SAMLVersion version) {
        super(namespaceURI, elementLocalName, namespacePrefix, version);
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
    public void setSubject(Subject subject) throws IllegalArgumentException {
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