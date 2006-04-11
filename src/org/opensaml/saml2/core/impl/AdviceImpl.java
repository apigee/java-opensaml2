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

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.core.Advice;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AssertionIDRef;
import org.opensaml.saml2.core.AssertionURIRef;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.Advice}.
 */
public class AdviceImpl extends AbstractSAMLObject implements Advice {

    /** List of AssertionID references */
    private final XMLObjectChildrenList<AssertionIDRef> assertionIDRef;

    /** List of AssertionURI references */
    private final XMLObjectChildrenList<AssertionURIRef> assertionURIRef;

    /** List of Assertions */
    private final XMLObjectChildrenList<Assertion> assertion;
    
    /** "any" children */
    private final XMLObjectChildrenList<XMLObject> unknownChildren;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected AdviceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        assertionIDRef = new XMLObjectChildrenList<AssertionIDRef>(this);
        assertionURIRef = new XMLObjectChildrenList<AssertionURIRef>(this);
        assertion = new XMLObjectChildrenList<Assertion>(this);
        unknownChildren = new XMLObjectChildrenList<XMLObject>(this);
    }

    /*
     * @see org.opensaml.saml2.core.Advice#getAssertionIDReferences()
     */
    public List<AssertionIDRef> getAssertionIDReferences() {
        return assertionIDRef;
    }

    /*
     * @see org.opensaml.saml2.core.Advice#getAssertionURIReferences()
     */
    public List<AssertionURIRef> getAssertionURIReferences() {
        return assertionURIRef;
    }

    /*
     * @see org.opensaml.saml2.core.Advice#getAssertions()
     */
    public List<Assertion> getAssertions() {
        return assertion;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<XMLObject> getUnknownXMLObjects() {
        return unknownChildren;
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.addAll(assertionIDRef);
        children.addAll(assertionURIRef);
        children.addAll(assertion);
        children.addAll(unknownChildren);

        return Collections.unmodifiableList(children);
    }
}