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

import org.opensaml.saml2.core.Action;
import org.opensaml.xml.XMLObject;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.Action}.
 */
public class ActionImpl extends AbstractAssertionSAMLObject implements Action {

    /** URI of the Namespace of this Action */
    private String namespace;

    /** Action value */
    private String action;

    /** Constructor */
    protected ActionImpl() {
        super(Action.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml2.core.Action#getNamespace()
     */
    public String getNamespace() {
        return namespace;
    }

    /*
     * @see org.opensaml.saml2.core.Action#setNamespace(java.lang.String)
     */
    public void setNamespace(String newNamespace) {
        this.namespace = prepareForAssignment(this.namespace, newNamespace);
    }

    /*
     * @see org.opensaml.saml2.core.Action#getAction()
     */
    public String getAction() {
        return action;
    }

    /*
     * @see org.opensaml.saml2.core.Action#setAction(java.lang.String)
     */
    public void setAction(String newAction) {
        this.action = prepareForAssignment(this.action, newAction);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}