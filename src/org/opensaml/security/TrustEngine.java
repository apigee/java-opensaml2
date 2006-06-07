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

package org.opensaml.security;

import org.opensaml.common.SignableSAMLObject;
import org.opensaml.saml2.metadata.RoleDescriptor;

/**
 * Evaluates the trustworthyness and validity of various claims against SAML 2 metadata information. Specific
 * implementations define what it means to be trustworthy and valid.
 */
public interface TrustEngine<CredentialType extends EntityCredential> {

    /**
     * Verifies that the given credential is a valid and trustworthy credential for the given role.
     * 
     * @param entityCredential the entity credential to verify
     * @param descriptor the metadata role to verify the credential against
     * 
     * @return true if the credential is valid and trustworthy, false if not
     */
    public boolean validate(CredentialType entityCredential, RoleDescriptor descriptor);

    /**
     * Verifies that the given signable SAML object bear credentials that are valid and trustworthy for the given role.
     * 
     * @param samlObject the singable SAML object
     * @param descriptor the metadata role to verify the credential against
     * 
     * @return the credential used that validated the signature
     */
    public CredentialType validate(SignableSAMLObject samlObject, RoleDescriptor descriptor);
}