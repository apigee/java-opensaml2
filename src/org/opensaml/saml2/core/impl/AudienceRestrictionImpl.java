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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.AudienceRestriction}
 */
public class AudienceRestrictionImpl extends AbstractSAMLObject implements AudienceRestriction {

    /**List of the audiences*/
    private XMLObjectChildrenList<Audience> audience;

    /** Constructor */
    public AudienceRestrictionImpl() {
        super(SAMLConstants.SAML20_NS, AudienceRestriction.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
        
        audience = new XMLObjectChildrenList<Audience>(this);
    }

    /**
     * @see org.opensaml.saml2.core.AudienceRestriction#getAudiences()
     */
    public List<Audience> getAudiences() {
        return audience;
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        children.addAll(audience);
        
        return Collections.unmodifiableList(children);
    }
}