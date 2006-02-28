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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.Status;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Implementation of the {@link org.opensaml.saml1.core.Response} Object
 */
public class ResponseImpl extends ResponseAbstractTypeImpl implements Response {

    /** Status associated with this element */
    private Status status = null;

    /** List of all the Assertions */
    private List<Assertion> assertions;

    /**
     * Constructor
     */
    protected ResponseImpl() {
        super(Response.LOCAL_NAME);

        assertions = new XMLObjectChildrenList<Assertion>(this);
    }

    /*
     * @see org.opensaml.saml1.core.Response#getAssertions()
     */
    public List<Assertion> getAssertions() {
        return assertions;
    }

    /*
     * @see org.opensaml.saml1.core.Response#getStatus()
     */
    public Status getStatus() {
        return status;
    }

    /*
     * @see org.opensaml.saml1.core.Response#getStatus(org.opensaml.saml1.core.Status)
     */
    public void setStatus(Status status) throws IllegalArgumentException {
        this.status = prepareForAssignment(this.status, status);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>(1 + assertions.size());

        children.addAll(assertions);

        if (status != null) {
            children.add(status);
        }

        if (children.size() == 0) {
            return null;
        }

        return Collections.unmodifiableList(children);
    }
}