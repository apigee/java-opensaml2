/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.common.binding.artifact;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.artifact.SAMLArtifactMap.SAMLArtifactMapEntry;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;

/**
 * Test the basic SAML artifact map entry factory.
 */
public class BasicSAMLArtifactMapEntryFactoryTest extends BaseTestCase {
    
    private BasicSAMLArtifactMapEntryFactory factory;
    private SAMLObject samlObject;
    
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        factory = new BasicSAMLArtifactMapEntryFactory();
        
        //Don't typically store assertions, but need something that can take a parent, for testing purposes.
        samlObject = (SAMLObject) unmarshallElement("/data/org/opensaml/saml1/core/SignedAssertion.xml");
    }

    public void testNoParent() {
        SAMLArtifactMapEntry entry = factory.newEntry("the-artifact", "the-issuer", "the-rp", samlObject, 60*60*1000);
        assertTrue("Parent-less SAMLObject resulted in different object in entry", 
                samlObject == entry.getSamlMessage());
    }
    
    public void testWithParent() {
        Response response = (Response) buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
        response.getAssertions().add((Assertion)samlObject);
        assertTrue(samlObject.hasParent());
        
        SAMLArtifactMapEntry entry = factory.newEntry("the-artifact", "the-issuer", "the-rp", samlObject, 60*60*1000);
        assertFalse("Parent-ed SAMLObject resulted in the same object in entry", 
                samlObject == entry.getSamlMessage());
    }
    
    public void testNoSerialization() {
        factory.setSerializeMessage(false);
        SAMLArtifactMapEntry entry = factory.newEntry("the-artifact", "the-issuer", "the-rp", samlObject, 60*60*1000);
        BasicSAMLArtifactMapEntry basicEntry = (BasicSAMLArtifactMapEntry) entry;
        assertNull("Serialized data was not null", basicEntry.getSerializedMessage());
    }
    
    public void testWithSerialization() {
        factory.setSerializeMessage(true);
        SAMLArtifactMapEntry entry = factory.newEntry("the-artifact", "the-issuer", "the-rp", samlObject, 60*60*1000);
        BasicSAMLArtifactMapEntry basicEntry = (BasicSAMLArtifactMapEntry) entry;
        assertNotNull("Serialized data was null", basicEntry.getSerializedMessage());
    }

}
