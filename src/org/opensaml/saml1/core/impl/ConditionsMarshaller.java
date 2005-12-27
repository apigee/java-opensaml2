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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallingException;
import org.opensaml.common.io.impl.AbstractMarshaller;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml2.common.impl.TimeBoundSAMLObjectHelper;
import org.w3c.dom.Element;

/**
 *
 */
public class ConditionsMarshaller extends AbstractMarshaller implements Marshaller {

    /**
     * Constructor
     */
    public ConditionsMarshaller() {
        super(Conditions.QNAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractMarshaller#marshallAttributes(org.opensaml.common.SAMLObject, org.w3c.dom.Element)
     */
    @Override
    protected void marshallAttributes(SAMLObject samlElement, Element domElement) throws MarshallingException {

        Conditions conditions = (Conditions) samlElement;

        if (conditions.getNotBefore() != null) {

            String date = TimeBoundSAMLObjectHelper.calendarToString(conditions.getNotBefore());

            domElement.setAttribute(Conditions.NOTBEFORE_ATTRIB_NAME, date);
        }

        if (conditions.getNotOnOrAfter() != null) {

            String date = TimeBoundSAMLObjectHelper.calendarToString(conditions.getNotOnOrAfter());

            domElement.setAttribute(Conditions.NOTONORAFTER_ATTRIB_NAME, date);
        }
    }
}
