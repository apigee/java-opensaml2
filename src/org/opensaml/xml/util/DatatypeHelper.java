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

package org.opensaml.xml.util;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

/**
 * Helper class for working with various datatypes.
 */
public class DatatypeHelper {

    /** Constant representing the UTC Timezone */
    public final static int UTC_TIMEZONE = 0;

    /** JAXP DatatypeFactory */
    private static DatatypeFactory dataTypeFactory;

    /**
     * Gets a static instance of a JAXP DatatypeFactory.
     * 
     * @return the factory or null if the factory could not be created
     */
    public static DatatypeFactory getDataTypeFactory() {
        if (dataTypeFactory == null) {
            try {
                dataTypeFactory = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException e) {
                // do nothing
            }
        }

        return dataTypeFactory;
    }

    /**
     * A "safe" null/empty check for strings.
     * 
     * @param s The string to check
     * 
     * @return true if the string is null or the trimmed string is length zero
     */
    public static boolean isEmpty(String s) {
        if (s != null) {
            s = s.trim();
            if (s.length() > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compares two strings for equality, allowing for nulls
     * 
     * @param s1 The first operand
     * @param s2 The second operand
     * 
     * @return true if both are null or both are non-null and the same strng value
     */
    public static <T> boolean safeEquals(T s1, T s2) {
        if (s1 == null || s2 == null) {
            return s1 == s2;
        }

        return s1.equals(s2);
    }

    /**
     * A safe string trim that handles nulls.
     * 
     * @param s the string to trim
     * 
     * @return the trimmed string or null if the given string was null
     */
    public static String safeTrim(String s) {
        if (s != null) {
            return s.trim();
        }

        return null;
    }

    /**
     * Removes preceeding or proceeding whitespace from a string or return null if the string is null or of zero length
     * after trimming (i.e. if the string only contained whitespace).
     * 
     * @param s the string to trim
     * 
     * @return the trimmed string or null
     */
    public static String safeTrimOrNullString(String s) {
        if (s != null) {
            s = s.trim();
            if (s.length() > 0) {
                return s;
            }
        }

        return null;
    }

    /**
     * Converts a lexical duration, as defined by XML Schema 1.0, into milliseconds.
     * 
     * @param duration lexical duration representation
     * 
     * @return duration in milliseconds
     */
    public static long durationToLong(String duration) {
        Duration xmlDuration = getDataTypeFactory().newDuration(duration);
        return xmlDuration.getTimeInMillis(new GregorianCalendar());
    }

    /**
     * Converts a duration in milliseconds to a lexical duration, as defined by XML Schema 1.0.
     * 
     * @param duration the duration
     * 
     * @return the lexical representation
     */
    public static String longToDuration(long duration) {
        Duration xmlDuration = getDataTypeFactory().newDuration(duration);
        return xmlDuration.toString();
    }
}