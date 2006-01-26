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

import java.util.List;

/**
 * This interface defines how the SAML1 <code> Request </code> objects behave.
 */
public interface Request extends RequestAbstractType {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "Request";

    /* 
     * A bit odd this, it s a choice so only one of these will return any value
     */
    
    /** Get the query (Query, SubjectQuery, AuthenticationQuery, AttributeQuery, AuthorizationDecisioonQuery */ 
    public Query getQuery();
    
    /** Set the query (Query, SubjectQuery, AuthenticationQuery, AttributeQuery, AuthorizationDecisioonQuery 
     * @throws IllegalAddException */ 
    public void setQuery(Query query) throws IllegalArgumentException;
    
    /** Get the lists of AssertionIDReferences */
    public List <AssertionIDReference> getAssertionIDReferences();
    
    /** Get the lists of */
    public List <AssertionArtifact> getAssertionArtifacts();
}
