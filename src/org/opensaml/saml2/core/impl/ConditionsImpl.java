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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.Condition;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.OneTimeUse;
import org.opensaml.saml2.core.ProxyRestriction;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Conditions}
 */
public class ConditionsImpl extends AbstractSAMLObject implements Conditions {

    /** A Condition */
    private XMLObjectChildrenList<Condition> condition;

    /** Audience Restriction condition */
    private XMLObjectChildrenList<AudienceRestriction> audienceRestriction;

    /** One Time Use condition */
    private OneTimeUse oneTimeUse;

    /** Proxy Restriction condition */
    private XMLObjectChildrenList<ProxyRestriction> proxyRestriction;

    /** Not Before condition */
    private DateTime notBefore;

    /** Not On Or After condition */
    private DateTime notOnOrAfter;

    /** Constructor */
    public ConditionsImpl() {
        super(SAMLConstants.SAML20_NS, Conditions.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
        
        condition = new XMLObjectChildrenList<Condition>(this);
        audienceRestriction = new XMLObjectChildrenList<AudienceRestriction>(this);
        proxyRestriction = new XMLObjectChildrenList<ProxyRestriction>(this);
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#getCondition()
     */
    public List<Condition> getConditions() {
        return condition;
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#getAudienceRestriction()
     */
    public List<AudienceRestriction> getAudienceRestrictions() {
        return audienceRestriction;
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#getOneTimeUse()
     */
    public OneTimeUse getOneTimeUse() {
        return oneTimeUse;
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#setOneTimeUse(org.opensaml.saml2.core.OneTimeUse)
     */
    public void setOneTimeUse(OneTimeUse newOneTimeUse) {
        this.oneTimeUse = prepareForAssignment(this.oneTimeUse, newOneTimeUse);
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#getProxyRestriction()
     */
    public List<ProxyRestriction> getProxyRestrictions() {
        return proxyRestriction;
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#getNotBefore()
     */
    public DateTime getNotBefore() {
        return notBefore;
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#setNotBefore(org.joda.time.DateTime)
     */
    public void setNotBefore(DateTime newNotBefore) {
        this.notBefore = prepareForAssignment(this.notBefore, newNotBefore.withZone(DateTimeZone.UTC));
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#getNotOnOrAfter()
     */
    public DateTime getNotOnOrAfter() {
        return notOnOrAfter;
    }

    /**
     * @see org.opensaml.saml2.core.Conditions#setNotOnOrAfter(org.joda.time.DateTime)
     */
    public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
        this.notOnOrAfter = prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter.withZone(DateTimeZone.UTC));
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();

        children.addAll(condition);
        children.addAll(audienceRestriction);
        children.add(oneTimeUse);
        children.addAll(proxyRestriction);

        return Collections.unmodifiableList(children);
    }
}