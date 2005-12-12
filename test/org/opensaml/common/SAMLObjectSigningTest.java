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

package org.opensaml.common;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallerFactory;
import org.opensaml.common.io.MarshallingException;
import org.opensaml.common.util.ElementSerializer;
import org.opensaml.common.util.SerializationException;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.w3c.dom.Element;

public class SAMLObjectSigningTest extends BaseTestCase {

    /** Signing key */
    private Key signingKey;
    
    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyGen.generateKeyPair();
        signingKey = keyPair.getPrivate();
    }
    
    /**
     * Tests that a SAML object can be signed and have it's signature validated.
     */
    public void testSAMLObjectSigning(){
        SAMLObjectBuilder edBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(EntitiesDescriptor.QNAME);
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor) edBuilder.buildObject();
        
        SigningContext dsigCtx = new SigningContext(signingKey, new SecureRandomIdentifierGenerator());
        entitiesDescriptor.setSigningContext(dsigCtx);
        
        Marshaller marshaller = MarshallerFactory.getInstance().getMarshaller(entitiesDescriptor);
        try{
            Element dom = marshaller.marshall(entitiesDescriptor);
            
            //TODO validate signature once validation code is done
            String seralizedDOM = ElementSerializer.serialize(dom);
            System.out.println(seralizedDOM);
        }catch(MarshallingException e){
            fail("Marshalling failed with the following error: " + e);
        } catch (SerializationException e) {
            fail("Unable to serialize resulting DOM document due to: " + e);
        }
    }
}