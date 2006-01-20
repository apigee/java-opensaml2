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

package org.opensaml.xml;

import javax.xml.namespace.QName;

import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

import junit.framework.TestCase;

/**
 * Test case for {@link org.opensaml.xml.util.IndexedXMLObjectChildrenList}. Note that this test only tests those
 * methods that modify the list because everything else is delegated to the
 * {@link org.opensaml.xml.util.XMLObjectChildrenList} which has it's own test cases that works all the other methods.
 * 
 */
public class IndexedXMLObjectChildrenListTest extends TestCase {

    /**
     * Test the add method to make sure it creates the index correctly.
     */
    public void testAdd() {
        SimpleXMLObject parentObject = new SimpleXMLObject();
        IndexedXMLObjectChildrenList<SimpleXMLObject> indexedList = new IndexedXMLObjectChildrenList<SimpleXMLObject>(
                parentObject);

        SimpleXMLObject child1 = new SimpleXMLObject();
        QName child1Type = new QName("example.org/ns/child1", "FooType");
        child1.setSchemaType(child1Type);
        indexedList.add(child1);
        assertEquals("List gotten by element QName index should have had 1 element", 1, indexedList.get(
                child1.getElementQName()).size());
        assertEquals("List gotten by type QName index should have had 1 element", 1, indexedList.get(
                child1.getSchemaType()).size());

        SimpleXMLObject child2 = new SimpleXMLObject();
        indexedList.add(child2);
        assertEquals("List gotten by element QName index should have had 1 element", 2, indexedList.get(
                child1.getElementQName()).size());
        assertEquals("List gotten by type QName index should have had 1 element", 1, indexedList.get(
                child1.getSchemaType()).size());
    }

    /**
     * Test the set method to make sure it removes items that have been replaced from the index.
     */
    public void testSet() {
        SimpleXMLObject parentObject = new SimpleXMLObject();
        IndexedXMLObjectChildrenList<SimpleXMLObject> indexedList = new IndexedXMLObjectChildrenList<SimpleXMLObject>(
                parentObject);

        SimpleXMLObject child1 = new SimpleXMLObject();
        QName child1Type = new QName("example.org/ns/child1", "FooType");
        child1.setSchemaType(child1Type);
        indexedList.add(child1);

        SimpleXMLObject child2 = new SimpleXMLObject();
        indexedList.set(0, child2);

        assertEquals("List gotten by element QName index should have had 1 element", 1, indexedList.get(
                child1.getElementQName()).size());
        assertNull("List gotten by type QName index should have been null", indexedList.get(child1.getSchemaType()));
    }

    /**
     * Test to ensure removed items are removed from the index.
     */
    public void testRemove() {
        SimpleXMLObject parentObject = new SimpleXMLObject();
        IndexedXMLObjectChildrenList<SimpleXMLObject> indexedList = new IndexedXMLObjectChildrenList<SimpleXMLObject>(
                parentObject);

        SimpleXMLObject child1 = new SimpleXMLObject();
        QName child1Type = new QName("example.org/ns/child1", "FooType");
        child1.setSchemaType(child1Type);
        indexedList.add(child1);

        SimpleXMLObject child2 = new SimpleXMLObject();
        indexedList.add(child2);

        indexedList.remove(child1);
        assertEquals("List gotten by element QName index should have had 1 element", 1, indexedList.get(
                child1.getElementQName()).size());
        assertNull("List gotten by type QName index should have been null", indexedList.get(child1.getSchemaType()));
    }
}