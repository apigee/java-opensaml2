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

package org.opensaml.common.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.common.util.xml.XMLConstants;

/**
 * A SAMLObject that can be used to represent any XML element that does not have a registered Object provider.
 */
public class NoDefSAMLObject extends AbstractSAMLObject implements SAMLObject {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -6280537875478720732L;
    
    /** QName for no defenition objects */
    public final static QName QNAME = new QName(XMLConstants.OPENSAML_CONFIG_NS, XMLConstants.OPENSAML_CONFIG_PREFIX, "NoDef");

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {
        // Does not allow children
        return null;
    }

    /**
     * Two NoDefSAMLObjects are equal if, and only if, they are the same object.
     */
    public boolean equals(SAMLObject element) {
        return this == element;
    }
}
