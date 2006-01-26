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

/**
 * This interface is for the SAML1 <code> SubjectQuery </code> extention point.
 */
public interface SubjectQuery extends Query {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "SubjectQuery";

    /** Get Subject child element */
    public Subject getSubject();
    
    /** Set Subject child element */
    public void setSubject(Subject subject);
}
