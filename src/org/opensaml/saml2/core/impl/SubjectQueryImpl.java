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
package org.opensaml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectQuery;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.SubjectQuery}
 */
public abstract class SubjectQueryImpl extends RequestImpl implements SubjectQuery {
    
    /** Subject child element */
    private Subject subject;

    /**
     * Constructor
     *
     * @param namespaceURI
     * @param elementLocalName
     */
    protected SubjectQueryImpl(String namespaceURI, String elementLocalName) {
        super(namespaceURI, elementLocalName);
    }

    /**
     * @see org.opensaml.saml2.core.SubjectQuery#getSubject()
     */
    public Subject getSubject() {
        return this.subject;
    }

    /**
     * @see org.opensaml.saml2.core.SubjectQuery#setSubject(org.opensaml.saml2.core.Issuer)
     */
    public void setSubject(Subject newSubject) {
        this.subject = prepareForAssignment(this.subject, newSubject);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestImpl#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());
        if (subject != null)        
            children.add(subject);
        
        if (children.size() == 0)
            return null;
        
        return Collections.unmodifiableList(children);
    }
    
    
    
    

}
