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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.StringHelper;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml1.core.StatusMessage;

/**
 * Concrete implementation of org.opensaml.saml1.core StatusMessage object
 */
public class StatusMessageImpl extends AbstractSAMLObject implements StatusMessage {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -766167197012104173L;

    /**
     * Contents of the element
     */
    private String message;

    /**
     * Constructor
     */
    public StatusMessageImpl() {
        super();
        setQName(StatusMessage.QNAME);
    }

    /*
     * @see org.opensaml.saml1.core.StatusMessage#getMessage()
     */
    public String getMessage() {

        return message;
    }

    /*
     * @see org.opensaml.saml1.core.StatusMessage#setMessage(java.lang.String)
     */
    public void setMessage(String message) {

        this.message = prepareForAssignment(this.message, message);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {
        return new UnmodifiableOrderedSet<SAMLObject>(new OrderedSet<SAMLObject>());
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {

        if (!(element instanceof StatusMessage)) {
            return false;
        }

        StatusMessage statusMessage = (StatusMessage) element;

        return StringHelper.safeEquals(this.message, statusMessage.getMessage());
    }

}
