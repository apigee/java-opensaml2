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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml1.core.Condition;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.DoNotCacheCondition;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * This is a concrete implementation of the {@link org.opensaml.saml1.core.Conditions} interface.
 */
public class ConditionsImpl extends AbstractAssertionSAMLObject implements Conditions {

    /** Value saved in the NotBefore attribute */
    private DateTime notBefore;

    /** Value saved in the NotOnOrAfter attribute */
    private DateTime notOnOrAfter;

    /** Set containing all the Conditions */
    private final IndexedXMLObjectChildrenList<Condition> conditions = new IndexedXMLObjectChildrenList<Condition>(this);

    /**
     * Hidden Constructor
     * @deprecated
     */
    private ConditionsImpl() {
        super(Conditions.LOCAL_NAME, null);
    }

    /**
     * Constructor
     *
     * @param version which version to create 
     */
    protected ConditionsImpl(SAMLVersion version) {
        super(Conditions.LOCAL_NAME, version);
    }
    
    /*
     * @see org.opensaml.saml1.core.Conditions#getNotBefore()
     */
    public DateTime getNotBefore() {
        return notBefore;
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#setNotBefore()
     */
    public void setNotBefore(DateTime notBefore) {
        this.notBefore = prepareForAssignment(this.notBefore, notBefore);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getNotOnOrAfter()
     */
    public DateTime getNotOnOrAfter() {
        return notOnOrAfter;
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#setNotOnOrAfter()
     */
    public void setNotOnOrAfter(DateTime notOnOrAfter) {
        this.notOnOrAfter = prepareForAssignment(this.notOnOrAfter, notOnOrAfter);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getConditions()
     */
    public List<Condition> getConditions() {
        return conditions;
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getConditions(javax.xml.namespace.QName)
     */
    public List<Condition> getConditions(QName typeOrName) {
        return (List<Condition>) conditions.subList(typeOrName);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getAudienceRestrictionConditions()
     */
    public List<AudienceRestrictionCondition> getAudienceRestrictionConditions() {
        QName qname = new QName(SAMLConstants.SAML1_NS, AudienceRestrictionCondition.LOCAL_NAME);
        return (List<AudienceRestrictionCondition>) conditions.subList(qname);
    }

    /*
     * @see org.opensaml.saml1.core.Conditions#getDoNotCacheConditions()
     */
    public List<DoNotCacheCondition> getDoNotCacheConditions() {
        QName qname = new QName(SAMLConstants.SAML1_NS, DoNotCacheCondition.LOCAL_NAME);
        return (List<DoNotCacheCondition>) conditions.subList(qname);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        if (conditions.size() == 0) {
            return null;
        }
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(conditions);
        return Collections.unmodifiableList(children);
    }
}