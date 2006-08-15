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

package org.opensaml.samlext.saml2mdquery;

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AttributeConsumingService;

/**
 * SAML 2.0 Metadata extension AttributeQueryDescriptorType
 */
public interface AttributeQueryDescriptor extends QueryDescriptorType {
    
    /** Element local name */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeQueryDescriptor";

    /** Default element name */
    public final static QName DEFAULT_ELEMENT_NAME = new QName(SAMLConstants.SAML20MDQUERY_NS,
            DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20MDQUERY_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "AttributeQueryDescriptorType";

    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML20MDQUERY_NS, TYPE_LOCAL_NAME,
            SAMLConstants.SAML20MDQUERY_PREFIX);

    /**
     * Gets the list of attribute consuming service endpoints support by this role.
     * 
     * @return the list of attribute consuming service endpoints support by this role
     */
    public List<AttributeConsumingService> getAttributeConsumingServices();
}