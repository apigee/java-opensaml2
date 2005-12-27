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

import java.util.GregorianCalendar;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SignableObject;
import org.opensaml.common.util.xml.XMLConstants;

/**
 * This interface defines how the object representing a SAML1 <code> Response </code> element behaves. 
 */
public interface Response extends SignableObject {
    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "Response";

    /** QName for this element. */
    public final static QName QNAME = new QName(XMLConstants.SAMLP1_NS, LOCAL_NAME, XMLConstants.SAMLP1_PREFIX);

    /** Name for the attribute which defines the responseID. */

    public final static String RESPONSEID_ATTRIB_NAME = "ResponseID";

    /** Name for the attribute which defines the responseID. */

    public final static String INRESPONSETO_ATTRIB_NAME = "InResponseTo";

    /** Name for the attribute which defines the Major Version (which must be "1". */

    public final static String MAJORVERSION_ATTRIB_NAME = "MajorVersion";

    /** Name for the attribute which defines the Minor Version. */

    public final static String MINORVERSION_ATTRIB_NAME = "MinorVersion";

    /** Name for the attribute which defines the Issue Instant. */

    public final static String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";

    /** Name for the attribute which defines the Recipient. */

    public final static String RECIPIENT_ATTRIB_NAME = "Recipient";

    /** Return the InResponseTo (attribute). */

    String getInResponseTo();

    /** Set the InResponseTo (attribute). */

    void setInResponseTo(String who);

    /** Return the Minor Version (attribute). */

    int getMinorVersion();

    /** Set the Minor Version (attribute). */

    void setMinorVersion(int version);

    /** Return the Issue Instant (attribute). */

    GregorianCalendar getIssueInstant();

    /** Set the Issue Instant (attribute). */

    void setIssueInstant(GregorianCalendar date);

    /** Return the Recipient (attribute). */

    String getRecipient();

    /** Set the Recipient (attribute). */

    void setRecipient(String recipient);

    /** Return the object representing the <code> Status <code> (element). */

    Status getStatus();

    /** Set the object representing the <code> Status <code> (element). */

    void setStatus(Status status) throws IllegalAddException;

    /** Return the object representing the <code>Assertion<code> (element). */

    Assertion getAssertion();

    /** Set the object representing the <code>Assertion<code> (element). */

    void setAssertion(Assertion assertion) throws IllegalAddException;

    //
    // Signature is dealt with in the base classes
    //
}
