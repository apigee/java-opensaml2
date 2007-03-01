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
import org.opensaml.ws.security.SecurityPolicy;

/**
 * A policy used to verify the security of an incoming SAML request.  Its security mechanisms may be used to 
 * check transport layer items (e.g client certificates and basic auth passwords) and the payload valiators 
 * may be used to check the payload of a request to ensure it meets certain criteria (e.g. valid digital signature).
 * 
 * @param <RequestType> type of incoming protocol request
 * @param <IssuerType> the message issuer type
 */
public interface SAMLSecurityPolicy<RequestType extends ServletRequest, IssuerType> 
        extends SecurityPolicy<RequestType, IssuerType> {
    
    /**
     * Convenience method for getting the metadata for the role in which the issuer is 
     * operating, obtained from the security policy's context instance.
     * 
     * @return metadata for the role in which the issuer is operating
     */
    public RoleDescriptor getIssuerMetadata();
}