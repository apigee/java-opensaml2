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
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.impl.TypeNameIndexedSAMLObjectList;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml1.core.Condition;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.DoNotCacheCondition;
import org.opensaml.xml.IllegalAddException;

/**
 * This is a concrete implementation of the {@link org.opensaml.saml1.core.Conditions} interface.
 */
public class ConditionsImpl extends AbstractSAMLObject implements Conditions {

    /** Value saved in the NotBefore attribute */
    private GregorianCalendar notBefore;

    /** Value saved in the NotOnOrAfter attribute */
    private GregorianCalendar notOnOrAfter;

    /** Set containing all the Conditions */
    private final TypeNameIndexedSAMLObjectList<Condition> conditions = new TypeNameIndexedSAMLObjectList<Condition>();

    /**
     * Constructor
     */
    public ConditionsImpl() {
        super(SAMLConstants.SAML1_NS, Conditions.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
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
        this.notBefore = prepareForAssignment(this.notBefore, notBefore);
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
        this.notOnOrAfter = prepareForAssignment(this.notOnOrAfter, notOnOrAfter);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getConditions()
     */
    public List<Condition> getConditions() {
        if (conditions.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(conditions);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getConditions(javax.xml.namespace.QName)
     */
    public List<Condition> getConditions(QName typeOrName) {
        
        List<Condition> list = conditions.get(typeOrName);
        if (list == null || list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#addAudienceRestrictionCondition(org.opensaml.saml1.core.Condition)
     */
    public void addCondition(Condition condition) throws IllegalAddException {
        addXMLObject(conditions, condition);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeConditions(java.util.List)
     */
    public void removeConditions(List<Condition> conditions) {
        removeXMLObjects(this.conditions, conditions);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#removeCondition(org.opensaml.saml1.core.Condition)
     */
    public void removeCondition(Condition condition) {
        removeXMLObject(conditions, condition);
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
     * @see org.opensaml.saml1.core.Conditions#removeAllConditions(javax.xml.namespace.QName)
     */
    public void removeAllConditions(QName typeOrName) {
        for (Condition condition : conditions.get(typeOrName)) {
            removeCondition(condition);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getAudienceRestrictionConditions()
     */
    public List<Condition> getAudienceRestrictionConditions() {
        QName conditionQName = new QName(SAMLConstants.SAML1_NS, AudienceRestrictionCondition.LOCAL_NAME);
        return getConditions(conditionQName);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getDoNotCacheConditions()
     */
    public List<Condition> getDoNotCacheConditions() {
        QName conditionQName = new QName(SAMLConstants.SAML1_NS, DoNotCacheCondition.LOCAL_NAME);
        return getConditions(conditionQName);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        if (conditions.size() == 0) {
            return null;
        }
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        children.addAll(conditions);
        return Collections.unmodifiableList(children);
    }
}