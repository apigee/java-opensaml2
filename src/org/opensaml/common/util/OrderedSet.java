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

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * LinkedHashSet that does not allow null elements.
 */
public class OrderedSet<E> extends LinkedHashSet<E> implements Set<E> {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 4543090575469111692L;
    
    /**
     * Constructor
     */
    public OrderedSet() {
        
    }
    
    /**
     * Copy constructor
     *
     * @param set set to copy
     */
    public OrderedSet(OrderedSet<? extends E> set) {
        super(set);
    }

    /**
     * Adds the given element to the collection.
     * 
     * @param element the element to be added to the set
     * 
     * @return true if the set was changed as a result of this call, false if not
     * 
     * @throws ClassCastException if the class of the specified element prevents it from being added to this collection
     * @throws IllegalArgumentException if some aspect of this element prevents it from being added to this collection
     */
    public boolean add(E element) throws ClassCastException, IllegalArgumentException {
        if (element == null) {
            return false;
        }

        return super.add(element);
    }
}