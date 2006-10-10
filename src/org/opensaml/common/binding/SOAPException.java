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

/**
 * Indicates that a SOAP processing error occurred in the context of the SAML SOAP binding. This subclass signals a
 * binding implementation to return a SOAP fault instead of a SAML error.
 * 
 */

public class SOAPException extends BindingException {

    public SOAPException(String message) {
        super(message);
    }

}
