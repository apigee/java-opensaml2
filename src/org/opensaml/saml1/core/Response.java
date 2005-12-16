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

package org.opensaml.saml1.core;

import java.util.Date;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.xml.XMLConstants;

public interface Response extends SAMLObject
{
    
    /** Element name, no namespace */
    public final static String LOCAL_NAME = "Response";
    
    /** QName for this element */
    public final static QName QNAME = new QName(XMLConstants.SAMLP1_NS, LOCAL_NAME, XMLConstants.SAMLP1_PREFIX);

    public final static String RESPONSEID_ATTRIB_NAME = "ResponseID";
    
    public final static String INRESPONSETO_ATTRIB_NAME = "InResponseTo";
    
    public final static String MAJORVERSION_ATTRIB_NAME = "MajorVersion";
    
    public final static String MINORVERSION_ATTRIB_NAME = "MinorVersion";

    public final static String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";
    
    public final static String RECIPIENT_ATTRIB_NAME = "Recipient";


    String getResponseID();

    void setResponseID(String id);

    // InResponseTo
    
    String getInResponseTo();

    void setInResponseTo(String who);

    // Major version is pegged to be 1 
    
    int getMinorVersion();

    void setMinorVersion(int version);
   
    Date getIssueInstant();

    void setIssueInstant(Date date);
    
    String getRecipient();
    
    void setRecipient(String recipient);
    
    Status getStatus();

    void setStatus(Status status) throws IllegalAddException;

    Assertion getAssertion();

    void setAssertion(Assertion assertion) throws IllegalAddException;
    
    //
    // Signature is dealt with in the layers below
    //
}
