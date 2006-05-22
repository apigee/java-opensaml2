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

package org.opensaml.common.impl;

import org.apache.log4j.Logger;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.xml.signature.ContentReference;

public class SAMLObjectContentReference implements ContentReference {

    /** Logger */
    private static Logger log = Logger.getLogger(SAMLObjectContentReference.class);
    
    /** SAMLObject this reference refers to */
    private SignableSAMLObject signableObject;
    
    /**
     * Constructor
     *
     * @param signableObject the SAMLObject this reference refers to
     */
    public SAMLObjectContentReference(SignableSAMLObject signableObject){
        this.signableObject = signableObject;
    }
    
    public void createReference(XMLSignature signature) {
        try {
            Transforms dsigTransforms = new Transforms(signature.getDocument());
            dsigTransforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
            dsigTransforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_WITH_COMMENTS);

            signature.addDocument("#" + signableObject.getSignatureReferenceID(), dsigTransforms, MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA256);
        } catch (Exception e) {
            log.error("Error while adding content reference", e);
        }
    }
}