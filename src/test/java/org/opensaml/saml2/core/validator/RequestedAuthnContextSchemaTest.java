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

/**
 * 
 */
package org.opensaml.saml2.core.validator;

import javax.xml.namespace.QName;

import org.opensaml.common.BaseSAMLObjectValidatorTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextDeclRef;
import org.opensaml.saml2.core.RequestedAuthnContext;

/**
 *
 */
public class RequestedAuthnContextSchemaTest extends BaseSAMLObjectValidatorTestCase {
    
    private QName classRefName;
    private QName  declRefName;

    /**
     * Constructor
     *
     */
    public RequestedAuthnContextSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML20P_NS, RequestedAuthnContext.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
        validator = new RequestedAuthnContextSchemaValidator();
        
        classRefName = AuthnContextClassRef.DEFAULT_ELEMENT_NAME;
        declRefName  = AuthnContextDeclRef.DEFAULT_ELEMENT_NAME;
    }

    /** {@inheritDoc} */
    protected void populateRequiredData() {
        super.populateRequiredData();
        RequestedAuthnContext rac = (RequestedAuthnContext) target;
        
        rac.getAuthnContextClassRefs().add((AuthnContextClassRef) buildXMLObject(classRefName));
    }
    
    /**
     * Tests for invalid combinations of child elements.
     */
    public void testChildrenFailure() {
        RequestedAuthnContext rac = (RequestedAuthnContext) target;
        
        rac.getAuthnContextDeclRefs().add((AuthnContextDeclRef) buildXMLObject(declRefName));
        assertValidationFail("Element had both AuthnContextClassRef and AuthnContextDeclRef children");
        
        rac.getAuthnContextClassRefs().clear();
        rac.getAuthnContextDeclRefs().clear();
        assertValidationFail("Element had neither AuthnContextClassRef nor AuthnContextDeclRef children");
    }
    
    
}
