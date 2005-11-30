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

import java.io.Serializable;

/**
 * A helper for SAMLElements that implement the {@link org.opensaml.saml2.common.CacheableSAMLObject} interface.
 * This helper is <strong>NOT</strong> thread safe.
 */
public class CacheableSAMLObjectHelper implements Serializable{

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 7605403740399106752L;

    /**
     * Cache duration
     */
    private Long cacheDuration;
    
    /**
     * The SAMLElement that contains this helper
     */
    private AbstractSAMLObject containingElement;
    
    /**
     * 
     * Constructor
     *
     */
    public CacheableSAMLObjectHelper(AbstractSAMLObject containingElement){
        this.containingElement = containingElement;
    }
    
    /**
     * Gets the maximum time, in milliseconds, that this descriptor should be cached.
     *  
     * @return the maximum time that this descriptor should be cached
     */
    public Long getCacheDuration(){
        return cacheDuration;
    }

    /**
     * Sets the maximum time, in milliseconds, that this descriptor should be cached.
     * 
     * @param duration the maximum time that this descriptor should be cached
     */
    public void setCacheDuration(Long duration){
        if(cacheDuration == null) {
            if(duration == null) {
                return;
            }
        }else {
            if(cacheDuration.equals(duration));
            return;
        }

        containingElement.releaseThisandParentDOM();
        cacheDuration = duration;
    }
}
