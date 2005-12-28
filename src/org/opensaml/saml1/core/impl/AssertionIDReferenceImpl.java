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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.StringHelper;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml1.core.AssertionIDReference;

/**
 * Concrete Implementation of {@link org.opensaml.saml1.core.AssertionIDReference} Object
 */
public class AssertionIDReferenceImpl extends AbstractSAMLObject implements AssertionIDReference {

    /**
     * Serialization UID.
     */
    private static final long serialVersionUID = 922710305837491058L;

    /** String to contain the NCName */

    private String NCName;

    /**
     * Constructor
     */
    public AssertionIDReferenceImpl() {
        super();
        setQName(AssertionIDReference.QNAME);
    }

    /*
     * @see org.opensaml.saml1.core.AssertionIDReference#getNCName()
     */
    public String getNCName() {
        return NCName;
    }

    /*
     * @see org.opensaml.saml1.core.AssertionIDReference#setNCName(java.lang.String)
     */
    public void setNCName(String NCName) {
        this.NCName = prepareForAssignment(this.NCName, NCName);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {

        // No children

        return new UnmodifiableOrderedSet<SAMLObject>(new OrderedSet<SAMLObject>());
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {

        if (element instanceof AssertionIDReferenceImpl) {
            AssertionIDReferenceImpl assertionIDReferenceImpl = (AssertionIDReferenceImpl) element;
            
            return StringHelper.safeEquals(NCName, assertionIDReferenceImpl.getNCName());
        }
        
        return false;
    }
}
