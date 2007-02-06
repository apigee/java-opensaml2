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

import javolution.util.FastSet;

import org.apache.log4j.Logger;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.transforms.params.InclusiveNamespaces;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.xml.Namespace;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.EncryptionConstants;
import org.opensaml.xml.signature.ContentReference;
import org.opensaml.xml.signature.SignatureConstants;
import org.w3c.dom.Element;

/**
 * A content reference for SAML objects that will be signed. The reference is created per the SAML specification. An
 * inclusive namespace list, used when the content is canonicalized, is generated from the namespaces, retrieved from
 * {@link org.opensaml.xml.XMLObject#getNamespaces()}, used by the SAML object to be signed and all of it's
 * descendants.
 */
public class SAMLObjectContentReference implements ContentReference {

    /** Class logger. */
    private static Logger log = Logger.getLogger(SAMLObjectContentReference.class);

    /** SAMLObject this reference refers to. */
    private SignableSAMLObject signableObject;

    /**
     * Constructor.
     * 
     * @param newSignableObject the SAMLObject this reference refers to
     */
    public SAMLObjectContentReference(SignableSAMLObject newSignableObject) {
        signableObject = newSignableObject;
    }

    /** {@inheritDoc} */
    public void createReference(XMLSignature signature) {
        try {
            Transforms dsigTransforms = new Transforms(signature.getDocument());
            dsigTransforms.addTransform(SignatureConstants.TRANSFORM_ENVELOPED_SIGNATURE);
            dsigTransforms.addTransform(SignatureConstants.TRANSFORM_C14N_EXCL_WITH_COMMENTS);

            // Namespaces that aren't visibly used, such as those used in QName attribute values, would
            // be stripped out by exclusive canonicalization. Need to make sure they aren't by explicitly
            // telling the transformer about them.
            if (log.isDebugEnabled()) {
                log.debug("Adding namespaces to list of inclusive namespaces for signature");
            }
            FastSet<String> inclusiveNamespacePrefixes = new FastSet<String>();
            populateNamespacePrefixes(inclusiveNamespacePrefixes, signableObject);

            if (inclusiveNamespacePrefixes != null && inclusiveNamespacePrefixes.size() > 0) {
                InclusiveNamespaces inclusiveNamespaces = new InclusiveNamespaces(signature.getDocument(),
                        inclusiveNamespacePrefixes);
                Element transformElem = dsigTransforms.item(1).getElement();
                transformElem.appendChild(inclusiveNamespaces.getElement());
            }

            signature.addDocument("#" + signableObject.getSignatureReferenceID(), dsigTransforms,
                    EncryptionConstants.ALGO_ID_DIGEST_SHA256);
        } catch (TransformationException e) {
            log.error("Unsupported signature transformation", e);
        } catch (XMLSignatureException e) {
            log.error("Error adding content reference to signature", e);
        }
    }

    /**
     * Populates the given set with all the namespaces used by the given XMLObject and all of its descendants.
     * 
     * @param namespacePrefixes the namespace prefix set to be populated
     * @param signatureContent the XMLObject whose namespace prefixes will be used to populate the set
     */
    private void populateNamespacePrefixes(FastSet<String> namespacePrefixes, XMLObject signatureContent) {
        if (signatureContent.getNamespaces() != null) {
            for (Namespace namespace : signatureContent.getNamespaces()) {
                if (namespace != null) {
                    namespacePrefixes.add(namespace.getNamespacePrefix());
                }
            }
        }

        if (signatureContent.getOrderedChildren() != null) {
            for (XMLObject xmlObject : signatureContent.getOrderedChildren()) {
                if (xmlObject != null) {
                    populateNamespacePrefixes(namespacePrefixes, xmlObject);
                }
            }
        }
    }
}