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

package org.opensaml.saml2.profile.ecp;

import java.io.OutputStream;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.xml.XMLObject;

/**
 * A client that uses the SAML Enhanced Client/Proxy profile to authenticate a user to service provider using
 * credentials conveyed in WS-Security header tokens.
 */
public class EnhancedClient {

    /**
     * Constructor
     * 
     * @param metadata the metadata provider that will return metadata information for the SP and IDP
     * @param spEntityID the entity ID of the service provider
     * @param idpEntityID the entity ID of the identity provider
     */
    public EnhancedClient(MetadataProvider metadata, String spEntityID, String idpEntityID) {

    }

    /**
     * Initiates the communication with the service provider. This corresponds to steps one and two in the ECP profile.
     * 
     * @throws IllegalStateException thrown if the ECP process has already been initiated
     */
    public void initiate() throws IllegalStateException {

    }

    /**
     * Attempts to authenticate the principal using the given WS-Security token. This corresponds to steps 4, 5, and 6
     * in the ECP profile. This method may be invoked repeatedly, for example, if the authentication fails and the
     * application wishes to allow the user to submit different credentials. Some identity providers MAY detect this as
     * a replay attack, but most shouldn't.
     * 
     * @param wssCredentials the WS-Security token containing the principal's credentials
     * 
     * @return true if the authentication is successful, false if not
     * 
     * @throws IllegalStateException thrown if there has already been a successful authentication or {@link #initiate()}
     *             has not been called yet
     */
    public boolean authenticatePrincipal(XMLObject wssCredentials) throws IllegalStateException {
        return false;
    }

    /**
     * Presents the SAML authentication response back to the service provider and completes the ECP process. This
     * corresponds to steps 7 and 8 in the ECP profile.
     * 
     * @return the output from the service provider
     * 
     * @throws IllegalStateException thrown if this method has already been called or
     *             {@link #authenticatePrincipal(XMLObject)} has not yet been called
     */
    public OutputStream presentResponseToSP() throws IllegalStateException {
        return null;
    }
}