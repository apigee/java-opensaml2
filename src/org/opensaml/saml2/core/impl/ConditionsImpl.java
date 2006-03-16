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

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.Condition;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.OneTimeUse;
import org.opensaml.saml2.core.ProxyRestriction;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Conditions}
 */
public class ConditionsImpl extends AbstractAssertionSAMLObject implements Conditions {

    /** A Condition */
    private IndexedXMLObjectChildrenList<Condition> condition;

    /** Not Before condition */
    private DateTime notBefore;

    /** Not On Or After condition */
    private DateTime notOnOrAfter;

    /** Constructor */
    protected ConditionsImpl() {
        super(Conditions.LOCAL_NAME);

        condition = new IndexedXMLObjectChildrenList<Condition>(this);
    }

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected ConditionsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml2.core.Conditions#getCondition()
     */
    public List<Condition> getConditions() {
        return condition;
    }

    /*
     * @see org.opensaml.saml2.core.Conditions#getAudienceRestriction()
     */
    public List<AudienceRestriction> getAudienceRestrictions() {
        QName conditionQName = new QName(SAMLConstants.SAML20_NS, AudienceRestriction.LOCAL_NAME,
                SAMLConstants.SAML20_PREFIX);
        return (List<AudienceRestriction>) condition.subList(conditionQName);
    }

    /*
     * @see org.opensaml.saml2.core.Conditions#getOneTimeUse()
     */
    public OneTimeUse getOneTimeUse() {
        QName conditionQName = new QName(SAMLConstants.SAML20_NS, OneTimeUse.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        List<OneTimeUse> list = (List<OneTimeUse>) condition.subList(conditionQName);
        if (list == null || list.size() == 0) {
            return null;
        } else
            return list.get(0);
    }

    /*
     * @see org.opensaml.saml2.core.Conditions#getProxyRestriction()
     */
    public ProxyRestriction getProxyRestriction() {
        QName conditionQName = new QName(SAMLConstants.SAML20_NS, ProxyRestriction.LOCAL_NAME,
                SAMLConstants.SAML20_PREFIX);
        List<ProxyRestriction> list = (List<ProxyRestriction>) condition.subList(conditionQName);
        if (list == null || list.size() == 0) {
            return null;
        } else
            return list.get(0);
    }

    /*
     * @see org.opensaml.saml2.core.Conditions#getNotBefore()
     */
    public DateTime getNotBefore() {
        return notBefore;
    }

    /*
     * @see org.opensaml.saml2.core.Conditions#setNotBefore(org.joda.time.DateTime)
     */
    public void setNotBefore(DateTime newNotBefore) {
        this.notBefore = prepareForAssignment(this.notBefore, newNotBefore);
    }

    /*
     * @see org.opensaml.saml2.core.Conditions#getNotOnOrAfter()
     */
    public DateTime getNotOnOrAfter() {
        return notOnOrAfter;
    }

    /*
     * @see org.opensaml.saml2.core.Conditions#setNotOnOrAfter(org.joda.time.DateTime)
     */
    public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
        this.notOnOrAfter = prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.addAll(condition);

        return Collections.unmodifiableList(children);
    }
}