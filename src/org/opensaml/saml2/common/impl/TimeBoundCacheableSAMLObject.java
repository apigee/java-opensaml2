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

package org.opensaml.saml2.common.impl;

import java.util.GregorianCalendar;

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;

/**
 * Abstract SAML object handling validUntil and cacheDuration functions
 */
public abstract class TimeBoundCacheableSAMLObject extends AbstractSAMLObject implements TimeBoundSAMLObject,
        CacheableSAMLObject {
    
    /**
     * Helper for dealing TimeBoundElement interface methods
     */
    private TimeBoundSAMLObjectHelper timeBoundHelper;
    
    /**
     * Helper for dealing CacheableElement interface methods
     */
    private CacheableSAMLObjectHelper cacheableHelper;
    
    /**
     * Constructor
     *
     */
    public TimeBoundCacheableSAMLObject(){
        super();
        timeBoundHelper = new TimeBoundSAMLObjectHelper(this);
        cacheableHelper = new CacheableSAMLObjectHelper(this);
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundElementAspect#isValid()
     */
    public boolean isValid() {
        return timeBoundHelper.isValid();
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundElementAspect#getValidUntil()
     */
    public GregorianCalendar getValidUntil() {
        return timeBoundHelper.getValidUntil();
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundElementAspect#setValidUntil(java.util.GregorianCalendar)
     */
    public void setValidUntil(GregorianCalendar validUntil) {
        timeBoundHelper.setValidUntil(validUntil);
    }

    /*
     * @see org.opensaml.saml2.common.CacheableElementAspect#getCacheDuration()
     */
    public Long getCacheDuration() {
        return cacheableHelper.getCacheDuration();
    }

    /*
     * @see org.opensaml.saml2.common.CacheableElementAspect#setCacheDuration(java.lang.Long)
     */
    public void setCacheDuration(Long duration) {
        cacheableHelper.setCacheDuration(duration);
    }
}
