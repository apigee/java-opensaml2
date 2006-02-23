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

import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Action;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.Action}.
 */
public class ActionImpl extends AbstractSAMLObject implements Action {

    /** URI of the Namespace of this Action */
    private String namespace;

    /** Action value */
    private String action;

    /** Constructor */
    public ActionImpl() {
        super(SAMLConstants.SAML20_NS, Action.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
    }

    /**
     * @see org.opensaml.saml2.core.Action#getNamespace()
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @see org.opensaml.saml2.core.Action#setNamespace(java.lang.String)
     */
    public void setNamespace(String newNamespace) {
        this.namespace = prepareForAssignment(this.namespace, newNamespace);
    }

    /**
     * @see org.opensaml.saml2.core.Action#getAction()
     */
    public String getAction() {
        return action;
    }

    /**
     * @see org.opensaml.saml2.core.Action#setAction(java.lang.String)
     */
    public void setAction(String newAction) {
        this.action = prepareForAssignment(this.action, newAction);
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }
}