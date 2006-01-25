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

import org.opensaml.common.SAMLObject;
import org.opensaml.xml.SignableXMLObject;

/**
 * This interface defines the base class for type derived from the SAML1 <code> ResponseAbstractType </code> .
 */
public interface ResponseAbstractType extends SAMLObject, SignableXMLObject {

    /** Name for the attribute which defines InResponseTo. */
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
}