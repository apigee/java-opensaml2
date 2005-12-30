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

package org.opensaml.saml2.metadata;

import java.util.Collection;
import java.util.List;

import org.opensaml.xml.IllegalAddException;

/**
 * A functional interface RoleDescriptors may use to deal with "AssertionIdRequestService" elements.
 *
 */
public interface AssertionIDRequestDescriptorComp {

    /**
     * Gets an immutable list of assertion request service {@link Endpoint}s for this authority.
     * 
     * @return list of assertion request services
     */
    public List<AssertionIDRequestService> getAssertionIDRequestServices();

    /**
     * Adds an assertion request service {@link Endpoint} for this authority.
     * 
     * @param service the assertion request service
     * 
     * @throws IllegalAddException thrown if the given endpoint is owned by another element
     */
    public void addAssertionIDRequestService(AssertionIDRequestService service) throws IllegalAddException;

    /**
     * Removes an assertion request service {@link Endpoint} for this authority.
     * 
     * @param service the assertion request service
     */
    public void removeAssertionIDRequestService(AssertionIDRequestService service);

    /**
     * Removes a list of assertion request service {@link Endpoint} for this authority.
     * 
     * @param services the assertion request services
     */
    public void removeAssertionIDRequestServices(Collection<AssertionIDRequestService> services);

    /**
     * Removes all the assertion request services for this authority.
     */
    public void removeAllAssertionIDRequestServices();

}