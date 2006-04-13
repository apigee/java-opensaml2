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

import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * Concrete Implementation of the {@link org.opensaml.saml1.core.Advice} Object
 */
public class AdviceImpl extends AbstractSAMLObject implements Advice {

    /** Contains all the SAML objects we have added */
    private final IndexedXMLObjectChildrenList<XMLObject> orderedChildren;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected AdviceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        orderedChildren = new IndexedXMLObjectChildrenList<XMLObject>(this);
    }
    
    /*
     * @see org.opensaml.saml1.core.Advice#getAssertionIDReferences()
     */
    public List<AssertionIDReference> getAssertionIDReferences() {
        //
        // The cast in the line below is unsafe. (it's checking against the erasure of l - which is List.
        // We are, howeverever guaranteed by sublist that although l is 'just' a List it
        // will only contain <AssertionIDReferences> explicit code in IndexedXMLObjectChildrenList$ListView.indexCheck
        // helps us be sure.

        QName assertionIDRefQName = new QName(SAMLConstants.SAML1_NS, AssertionIDReference.DEFAULT_ELEMENT_LOCAL_NAME);
        return (List<AssertionIDReference>) orderedChildren.subList(assertionIDRefQName);
    }

    /*
     * @see org.opensaml.saml1.core.Advice#getAssertions()
     */
    public List<Assertion> getAssertions() {
        // See Comment for getAssertionIDReference as to why this unsafe casting is OK
        QName assertionQname = new QName(SAMLConstants.SAML1_NS, Assertion.DEFAULT_ELEMENT_LOCAL_NAME);
        return (List<Assertion>) orderedChildren.subList(assertionQname);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        if (orderedChildren.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(orderedChildren);
    }
}