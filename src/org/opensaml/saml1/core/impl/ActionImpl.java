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

import java.util.List;

import org.opensaml.common.SAMLVersion;
import org.opensaml.saml1.core.Action;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml1.core.Action}
 */
public class ActionImpl extends AbstractAssertionSAMLObject implements Action {

    /** Place to store the namespace */
    private String namespace;

    /** Where to store the contents */
    private String contents;

    /**
     * Hidden Constructor
     * @deprecated
     */
    private ActionImpl() {
        super(Action.LOCAL_NAME, null);
    }
    
    /**
     * Constructor
     *
     * @param version the version this is to be created with
     */
    public ActionImpl(SAMLVersion version) {
        super(Action.LOCAL_NAME, version);
    }

    /*
     * @see org.opensaml.saml1.core.Action#getNamespace()
     */
    public String getNamespace() {
        return namespace;
    }

    /*
     * @see org.opensaml.saml1.core.Action#setNamespace(java.lang.String)
     */
    public void setNamespace(String namespace) {
        this.namespace = prepareForAssignment(this.namespace, namespace);
    }

    /*
     * @see org.opensaml.saml1.core.Action#getContents()
     */
    public String getContents() {
        return contents;
    }

    /*
     * @see org.opensaml.saml1.core.Action#setContents(java.lang.String)
     */
    public void setContents(String contents) {
        this.contents = prepareForAssignment(this.contents, contents);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        // No elements
        return null;
    }
}
