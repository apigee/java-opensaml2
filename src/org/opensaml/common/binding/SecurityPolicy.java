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

import java.util.List;

import javax.servlet.ServletRequest;

import org.opensaml.xml.validation.ValidatorSuite;

/**
 * A policy used to verify the security of an incoming request.  Its security mechanisms may be used to 
 * check transport layer items (e.g client certificates and basic auth passwords) and the payload valiators 
 * may be used to check the payload of a request to ensure it meets certain criteria (e.g. valid digital signature).
 * 
 * Policies must be thread-safe and stateless such that multiple threads may call the validate method one or more 
 * times and recieve a proper answer.
 */
public interface SecurityPolicy<RequestType extends ServletRequest> {

    /**
     * Gets the list of security mechanisms that will be used to ensure the transport layer is secure.  
     * Security mechanisms will be evaluated in the order they appear in the list.
     * 
     * @return security mechanisms that will be used to ensure the transport layer is secure
     */
    public List<TransportValidator<RequestType>> getSecurityMechanisms();
    
    /**
     * Gets the validators used to determine if the request payload is secure.  Validators are evaluated in 
     * the order they appear in the list.
     * 
     * @return validators used to determine if the request payload is secure
     */
    public List<ValidatorSuite> getPayloadValidation();
    
    /**
     * Gets the entity ID of the relying party that issues the SAML request.
     * 
     * @return entity ID of the relying party that issues the SAML request
     */
    public String getRelyingParty();
    
    /**
     * Evaluates this policy.
     * 
     * @throws BindingException thrown if the request does not meet the requirements of this policy
     */
    public void validate(RequestType request) throws BindingException;
}