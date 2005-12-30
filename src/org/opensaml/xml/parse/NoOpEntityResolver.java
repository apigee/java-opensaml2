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

package org.opensaml.xml.parse;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * An entity resolver that returns a dummy source to shortcut resolution
 * attempts.
 * 
 * During parsing, this should not be called with a systemId corresponding
 * to any known externally resolvable entity. It prevents "accidental"
 * resolution of external entities via URI resolution. Network based
 * retrieval of resources is NOT allowable and should really be something
 * the parser can block globally. We also can't return null, because that
 * signals URI resolution.
 */
public class NoOpEntityResolver implements EntityResolver {
    public NoOpEntityResolver() {

    }

    public InputSource resolveEntity(String publicId, String systemId){
        return new InputSource();
        // Hopefully this will fail the parser
        // and not be treated as null.
    }
}