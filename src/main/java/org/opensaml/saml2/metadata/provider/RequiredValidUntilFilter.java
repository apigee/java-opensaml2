/*
 * Copyright 2008 University Corporation for Advanced Internet Development, Inc.
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

package org.opensaml.saml2.metadata.provider;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A metadata filter that requires the presence of a <code>validUntil</code> attribute on the root element of the
 * metadata document. It can optionally also enforce that the validity period (now minus <code>validUntil</code> date)
 * is not longer than a specified amount.
 * 
 * A maximum validity interval of less than 1 means the no restriction is placed on the metadata's
 * <code>validUntil</code> attribute.
 */
public class RequiredValidUntilFilter implements MetadataFilter {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(RequiredValidUntilFilter.class);

    /** The maximum interval, in seconds, between now and the <code>validUntil</code> date. */
    private long maxValidityInterval;

    /**
     * Constructor.
     * 
     * Requires that the maximum validity interval is no greater than 1 week.
     */
    public RequiredValidUntilFilter() {
        maxValidityInterval = 60 * 60 * 24 * 7;
    }

    /**
     * Constructor.
     * 
     * @param maxValidityInterval maximum internal, in seconds, between now and the <code>validUntil</code> date
     */
    public RequiredValidUntilFilter(long maxValidityInterval) {
        this.maxValidityInterval = maxValidityInterval;
    }

    /**
     * Gets the maximum internal, in seconds, between now and the <code>validUntil</code> date. A value of less than 1
     * indicates that there is no restriction.
     * 
     * @return maximum internal, in seconds, between now and the <code>validUntil</code> date
     */
    public long getMaxValidityInterval() {
        return maxValidityInterval;
    }

    /** {@inheritDoc} */
    public void doFilter(XMLObject metadata) throws FilterException {
        DateTime validUntil;

        if (metadata instanceof EntitiesDescriptor) {
            validUntil = ((EntitiesDescriptor) metadata).getValidUntil();
        } else if (metadata instanceof EntityDescriptor) {
            validUntil = ((EntityDescriptor) metadata).getValidUntil();
        } else {
            log.error("Metadata root element was not an EntitiesDescriptor or EntityDescriptor it was a {}", metadata
                    .getElementQName());
            throw new FilterException("Metadata root element was not an EntitiesDescriptor or EntityDescriptor");
        }

        if (validUntil == null) {
            throw new FilterException("Metadata did not include a validUntil attribute");
        }

        long validityInterval = new Interval(new DateTime(), validUntil).toDurationMillis();
        if (validityInterval > maxValidityInterval * 1000) {
            throw new FilterException("Metadata's validity interval, " + validityInterval
                    + "ms, is larger than is allowed, " + maxValidityInterval + "ms.");
        }
    }
}