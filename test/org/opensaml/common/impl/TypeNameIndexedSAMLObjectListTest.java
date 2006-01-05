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

package org.opensaml.common.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AdditionalMetadataLocation;

public class TypeNameIndexedSAMLObjectListTest extends BaseTestCase {

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * Test method for 'org.opensaml.common.impl.TypeNameIndexedSAMLObjectList.size()'
     */
    public void testSize() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AdditionalMetadataLocation.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        AdditionalMetadataLocation location = (AdditionalMetadataLocation) buildSAMLObject(qname);
        TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation> list = new TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation>();
        
        list.add(location);
        assertEquals("Expected one element in list, instead size() report " + list.size(), 1, list.size());
    }

    /*
     * Test method for 'org.opensaml.common.impl.TypeNameIndexedSAMLObjectList.get(int)'
     */
    public void testGetInt() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AdditionalMetadataLocation.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        AdditionalMetadataLocation location = (AdditionalMetadataLocation) buildSAMLObject(qname);
        TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation> list = new TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation>();
        
        list.add(location);
        assertEquals(location, list.get(0));
    }

    /*
     * Test method for 'org.opensaml.common.impl.TypeNameIndexedSAMLObjectList.get(QName)'
     */
    public void testGetQName() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AdditionalMetadataLocation.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        AdditionalMetadataLocation location = (AdditionalMetadataLocation) buildSAMLObject(qname);
        TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation> list = new TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation>();
        
        list.add(location);
        assertEquals(1, list.get(qname).size());
    }

    /*
     * Test method for 'org.opensaml.common.impl.TypeNameIndexedSAMLObjectList.set(int, ElementType)'
     */
    public void testSetIntElementType() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AdditionalMetadataLocation.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        AdditionalMetadataLocation location = (AdditionalMetadataLocation) buildSAMLObject(qname);
        AdditionalMetadataLocation location2 = (AdditionalMetadataLocation) buildSAMLObject(qname);
        
        TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation> list = new TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation>();
        list.add(location);
        list.set(0, location2);
        
        assertEquals("Expected one element in list, instead size() report " + list.size(), 1, list.size());
    }

    /*
     * Test method for 'org.opensaml.common.impl.TypeNameIndexedSAMLObjectList.remove(int)'
     */
    public void testRemoveInt() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AdditionalMetadataLocation.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        AdditionalMetadataLocation location = (AdditionalMetadataLocation) buildSAMLObject(qname);
        TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation> list = new TypeNameIndexedSAMLObjectList<AdditionalMetadataLocation>();
        
        list.add(location);
        list.remove(0);
        assertEquals("Expected list to be empty, instead size() reports " + list.size(), 0, list.size());
    }

}
