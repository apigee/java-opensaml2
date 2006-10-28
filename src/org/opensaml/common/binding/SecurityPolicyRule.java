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

package org.opensaml.common.binding;

import javax.servlet.ServletRequest;

import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.xml.XMLObject;

/**
 * A rule that a protocol request and message must meet in order to be valid and secure.
 *
 * @param <RequestType> the protocol request type
 */
public interface SecurityPolicyRule<RequestType extends ServletRequest> {
    
    /**
     * Gets the issuer as determined by this rule.  If no issuer can be determined by 
     * this rule this method must return null
     * 
     * @return the issuer as determined by this rule
     */
    public String getIssuer();
    
    /**
     * Gets the metadata for the role the issuer is operating in.
     * 
     * @return metadata for the role the issuer is operating in
     */
    public RoleDescriptor getIssuerMetadata();

    /**
     * Evaluates the rule against the given request and message.
     * 
     * @param request the protocol request
     * @param message the incoming message
     * 
     * @throws BindingException thrown if the request/message do not meet the requirements of this rule
     */
    public void evaluate(RequestType request, XMLObject message) throws BindingException;
}