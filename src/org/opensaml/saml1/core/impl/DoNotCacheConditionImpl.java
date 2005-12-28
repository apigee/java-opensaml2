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
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml1.core.DoNotCacheCondition;

/**
 * Concrete Implememtation of a {@link org.opensaml.saml1.core.DoNotCacheCondition} Objects
 */
public class DoNotCacheConditionImpl extends AbstractSAMLObject implements DoNotCacheCondition {

    /*
     * This is a marker elelment only, no contents
     */
    
    /**
     * Serialization GUID
     */
    private static final long serialVersionUID = -1795979542699952541L;

    /**
     * Constructor
     *
     */
    public DoNotCacheConditionImpl() {
        setQName(DoNotCacheCondition.QNAME);
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {
        return new UnmodifiableOrderedSet<SAMLObject>(new OrderedSet<SAMLObject>(0));
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {
        //
        // true iff the other object is a DoNotCacheCondition object
        //
        if (element instanceof DoNotCacheCondition) {
            return true;
        }
        return false;
    }
}
