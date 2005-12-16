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

package org.opensaml.saml1.core.impl;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallerFactory;
import org.opensaml.common.io.MarshallingException;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallerFactory;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.util.ElementSerializer;
import org.opensaml.common.util.SerializationException;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.saml1.core.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class ResponseTest extends BaseTestCase {

    private final String TEST_FILE = "/data/TestResponse.xml";
    
    private final int MINOR_VERSION = 1;
    
    Element entitiesDescriptorElem;
    
    protected void setUp() throws Exception {
        super.setUp();
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        Document doc = ppMgr.parse(new InputSource(this.getClass().getResourceAsStream(TEST_FILE)));
        entitiesDescriptorElem = doc.getDocumentElement();
    }   

    public void testUnmarshallResponse(){

        Response response = null;
        
        try {
            Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(entitiesDescriptorElem);
            
            if(unmarshaller == null){
                
                fail("Unable to retrieve unmarshaller by DOM Element");
            }
            
            SAMLObject object = unmarshaller.unmarshall(entitiesDescriptorElem);
            
            assertTrue("Returned type wrong", object instanceof Response);
            
            response = (Response) object;
            
        } catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown but example element does not contain any unknown attributes: " + e);
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown but example element does not contain any child elements");
        } catch (UnmarshallingException e) {
            fail("Unmarshalling failed with the following error:"  + e);
        } catch (Exception e) {
            fail("Unmarshall test failed with the following error:"  + e);
        }
        
        if (response != null) {
            assertEquals("Minor version wrong", response.getMinorVersion(), MINOR_VERSION);
        } else {
            fail("Unmarshall test fails - reponse not initialized");
        }
    }
    
    public void testMarshallResponse() {
        
        Response response = null;
        
        try {
            Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(entitiesDescriptorElem);
                
            if(unmarshaller == null){
                    
                fail("Unable to retrieve unmarshaller by DOM Element");
            }
                
            response = (Response) unmarshaller.unmarshall(entitiesDescriptorElem);

        } catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown but example element does not contain any unknown attributes: " + e);
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown but example element does not contain any child elements");
        } catch (Exception e) {
            fail("Marshalling test setup failed with the following error:"  + e);
        }
        
        if (response instanceof AbstractSAMLObject) {
            AbstractSAMLObject abstractSAMLObject = (AbstractSAMLObject) response;
            
            abstractSAMLObject.releaseThisandParentDOM();
        
        } else {
            fail("Response was not an Abstract SamlObject");
        }

        if (response != null) {
            Marshaller marshaller = MarshallerFactory.getInstance().getMarshaller(response);
            
            try{
                Element dom = marshaller.marshall(response);
                System.out.println(ElementSerializer.serialize(dom));
            }catch(MarshallingException e){
                fail("Marshalling failed with the following error: " + e);
            } catch (SerializationException e) {
                fail("Unable to serialize resulting DOM document due to: " + e);
            } catch (Exception e) {
                fail("Marshalling test failed with the following error:"  + e);
            }
            
        } else {
            fail("Marshalling test failed - response not setup");
        }
    }
}
