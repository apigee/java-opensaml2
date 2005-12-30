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

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SAXErrorHandler implements ErrorHandler {
    
    /** logger */
    private static Logger log = Logger.getLogger(SAXErrorHandler.class);
    
    /**
     * Constructor
     */
    public SAXErrorHandler() {

    }

    /**
     * Called by parser if a fatal error is detected, does nothing
     * 
     * @param exception Describes the error
     * @exception SAXException Can be raised to indicate an explicit error
     */
    public void fatalError(SAXParseException e) throws SAXException {
        throw e;
    }

    /**
     * Called by parser if an error is detected, currently just throws e
     * 
     * @param e Description of Parameter
     * @exception SAXParseException Can be raised to indicate an explicit error
     */
    public void error(SAXParseException e) throws SAXParseException {
        throw e;
    }

    /**
     * Called by parser if a warning is issued, currently logs the condition
     * 
     * @param e Describes the warning
     * @exception SAXParseException Can be raised to indicate an explicit error
     */
    public void warning(SAXParseException e) {
        log.warn("Parser warning: line = " + e.getLineNumber() + " : uri = " + e.getSystemId());
        log.warn("Parser warning (root cause): " + e.getMessage());
    }
}