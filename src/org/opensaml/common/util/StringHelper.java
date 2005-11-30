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

package org.opensaml.common.util;

/**
 * A collection of helper methods.
 *
 */
public class StringHelper {
    /**
     *  A "safe" null/empty check for strings.
     * 
     * @param s     The string to check
     * 
     * @return  true if the string is null or the trimmed string is length zero
     */
    public static boolean isEmpty(String s) {
        if(s != null) {
            s = s.trim();
            if(s.length() > 0) {
                return false;
            }
        }
        
        return true;
    }

    /**
     *  A "safe" assignment function for strings that blocks the empty string
     * 
     * @param s the string to check
     * 
     * @return null if the given string is empty, the given trimmed given string if it isn't 
     */
    public static String assign(String s) {
        if(isEmpty(s)) {
            return null;
        }
        
        return s;
    }
    
    /**
     *  Compares two strings for equality, allowing for nulls
     * 
     * @param s1    The first operand
     * @param s2    The second operand
     * 
     * @return  true if both are null or both are non-null and the same strng value
     */
    public static boolean safeEquals(String s1, String s2) {
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
    public static String safeTrim(String s){
        if(s != null){
            return s.trim();
        }
        
        return null;
    }
    
    /**
     * Removes preceeding or proceeding whitespace from a string or return null if the 
     * string is null or of zero length after trimming (i.e. if the string only contained whitespace).
     * 
     * @param s the string to trim
     * 
     * @return the trimmed string or null
     */
    public static String safeTrimOrNullString(String s){
        if(s != null){
            s = s.trim();
            if(s.length() > 0){
                return s;
            }
        }
        
        return null;
    }
}
