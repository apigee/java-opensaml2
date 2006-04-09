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

import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.Scoping;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.XMLObject;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.AuthnRequest}.
 */
public class AuthnRequestImpl extends RequestImpl implements AuthnRequest {

    /** Subject child element */
    private Subject subject;

    /** NameIDPolicy child element */
    private NameIDPolicy nameIDPolicy;

    /** Conditions child element */
    private Conditions conditions;

    /** RequestedAuthnContext child element */
    private RequestedAuthnContext requestedAuthnContext;

    /** Scoping child element */
    private Scoping scoping;

    /** ForeceAuthn attribute */
    private Boolean forceAuthn;

    /** IsPassive attribute */
    private Boolean isPassive;

    /** ProtocolBinding attribute */
    private String protocolBinding;

    /** AssertionConsumerServiceIndex attribute */
    private Integer assertionConsumerServiceIndex;

    /** AssertionConsumerServiceURL attribute */
    private String assertionConsumerServiceURL;

    /** AttributeConsumingServiceIndex attribute */
    private Integer attributeConsumingServiceIndex;

    /** ProviderName attribute */
    private String providerName;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected AuthnRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getForceAuthn()
     */
    public Boolean getForceAuthn() {
        return this.forceAuthn;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setForceAuthn(java.lang.Boolean)
     */
    public void setForceAuthn(Boolean newForceAuthn) {
        this.forceAuthn = prepareForAssignment(this.forceAuthn, newForceAuthn);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getIsPassive()
     */
    public Boolean getIsPassive() {
        return this.isPassive;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setIsPassive(java.lang.Boolean)
     */
    public void setIsPassive(Boolean newIsPassive) {
        this.isPassive = prepareForAssignment(this.isPassive, newIsPassive);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getProtocolBinding()
     */
    public String getProtocolBinding() {
        return this.protocolBinding;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setProtocolBinding(java.lang.String)
     */
    public void setProtocolBinding(String newProtocolBinding) {
        this.protocolBinding = prepareForAssignment(this.protocolBinding, newProtocolBinding);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getAssertionConsumerServiceIndex()
     */
    public Integer getAssertionConsumerServiceIndex() {
        return assertionConsumerServiceIndex;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setAssertionConsumerServiceIndex(java.lang.Integer)
     */
    public void setAssertionConsumerServiceIndex(Integer newAssertionConsumerServiceIndex) {
        this.assertionConsumerServiceIndex = prepareForAssignment(this.assertionConsumerServiceIndex,
                newAssertionConsumerServiceIndex);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getAssertionConsumerServiceURL()
     */
    public String getAssertionConsumerServiceURL() {
        return this.assertionConsumerServiceURL;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setAssertionConsumerServiceURL(java.lang.String)
     */
    public void setAssertionConsumerServiceURL(String newAssertionConsumerServiceURL) {
        this.assertionConsumerServiceURL = prepareForAssignment(this.assertionConsumerServiceURL,
                newAssertionConsumerServiceURL);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getAttributeConsumingServiceIndex()
     */
    public Integer getAttributeConsumingServiceIndex() {
        return this.attributeConsumingServiceIndex;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setAttributeConsumingServiceIndex(java.lang.Integer)
     */
    public void setAttributeConsumingServiceIndex(Integer newAttributeConsumingServiceIndex) {
        this.attributeConsumingServiceIndex = prepareForAssignment(this.attributeConsumingServiceIndex,
                newAttributeConsumingServiceIndex);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getProviderName()
     */
    public String getProviderName() {
        return this.providerName;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setProviderName(java.lang.String)
     */
    public void setProviderName(String newProviderName) {
        this.providerName = prepareForAssignment(this.providerName, newProviderName);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getSubject()
     */
    public Subject getSubject() {
        return this.subject;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setSubject(org.opensaml.saml2.core.Subject)
     */
    public void setSubject(Subject newSubject) {
        this.subject = prepareForAssignment(this.subject, newSubject);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getNameIDPolicy()
     */
    public NameIDPolicy getNameIDPolicy() {
        return this.nameIDPolicy;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setNameIDPolicy(org.opensaml.saml2.core.NameIDPolicy)
     */
    public void setNameIDPolicy(NameIDPolicy newNameIDPolicy) {
        this.nameIDPolicy = prepareForAssignment(this.nameIDPolicy, newNameIDPolicy);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getConditions()
     */
    public Conditions getConditions() {
        return this.conditions;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setConditions(org.opensaml.saml2.core.Conditions)
     */
    public void setConditions(Conditions newConditions) {
        this.conditions = prepareForAssignment(this.conditions, newConditions);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getRequestedAuthnContext()
     */
    public RequestedAuthnContext getRequestedAuthnContext() {
        return this.requestedAuthnContext;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setRequestedAuthnContext(org.opensaml.saml2.core.RequestedAuthnContext)
     */
    public void setRequestedAuthnContext(RequestedAuthnContext newRequestedAuthnContext) {
        this.requestedAuthnContext = prepareForAssignment(this.requestedAuthnContext, newRequestedAuthnContext);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#getScoping()
     */
    public Scoping getScoping() {
        return this.scoping;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnRequest#setScoping(org.opensaml.saml2.core.Scoping)
     */
    public void setScoping(Scoping newScoping) {
        this.scoping = prepareForAssignment(this.scoping, newScoping);
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());

        if (subject != null)
            children.add(subject);
        if (nameIDPolicy != null)
            children.add(nameIDPolicy);
        if (conditions != null)
            children.add(conditions);
        if (requestedAuthnContext != null)
            children.add(requestedAuthnContext);
        if (scoping != null)
            children.add(scoping);

        if (children.size() == 0)
            return null;

        return Collections.unmodifiableList(children);
    }
}