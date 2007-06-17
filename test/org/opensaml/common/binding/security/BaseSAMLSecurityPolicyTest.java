/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.common.binding.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.xml.namespace.QName;

import org.opensaml.common.BaseTestCase;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRuleFactory;
import org.opensaml.xml.XMLObject;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Base class for security policy rule tests.
 */
public abstract class BaseSAMLSecurityPolicyTest extends BaseTestCase {
    
    /** HTTP servlet request. */
    protected MockHttpServletRequest httpRequest;
    
    /** The message to be passed into the security policy rule for evaluation. */
    protected XMLObject message;
    
    /** The list of rule factories.  Modifications are reflected in policy that is run. */
    protected List<SecurityPolicyRuleFactory<ServletRequest>> ruleFactories;
    
    /** The factory which produces the policy that will be evaled. */
    protected SAMLSecurityPolicyFactory policyFactory;
    
    /** The most recently executed policy from the evalPolicy*() methods. Reset after each test via setup(). */
    protected SAMLSecurityPolicy policy;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        httpRequest = buildServletRequest();
        message = buildMessage();
        ruleFactories = getPolicyRuleFactories();
        
        policyFactory = getSecurityPolicyFactory();
        // These can't be null, so default in some dummy values. Tests should change if needed.
        policyFactory.setIssuerRole(new QName("someURI", "dummy", "abc123"));
        policyFactory.setIssuerProtocol("dummy-protocol");
        policyFactory.setRequiredAuthenticatedIssuer(false);
        
        policy = null;
    }
    
    /**
     * Build a mock servlet request. Subclasses may override.
     * 
     * @return mock servlet request
     */
    protected MockHttpServletRequest buildServletRequest() {
        return new MockHttpServletRequest();
    }
    
    /**
     * Build the message to be evaled.  Subclasses should override
     * if they will be testing/manipulating message content.
     * 
     * @return a newly constructed message object
     */
    protected XMLObject buildMessage() {
       return null; 
    }

    /**
     * Get the factory for the security policy.
     * 
     * @return security policy factory
     */
    protected SAMLSecurityPolicyFactory getSecurityPolicyFactory() {
        if (policyFactory == null) {
            SAMLSecurityPolicyFactory factory = new SAMLSecurityPolicyFactory();
            factory.setPolicyRuleFactories( getPolicyRuleFactories() );
            policyFactory = factory;
        }
        return policyFactory;
    }
    
    /**
     * Create and set the list of rule factories.  Subclasses should add rule(s) to be
     * tested to this list.
     * 
     * @return the current, possibly newly constructed list, of rule factories
     */
    protected List<SecurityPolicyRuleFactory<ServletRequest>> getPolicyRuleFactories() {
        if (ruleFactories == null) {
            ruleFactories = new ArrayList<SecurityPolicyRuleFactory<ServletRequest>>();
        }
        return ruleFactories;
    }
    
    /**
     * Evaluate a policy obtained from the current factory, against the
     * current request and message, using the current rule list.
     * Success is expected.
     * 
     * @param msg message to include in fail() messages
     */
    protected void assertPolicySuccess(String msg) {
        policy = (SAMLSecurityPolicy) policyFactory.createPolicyInstance();
        try {
            policy.evaluate(httpRequest, message);
        } catch (SecurityPolicyException e) {
            fail("Security policy failed, expected success: " + msg + ": " + e);
        }
    }
    
    /**
     * Evaluate a policy obtained from the current factory, against the
     * current request and message, using the current rule list.
     * Failure is expected.
     * 
     * @param msg message to include in fail() messages
     */
    protected void assertPolicyFail(String msg) {
        policy = (SAMLSecurityPolicy) policyFactory.createPolicyInstance();
        try {
            policy.evaluate(httpRequest, message);
            fail("Security policy succeeded, expected failure: " + msg);
        } catch (SecurityPolicyException e) {
            //do nothing, failure expected
        }
    }
}
