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

import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.saml2.common.ExtensionsExtensibleSAMLObject;

/**
 * SAML 2.0 Metadata Organization
 */
public interface Organization extends SAMLObject, ExtensionsExtensibleSAMLObject, NameDescriptorComp{
    
    /** Local name, no namespace */
    public final static String LOCAL_NAME = "Organization";
    
    /** QName for element */
    public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);

    /**
     * Gets an immutable list of diaply names, {@link LocalizedString}s, for this service.
     * 
     * @return list of names
     */
	public Set /*<LocaledString>*/ getDisplayName();

    /**
     * Gets the localized display name in a given language.
     * 
     * @param language the language
     * @return the name for this service localized to the given language
     */
	public LocalizedString getDisplayName(String language);
    
    /**
     * Convience method for get the localized string of the display name in a given language. This is the same
     * as calling this.getDisplayName(String).getLocalString().
     * 
     * @param language the language of the name
     * @return the name for this service localized to the given language
     */
    public String getDisplayNameAsString(String language);
    
    /**
     * Adds a localized display name. If a localized name in the same language is already present it is
     * replaced.
     * 
     * @param name the name
     */
    public void addDisplayName(LocalizedString name);
    
    /**
     * Adds a list of localized display name. If a localized name in the same language is already present it
     * is replaced.
     * 
     * @param names the names
     */
    public void addDisplayNames(Set /*<LocalizedString>*/ names);
    
    /**
     * Removes a localized display name.
     * 
     * @param name the name
     */
    public void removeDisplayName(LocalizedString name);
    
    /**
     * Removes a list of localized display names.
     * 
     * @param names the names
     */
    public void removeDisplayNames(Set /*<LocalizedString>*/ names);
    
    /**
     * Removes all the localized display names.
     */
    public void removeAllDisplayNames();

    /**
     * Gets an immutable list of URLs, {@link LocalizedString}s, for this organization.
     * 
     * @return list of URLs, {@link LocalizedString}s, for this organization
     */
	public Set /*<LocalizedString>*/ getURLs();

    /**
     * Gets the URL for the given language.
     * 
     * @param language the language
     * 
     * @return the URL for this organization
     */
	public LocalizedString getURL(String language);
    
    /**
     * Gets the URL for the given language as a string.
     * 
     * @param language the language
     * 
     * @return the URL for this organization
     */
    public String getURLAsString(String language);
    
    /**
     * Adds a URL to this organization.
     * 
     * @param url the URL
     */
	public void addURL(LocalizedString url);
    
    /**
     * Adds a list of URL to this organization.
     * 
     * @param urls the URLs
     */
    public void addURLs(Set /*<LocalizedString>*/ urls);
    
    /**
     * Removes a URL from this organization.
     * 
     * @param url the URL
     */
    public void removeURL(LocalizedString url);
    
    /**
     * Removes a list of URLs from this organization.
     * 
     * @param urls the URLs
     */
    public void removeURLs(Set /*<LocalizedString>*/ urls);
    
    /**
     * Removes all the URLs from this organizaition.
     *
     */
    public void removeAllURLs();
}
