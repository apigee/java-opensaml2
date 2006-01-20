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
public class IndexedXMLObjectChildrenList<ElementType extends XMLObject> extends XMLObjectChildrenList<ElementType> {

    /** Index of objects by type and name */
    private HashMap<QName, ArrayList<ElementType>> objectIndex;

    /**
     * Constructor
     */
    public IndexedXMLObjectChildrenList(XMLObject parent) {
        super(parent);
        objectIndex = new HashMap<QName, ArrayList<ElementType>>();
    }

    /**
     * Constructor
     * 
     * @param col collection to add to this list
     */
    public IndexedXMLObjectChildrenList(XMLObject parent, Collection<ElementType> col) {
        super(parent);
        objectIndex = new HashMap<QName, ArrayList<ElementType>>();
        addAll(col);
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
        ElementType returnValue = super.set(index, element);

        removeElementFromIndex(returnValue);

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
        super.add(index, element);
        indexElement(element);
    }

    /**
     * Removes the element at the specified position in this list. Shifts any subsequent elements to the left (subtracts
     * one from their indices). Returns the element that was removed from the list
     * 
     * @param index the index of the element to remove
     */
    public ElementType remove(int index) {
        ElementType returnValue = super.remove(index);

        removeElementFromIndex(returnValue);

        return returnValue;
    }

    /**
     * Indexes the given SAMLObject by type and element name.
     * 
     * @param element the SAMLObject to index
     */
    protected void indexElement(ElementType element) {
        if (element == null) {
            return;
        }

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

    /**
     * Removes the given element from the schema type and element qname index.
     * 
     * @param element the element to remove from the index
     */
    protected void removeElementFromIndex(ElementType element) {
        if (element == null) {
            return;
        }

        QName type = element.getSchemaType();
        if (type != null) {
            removeElementFromIndex(type, element);
        }

        removeElementFromIndex(element.getElementQName(), element);
    }

    /**
     * Removes an object from the given index id.
     * 
     * @param index the id of the index
     * @param element the element to be removed from that index
     */
    protected void removeElementFromIndex(QName index, ElementType element) {
        ArrayList<ElementType> objects = objectIndex.get(index);
        if (objects != null) {
            objects.remove(element);
        }

        if (objects.size() == 0) {
            objectIndex.remove(index);
        }
    }
}