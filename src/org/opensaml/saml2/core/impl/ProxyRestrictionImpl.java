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

import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.ProxyRestriction;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.ProxyRestriction}
 */
public class ProxyRestrictionImpl extends AbstractAssertionSAMLObject implements ProxyRestriction {

    /** Audiences of the Restriction */
    private XMLObjectChildrenList<Audience> audience;

    /** Count of the Restriction */
    private Integer proxyCount;

    /** Constructor */
    protected ProxyRestrictionImpl() {
        super(ProxyRestriction.LOCAL_NAME);

        audience = new XMLObjectChildrenList<Audience>(this);
    }

    /*
     * @see org.opensaml.saml2.core.ProxyRestriction#getAudiences()
     */
    public List<Audience> getAudiences() {
        return audience;
    }

    /*
     * @see org.opensaml.saml2.core.ProxyRestriction#getCount()
     */
    public Integer getProxyCount() {
        return proxyCount;
    }

    /*
     * @see org.opensaml.saml2.core.ProxyRestriction#setCount(java.lang.Integer)
     */
    public void setProxyCount(Integer newProxyCount) {
        if (newProxyCount >= 0) {
            this.proxyCount = prepareForAssignment(this.proxyCount, newProxyCount);
        } else
            throw (new IllegalArgumentException("Count must be a non-negative integer."));
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.addAll(audience);
        return Collections.unmodifiableList(children);
    }
}