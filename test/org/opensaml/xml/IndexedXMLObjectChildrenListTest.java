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

import java.util.List;

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
    
    /**
     * Tests the sublist functionality.
     */
    public void testSublist() {
        SimpleXMLObject parentObject = new SimpleXMLObject();
        IndexedXMLObjectChildrenList<XMLObject> indexedList = new IndexedXMLObjectChildrenList<XMLObject>(
                parentObject);

        QName type1 = new QName("example.org/ns/type1", "Type1");
        QName type2 = new QName("example.org/ns/type2", "Type2");
        
        SimpleXMLObject child1 = new SimpleXMLObject();
        child1.setSchemaType(type1);
        indexedList.add(child1);

        SimpleXMLObject child2 = new SimpleXMLObject();
        child2.setSchemaType(type2);
        indexedList.add(child2);
        
        SimpleXMLObject child3 = new SimpleXMLObject();
        indexedList.add(child3);
        
        SimpleXMLObject child4 = new SimpleXMLObject();
        child4.setSchemaType(type2);
        indexedList.add(child4);
        
        SimpleXMLObject child5 = new SimpleXMLObject();
        child5.setSchemaType(type1);
        indexedList.add(child5);
        
        SimpleXMLObject child6 = new SimpleXMLObject();
        child6.setSchemaType(type1);
        indexedList.add(child6);
        
        List<SimpleXMLObject> elementNameSublist = (List<SimpleXMLObject>) indexedList.subList(child1.getElementQName());
        List<SimpleXMLObject> type1SchemaSublist = (List<SimpleXMLObject>) indexedList.subList(type1);
        List<SimpleXMLObject> type2SchemaSublist = (List<SimpleXMLObject>) indexedList.subList(type2);
        
        assertEquals("Element name index sublist did not have expected number of elements", 6, elementNameSublist.size());
        assertEquals("Schema Type1 index sublist did not have expected number of elements", 3, type1SchemaSublist.size());
        assertEquals("Schema Type2 index sublist did not have expected number of elements", 2, type2SchemaSublist.size());
        
        SimpleXMLObject child7 = new SimpleXMLObject();
        child7.setSchemaType(type1);
        type1SchemaSublist.add(child7);
        
        assertEquals("Child added to sublist did not have parent properly set", parentObject, child7.getParent());
        assertEquals("Element name index sublist did not have expected number of elements", 7, elementNameSublist.size());
        assertEquals("Schema Type1 index sublist did not have expected number of elements", 4, type1SchemaSublist.size());
        assertEquals("Schema Type2 index sublist did not have expected number of elements", 2, type2SchemaSublist.size());
        
        SimpleXMLObject child8 = new SimpleXMLObject();
        child8.setSchemaType(type2);
        SimpleXMLObject replacedObject = type2SchemaSublist.set(0, child8);
        
        assertEquals("Element name index sublist did not have expected number of elements", 7, elementNameSublist.size());
        assertEquals("Schema Type1 index sublist did not have expected number of elements", 4, type1SchemaSublist.size());
        assertEquals("Schema Type2 index sublist did not have expected number of elements", 2, type2SchemaSublist.size());
        assertEquals("Replaced object was not expected object", child2, replacedObject);
    }
}