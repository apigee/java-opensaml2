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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * An unmodifiable version of {@link org.opensaml.common.util.OrderedSet}.
 *
 * @param <E> the object type of the elements of the set
 */
public class UnmodifiableOrderedSet<E> implements Set<E> {

    /** OrderSet backing */
    private OrderedSet<E> set;
    
    /**
     * Constructor
     *
     * @param set the set to be wrapped so that it may not be modified
     */
    public UnmodifiableOrderedSet(OrderedSet<E> set) {
        this.set = new OrderedSet<E>(set);
    }

    /*
     * @see java.util.Collection#size()
     */
    public int size() {
        return set.size();
    }

    /*
     * @see java.util.Collection#isEmpty()
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /*
     * @see java.util.Collection#contains(java.lang.Object)
     */
    public boolean contains(Object object) {
        return set.contains(object);
    }
    
    /*
     * @see java.util.Collection#containsAll(java.util.Collection)
     */
    public boolean containsAll(Collection<?> collection) {
        return set.containsAll(collection);
    }

    /*
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<E> iterator() {
        return new UnmodifiableIterator<E>(set.iterator());
    }

    /*
     * @see java.util.Collection#toArray()
     */
    public Object[] toArray() {
        return set.toArray();
    }

    /*
     * @see java.util.Collection#toArray(T[])
     */
    public <T> T[] toArray(T[] array) {
        return set.toArray(array);
    }

    /**
     * Unsupported operation.  This always throwns UnsupportedOperationException.
     */
    public boolean add(E arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation.  This always throwns UnsupportedOperationException.
     */
    public boolean remove(Object arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation.  This always throwns UnsupportedOperationException.
     */
    public boolean addAll(Collection<? extends E> arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation.  This always throwns UnsupportedOperationException.
     */
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation.  This always throwns UnsupportedOperationException.
     */
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation.  This always throwns UnsupportedOperationException.
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Iterator that does not allow modification of data structure that it is iterating over
     *
     * @param <O> the object type being iterated over
     */
    public class UnmodifiableIterator<O> implements Iterator<O>{

        /** wrapped iterator */
        private Iterator<O> itr;
        
        /**
         * Constructor
         *
         * @param i iterator being wrapped
         */
        public UnmodifiableIterator(Iterator<O> i) {
            itr = i;
        }
        
        /*
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            return itr.hasNext();
        }

        /*
         * @see java.util.Iterator#next()
         */
        public O next() {
            return itr.next();
        }

        /**
         * Unsupported operation.  This always throwns UnsupportedOperationException.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
