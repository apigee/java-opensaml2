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

package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Audience;
import org.opensaml.saml1.core.AudienceRestrictionCondition;

/**
 * Concrete implementation of the org.opensaml.saml1.core.AudienceRestrictionCondition
 */
public class AudienceRestrictionConditionImpl extends AbstractSignableSAMLObject implements
        AudienceRestrictionCondition {

    private final ArrayList<Audience> audiences = new ArrayList<Audience>();

    public AudienceRestrictionConditionImpl() {
        super(SAMLConstants.SAML1_NS, AudienceRestrictionCondition.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#getAudiences()
     */
    public List<Audience> getAudiences() {
        if (audiences.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(audiences);
    }

    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#addAudience(org.opensaml.saml1.core.Audience)
     */
    public void addAudience(Audience audience) throws IllegalArgumentException {
        addXMLObject(audiences, audience);
    }

    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#removeAudience(org.opensaml.saml1.core.Audience)
     */
    public void removeAudience(Audience audience) {
        removeXMLObject(audiences, audience);
    }

    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#removeAudiences(java.util.Set)
     */
    public void removeAudiences(Collection<Audience> audiences) {
        if (audiences == null) {
            return;
        }
        for (Audience audience : audiences) {
            removeAudience(audience);
        }
    }

    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#removeAllAudiences()
     */
    public void removeAllAudiences() {

        for (Audience audience : this.audiences) {
            removeAudience(audience);
        }
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        
        if (audiences.size() == 0){
            return null;
        }
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>(audiences);
        return Collections.unmodifiableList(children);
    }
}