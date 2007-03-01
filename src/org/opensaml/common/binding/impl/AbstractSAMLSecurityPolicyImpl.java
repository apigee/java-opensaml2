/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.common.binding.impl;

import javax.servlet.ServletRequest;

import org.opensaml.common.binding.SAMLSecurityPolicyContext;
import org.opensaml.common.binding.SAMLSecurityPolicy;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.impl.BasicSecurityPolicyImpl;

/**
 * SAML-specifc security policy which provides metadata access and supplies
 * a {@link SecurityPolicyContext} of a SAML-specific subtype.
 * 
 * @param <RequestType> the message request type
 * @param <IssuerType> the message issuer type
 * 
 */
public abstract class AbstractSAMLSecurityPolicyImpl<RequestType extends ServletRequest, IssuerType> 
        extends BasicSecurityPolicyImpl<RequestType, IssuerType> 
        implements SAMLSecurityPolicy<RequestType, IssuerType> {

    /** {@inheritDoc} */
    public RoleDescriptor getIssuerMetadata() {
        return ((SAMLSecurityPolicyContext<IssuerType>)  getSecurityPolicyContext()).getIssuerMetadata();
    }

    /** {@inheritDoc} */
    protected SecurityPolicyContext<IssuerType> createNewContext() {
        return new SAMLSecurityPolicyContext<IssuerType>();
    }

}
