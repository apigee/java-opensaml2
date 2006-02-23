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
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AssertionIDRef;
import org.opensaml.saml2.core.AssertionURIRef;
import org.opensaml.saml2.core.Evidence;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.Evidence}.
 */
public class EvidenceImpl extends AbstractSAMLObject implements Evidence {

    /** Assertion ID Reference of the Evidence */
    private XMLObjectChildrenList<AssertionIDRef> assertionIDRef;

    /** Assertion URI Reference of the Evidence */
    private XMLObjectChildrenList<AssertionURIRef> assertionURIRef;

    /** Assertion of the Evidence */
    private XMLObjectChildrenList<Assertion> assertion;

    /** Constructor */
    public EvidenceImpl() {
        super(SAMLConstants.SAML20_NS, Evidence.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
        
        assertionIDRef = new XMLObjectChildrenList<AssertionIDRef>(this);
        assertionURIRef = new XMLObjectChildrenList<AssertionURIRef>(this);
        assertion = new XMLObjectChildrenList<Assertion>(this);
    }

    /**
     * @see org.opensaml.saml2.core.Evidence#getAssertionIDRef()
     */
    public List<AssertionIDRef> getAssertionIDReferences() {
        return assertionIDRef;
    }

    /**
     * @see org.opensaml.saml2.core.Evidence#getAssertionURIRef()
     */
    public List<AssertionURIRef> getAssertionURIReferences() {
        return assertionURIRef;
    }

    /**
     * @see org.opensaml.saml2.core.Evidence#getAssertion()
     */
    public List<Assertion> getAssertions() {
        return assertion;
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();

        children.addAll(assertionIDRef);
        children.addAll(assertionURIRef);
        children.addAll(assertion);

        return Collections.unmodifiableList(children);
    }
}