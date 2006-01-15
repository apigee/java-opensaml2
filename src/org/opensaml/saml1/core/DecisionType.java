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
package org.opensaml.saml1.core;


/**
 * Encapsulation of the SAML1 DecisionType
 */
public enum DecisionType {

    Permit,
    Deny,
    Indeterminate;

    /*    private final static String PERMIT_NAME = "Permit";
    private final static String DENY_NAME = "Deny";
    private final static String INDETERMINATE_NAME = "Indeterminate";
    
    public static DecisionType stringToDecisionType(String s) {
        if (PERMIT_NAME.equals(s)) {
            return Permit;
        } else if (DENY_NAME.equals(s)) {
            return Deny;
        } else if (INDETERMINATE_NAME.equals(s)) {
            return Indeterminate;
        }
        DecisionType st = Deny;
        st.toString();
        // LOG stuff
        // throw stuff
        return Indeterminate;
    }
 */   
    
}
