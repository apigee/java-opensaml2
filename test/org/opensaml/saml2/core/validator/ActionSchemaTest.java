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

package org.opensaml.saml2.core.validator;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Action;
import org.opensaml.xml.validation.ValidationException;

public class ActionSchemaTest extends SAMLObjectValidatorBaseTestCase {

    private QName qname;
    private ActionSchemaValidator actionValidator;
    
    /**Constructor*/
    public ActionSchemaTest() {
        qname = new QName(SAMLConstants.SAML20_NS, Action.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        actionValidator = new ActionSchemaValidator();
    }
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Tests the correct case.
     * 
     * @throws ValidationException
     */
    public void testProper() throws ValidationException {

        Action action = (Action) buildXMLObject(qname);

        action.setNamespace("ns");
        action.setAction("action label");

        actionValidator.validate(action);
    }

    /**
     * Tests absent Namespace failure.
     * 
     * @throws ValidationException
     */
    public void testNameSpaceFailure() throws ValidationException {
        Action action = (Action) buildXMLObject(qname);

        action.setAction("action label");

        try {
            actionValidator.validate(action);
            fail ("Action missing, should raise a Validation Exception");
            } catch 
                (ValidationException success) {
            }
    }

    /**
     * Tests absent Action failure.
     * 
     * @throws ValidationException
     */
    public void testActionFailure() throws ValidationException {
        Action action = (Action) buildXMLObject(qname);

        action.setNamespace("ns");

        try {
        actionValidator.validate(action);
        fail ("Namespace missing, should raise a Validation Exception");
        } catch 
            (ValidationException success) {
        }
    }
}