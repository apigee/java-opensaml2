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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * A list which indexes XMLObjects by their schema type and element QName for quick retrival based on those items. This
 * list does not allow null elements and is <strong>not</strong> synchronized.
 */
public class IndexedXMLObjectChildrenList<ElementType extends XMLObject> extends AbstractList<ElementType> {

    /** List of objects */
    private XMLObjectChildrenList<ElementType> objects;;

    /** Index of objects by type and name */
    private HashMap<QName, ArrayList<ElementType>> objectIndex;

    /**
     * Constructor
     */
    public IndexedXMLObjectChildrenList(XMLObject parent) {
        super();
        objects = new XMLObjectChildrenList<ElementType>(parent);
        objectIndex = new HashMap<QName, ArrayList<ElementType>>();
    }

    /**
     * Constructor
     * 
     * @param col collection to add to this list
     */
    public IndexedXMLObjectChildrenList(XMLObject parent, Collection<ElementType> col) {
        objects = new XMLObjectChildrenList<ElementType>(parent);
        objectIndex = new HashMap<QName, ArrayList<ElementType>>();
        addAll(col);
    }

    /**
     * Returns the number of elements in this list.
     * 
     * @return the number of elements in this list
     */
    public int size() {
        return objects.size();
    }

    /**
     * Returns the SAMLObject at the specified position in this list.
     * 
     * @param index the given index
     * 
     * @return the SAMLObject at the given index
     */
    public ElementType get(int index) {
        return objects.get(index);
    }

    /**
     * Retrieves all the SAMLObjects that have given schema type or element name
     * 
     * @param typeOrName the schema type or element name
     * 
     * @return list of SAMLObjects that have given schema type or element name or null
     */
    public List<ElementType> get(QName typeOrName) {
        return objectIndex.get(new QName(typeOrName.getNamespaceURI(), typeOrName.getLocalPart()));
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * 
     * @param index index of element to replace
     * @param element element to be stored at the specified position
     * 
     * @return the element previously at the specified position
     */
    public ElementType set(int index, ElementType element) {
        ElementType returnValue = objects.set(index, element);
        indexElement(element);
        return returnValue;
    }

    /**
     * Inserts the specified element at the specified position in this list. Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (adds one to their indices).
     * 
     * @param index index of element to add
     * @param element element to be stored at the specified position
     */
    public void add(int index, ElementType element) {
        objects.add(element);
        indexElement(element);
    }

    /**
     * Removes the element at the specified position in this list. Shifts any subsequent elements to the left (subtracts
     * one from their indices). Returns the element that was removed from the list
     * 
     * @param index the index of the element to remove
     */
    public ElementType remove(int index) {
        ElementType returnValue = objects.remove(index);

        ArrayList<ElementType> objects;
        QName type = returnValue.getSchemaType();
        if (type != null) {
            objects = objectIndex.get(type);
            objects.remove(returnValue);
        }

        objects = objectIndex.get(returnValue.getElementQName());
        objects.remove(returnValue);

        return returnValue;
    }

    /**
     * Indexes the given SAMLObject by type and element name.
     * 
     * @param element the SAMLObject to index
     */
    protected void indexElement(ElementType element) {
        QName type = element.getSchemaType();
        if (type != null) {
            indexElement(type, element);
        }

        indexElement(element.getElementQName(), element);
    }

    /**
     * Indexes the given SAMLobject by the given index.
     * 
     * @param index the index for the element
     * @param element the element to be indexed
     */
    protected void indexElement(QName index, ElementType element) {
        QName safeIndex = new QName(index.getNamespaceURI(), index.getLocalPart());
        ArrayList<ElementType> objects = objectIndex.get(safeIndex);
        if (objects == null) {
            objects = new ArrayList<ElementType>();
            objectIndex.put(safeIndex, objects);
        }

        objects.add(element);
    }
}