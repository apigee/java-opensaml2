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
import org.opensaml.saml2.core.BaseID;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.BaseID}
 */
public class BaseIDImpl extends AbstractSAMLObject implements BaseID {

    /** Name Qualifier of BaseID */
    private String nameQualifier;

    /** SP Name Qualifier of Base */
    private String spNameQualfier;

    /** Constructor */
    public BaseIDImpl() {
        super(SAMLConstants.SAML20_NS, BaseID.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
    }

    /**
     * @see org.opensaml.saml2.core.BaseID#getNameQualifier()
     */
    public String getNameQualifier() {
        return nameQualifier;
    }

    /**
     * @see org.opensaml.saml2.core.BaseID#setNameQualifier(java.lang.String)
     */
    public void setNameQualifier(String newNameQualifier) {
        this.nameQualifier = prepareForAssignment(this.nameQualifier, newNameQualifier);
    }

    /**
     * @see org.opensaml.saml2.core.BaseID#getSPNameQualifier()
     */
    public String getSPNameQualifier() {
        return spNameQualfier;
    }

    /**
     * @see org.opensaml.saml2.core.BaseID#setSPNameQualifier(java.lang.String)
     */
    public void setSPNameQualifier(String newSPNameQualifier) {
        this.spNameQualfier = prepareForAssignment(this.spNameQualfier, newSPNameQualifier);
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }
}