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

import java.util.Iterator;
import java.util.Set;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml1.core.Audience;
import org.opensaml.saml1.core.AudienceRestrictionCondition;

/**
 * Concrete implementation of the org.opensaml.saml1.core.AudienceRestrictionCondition 
 */
public class AudienceRestrictionConditionImpl extends AbstractSignableSAMLObject implements AudienceRestrictionCondition {

    /**
     * Serialization GUID
     */
    private static final long serialVersionUID = 6866037911405940543L;

    private final OrderedSet<Audience> audiences = new OrderedSet<Audience>();
    
    public AudienceRestrictionConditionImpl() {
        setQName(AudienceRestrictionCondition.QNAME);
    }
    
    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#getAudiences()
     */
    public UnmodifiableOrderedSet<Audience> getAudiences() {
        return new UnmodifiableOrderedSet<Audience>(audiences);
    }

    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#addAudience(org.opensaml.saml1.core.Audience)
     */
    public void addAudience(Audience audience) throws IllegalAddException {
        addSAMLObject(audiences, audience);
    }

    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#removeAudience(org.opensaml.saml1.core.Audience)
     */
    public void removeAudience(Audience audience) {
        removeSAMLObject(audiences, audience);
    }

    /*
     * @see org.opensaml.saml1.core.AudienceRestrictionCondition#removeAudiences(java.util.Set)
     */
    public void removeAudiences(Set<Audience> audiences) {

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
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {
        OrderedSet<SAMLObject> set = new OrderedSet<SAMLObject>(audiences);
        return new UnmodifiableOrderedSet<SAMLObject>(set);
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {
        
        if (!(element instanceof AudienceRestrictionCondition)) {
            return false;
            
        }
        
        AudienceRestrictionCondition audienceRestrictionCondition = (AudienceRestrictionCondition) element;
        
        if (audienceRestrictionCondition.getAudiences().size() != audiences.size()) {
            return false;
        }
        
        Iterator<Audience> it = audienceRestrictionCondition.getAudiences().iterator();
        
        for (Audience audience : audiences) {
            
            if (!audience.equals(it.next())) {
                return false;
            }
        }
        return true;
    }

}
