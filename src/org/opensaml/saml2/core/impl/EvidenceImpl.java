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

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Evidentiary;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AssertionIDRef;
import org.opensaml.saml2.core.AssertionURIRef;
import org.opensaml.saml2.core.Evidence;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.Evidence}.
 */
public class EvidenceImpl extends AbstractAssertionSAMLObject implements Evidence {

    /** Assertion of the Evidence */
    private IndexedXMLObjectChildrenList<Evidentiary> evidence;

    /** Constructor */
    protected EvidenceImpl() {
        super(Evidence.LOCAL_NAME);

        evidence = new IndexedXMLObjectChildrenList<Evidentiary>(this);
    }

    /*
     * @see org.opensaml.saml2.core.Evidence#getEvidence()
     */
    public List<Evidentiary> getEvidence() {
        return evidence;
    }

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected EvidenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml2.core.Evidence#getAssertionIDRef()
     */
    public List<AssertionIDRef> getAssertionIDReferences() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AssertionIDRef.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        return (List<AssertionIDRef>) evidence.subList(qname);
    }

    /*
     * @see org.opensaml.saml2.core.Evidence#getAssertionURIRef()
     */
    public List<AssertionURIRef> getAssertionURIReferences() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AssertionURIRef.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        return (List<AssertionURIRef>) evidence.subList(qname);
    }

    /*
     * @see org.opensaml.saml2.core.Evidence#getAssertion()
     */
    public List<Assertion> getAssertions() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        return (List<Assertion>) evidence.subList(qname);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        
        if (evidence.size() == 0) {
            return null;
        }

        children.addAll(evidence);

        return Collections.unmodifiableList(children);
    }
}