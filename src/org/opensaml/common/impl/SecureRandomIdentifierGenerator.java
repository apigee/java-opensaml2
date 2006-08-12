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

package org.opensaml.common.impl;

import java.security.SecureRandom;

import org.bouncycastle.util.encoders.Hex;
import org.opensaml.common.IdentifierGenerator;

/**
 * Generates a 16 byte identifier using random data obtained from a {@link java.security.SecureRandom} instance.
 */
public class SecureRandomIdentifierGenerator implements IdentifierGenerator {

    /** Random number generator */
    private static SecureRandom random = new SecureRandom();

    /**
     * Constructor
     */
    public SecureRandomIdentifierGenerator() {

    }

    /*
     * @see org.opensaml.common.IdentifierGenerator#generateIdentifier()
     */
    public String generateIdentifier() {
        byte[] buf = new byte[16];
        random.nextBytes(buf);
        return "_".concat(new String(Hex.encode(buf)));
    }

}
