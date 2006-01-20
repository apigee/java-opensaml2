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

package org.opensaml.xml.util;

import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.Set;

import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.content.X509Data;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.transforms.params.InclusiveNamespaces;
import org.opensaml.xml.DOMCachingXMLObject;
import org.opensaml.xml.SignableXMLObject;
import org.opensaml.xml.SigningContext;
import org.opensaml.xml.XMLObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Helper class for working with digital signatures.
 */
public class DigitalSignatureHelper {

    /** Signature element name */
    public static final String SIGNATURE_ELEMENT_NAME = "Signature";

    /**
     * Checks to see if a given XMLObject is signed. A XMLObject can only be signed if it's an instance of
     * {@link SignableXMLObject} and {@link DOMCachingXMLObject}, it has a cached DOM, that is
     * {@link DOMCachingXMLObject#getDOM()} does not return null, and that DOM contains a signature element. Note that
     * the validity of the signature is not checked.
     * 
     * @param XMLObject the XML object to check
     * 
     * @return true if the object is signed, false if not
     */
    public static boolean isSigned(XMLObject xmlObject) {
        if (xmlObject instanceof SignableXMLObject && xmlObject instanceof DOMCachingXMLObject) {
            DOMCachingXMLObject domCachingXMLObject = (DOMCachingXMLObject) xmlObject;
            return isSigned(domCachingXMLObject.getDOM());
        }

        return false;
    }
    
    /**
     * Checks to see if the given DOM element is signed.  Note that
     * the validity of the signature is not checked.
     * 
     * @param domElement the DOM element to check
     * 
     * @return true if the element is signed, false if not
     */
    public static boolean isSigned(Element domElement) {
        if(domElement == null) {
            return false;
        }
        
        if (getSignatureElement(domElement) != null) {
            return true;
        }
        
        return false;
    }

    /**
     * Gets the Signature element for this DOM Element, or null if the it does not contain a Signature element.
     * 
     * @param domElement the DOM Element to retrieve the Signature element from
     * 
     * @return the Signature element
     */
    public static Element getSignatureElement(Element domElement) {
        if (domElement == null) {
            return null;
        }

        NodeList children = domElement.getChildNodes();
        Element childElement;
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            childElement = (Element) children.item(i);
            if (childElement.getNamespaceURI().equals(XMLConstants.XMLSIG_NS)
                    && childElement.getLocalName().equals(SIGNATURE_ELEMENT_NAME)) {
                return childElement;
            }
        }
        return null;
    }

    /**
     * Signs a DOM element.
     * 
     * @param domElement the DOM Element to be signed
     * @param signatureContext the signature context containing the information need for signing
     * @param inclusiveNamespacePrefixes list of namespace, identified by prefix, to include in the signature
     * 
     * @throws SignatureException thrown if there is a problem creating the signature
     */
    public static void signElement(Element domElement, SigningContext signatureContext,
            Set<String> inclusiveNamespacePrefixes) throws SignatureException {
        try {
            String idAttributeName = signatureContext.getIdAttributeName();
            String idAttributeValue = signatureContext.getIdAttributeValue();

            domElement.setAttributeNS(null, idAttributeName, idAttributeValue);
            domElement.setIdAttributeNS(null, idAttributeName, true);

            XMLSignature dsig = new XMLSignature(domElement.getOwnerDocument(), "", signatureContext
                    .getSignatureAlgorithim(), Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

            if (signatureContext.getPublicKeyCertificate() != null) {
                dsig.addKeyInfo(signatureContext.getPublicKeyCertificate());
            }
            if (signatureContext.getPublicKey() != null) {
                dsig.addKeyInfo(signatureContext.getPublicKey());
            }

            domElement.appendChild(dsig.getElement());

            // Create the transformations the element will go through to prepare for signing
            Transforms dsigTransforms = new Transforms(dsig.getDocument());
            dsigTransforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
            dsigTransforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);

            // Namespaces that aren't visibly used, such as those used in QName attribute values, would
            // be stripped out by exclusive canonicalization. Need to make sure they aren't by explicitly
            // telling the transformer about them.
            if (inclusiveNamespacePrefixes != null && inclusiveNamespacePrefixes.size() > 0) {
                InclusiveNamespaces inclusiveNamespaces = new InclusiveNamespaces(domElement.getOwnerDocument(),
                        inclusiveNamespacePrefixes);
                Element transformElem = dsigTransforms.item(1).getElement();
                transformElem.appendChild(inclusiveNamespaces.getElement());
            }

            dsig.addDocument("#" + idAttributeValue, dsigTransforms, signatureContext.getDigestAlgorithim());

            X509Data x509Data = new X509Data(domElement.getOwnerDocument());
            if (signatureContext.getCerts() != null) {
                for (X509Certificate cert : signatureContext.getCerts()) {
                    x509Data.addCertificate(cert);
                }
            }

            dsig.sign(signatureContext.getSigningKey());

        } catch (XMLSecurityException e) {
            throw new SignatureException("Unable to sign Element " + domElement.getLocalName(), e);
        }
    }

    /**
     * Removes the digital signature, including the associated {@link SigningContext}, from the given XMLObject.  If
     * the XMLObject is not signed nothing happens.
     * 
     * @param XMLObject the XMLObject to remove the signature from
     */
    public static void removeSignature(XMLObject xmlObject) {
        if (xmlObject instanceof SignableXMLObject && xmlObject instanceof DOMCachingXMLObject) {
            DOMCachingXMLObject domCachingXMLObject = (DOMCachingXMLObject) xmlObject;
            removeSignature(domCachingXMLObject.getDOM());
            
            SignableXMLObject signableXMLObject = (SignableXMLObject)xmlObject;
            signableXMLObject.setSigningContext(null);
        }
    }

    /**
     * Removes the digital signature from the given DOM element.  If the DOM element is not signed nothing happens.
     * 
     * @param domElement the DOM element to remove the signature from
     */
    public static void removeSignature(Element domElement) {
        if(domElement == null) {
            return;
        }
        
        Element signatureElement = getSignatureElement(domElement);
        if(signatureElement == null) {
            return;
        }
        
        domElement.removeChild(signatureElement);
    }

    /**
     * Validates the digital signature of the given Element. The element must contain either the public key that will be
     * used to validate the signature or a certificate that contains the public key.
     * 
     * @param domElement the Element
     * 
     * @throws SignatureException thrown if the Element is not signed, the signature can not be validated, or the
     *             signature is not valid
     */
    public static void verifySignature(Element domElement) throws SignatureException {
        try {
            Element signatureElement = getSignatureElement(domElement);
            if (signatureElement == null) {
                throw new SignatureException("Element " + domElement.getLocalName() + " is not signed.");

            }
            XMLSignature signature = new XMLSignature(signatureElement, "");

            KeyInfo keyInfo = signature.getKeyInfo();
            if (keyInfo == null) {
                throw new SignatureException("Unable to validate digital signature for Element "
                        + domElement.getLocalName() + ", no key info present within Signatue element");
            }

            X509Certificate cert = keyInfo.getX509Certificate();
            if (cert != null) {
                if (!signature.checkSignatureValue(cert)) {
                    throw new SignatureException("Digital signature for Element " + domElement.getLocalName()
                            + " was not valid.");
                }

                return;
            }

            PublicKey publicKey = keyInfo.getPublicKey();
            if (publicKey != null) {
                if (!signature.checkSignatureValue(publicKey)) {
                    throw new SignatureException("Digital signature for Element " + domElement.getLocalName()
                            + " was not valid.");
                }

                return;
            }

            throw new SecurityException("Element " + domElement.getLocalName()
                    + " did not contain a public key or certificate that could be used to validate digital signature");
        } catch (XMLSecurityException e) {
            throw new SignatureException("Unable to validate digital signature for Element "
                    + domElement.getLocalName(), e);
        }
    }

    /**
     * Constructs a signing context from the information located a signed DOM Element. This information does NOT contain
     * the private signing key or the digest method.
     * 
     * @param domElement the signed DOM element
     * 
     * @return the SigningContext populated with information from the digital signature or null if the element is not
     *         signed
     */
    public static SigningContext buildFromSignature(Element domElement) {
        try {
            Element signatureElement = getSignatureElement(domElement);
            if (signatureElement == null) {
                return null;
            }
            Attr idAttribute = XMLHelper.getIdAttribute(domElement);
            if (idAttribute == null) {
                return null;
            }

            SigningContext signatureContext = new SigningContext(idAttribute.getLocalName(), idAttribute.getValue());

            XMLSignature signature = new XMLSignature(signatureElement, "");

            signatureContext.setSignatureAlgorithim(signature.getSignedInfo().getSignatureMethodURI());

            KeyInfo keyInfo = signature.getKeyInfo();
            if (keyInfo != null) {
                signatureContext.setPublicKeyCertificate(keyInfo.getX509Certificate());
                signatureContext.setPublicKey(keyInfo.getPublicKey());
            }

            return signatureContext;
        } catch (XMLSecurityException e) {
            return null;
        }
    }
}
