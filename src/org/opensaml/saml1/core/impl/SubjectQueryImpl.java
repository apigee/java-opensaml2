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

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml1.core.Subject;
import org.opensaml.saml1.core.SubjectQuery;

/**
 * Concrete (but abstract) implementation of {@link org.opensaml.saml1.core.SubjectQuery} abstract type
 */
public abstract class SubjectQueryImpl extends AbstractSAMLObject implements SubjectQuery {

    /** Contains the Subject subelement */
    private Subject subject;
    
    /**
     * Constructor
     *
     * @param namespaceURI
     * @param elementLocalName
     */
    public SubjectQueryImpl(String namespaceURI, String elementLocalName) {
        super(namespaceURI, elementLocalName);
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
}
