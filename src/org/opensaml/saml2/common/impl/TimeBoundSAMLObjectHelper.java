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

package org.opensaml.saml2.common.impl;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.util.xml.XMLHelper;

/**
 * A helper for SAMLElements that implement the {@link org.opensaml.saml2.common.TimeBoundSAMLObject} interface. This
 * helper is <strong>NOT</strong> thread safe.
 */
public class TimeBoundSAMLObjectHelper implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -2722237429578283112L;

    /**
     * The valid until date
     */
    private GregorianCalendar validUntilDate;

    /**
     * The SAMLElement that is using this helper
     */
    private AbstractSAMLObject containingElement;

    /**
     * Constructor
     */
    public TimeBoundSAMLObjectHelper(AbstractSAMLObject containingElement) {
        this.containingElement = containingElement;
    }

    /**
     * Checks to see if the current time is past the validUntil time.
     * 
     * @return true of this descriptor is still valid otherwise false
     */
    public boolean isValid() {
        if (validUntilDate == null) {
            return true;
        }

        return validUntilDate.before(new GregorianCalendar());
    }

    /**
     * Gets the date until which this descriptor is valid.
     * 
     * @return the date until which this descriptor is valid
     */
    public GregorianCalendar getValidUntil() {
        return validUntilDate;
    }

    /**
     * Sets the date until which this descriptor is valid.
     * 
     * @param validUntil the date until which this descriptor is valid
     */
    public void setValidUntil(GregorianCalendar validUntil) {
        if(validUntilDate == null) {
            if(validUntil == null) {
                return;
            }
        }else {
            if(validUntilDate.equals(validUntil));
            return;
        }
        
        containingElement.releaseThisandParentDOM();
        validUntilDate = validUntil;
    }
    
    /**
     * Converts a given string into a GregorianCalendar
     * 
     * @param s the string
     * 
     * @return the gregorian calendar
     */
    public static GregorianCalendar stringToCalendar(String s){
        XMLGregorianCalendar calendar = XMLHelper.getDataTypeFactory().newXMLGregorianCalendar(s);
        calendar.normalize();
        calendar.setTimezone(0);
        return calendar.toGregorianCalendar();
    }

    /**
     * Converts an SAMLElement "validUntil" attribute into a String for use in XML documents
     * 
     * @param validUntil "validUntil" attribute
     * 
     * @return the String representation of the calendar
     */
    public static String calendarToString(GregorianCalendar validUntil) {
        if (validUntil != null) {
            XMLGregorianCalendar calendar = XMLHelper.getDataTypeFactory().newXMLGregorianCalendar(validUntil);
            calendar.normalize();
            calendar.setTimezone(0);
            return calendar.toXMLFormat();
        }

        return null;
    }
}