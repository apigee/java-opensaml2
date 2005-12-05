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

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.io.impl.AbstractUnmarshaller;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.common.impl.TimeBoundSAMLObjectHelper;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.Extensions;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.RoleDescriptor;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.RoleDescriptor}
 * objects. <strong>NOTE</strong> this Unmarshaller will only work that are derived from
 * {@link org.opensaml.saml2.common.impl.AbstractSAMLObject}.
 */
public class RoleDescriptorUnmarshaller extends AbstractUnmarshaller implements Unmarshaller {

    /**
     * 
     * Constructor
     * 
     * @param target the QName of the type or elment this unmarshaller operates on
     */
    protected RoleDescriptorUnmarshaller(QName target) {
        super(target);
    }
    
    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addChildElement(org.opensaml.saml2.common.impl.AbstractSAMLElement, org.opensaml.saml2.common.impl.AbstractSAMLElement)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement) throws UnmarshallingException{
        RoleDescriptor roleDescriptor = (RoleDescriptor)parentElement;
        try {
            if(childElement instanceof Extensions) {
                roleDescriptor.setExtensions((Extensions) childElement);
            }else if(childElement instanceof KeyDescriptor) {
                roleDescriptor.addKeyDescriptor((KeyDescriptor) childElement);
            }else if(childElement instanceof Organization) {
                roleDescriptor.setOrganization((Organization) childElement);
            }else if(childElement instanceof ContactPerson) {
                roleDescriptor.addContactPerson((ContactPerson) childElement);
            }
        }catch(IllegalAddException e){
            //This should never happen
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.saml2.common.impl.AbstractSAMLElement, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue){
        RoleDescriptor roleDescriptor = (RoleDescriptor)samlElement;
        
        if(attributeName.equals(TimeBoundSAMLObject.VALID_UNTIL_ATTRIB_NAME)) {
            roleDescriptor.setValidUntil(TimeBoundSAMLObjectHelper.stringToCalendar(attributeValue));
        }else if(attributeName.equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
            roleDescriptor.setCacheDuration(new Long(Long.parseLong(attributeValue)));
        }else if(attributeName.equals(RoleDescriptor.PROTOCOL_ENUMERATION_ATTRIB_NAME)) {
            StringTokenizer protocolTokenizer = new StringTokenizer(attributeValue, " ");           
            while(protocolTokenizer.hasMoreTokens()) {
                roleDescriptor.addSupportedProtocol(protocolTokenizer.nextToken());
            }
        }else if(attributeName.equals(RoleDescriptor.ERROR_URL_ATTRIB_NAME)) {
            roleDescriptor.setErrorURL(attributeValue);
        }
    }

}
