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

package org.opensaml.common.saml2.metadata.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallerFactory;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.common.util.xml.XMLParserException;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.metadata.impl.EntitiesDescriptorImpl}.
 */
public class EntitiesDescriptorTest extends BaseTestCase {

    private static String singleElementFile = "/data/singleEntitiesDescriptor.xml";
    
    /**
     * Tests unmarshalling a document that contains a single EntitiesDescriptor element, with no children.
     */
    public void testElementUnmarshall(){
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        try {
            Document doc = ppMgr.parse(new InputSource(EntitiesDescriptorTest.class.getResourceAsStream(singleElementFile)));
            Element entitiesDescriptorElem = doc.getDocumentElement();
            
            Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(entitiesDescriptorElem);
            if(unmarshaller == null){
                fail("Unable to retrieve unmarshaller by DOM Element");
            }
            
            EntitiesDescriptor entitiesDescriptorObj = (EntitiesDescriptor) unmarshaller.unmarshall(entitiesDescriptorElem);
            
            String name = entitiesDescriptorObj.getName();
            assertEquals("Name attribute has a value of " + name + ", expected a value of eDescName", name, "eDescName");
            
            long duration = entitiesDescriptorObj.getCacheDuration().longValue();
            assertEquals("cacheDuration attribute has a value of " + duration + ", expected a value of 90000", duration, 90000);
            
            GregorianCalendar expectedValidUntil = new GregorianCalendar(2005, Calendar.DECEMBER, 7, 10, 21, 0);
            GregorianCalendar validUntil = entitiesDescriptorObj.getValidUntil();
            assertEquals("validUntil attribute value did not match expected value", 0, expectedValidUntil.compareTo(validUntil));
            
        } catch (XMLParserException e) {
            fail("Unable to parse file containing single EntitiesDescriptor element");
        } catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown but example element does not contain any unknown attributes: " + e);
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown but example element does not contain any child elements");
        } catch (UnmarshallingException e) {
            fail("Unmarshalling failed with the following error:"  + e);
        }
    }
}
