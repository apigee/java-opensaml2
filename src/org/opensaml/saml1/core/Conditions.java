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

package org.opensaml.saml1.core;

import java.util.GregorianCalendar;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.common.util.xml.XMLConstants;

/**
 * This interface defines how the object representing a SAML1 <code> Conditions</code> element behaves. 
 */
public interface Conditions extends SAMLObject {

    /** Element name, no namespace. */

    public final static String LOCAL_NAME = "Conditions";

    /** QName for this element */

    public final static QName QNAME = new QName(XMLConstants.SAML1_NS, LOCAL_NAME, XMLConstants.SAML1_PREFIX);

    /** Name for the NotBefore attribute. */
    public final static String NOTBEFORE_ATTRIB_NAME = "NotBefore";

    /** Name for the NotBefore attribute. */
    public final static String NOTONORAFTER_ATTRIB_NAME = "NotOnOrAfter";

    /** Return the value of the NotBefore attribute. */

    public GregorianCalendar getNotBefore();

    /** Set the value of the NotBefore attribute. */

    public void setNotBefore(GregorianCalendar notBefore);

    /** Return the value of the NotOnOrAfter attribute. */

    public GregorianCalendar getNotOnOrAfter();

    /** Set the value of the NotOnOrAfter attribute. */

    public void setNotOnOrAfter(GregorianCalendar notOnOrAfter);

    /**
     * Return the Set representing all the <code> AudienceRestrictionCondition </code> sub elements.
     */
    public UnmodifiableOrderedSet<AudienceRestrictionCondition> getAudienceRestrictionConditions();

    /** 
     * Add a single <code> AudienceRestrictionCondition </code> to the set (if appropriate). 
     * 
     * @param audienceRestrictionCondition what to add
     * 
     * @throws IllegalAddException if the object has already been put into an SAMLObject
     */
    public void addAudienceRestrictionCondition(AudienceRestrictionCondition audienceRestrictionCondition)
            throws IllegalAddException;

    /** 
     * Remove a single <code> AudienceRestrictionConditions</code> from the set.
     *
     * @param audienceRestrictionCondition what to remove
     */
    public void removeAudienceRestrictionCondition(AudienceRestrictionCondition audienceRestrictionCondition);

    /** 
     * Remove the presented AudienceRestrictionConditionss  from the set.
     *
     * @param audienceRestrictionConditions which audienceRestrictionConditions to remove
     */
    public void removeAudienceRestrictionConditions(Set<AudienceRestrictionCondition> audienceRestrictionConditions);

    /** 
     * Remove the all the AudienceRestrictionConditions from the set.
     */

    public void removeAllAudienceRestrictionConditions();

    /**
     * Return the Set representing all the <code> DoNotCacheCondition </code> sub elements.
     */
    public UnmodifiableOrderedSet<DoNotCacheCondition> getDoNotCacheConditions();

    /** 
     * Add a single <code> DoNotCacheCondition </code> to the set (if appropriate). 
     * 
     * @param doNotCacheCondition what to add
     * 
     * @throws IllegalAddException if the object has already been put into an SAMLObject
     */
    public void addDoNotCacheCondition(DoNotCacheCondition doNotCacheCondition) throws IllegalAddException;

    /** 
     * Remove a single <code> DoNotCacheConditions</code> from the set.
     *
     * @param doNotCacheCondition what to remove
     */
    public void removeDoNotCacheCondition(DoNotCacheCondition doNotCacheCondition);

    /** 
     * Remove the presented DoNotCacheConditions  from the set.
     *
     * @param doNotCacheConditions which DoNotCacheCondition to remove
     */
    public void removeDoNotCacheConditions(Set<DoNotCacheCondition> doNotCacheConditions);

    /** 
     * Remove the all the DoNotCacheConditions from the set.
     */

    public void removeAllDoNotCacheConditions();

    /**
     * Return the Set representing all the <code> Condition </code> sub elements.
     */
    public UnmodifiableOrderedSet<Condition> getConditions();

    /** 
     * Add a single <code> Condition </code> to the set (if appropriate). 
     * 
     * @param Condition what to add
     * 
     * @throws IllegalAddException if the object has already been put into an SAMLObject
     */
    public void addCondition(Condition condition) throws IllegalAddException;

    /** 
     * Remove a single <code> Conditions</code> from the set.
     *
     * @param condition what to remove
     */
    public void removeCondition(Condition condition);

    /** 
     * Remove the presented Conditions  from the set.
     *
     * @param conditions which Conditions to remove
     */
    public void removeConditions(Set<Condition> conditions);

    /** 
     * Remove the all the Conditions from the set.
     */

    public void removeAllConditions();

}
