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

/**
 * 
 */
package org.opensaml.saml1.core.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.saml1.core.RespondWith;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete (but abstract) class for <code> org.opensaml.saml1.core.RequestAbstractType </code>
 */
public abstract class RequestAbstractTypeImpl extends AbstractSignableSAMLObject implements RequestAbstractType {

    /** Contains the minor version */
    private int version;
    
    /** Containt the IssueInstant */
    public DateTime issueInstant;
    
    /** Contains the RespondWiths */
    public final List<RespondWith> RespondWiths; 
    
    /**
     * Constructor.
     */
    public RequestAbstractTypeImpl(String namespaceURI, String elementLocalName) {
        super(namespaceURI, elementLocalName);
        RespondWiths = new XMLObjectChildrenList<RespondWith>(this);
    }

    public int getMinorVersion() {
        return version;
    }

    public void setMinorVersion(int version) {
        if (this.version != version) {
            releaseThisandParentDOM();
            this.version = version;
        }
    }

    public DateTime getIssueInstant() {
        return issueInstant;
    }

    public void setIssueInstant(DateTime gregorianCalendar) {
        this.issueInstant = prepareForAssignment(this.issueInstant, gregorianCalendar.withZone(DateTimeZone.UTC));
    }

    public List<RespondWith> getRespondWiths() {
        return RespondWiths;
    }
}
