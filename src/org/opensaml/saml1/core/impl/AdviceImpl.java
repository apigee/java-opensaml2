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

import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * Concrete Implementation of the {@link org.opensaml.saml1.core.Advice} Object
 */
public class AdviceImpl extends AbstractSAMLObject implements Advice {

    /** Contains all the SAML objects we have added */
    private final IndexedXMLObjectChildrenList<SAMLObject> orderedChildren = new IndexedXMLObjectChildrenList<SAMLObject>(this);

    /**
     * Constructor
     */
    public AdviceImpl() {
        super(SAMLConstants.SAML1_NS, Advice.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.saml1.core.Advice#getAssertionIDReferences()
     */
    @SuppressWarnings("unchecked")
    public List<AssertionIDReference> getAssertionIDReferences() {
        List<? extends SAMLObject> l = orderedChildren.subList(new QName(SAMLConstants.SAML1_NS, AssertionIDReference.LOCAL_NAME));
        //
        // The cast in the line below is unsafe. (it's checking against the erasure of l - which is List.
        // We are, howeverever guaranteed by sublist that although l is 'just' a List it
        // will only contain <AssertionIDReferences> explicit code in IndexedXMLObjectChildrenList$ListView.indexCheck
        // helps us be sure.
        
        return (List<AssertionIDReference>) l;
    }

    @SuppressWarnings("unchecked")
    public List<Assertion> getAssertions() {
        List<? extends SAMLObject> l = orderedChildren.subList(new QName(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME));
        // See Comment for getAssertionIDReference as to why this unsafe casting is OK
        return (List<Assertion>) l;
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        if (orderedChildren.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(orderedChildren);
    }
}