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

import java.util.GregorianCalendar;
import java.util.Set;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml1.core.Condition;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.DoNotCacheCondition;


/**
 * This is a concrete implementation of the {@link org.opensaml.saml1.core.Conditions} interface.
 */
public class ConditionsImpl extends AbstractSAMLObject implements Conditions {

    /**
     * Serialization GUID
     */
    private static final long serialVersionUID = 6517532051958214553L;

    /** Value saved in the NotBefore attribute */

    private GregorianCalendar notBefore;

    /** Value saved in the NotOnOrAfter attribute */

    private GregorianCalendar notOnOrAfter;

    /** Set containing all the AudienceRestrictionConditions */

    private final OrderedSet<AudienceRestrictionCondition> audienceRestrictionConditions;

    /** Set containing all the DoNotCacheConditions */

    private final OrderedSet<DoNotCacheCondition> doNotCacheConditions;

    /** Set containing all the Conditions */

    private final OrderedSet<Condition> conditions;

    /** Ordered Set of all subelements */

    private final OrderedSet<SAMLObject> orderedChildren;

    /**
     * Constructor
     */
    public ConditionsImpl() {
        super();
        notBefore = null;
        notOnOrAfter = null;
        audienceRestrictionConditions = new OrderedSet<AudienceRestrictionCondition>();
        doNotCacheConditions = new OrderedSet<DoNotCacheCondition>();
        conditions = new OrderedSet<Condition>();
        orderedChildren = new OrderedSet<SAMLObject>();
        setQName(Conditions.QNAME);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getNotBefore()
     */
    public GregorianCalendar getNotBefore() {

        return notBefore;
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#setNotBefore()
     */
    public void setNotBefore(GregorianCalendar notBefore) {

        if (notBefore == null && this.notBefore == null) {
            // no change - return
            return;
        }

        if (this.notBefore == null || !this.notBefore.equals(notBefore)) {
            releaseThisandParentDOM();
            this.notBefore = notBefore;
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getNotOnOrAfter()
     */
    public GregorianCalendar getNotOnOrAfter() {

        return notOnOrAfter;
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#setNotOnOrAfter()
     */
    public void setNotOnOrAfter(GregorianCalendar notOnOrAfter) {
        if (notOnOrAfter == null && this.notOnOrAfter == null) {
            // no change - return
            return;
        }

        if (this.notOnOrAfter == null || !this.notOnOrAfter.equals(notOnOrAfter)) {
            releaseThisandParentDOM();
            this.notOnOrAfter = notOnOrAfter;
        }

    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getAudienceRestrictionConditions()
     */
    public UnmodifiableOrderedSet<AudienceRestrictionCondition> getAudienceRestrictionConditions() {
        return new UnmodifiableOrderedSet<AudienceRestrictionCondition>(audienceRestrictionConditions);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#addAudienceRestrictionCondition(org.opensaml.saml1.core.AudienceRestrictionCondition)
     */
    public void addAudienceRestrictionCondition(AudienceRestrictionCondition audienceRestrictionCondition)
            throws IllegalAddException {

        if (addSAMLObject(audienceRestrictionConditions, audienceRestrictionCondition)) {
            orderedChildren.add(audienceRestrictionCondition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeAudienceRestrictionCondition(org.opensaml.saml1.core.AudienceRestrictionCondition)
     */
    public void removeAudienceRestrictionCondition(AudienceRestrictionCondition audienceRestrictionCondition) {

        if (removeSAMLObject(audienceRestrictionConditions, audienceRestrictionCondition)) {
            orderedChildren.remove(audienceRestrictionCondition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeAudienceRestrictionConditions(java.util.Set)
     */
    public void removeAudienceRestrictionConditions(Set<AudienceRestrictionCondition> audienceRestrictionConditions) {
        
        for (AudienceRestrictionCondition audienceRestrictionCondition : audienceRestrictionConditions) {
            removeAudienceRestrictionCondition(audienceRestrictionCondition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeAllAudienceRestrictionConditions()
     */
    public void removeAllAudienceRestrictionConditions() {
        
        for (AudienceRestrictionCondition audienceRestrictionCondition : audienceRestrictionConditions) {
            removeAudienceRestrictionCondition(audienceRestrictionCondition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getDoNotCacheConditions()
     */
    public UnmodifiableOrderedSet<DoNotCacheCondition> getDoNotCacheConditions() {

        return new UnmodifiableOrderedSet<DoNotCacheCondition>(doNotCacheConditions);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#addAudienceRestrictionCondition(org.opensaml.saml1.core.DoNotCacheCondition)
     */
    public void addDoNotCacheCondition(DoNotCacheCondition doNotCacheCondition) throws IllegalAddException {
        
        if (addSAMLObject(doNotCacheConditions, doNotCacheCondition)) {
            orderedChildren.add(doNotCacheCondition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeDoNotCacheCondition(org.opensaml.saml1.core.DoNotCacheCondition)
     */
    public void removeDoNotCacheCondition(DoNotCacheCondition doNotCacheCondition) {
 
       if (removeSAMLObject(doNotCacheConditions, doNotCacheCondition)) {
           orderedChildren.remove(doNotCacheCondition);
       }
   }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeDoNotCacheConditions(java.util.Set)
     */
    public void removeDoNotCacheConditions(Set<DoNotCacheCondition> doNotCacheConditions) {
        for (DoNotCacheCondition doNotCacheCondition : doNotCacheConditions) {
            removeDoNotCacheCondition(doNotCacheCondition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeAllDoNotCacheConditions()
     */
    public void removeAllDoNotCacheConditions() {
        for (DoNotCacheCondition doNotCacheCondition : doNotCacheConditions) {
            removeDoNotCacheCondition(doNotCacheCondition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getConditions()
     */
    public UnmodifiableOrderedSet<Condition> getConditions() {
       
        return new UnmodifiableOrderedSet<Condition>(conditions);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#addAudienceRestrictionCondition(org.opensaml.saml1.core.Condition)
     */
    public void addCondition(Condition condition) throws IllegalAddException {
        if (addSAMLObject(conditions, condition)) {
            orderedChildren.add(condition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeCondition(org.opensaml.saml1.core.Condition)
     */
    public void removeCondition(Condition condition) {
        if (removeSAMLObject(conditions, condition)) {
            orderedChildren.remove(condition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeConditions(java.util.Set)
     */
    public void removeConditions(Set<Condition> conditions) {
        
        for (Condition condition : conditions) {
            removeCondition(condition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeAllConditions()
     */
    public void removeAllConditions() {

        for (Condition condition : conditions) {
            removeCondition(condition);
        }
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {

        return new UnmodifiableOrderedSet<SAMLObject>(orderedChildren);
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {
        // TODO Implement equals
        return false;
    }

}
