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

import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import javolution.util.FastList;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERString;

public class X509Util {

    /** Class logger */
    private final static Logger log = Logger.getLogger(X509Util.class);

    /** Common Name (CN) OID */
    public static final String CN_OID = "2.5.4.3";
    
    /** RFC 2459 Other Subject Alt Name type */
    public static final Integer OTHER_ALT_NAME = new Integer(0);
    
    /** RFC 2459 RFC 822 (email address) Subject Alt Name type */
    public static final Integer RFC822_ALT_NAME = new Integer(1);
    
    /** RFC 2459 DNS Subject Alt Name type */
    public static final Integer DNS_ALT_NAME = new Integer(2);
    
    /** RFC 2459 X.400 Address Subject Alt Name type */
    public static final Integer X400ADDRESS_ALT_NAME = new Integer(3);
    
    /** RFC 2459 Directory Name Subject Alt Name type */
    public static final Integer DIRECTORY_ALT_NAME = new Integer(4);
    
    /** RFC 2459 EDI Party Name Subject Alt Name type */
    public static final Integer EDI_PARTY_ALT_NAME = new Integer(5);
    
    /** RFC 2459 URI Subject Alt Name type */
    public static final Integer URI_ALT_NAME = new Integer(6);
    
    /** RFC 2459 IP Address Subject Alt Name type */
    public static final Integer IP_ADDRESS_ALT_NAME = new Integer(7);
    
    /** RFC 2459 Registered ID Subject Alt Name type */
    public static final Integer REGISTERED_ID_ALT_NAME = new Integer(8);

    /**
     * Gets the commons names that appear within the given distinguished name. The returned list provides the names in
     * the order they appeared in the DN.
     * 
     * @param dn the DN to extract the common names from
     * 
     * @return the common names that appear in the DN in the order they appear or null if the given DN is null
     */
    public static List<String> getCommonNames(X500Principal dn) {
        if(dn == null){
            return null;
        }
        
        if (log.isDebugEnabled()) {
            log.debug("Extracting CNs from the following DN: " + dn.toString());
        }

        FastList<String> commonNames = new FastList<String>();
        try {
            ASN1InputStream asn1Stream = new ASN1InputStream(dn.getEncoded());
            DERObject parent = asn1Stream.readObject();

            String cn = null;
            DERObject dnComponent;
            DERSequence grandChild;
            DERObjectIdentifier componentId;
            for (int i = 0; i < ((DERSequence) parent).size(); i++) {
                dnComponent = ((DERSequence) parent).getObjectAt(i).getDERObject();
                if (!(dnComponent instanceof DERSet)) {
                    if (log.isDebugEnabled()) {
                        log.debug("No DN components.");
                    }
                    continue;
                }

                // Each DN component is a set
                for (int j = 0; j < ((DERSet) dnComponent).size(); j++) {
                    grandChild = (DERSequence) ((DERSet) dnComponent).getObjectAt(j).getDERObject();

                    if (grandChild.getObjectAt(0) != null
                            && grandChild.getObjectAt(0).getDERObject() instanceof DERObjectIdentifier) {
                        componentId = (DERObjectIdentifier) grandChild.getObjectAt(0).getDERObject();

                        if (CN_OID.equals(componentId.getId())) {
                            // OK, this dn component is actually a cn attribute
                            if (grandChild.getObjectAt(1) != null
                                    && grandChild.getObjectAt(1).getDERObject() instanceof DERString) {
                                cn = ((DERString) grandChild.getObjectAt(1).getDERObject()).getString();
                                commonNames.add(cn);
                            }
                        }
                    }
                }
            }

            asn1Stream.close();

            return commonNames;

        } catch (IOException e) {
            log.error("Unable to extract common names from DN: ASN.1 parsing failed: " + e);
            return null;
        }
    }
    
    /**
     * Gets the list of alternative names of a given name type.
     * 
     * @param certificate the certificate to extract the alternative names from
     * @param nameTypes the name types
     * 
     * @return the alt names, of the given type, within the cert 
     */
    public static List getAltNames(X509Certificate certificate, Integer[] nameTypes){
        if(certificate == null){
            return null;
        }
        
        FastList<Object> names = new FastList<Object>();
        try {
            Collection<List<?>> altNames = certificate.getSubjectAlternativeNames();
            if (altNames != null) {
                //  0th position represents the alt name type
                // 1st position contains the alt name data
                List altName;
                for (Iterator<List<?>> nameIterator = altNames.iterator(); nameIterator.hasNext();) {
                    altName = nameIterator.next();
                    for(int i = 0; i < nameTypes.length; i ++){
                        if(altName.get(0).equals(nameTypes[i])){
                            names.add(altName.get(1));
                            break;
                        }
                    }
                }
            }
        } catch (CertificateParsingException e1) {
            log.error("Encountered an problem trying to extract Subject Alternate "
                    + "Name from supplied certificate: " + e1);
        }
        
        return names;
    }

}