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

import javax.xml.datatype.DatatypeFactory;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.io.impl.AbstractUnmarshaller;
import org.opensaml.common.util.xml.XMLParserException;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.common.impl.TimeBoundSAMLObjectHelper;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.Extensions;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.EntitiesDescriptor}
 * objects. <strong>NOTE</strong> this Unmarshaller will only work that are derived from
 * {@link org.opensaml.saml2.common.impl.AbstractSAMLObject}.
 */
public class EntitiesDescriptorUnmarshaller extends AbstractUnmarshaller implements Unmarshaller {

    /**
     * Constructor
     * 
     * @throws XMLParserException thrown if this Marshaller is unable to create a {@link DatatypeFactory}
     */
    public EntitiesDescriptorUnmarshaller(){
        super(EntitiesDescriptor.QNAME);
    }
    
    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement) throws UnmarshallingException{
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor)parentElement;
        try {
            if(childElement instanceof Extensions) {
                entitiesDescriptor.setExtensions((Extensions) childElement);
            }else if(childElement instanceof EntitiesDescriptor) {
                entitiesDescriptor.addEntitiesDescriptor((EntitiesDescriptor) childElement);
            }else if(childElement instanceof EntityDescriptor) {
                entitiesDescriptor.addEntityDescriptor((EntityDescriptor) childElement);
            }else {
                throw new UnmarshallingException(childElement.getElementQName().getLocalPart() + " is not a valid EntitiesDescriptor child element");
            }
        }catch(IllegalAddException e){
            //This should never happen
            throw new UnmarshallingException(e);
        }
    }
    
    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue) throws UnmarshallingException{
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor)samlElement;
        
        if(attributeName.equals(TimeBoundSAMLObject.VALID_UNTIL_ATTRIB_NAME)) {
            entitiesDescriptor.setValidUntil(TimeBoundSAMLObjectHelper.stringToCalendar(attributeValue));
        }else if(attributeName.equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
            entitiesDescriptor.setCacheDuration(new Long(Long.parseLong(attributeValue)));
        }else if(attributeName.equals(EntitiesDescriptor.NAME_ATTRIB_NAME)) {
            entitiesDescriptor.setName(attributeValue);
        }else {
            throw new UnmarshallingException("Attribute " + attributeName + " is not a valid attribute for an EntitiesDescriptor");
        }
    }
}