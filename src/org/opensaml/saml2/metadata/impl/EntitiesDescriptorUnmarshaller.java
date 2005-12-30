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

import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.EntitiesDescriptor}
 * objects. <strong>NOTE</strong> this Unmarshaller will only work that are derived from
 * {@link org.opensaml.saml2.common.impl.AbstractXMLObject}.
 */
public class EntitiesDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     * 
     * @throws XMLParserException thrown if this Marshaller is unable to create a {@link DatatypeFactory}
     */
    public EntitiesDescriptorUnmarshaller(){
        super(SAMLConstants.SAML20MD_NS, EntitiesDescriptor.LOCAL_NAME);
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
                if(!SAMLConfig.ignoreUnknownElements()){
                    throw new UnknownElementException(childElement.getElementQName() + " is not a supported element for EntitiesDescriptor objects");
                }
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
            entitiesDescriptor.setValidUntil(DatatypeHelper.stringToCalendar(attributeValue, 0));
        }else if(attributeName.equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
            entitiesDescriptor.setCacheDuration(new Long(DatatypeHelper.durationToLong(attributeValue)));
        }else if(attributeName.equals(EntitiesDescriptor.NAME_ATTRIB_NAME)) {
            entitiesDescriptor.setName(attributeValue);
        }else {
            if(!SAMLConfig.ignoreUnknownAttributes()){
                throw new UnknownAttributeException(attributeName + " is not a supported attributed for EntitiesDescriptor objects");
            }
        }
    }
}