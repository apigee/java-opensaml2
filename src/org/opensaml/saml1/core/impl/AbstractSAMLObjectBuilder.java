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

package org.opensaml.saml1.core.impl;

import java.util.Map;

import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.xml.ExtendedXMLObjectBuilder;
import org.w3c.dom.Element;

/** Class whose sole job is the creation of a {@link AssertionImpl} object */

public abstract class AbstractSAMLObjectBuilder implements ExtendedXMLObjectBuilder {

    /** The name we use to look up the version in the context */
    
    public static final String contextVersion = "org.opensaml.saml1.core.impl.contextVersion"; 
    
   /**
     * This method returns the version from the provided context.  If this fails 
     * it returns null.
     * @param context The unmarshalling context
     * @return the correct SAML verison
     */
    protected static SAMLVersion getVersion (Map<String, Object> context) {
        SAMLVersion version = null;
 
        //
        // Do not protect the cast with a instance check.  If the cast fails it is a logic error
        //
        // We use the SAML1 Assertyion namespace as a convenient tag.
        //
        if (context != null) {
            version = (SAMLVersion) context.get(SAMLConstants.SAML1_NS);
        }
 
        return version;
    } 
    
    /**
     * This method, for the use of elements with a MinorId attribute
     * looks up the version in the DOM and returns the value.  
     * 
     * @param domElement the dom element for the object under construction 
     * @param attributename the name for the minor version attribute 
     * ("MinorVersion" one assumes)
     * @return the correct SAML version
     */
    protected static SAMLVersion getVersion (Element domElement, Map<String, Object> context, String attributeName) {

        //
        // Peek into the domElement to see whether we have a minor version and construct the 
        // correct sort of Assertion.  Check in the context to see whether there is a version
        // and it maps.
        //
        // If there is no domElement take a look at the context to see whether there is a version if so, use it
        //
        SAMLVersion version = null;
        
        if (domElement != null) {
            String strVersion = domElement.getAttribute(Assertion.MINORVERSION_ATTRIB_NAME);
            
            if ("1".equals(strVersion)) {
                version = SAMLVersion.VERSION_11;
            } else if ("0".equals(strVersion)) {
                version = SAMLVersion.VERSION_10;
            }
        } 
        if (version == null) {
            return getVersion(context);
        }
        return version;
    }
}