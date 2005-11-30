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

import java.util.Comparator;

import javax.xml.namespace.QName;

/**
 * Simple comparator for comparing namespaces represented by QNames.  The comparison 
 * is based on the prefix and namespace URI.
 *
 */
public class NamespaceComparator implements Comparator<QName> {

    /**
     * <code>qname1</code> is greater than <code>qname2</code> if it's prefix is lexically greater or, 
     * if the prefixes are equal, the namespace URI is lexically greater, conversly <code>qname1</code> 
     * is less than <code>qname2</code> if it's prefix is lexically less than or, if the prexfixes are equal,
     * the namespace URI is lexically less than.  <code>qname1</code> is equal to <code>qname2</code> if, 
     * and only if, both QNames have lexically equal prefixes and namespace URIs.
     * 
     * @param qname1 the first QName being compared
     * @param qname2 the second QName being compared
     * 
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     */
    public int compare(QName qname1, QName qname2) {
        int prefixCompare = qname1.getPrefix().compareTo(qname2.getPrefix());
        if(prefixCompare == 0 ) {
            return qname1.getNamespaceURI().compareTo(qname2.getNamespaceURI());
        }
        
        return prefixCompare;
    }
}
