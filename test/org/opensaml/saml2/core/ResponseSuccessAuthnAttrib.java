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

package org.opensaml.saml2.core;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.ComplexSAMLObjectBaseTestCase;
import org.opensaml.common.SAMLVersion;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.schema.XSIString;

/**
 * Tests unmarshalling and marshalling for various response messages.
 */
public class ResponseSuccessAuthnAttrib extends ComplexSAMLObjectBaseTestCase {
    
    /**
     * Constructor
     */
    public ResponseSuccessAuthnAttrib(){
        elementFile = "/data/org/opensaml/saml2/core/ResponseSuccessAuthnAttrib.xml";
    }

    /*
     * @see org.opensaml.common.ComplexSAMLObjectBaseTestCase#testUnmarshall()
     */
    public void testUnmarshall() {
        Response response = (Response) unmarshallElement(elementFile);
        
        assertNotNull("Response was null", response);
        assertEquals("Response ID", "_c7055387-af61-4fce-8b98-e2927324b306", response.getID());
        assertEquals("InResponseTo", "_abcdef123456", response.getInResponseTo());
        assertEquals("Version", SAMLVersion.VERSION_20.toString(), response.getVersion().toString());
        assertEquals("IssueInstant", new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()), response.getIssueInstant());
        assertEquals("Issuer/@Format", "urn:oasis:names:tc:SAML:2.0:nameid-format:entity", response.getIssuer().getFormat());
        assertEquals("Status/Statuscode/@Value", "urn:oasis:names:tc:SAML:2.0:status:Success", response.getStatus().getStatusCode().getValue());
        
        Assertion assertion = (Assertion) response.getAssertions().get(0);
        assertNotNull("Assertion[0] was null", assertion);
        assertEquals("Assertion ID", "_a75adf55-01d7-40cc-929f-dbd8372ebdfc", assertion.getID());
        assertEquals("Assertion/@IssueInstant", new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()), assertion.getIssueInstant());
        assertEquals("Assertion/@Version", SAMLVersion.VERSION_20.toString(), assertion.getVersion().toString());
        assertEquals("Assertion/Issuer/@Format", "urn:oasis:names:tc:SAML:2.0:nameid-format:entity", assertion.getIssuer().getFormat());
        assertEquals("Assertion/Subject/NameID/@Format", "urn:oasis:names:tc:SAML:2.0:nameid-format:transient", assertion.getSubject().getNameID().getFormat());
        assertEquals("Assertion/Subject/NameID contents", "_820d2843-2342-8236-ad28-8ac94fb3e6a1", assertion.getSubject().getNameID().getValue());
        SubjectConfirmation sc = assertion.getSubject().getSubjectConfirmations().get(0);
        assertEquals("Assertion/Subject/SubjectConfirmation/@Method", "urn:oasis:names:tc:SAML:2.0:cm:bearer", sc.getMethod());
        assertEquals("Assertion/Condition/@NotBefore", new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()), assertion.getConditions().getNotBefore());
        assertEquals("Assertion/Condition/@NotOnOrAfter", new DateTime(2006, 1, 26, 13, 45, 5, 0, ISOChronology.getInstanceUTC()), assertion.getConditions().getNotOnOrAfter());
        Audience audience = (Audience) assertion.getConditions().getAudienceRestrictions().get(0).getAudiences().get(0);
        assertEquals("Assertion/Conditions/AudienceRestriction/Audience contents", "https://sp.example.org", audience.getAudienceURI());
        
        AuthnStatement authnStatement = (AuthnStatement) assertion.getAuthnStatements().get(0);
        assertEquals("Assertion/AuthnStatement/@AuthnInstant", new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()), authnStatement.getAuthnInstant());
        assertEquals("Assertion/AuthnStatement/AuthnContext/AuthnContextClassRef contents", "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport", authnStatement.getAuthnContext().getAuthnContextClassRef().getAuthnContextClassRef());
        
        AttributeStatement  attribStatement = (AttributeStatement) assertion.getAttributeStatement().get(0);
        Attribute attrib = null;
        AttributeValue value = null;
        
        //TODO testing attribute values and other element attributes, e.g. x500:Encoding for x500/LDAP attribs
        attrib = attribStatement.getAttributes().get(0);
        assertEquals("Attribute/@FriendlyName", "fooAttrib", attrib.getFriendlyName());
        assertEquals("Attribute/@Name", "urn:foo:attrib", attrib.getName());
        assertEquals("Attribute/@NameFormat", "urn:oasis:names:tc:SAML:2.0:attrname-format:uri", attrib.getNameFormat());
        //value = (AttributeValue) attrib.getAttributeValues().get(0);
        
        attrib = attribStatement.getAttributes().get(1);
        assertEquals("Attribute/@FriendlyName", "eduPersonPrincipalName", attrib.getFriendlyName());
        assertEquals("Attribute/@Name", "urn:oid:1.3.6.1.4.1.5923.1.1.1.6", attrib.getName());
        assertEquals("Attribute/@NameFormat", "urn:oasis:names:tc:SAML:2.0:attrname-format:uri", attrib.getNameFormat());
        //value = (AttributeValue) attrib.getAttributeValues().get(0);
    }

    /*
     * @see org.opensaml.common.ComplexSAMLObjectBaseTestCase#testMarshall()
     */
    public void testMarshall() {
        Response response = (Response) buildXMLObject(Response.DEFUALT_ELEMENT_NAME);
        response.setID("_c7055387-af61-4fce-8b98-e2927324b306");
        response.setInResponseTo("_abcdef123456");
        response.setIssueInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
        
        Issuer rIssuer = (Issuer) buildXMLObject(Issuer.DEFUALT_ELEMENT_NAME);
        rIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
        rIssuer.setValue("https://idp.example.org");
        
        Status status = (Status) buildXMLObject(Status.DEFUALT_ELEMENT_NAME);
        StatusCode statusCode = (StatusCode) buildXMLObject(StatusCode.DEFUALT_ELEMENT_NAME);
        statusCode.setValue("urn:oasis:names:tc:SAML:2.0:status:Success");
        
        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFUALT_ELEMENT_NAME);
        assertion.setID("_a75adf55-01d7-40cc-929f-dbd8372ebdfc");
        assertion.setIssueInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
        
        Issuer aIssuer = (Issuer) buildXMLObject(Issuer.DEFUALT_ELEMENT_NAME);
        aIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
        aIssuer.setValue("https://idp.example.org");
        
        Subject subject = (Subject) buildXMLObject(Subject.DEFUALT_ELEMENT_NAME);
        NameID nameID = (NameID) buildXMLObject(NameID.DEFUALT_ELEMENT_NAME);
        nameID.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:transient");
        nameID.setValue("_820d2843-2342-8236-ad28-8ac94fb3e6a1");
        
        SubjectConfirmation subjectConfirmation = (SubjectConfirmation) buildXMLObject(SubjectConfirmation.DEFUALT_ELEMENT_NAME);
        subjectConfirmation.setMethod("urn:oasis:names:tc:SAML:2.0:cm:bearer");
        
        Conditions conditions = (Conditions) buildXMLObject(Conditions.DEFUALT_ELEMENT_NAME);
        conditions.setNotBefore(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
        conditions.setNotOnOrAfter(new DateTime(2006, 1, 26, 13, 45, 5, 0, ISOChronology.getInstanceUTC()));
        
        AudienceRestriction audienceRestriction = (AudienceRestriction) buildXMLObject(AudienceRestriction.DEFUALT_ELEMENT_NAME);
        Audience audience = (Audience) buildXMLObject(Audience.DEFUALT_ELEMENT_NAME);
        audience.setAudienceURI("https://sp.example.org");
        
        AuthnStatement authnStatement = (AuthnStatement) buildXMLObject(AuthnStatement.DEFUALT_ELEMENT_NAME);
        authnStatement.setAuthnInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
        
        AuthnContext authnContext = (AuthnContext) buildXMLObject(AuthnContext.DEFUALT_ELEMENT_NAME);
        AuthnContextClassRef classRef = (AuthnContextClassRef) buildXMLObject(AuthnContextClassRef.DEFUALT_ELEMENT_NAME);
        classRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        
        AttributeStatement attribStatement = (AttributeStatement) buildXMLObject(AttributeStatement.DEFUALT_ELEMENT_NAME);
        //TODO is this right for xs:string AttributeValue types ?
        XMLObjectBuilder stringBuilder = builderFactory.getBuilder(XSIString.TYPE_NAME);
        
        Attribute fooAttrib = (Attribute) buildXMLObject(Attribute.DEFUALT_ELEMENT_NAME);
        fooAttrib.setFriendlyName("fooAttrib");
        fooAttrib.setName("urn:foo:attrib");
        fooAttrib.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
        //TODO attribute values
        //AttributeValue fooAttribValue = (AttributeValue) buildXMLObject(AttributeValue.DEFUALT_ELEMENT_NAME);
        XSIString fooAttribValue = (XSIString) stringBuilder.buildObject(AttributeValue.DEFUALT_ELEMENT_NAME);
        fooAttribValue.setValue("SomeValue");
        //fooAttrib.getAttributeValues().add(fooAttribValue);
        
        Attribute ldapAttrib = (Attribute) buildXMLObject(Attribute.DEFUALT_ELEMENT_NAME);
        ldapAttrib.setFriendlyName("eduPersonPrincipalName");
        ldapAttrib.setName("urn:oid:1.3.6.1.4.1.5923.1.1.1.6");
        ldapAttrib.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
        //TODO attribute values
        //AttributeValue ldapAttribValue = (AttributeValue) buildXMLObject(AttributeValue.DEFUALT_ELEMENT_NAME);
        XSIString ldapAttribValue = (XSIString) stringBuilder.buildObject(AttributeValue.DEFUALT_ELEMENT_NAME);
        ldapAttribValue.setValue("j.doe@idp.example.org");
        //ldapAttrib.getAttributeValues().add(ldapAttribValue);
        
        response.setIssuer(rIssuer);
        status.setStatusCode(statusCode);
        response.setStatus(status);
        response.getAssertions().add(assertion);
        
        assertion.setIssuer(aIssuer);
        subject.setNameID(nameID);
        subject.getSubjectConfirmations().add(subjectConfirmation);
        assertion.setSubject(subject);
        
        audienceRestriction.getAudiences().add(audience);
        conditions.getAudienceRestrictions().add(audienceRestriction);
        assertion.setConditions(conditions);
        
        authnContext.setAuthnContextClassRef(classRef);
        authnStatement.setAuthnContext(authnContext);
        assertion.getAuthnStatements().add(authnStatement);
        
        attribStatement.getAttributes().add(fooAttrib);
        attribStatement.getAttributes().add(ldapAttrib);
        assertion.getAttributeStatement().add(attribStatement);
        
        // TODO comment out....
        // just for looking at the serialized XML when debugging
        try {
            printXML(marshallerFactory.getMarshaller(response).marshall(response).getOwnerDocument());
        } catch (Exception e) {e.printStackTrace();}
        assertEquals("Marshalled Response was not the expected value", expectedDOM, response);
    }
    
}