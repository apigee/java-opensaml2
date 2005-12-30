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

package org.opensaml.xml.util;

/**
 * XML related constants.
 */
public class XMLConstants{

    /**  XML core namespace */
    public final static String XML_NS = "http://www.w3.org/XML/1998/namespace";

    /**  XML namespace for xmlns attributes */
    public final static String XMLNS_NS = "http://www.w3.org/2000/xmlns/";
    
    /** XML namespace prefix for xmlns attributes */
    public final static String XMLNS_PREFIX = "xmlns";

    /**  XML Schema namespace */
    public final static String XSD_NS = "http://www.w3.org/2001/XMLSchema";
    
    /**  XML Schema QName prefix */
    public final static String XSD_PREFIX ="xs";

    /**  XML Schema Instance namespace */
    public final static String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    
    /**  XML Schema Instance QName prefix */
    public final static String XSI_PREFIX ="xsi";
    
    /**  XML Signature namespace */
    public final static String XMLSIG_NS = "http://www.w3.org/2000/09/xmldsig#";
    
    /**  XML Signature QName prefix */
    public final static String XMLSIG_PREFIX = "ds";
    
    /**  XML Encryption namespace */
    public final static String XMLENC_NS = "http://www.w3.org/2001/04/xmlenc#";
    
    /**  XML Encryption QName prefix */
    public final static String XMLENC_PREFIX = "xenc";
}