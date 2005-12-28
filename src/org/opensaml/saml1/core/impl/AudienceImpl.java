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
import org.opensaml.saml1.core.Audience;

/**
 * Concrete class implementation of org.opensaml.saml1.core.Audience  
 */
public class AudienceImpl extends AbstractSAMLObject implements Audience {

    /**
     * Serialization GUID
     */
    private static final long serialVersionUID = 1277136971012634435L;

    /** String to hold the URI */
    private String uri;

    /**
     * Constructor
     */
    public AudienceImpl() {
        super();
        setQName(Audience.QNAME);
    }

    /*
     * @see org.opensaml.saml1.core.Audience#getUri()
     */
    public String getUri() {
        return uri;
    }

    /*
     * @see org.opensaml.saml1.core.Audience#setUri()
     */
    public void setUri(String uri) {

        this.uri = prepareForAssignment(this.uri, uri);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {
        return new UnmodifiableOrderedSet<SAMLObject>(new OrderedSet<SAMLObject>(0));
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {

        if (!(element instanceof Audience)) {
            return false;
        }
        Audience other = (Audience) element;

        return StringHelper.safeEquals(uri, other.getUri());
    }

}
