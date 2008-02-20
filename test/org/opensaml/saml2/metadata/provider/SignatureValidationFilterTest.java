/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.metadata.provider;

import java.security.cert.X509Certificate;

import org.opensaml.Configuration;
import org.opensaml.common.BaseTestCase;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.SecurityTestHelper;
import org.opensaml.xml.security.credential.StaticCredentialResolver;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.SignatureTrustEngine;
import org.opensaml.xml.signature.impl.ExplicitKeySignatureTrustEngine;
import org.w3c.dom.Document;

/**
 * Unit tests for {@link SignatureValidationFilter}.
 */
public class SignatureValidationFilterTest extends BaseTestCase {
    
    private final String switchMDFileValid = "/data/org/opensaml/saml2/metadata/provider/metadata.aaitest_signed.xml";
    private final String switchMDFileInvalid = "/data/org/opensaml/saml2/metadata/provider/metadata.aaitest_signed.invalid.xml";
    
    private Document switchMDDocumentValid;
    private Document switchMDDocumentInvalid;
    
    private SignatureTrustEngine switchSigTrustEngine;
    
    private String switchMDCertBase64 = 
        "MIICrzCCAhgCAQAwDQYJKoZIhvcNAQEEBQAwgZ8xCzAJBgNVBAYTAkNIMUAwPgYDVQQKEzdTV0lU" +
        "Q0ggLSBUZWxlaW5mb3JtYXRpa2RpZW5zdGUgZnVlciBMZWhyZSB1bmQgRm9yc2NodW5nMQwwCgYD" +
        "VQQLEwNBQUkxIjAgBgNVBAMTGVNXSVRDSGFhaSBNZXRhZGF0YSBTaWduZXIxHDAaBgkqhkiG9w0B" +
        "CQEWDWFhaUBzd2l0Y2guY2gwHhcNMDUwODAzMTEyMjUxWhcNMTUwODAxMTEyMjUxWjCBnzELMAkG" +
        "A1UEBhMCQ0gxQDA+BgNVBAoTN1NXSVRDSCAtIFRlbGVpbmZvcm1hdGlrZGllbnN0ZSBmdWVyIExl" +
        "aHJlIHVuZCBGb3JzY2h1bmcxDDAKBgNVBAsTA0FBSTEiMCAGA1UEAxMZU1dJVENIYWFpIE1ldGFk" +
        "YXRhIFNpZ25lcjEcMBoGCSqGSIb3DQEJARYNYWFpQHN3aXRjaC5jaDCBnzANBgkqhkiG9w0BAQEF" +
        "AAOBjQAwgYkCgYEAsmyBYNZ8mKYutdyQShzuOgnVxDP1UBZE+57S2ORZg1qi4JExOJEPnviHuh6H" +
        "EajljhAMGHxr656paDpfXkmGq/Ybk3xmXy2FTnFGpjFpZUV6dY/oJ82rve27C/NVcwZw2nYRl5C5" +
        "aCCgx/QlWsBTw+9972141+wBDH7dXlJ+UGkCAwEAATANBgkqhkiG9w0BAQQFAAOBgQCcLuNwTINk" +
        "fhBlVCIuTixR1R6mYu/+4KUJWtHlRCOUZhSLFept8HxEvfwnuX9xm+Q6Ju/sOgmI1INuSstUGWwV" +
        "y0AbpCphUDDmIh9A85ye8DrVaBHQrj5b/JEjCvkY0zhLJzgDzZ6btT40TuCnk2GpdAClu5SyCTiy" +
        "56+zDYqPqg==";

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        switchMDDocumentValid = parser.parse(SignatureValidationFilterTest.class.getResourceAsStream(switchMDFileValid));
        switchMDDocumentInvalid = parser.parse(SignatureValidationFilterTest.class.getResourceAsStream(switchMDFileInvalid));
        
        X509Certificate switchCert = SecurityTestHelper.buildJavaX509Cert(switchMDCertBase64);
        X509Credential switchCred = SecurityHelper.getSimpleCredential(switchCert, null);
        StaticCredentialResolver switchCredResolver = new StaticCredentialResolver(switchCred);
        switchSigTrustEngine = new ExplicitKeySignatureTrustEngine(switchCredResolver, 
                Configuration.getGlobalSecurityConfiguration().getDefaultKeyInfoCredentialResolver());
    }

    public void testValidSWITCHStandalone() throws UnmarshallingException {
        XMLObject xmlObject = unmarshallerFactory.getUnmarshaller(switchMDDocumentValid
                .getDocumentElement()).unmarshall(switchMDDocumentValid.getDocumentElement());
        
        SignatureValidationFilter filter = new SignatureValidationFilter(switchSigTrustEngine);
        try {
            filter.doFilter(xmlObject);
        } catch (FilterException e) {
            fail("Filter failed validation, should have succeeded: " + e.getMessage());
        }
    }
    
    public void testInvalidSWITCHStandalone() throws UnmarshallingException {
        XMLObject xmlObject = unmarshallerFactory.getUnmarshaller(switchMDDocumentInvalid
                .getDocumentElement()).unmarshall(switchMDDocumentInvalid.getDocumentElement());
        
        SignatureValidationFilter filter = new SignatureValidationFilter(switchSigTrustEngine);
        try {
            filter.doFilter(xmlObject);
            fail("Filter passed validation, should have failed");
        } catch (FilterException e) {
            // do nothing, should fail
        }
    }

}