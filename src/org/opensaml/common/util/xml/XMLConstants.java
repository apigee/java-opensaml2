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

package org.opensaml.common.util.xml;

/**
 * XML related constants.
 */
public class XMLConstants {
    //****************************
    // OpenSAML 2
    //****************************
    /** Directory, on the classpath, schemas are located in */
    private final static String SCHEMA_DIR = "/schema/";
    
    /**  OpenSAML XML namespace */
    public final static String OPENSAML_CONFIG_NS = "http://www.opensaml.org/opensaml2-config";
    
    /** OpenSAML configuration schema system Id */
    public final static String OPENSAML_CONFIG_SCHEMA_LOCATION = SCHEMA_DIR + "opensaml2-config.xsd";
    
    public final static String OPENSAML_CONFIG_PREFIX = "os2";
    
    //****************************
    //    Core XML
    //****************************
    /**  XML core namespace */
    public final static String XML_NS = "http://www.w3.org/XML/1998/namespace";
    
    /** XML core schema system Id */
    public final static String XML_SCHEMA_LOCATION = SCHEMA_DIR + "xml.xsd";

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
    
    /**  XML Signature schema Id */
    public final static String XMLSIG_SCHEMA_LOCATION = SCHEMA_DIR + "xmldsig-core-schema.xsd";
    
    /**  XML Signature namespace */
    public final static String XMLSIG_NS = "http://www.w3.org/2001/04/xmlenc#";
    
    /**  XML Signature QName prefix */
    public final static String XMLSIG_PREFIX = "ds";
    
    /** XML Encryption schema Id */
    public final static String XMLENC_SCHEMA_LOCATION = SCHEMA_DIR + "xenc-schema.xsd";
    
    /**  XML Encryption namespace */
    public final static String XMLENC_NS = "http://www.w3.org/2001/04/xmlenc#";
    
    /**  XML Encryption QName prefix */
    public final static String XMLENC_PREFIX = "xenc";

    //****************************
    //    SOAP
    //****************************
    /**  SOAP 1.1 schema Id */
    public final static String SOAP11ENV_SCHEMA_LOCATION = SCHEMA_DIR + SCHEMA_DIR + "soap-envelope.xsd";
    
    /**  SOAP 1.1 Envelope XML namespace */
    public final static String SOAP11ENV_NS = "http://schemas.xmlsoap.org/soap/envelope/";
    
    /**  SOAP 1.1 Envelope QName prefix */
    public final static String SOAP11ENV_PREFIX = "SOAP-ENV";
    
    /**  Liberty PAOS XML Namespace */
    public final static String PAOS_NA = "urn:liberty:paos:2003-08";
    
    /**  Liberty PAOS QName prefix */
    public final static String PAOS_PREFIX = "paos";

    //****************************
    //    SAML 1.X
    //****************************
    /** SAML 1.0 Assertion schema system Id */
    public final static String SAML10_SCHEMA_LOCATION = SCHEMA_DIR + "cs-sstc-schema-assertion-01.xsd";
    
    /** SAML 1.1 Assertion schema system Id */
    public final static String SAML11_SCHEMA_LOCATION = SCHEMA_DIR + "cs-sstc-schema-assertion-1.1.xsd";
    
    /**  SAML 1.X XML namespace */
    public final static String SAML1_NS = "urn:oasis:names:tc:SAML:1.0:assertion";
    
    /** SAML 1.0 Protocol schema system Id */
    public final static String  SAML10P_SCHEMA_LOCATION = SCHEMA_DIR + "cs-sstc-schema-protocol-01.xsd";
    
    /** SAML 1.1 Protocol schema system Id */
    public final static String SAML11P_SCHEMA_LOCATION = SCHEMA_DIR + "cs-sstc-schema-protocol-1.1.xsd";

    /**  SAML 1.X protocol XML namespace */
    public final static String SAMLP1_NS = "urn:oasis:names:tc:SAML:1.0:protocol";
    
    /** SAML 1.X Protocol QName prefix */
    public final static String SAMLP1_PREFIX ="samlp";

    /** SAML 1.X Assertion QName prefix */
    public final static String SAML1_PREFIX ="saml";

    /**  SAML 1.X Metadata Profile protocol indicators and namespace */
    public final static String SAML10_PROTOCOL_ENUM = SAMLP1_NS;
    public final static String SAML11_PROTOCOL_ENUM = "urn:oasis:names:tc:SAML:1.1:protocol";
    public final static String SAML_ARTIFACT_SOURCEID = "urn:oasis:names:tc:SAML:profiles:v1metadata";
    
    //****************************
    //    SAML 2.0
    //****************************
    /** SAML 2.0 Assertion schema Id */
    public final static String SAML20_SCHEMA_LOCATION = SCHEMA_DIR + "saml-schema-assertion-2.0.xsd";
    
    /** SAML 2.0 Assertion XML Namespace */
    public final static String SAML20_NS = "urn:oasis:names:tc:SAML:2.0:assertion";
    
    /** SAML 2.0 Assertion QName prefix */
    public final static String SAML20_PREFIX ="saml";
    
    /** SAML 2.0 Protocol schema Id */
    public final static String SAML20P_SCHEMA_LOCATION = SCHEMA_DIR + "saml-schema-protocol-2.0.xsd";
    
    /** SAML 2.0 Protocol XML Namespace */
    public final static String SAML20P_NS = "urn:oasis:names:tc:SAML:2.0:protocol";
    
    /** SAML 2.0 Protocol QName prefix */
    public final static String SAML20P_PREFIX ="samlp";
    
    /** SAML 2.0 Metadata schema Id */
    public final static String SAML20MD_SCHEMA_LOCATION = SCHEMA_DIR + "saml-schema-metadata-2.0.xsd";
    
    /** SAML 2.0 Metadata XML Namespace */
    public final static String SAML20MD_NS ="urn:oasis:names:tc:SAML:2.0:metadata";
    
    /** SAML 2.0 Metadata QName prefix */
    public final static String SAML20MD_PREFIX = "md";
    
    /** SAML 2.0 Authentication Context schema Id */
    public final static String SAML20AC_SCHEMA_LOCATION = SCHEMA_DIR + "saml-schema-authn-context-2.0.xsd";
    
    /** SAML 2.0 Authentication Context XML Namespace */
    public final static String SAML20AC_NS ="urn:oasis:names:tc:SAML:2.0:ac";
    
    /** SAML 2.0 Authentication Context QName prefix */
    public final static String SAML20AC_PREFIX = "ac";
    
    /** SAML 2.0 Enhanced Client/Proxy SSO Profile schema Id */
    public final static String SAML20ECP_SCHEMA_LOCATION = SCHEMA_DIR + "saml-schema-ecp-2.0.xsd";
    
    /** SAML 2.0 Enhanced Client/Proxy SSO Profile XML Namespace */
    public final static String SAML20ECP_NS = "urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp";
    
    /** SAML 2.0 Enhanced Client/Proxy SSO Profile QName prefix */
    public final static String SAML20ECP_PREFIX = "ecp";
    
    /** SAML 2.0 DCE PAC Attribute Profile schema Id */
    public final static String SAML20DCE_SCHEMA_LOCATION = SCHEMA_DIR + "saml-schema-dce-2.0.xsd";
    
    /** SAML 2.0 DCE PAC Attribute Profile XML Namespace */
    public final static String SAML20DCE_NS = "urn:oasis:names:tc:SAML:2.0:profiles:attribute:DCE";
    
    /** SAML 2.0 DCE PAC Attribute Profile QName prefix */
    public final static String SAML20DCE_PREFIX = "DCE";
    
    /** SAML 2.0 X.500 Attribute Profile schema Id */
    public final static String SAML20X500_SCHEMA_LOCATION = SCHEMA_DIR + "saml-schema-x500-2.0.xsd";
    
    /** SAML 2.0 X.500 Attribute Profile XML Namespace */
    public final static String SAML20X500_NS = "urn:oasis:names:tc:SAML:2.0:profiles:attribute:X500";
    
    /** SAML 2.0 X.500 Attribute Profile QName prefix */
    public final static String SAML20X500_PREFIX = "x500";
    
    /** SAML 2.0 XACML Attribute Profile schema Id */
    public final static String SAML20XACML_SCHEMA_LOCATION = SCHEMA_DIR + "saml-schema-xacml-2.0.xsd";
    
    /** SAML 2.0 XACML Attribute Profile XML Namespace */
    public final static String SAML20XACML_NS = "urn:oasis:names:tc:SAML:2.0:profiles:attribute:XACML";
    
    /** SAML 2.0 XACML Attribute Profile QName prefix */
    public final static String SAML20XACML_PREFIX = "xacmlprof";
}
