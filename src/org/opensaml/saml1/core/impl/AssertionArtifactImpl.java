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

import org.opensaml.saml1.core.AssertionArtifact;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation if {@link org.opensaml.saml1.core.AssertionArtifact}
 */
public class AssertionArtifactImpl extends AbstractProtocolSAMLObject implements AssertionArtifact {

    /** The assertion artifact */
    private String assertionArtifact;

    /**
     * Constructor
     */
    public AssertionArtifactImpl() {
        super(AssertionArtifact.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml1.core.AssertionArtifact#getAssertionArtifact()
     */
    public String getAssertionArtifact() {
        return assertionArtifact;
    }

    /*
     * @see org.opensaml.saml1.core.AssertionArtifact#setAssertionArtifact(java.lang.String)
     */
    public void setAssertionArtifact(String assertionArtifact) {
        this.assertionArtifact = prepareForAssignment(this.assertionArtifact, assertionArtifact);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}