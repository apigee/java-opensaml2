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

package org.opensaml.common.impl;

import java.util.List;

import javax.security.cert.X509Certificate;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SigningContext;

/**
 * A helper for SAMLElements that implement the {@link org.opensaml.common.SignableObject} interface.
 * This helper is thread safe.
 */
public class SignableSAMLObjectHelper{
    
    private SigningContext dsigCtx;
    
    public SignableSAMLObjectHelper() {
    }
    
    public SigningContext getSigningContext() {
        return dsigCtx;
    }
    
    public void setSigningContext(SigningContext signingContext) {
        dsigCtx = signingContext;
    }

    public static String getId(SAMLObject element) {
        // TODO Auto-generated method stub
        return null;
    }

    public static boolean isSigned(SAMLObject element) {
        // TODO Auto-generated method stub
        return false;
    }

    public static String getDigestAlgorithm(SAMLObject element) {
        // TODO Auto-generated method stub
        return null;
    }

    public static String getSignatureAlgorithm(SAMLObject element) {
        // TODO Auto-generated method stub
        return null;
    }

    public static List<X509Certificate> getX509Certificates(SAMLObject element) {
        // TODO Auto-generated method stub
        return null;
    }

    public static void removeSignature(SAMLObject element) {
        // TODO Auto-generated method stub

    }
}