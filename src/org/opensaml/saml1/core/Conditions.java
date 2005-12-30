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
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.xml.IllegalAddException;

/**
 * This interface defines how the object representing a SAML1 <code> Conditions</code> element behaves.
 */
public interface Conditions extends SAMLObject {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "Conditions";

    /** Name for the NotBefore attribute. */
    public final static String NOTBEFORE_ATTRIB_NAME = "NotBefore";

    /** Name for the NotBefore attribute. */
    public final static String NOTONORAFTER_ATTRIB_NAME = "NotOnOrAfter";

    /** Return the value of the NotBefore attribute. */
    public GregorianCalendar getNotBefore();

    /** List the value of the NotBefore attribute. */
    public void setNotBefore(GregorianCalendar notBefore);

    /** Return the value of the NotOnOrAfter attribute. */
    public GregorianCalendar getNotOnOrAfter();

    /** List the value of the NotOnOrAfter attribute. */
    public void setNotOnOrAfter(GregorianCalendar notOnOrAfter);
    
    /**
     * Return the List representing all the <code> Condition </code> sub elements.
     */
    public List<Condition> getConditions();
    
    /**
     * Return the List representing all the <code>Condition</code>s with the given schema type or element name.
     * 
     * @param typeOrName the schema type or element name
     */
    public List<Condition> getConditions(QName typeOrName);

    /**
     * Add a single <code> Condition </code> to the List (if appropriate).
     * 
     * @param Condition what to add
     * 
     * @throws IllegalAddException if the object has already been put into an SAMLObject
     */
    public void addCondition(Condition condition) throws IllegalAddException;

    /**
     * Remove a single <code> Conditions</code> from the List.
     * 
     * @param condition what to remove
     */
    public void removeCondition(Condition condition);

    /**
     * Remove the presented Conditions from the List.
     * 
     * @param conditions which Conditions to remove
     */
    public void removeConditions(List<Condition> conditions);

    /**
     * Remove all the Conditions from the List.
     */
    public void removeAllConditions();
    
    /**
     * Removes all the Conditions or the given schema type or element name.
     * 
     * @param typeOrName the schema type or element name
     */
    public void removeAllConditions(QName typeOrName);

    /**
     * Return the List representing all the <code> AudienceRestrictionCondition </code> sub elements.
     */
    public List<Condition> getAudienceRestrictionConditions();

    /**
     * Return the List representing all the <code> DoNotCacheCondition </code> sub elements.
     */
    public List<Condition> getDoNotCacheConditions();
}