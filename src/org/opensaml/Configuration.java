/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml;

import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opensaml.common.binding.artifact.SAMLArtifactFactory;

/**
 * OpenSAML configuration singleton.
 * 
 * The library must be initialized with a set of configurations prior to usage. This is often done by invoking
 * {@link DefaultBootstrap#bootstrap()} but may done in any manner so long as all the needed object providers and
 * artifact factory are created and registered with the configuration.
 */
public class Configuration extends org.opensaml.xml.Configuration {

    /** Date format in SAML object, default is yyyy-MM-dd'T'HH:mm:ss.SSS'Z'. */
    private static String defaultDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    
    /** Formatter used to write dates. */
    private static DateTimeFormatter dateFormatter;

    /** SAML Artifact factory. */
    private static SAMLArtifactFactory artifactFactory;

    /**
     * Gets the date format used to string'ify SAML's {@link DateTime} objects.
     * 
     * @return date format used to string'ify date objects
     */
    public static DateTimeFormatter getSAMLDateFormatter() {
        if(dateFormatter == null){
            DateTimeFormatter formatter = DateTimeFormat.forPattern(defaultDateFormat);
            dateFormatter = formatter.withChronology(ISOChronology.getInstanceUTC());
        }
        
        return dateFormatter;
    }

    /**
     * Sets the date format used to string'ify SAML's date/time objects.
     * 
     * See the
     * {@link <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat</a>}
     * documentation for format syntax.
     * 
     * @param format date format used to string'ify date objects
     */
    public static void setSAMLDateFormat(String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        dateFormatter = formatter.withChronology(ISOChronology.getInstanceUTC());
    }

    /**
     * Gets the artifact factory for the library.
     * 
     * @return artifact factory for the library
     */
    public static SAMLArtifactFactory getArtifactFactory() {
        return artifactFactory;
    }

    /**
     * Sets the artifact factory for the library.
     * 
     * @param factory artifact factory for the library
     */
    public static void setArtifactFactory(SAMLArtifactFactory factory) {
        artifactFactory = factory;
    }
}