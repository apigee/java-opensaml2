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
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.xml.validation.ValidationException;

public class AudienceRestrictionSchemaTest extends SAMLObjectValidatorBaseTestCase {

    private QName qname;

    private AudienceRestrictionSchemaValidator audienceRestrictionValidator;

    private QName audQName;

    private Audience audience;

    /** Constructor */
    public AudienceRestrictionSchemaTest() {
        qname = new QName(SAMLConstants.SAML20_NS, AudienceRestriction.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        audQName = new QName(SAMLConstants.SAML20_NS, Audience.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        audience = (Audience) buildXMLObject(audQName);
        audienceRestrictionValidator = new AudienceRestrictionSchemaValidator();
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
        AudienceRestriction audienceRestriction = (AudienceRestriction) buildXMLObject(qname);

        audienceRestriction.getAudiences().add(audience);

        audienceRestrictionValidator.validate(audienceRestriction);
    }

    /**
     * Tests absent Audience failure.
     * 
     * @throws ValidationException
     */
    public void testAudienceFailure() throws ValidationException {
        AudienceRestriction audienceRestriction = (AudienceRestriction) buildXMLObject(qname);

        try {
            audienceRestrictionValidator.validate(audienceRestriction);
            fail("Audience missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
}