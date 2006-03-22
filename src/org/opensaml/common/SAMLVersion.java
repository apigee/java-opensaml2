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

package org.opensaml.common;

/**
 * A type safe SAML version enumeration.
 */
public class SAMLVersion {
    /**
     * SAML version 1.0
     */
    public static final SAMLVersion VERSION_10 = new SAMLVersion(1, 0);
    
    /**
     * SAML Version 1.1
     */
    public static final SAMLVersion VERSION_11 = new SAMLVersion(1, 1);
    
    /**
     * SAML Version 2.0
     */
    public static final SAMLVersion VERSION_20 = new SAMLVersion(2, 0);

    /** Major version number */
    private int majorVersion;
    
    /** Minor version number */
    private int minorVersion;
    
    /** String representation of the version */
    private String versionString;
    
    /** Constructor */
    public SAMLVersion(int majorVersion, int minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        
        versionString = majorVersion + "." + minorVersion;
    }
    
    /**
     * Gets the major version of the SAML version.
     * 
     * @return the major version of the SAML version
     */
    public int getMajorVersion() {
        return majorVersion;
    }
    
    /**
     * Gets the minor version of the SAML version.
     * 
     * @return the minor version of the SAML version
     */
    public int getMinorVersion() {
        return minorVersion;
    }
    
    /*
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return versionString;
    }
}
