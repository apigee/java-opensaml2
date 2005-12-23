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

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
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
import org.opensaml.common.util.xml.XMLParserException;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.Status;
import org.opensaml.saml1.core.StatusCode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class StatusCodeTest extends SAMLObjectBaseTestCase {

    private final String TEST_FILE = "/data/TestResponse.xml";
    private final String FIRST_STATUS = "samlp:Success";
    private final String SECOND_STATUS = "nibble, a happy warthog";

    public void testUnarshallStatusCode() {

        Response response; 

        try {
            ParserPoolManager ppMgr = ParserPoolManager.getInstance();
            Document doc;
            doc = ppMgr.parse(new InputSource(StatusCodeTest.class.getResourceAsStream(TEST_FILE)));
      
            Element entitiesDescriptorElem = doc.getDocumentElement();
            Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(entitiesDescriptorElem);

            response = (Response) unmarshaller.unmarshall(entitiesDescriptorElem);
            
        } catch (XMLParserException e) {
            fail("XML Parsing failed: " + e);
            return;
        }
        catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown but example element does not contain any unknown attributes: " + e);
            return;
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown but example element does not contain any child elements");
            return;
        }
        catch (UnmarshallingException e) {
            fail("Could not unmarshall:" + e);
            return;
        }

        SAMLObject object = response.getStatus();
        
        if (object == null) {
            fail("Not <Status> found");
            return;
        }
        
        if (!(object instanceof Status)) {
            fail("Object returned from getStatus was not Status");
            return;
        }
 
        Status status = (Status) object; 
        
        object = status.getStatusCode();
        
        if (object == null) {
            fail("No <StatusCode> found");
            return;
        }
        
        if (!(object instanceof StatusCode)) {
            fail("Object returned from getStatusCode was not StatusCode");
            return;
        }
 
        StatusCode statusCode = (StatusCode) object;
        
        assertEquals("First StatusCode value not the same", statusCode.getValue(), FIRST_STATUS);
        
        object = statusCode.getStatusCode();
        if (object == null) {
            fail("No child <StatusCode> found");
            return;
        }
        
        if (!(object instanceof StatusCode)) {
            fail("Object returned from StatusCode.getStatusCode was not StatusCode");
            return;
        }
 
        statusCode = (StatusCode) object;
        
        assertEquals("second StatusCode value not the same", statusCode.getValue(), SECOND_STATUS);
        
        object = statusCode.getStatusCode();
        
        assertNull("Third child should be null", statusCode.getStatusCode());
    }
    
    public void testMarshallStatusCode() {
        
        StatusCode statusCode = new StatusCodeImpl();
        StatusCode statusCode2 = new StatusCodeImpl();
        StatusCode statusCode3 = new StatusCodeImpl();
        
        statusCode.setValue("Top");
        statusCode2.setValue("Two");
        statusCode3.setValue("Three");
        
        try {
            statusCode.setStatusCode(statusCode2);
        } catch (IllegalAddException e) {
            fail("SetStatusCode threw exception"); 
        }
        
        boolean threw = false;
        
        try {
            statusCode3.setStatusCode(statusCode2);
        } catch (IllegalAddException e) {
           threw = true;
        }        
        
        assertTrue("Second set of parent not caught", threw);
        
        try {
            statusCode2.setStatusCode(statusCode3);
        } catch (IllegalAddException e) {
            fail("Thrid SetStatusCode threw exception");
        }
        
        Marshaller marshaller = MarshallerFactory.getInstance().getMarshaller(statusCode);
        
        Element dom;
        try {
            dom = marshaller.marshall(statusCode);
            System.out.println(ElementSerializer.serialize(dom));
        } catch (MarshallingException e) {
            fail("marshall() call threw exception: " + e);
        } catch (SerializationException e) {
            fail("Couldn't serialize of the DOM:" + e);
        }
    }
}
