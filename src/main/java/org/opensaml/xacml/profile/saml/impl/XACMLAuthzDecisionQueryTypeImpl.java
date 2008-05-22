/*
Copyright 2008 Members of the EGEE Collaboration.
Copyright 2008 University Corporation for Advanced Internet Development,
Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.opensaml.xacml.profile.saml.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.saml2.core.impl.RequestAbstractTypeImpl;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionQueryType;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSBooleanValue;

/** A concrete implementation of {@link XACMLAuthzDecisionQueryType}. */
public class XACMLAuthzDecisionQueryTypeImpl extends RequestAbstractTypeImpl implements XACMLAuthzDecisionQueryType {

    /** The xacml-context:Request. */
    private RequestType request;

    /** InputContextOnly attribute value. Default = false. */
    private XSBooleanValue inputContextOnly;

    /** ReturnContext attribute value.Default = false. */
    private XSBooleanValue returnContext;

    /** CombinePolicies attribute value. Default = true. */
    private XSBooleanValue combinePolicies;

    /**
     * Constructor.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected XACMLAuthzDecisionQueryTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        setElementNamespacePrefix(namespacePrefix);       
    }

    /** {@inheritDoc} */
    public XSBooleanValue getCombinePoliciesXSBooleanValue() {
        return combinePolicies;
    }

    /** {@inheritDoc} */
    public XSBooleanValue getInputContextOnlyXSBooleanValue() {
        return inputContextOnly;
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (request != null) {
            children.add(request);
        }
        return Collections.unmodifiableList(children);
    }

    /** {@inheritDoc} */
    public RequestType getRequest() {
        return request;
    }

    /** {@inheritDoc} */
    public XSBooleanValue getReturnContextXSBooleanValue() {
        return returnContext;
    }

    /** {@inheritDoc} */
    public boolean isCombinePolicies() {
        if (combinePolicies != null) {
            return combinePolicies.getValue();
        }

        return true;
    }

    /** {@inheritDoc} */
    public boolean isInputContextOnly() {
        if (inputContextOnly != null) {
            return inputContextOnly.getValue();
        }

        return false;
    }

    /** {@inheritDoc} */
    public boolean isReturnContext() {
        if (returnContext != null) {
            return returnContext.getValue();
        }

        return false;
    }

    /** {@inheritDoc} */
    public void setCombinePolicies(XSBooleanValue combinePolicies) {
        this.combinePolicies = prepareForAssignment(this.combinePolicies, combinePolicies);
    }

    /** {@inheritDoc} */
    public void setInputContextOnly(XSBooleanValue inputContextOnly) {
        this.inputContextOnly = prepareForAssignment(this.inputContextOnly, inputContextOnly);
    }

    /** {@inheritDoc} */
    public void setRequest(RequestType request) {
        this.request = prepareForAssignment(this.request, request);
    }

    /** {@inheritDoc} */
    public void setReturnContext(XSBooleanValue returnContext) {
        this.returnContext = prepareForAssignment(this.returnContext, returnContext);
    }
}