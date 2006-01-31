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

package org.opensaml.saml2.metadata.impl;

import java.util.StringTokenizer;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.RoleDescriptor}
 * objects.
 */
public class RoleDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     * 
     * @param targetNamespaceURI the namespaceURI of the SAMLObject this unmarshaller operates on
     * @param localName the local name of the SAMLObject this unmarshaller operates on
     */
    protected RoleDescriptorUnmarshaller(String targetNamespaceURI, String targetLocalName) {
        super(targetNamespaceURI, targetLocalName);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addChildElement(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      org.opensaml.saml2.common.impl.AbstractSAMLElement)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
            throws UnmarshallingException {
        RoleDescriptor roleDescriptor = (RoleDescriptor) parentSAMLObject;

        if (childSAMLObject instanceof Extensions) {
            roleDescriptor.setExtensions((Extensions) childSAMLObject);
        } else if (childSAMLObject instanceof KeyDescriptor) {
            roleDescriptor.getKeyDescriptors().add((KeyDescriptor) childSAMLObject);
        } else if (childSAMLObject instanceof Organization) {
            roleDescriptor.setOrganization((Organization) childSAMLObject);
        } else if (childSAMLObject instanceof ContactPerson) {
            roleDescriptor.getContactPersons().add((ContactPerson) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException {
        RoleDescriptor roleDescriptor = (RoleDescriptor) samlObject;

        if (attributeName.equals(TimeBoundSAMLObject.VALID_UNTIL_ATTRIB_NAME)) {
            roleDescriptor.setValidUntil(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else if (attributeName.equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
            roleDescriptor.setCacheDuration(DatatypeHelper.durationToLong(attributeValue));
        } else if (attributeName.equals(RoleDescriptor.PROTOCOL_ENUMERATION_ATTRIB_NAME)) {
            StringTokenizer protocolTokenizer = new StringTokenizer(attributeValue, " ");
            while (protocolTokenizer.hasMoreTokens()) {
                roleDescriptor.addSupportedProtocol(protocolTokenizer.nextToken());
            }
        } else if (attributeName.equals(RoleDescriptor.ERROR_URL_ATTRIB_NAME)) {
            roleDescriptor.setErrorURL(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}