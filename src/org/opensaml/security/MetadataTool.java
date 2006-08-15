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

package org.opensaml.security;

import jargs.gnu.CmdLineParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.saml2.metadata.provider.URLMetadataProvider;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLHelper;

/**
 * A tool for retrieving, verifying, and signing metadata.
 */
public class MetadataTool {

    private static Logger log;

    /**
     * Main entry point to program.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option helpOption = parser.addBooleanOption('h', "help");
        CmdLineParser.Option inputFileOption = parser.addStringOption('f', "inputfile");
        CmdLineParser.Option inputURLOption = parser.addStringOption('u', "inputurl");
        CmdLineParser.Option keystoreOption = parser.addStringOption('e', "keystore");
        CmdLineParser.Option storeTypeOption = parser.addStringOption('t', "storetype");
        CmdLineParser.Option aliasOption = parser.addStringOption('a', "alias");
        CmdLineParser.Option storePassOption = parser.addStringOption('p', "storepass");
        CmdLineParser.Option keyPassOption = parser.addStringOption('k', "keypass");
        CmdLineParser.Option outFileOption = parser.addStringOption('o', "outfile");
        CmdLineParser.Option signOption = parser.addBooleanOption('s', "sign");

        try {
            parser.parse(args);
        } catch (CmdLineParser.OptionException e) {
            System.err.println(e.getMessage());
            printHelp(System.out);
            System.out.flush();
            System.exit(-1);
        }

        Boolean helpEnabled = (Boolean) parser.getOptionValue(helpOption);
        if (helpEnabled != null && helpEnabled.booleanValue()) {
            printHelp(System.out);
            System.out.flush();
            System.exit(0);
        }

        configureLogging();

        XMLObject metadata = null;
        try {
            metadata = fetchMetadata((String) parser.getOptionValue(inputFileOption), (String) parser
                    .getOptionValue(inputURLOption));
        } catch (MetadataProviderException e) {
            log.error("Unable to fetch metadata", e);
            System.exit(1);
        }

        String keystorePath = (String) parser.getOptionValue(keystoreOption);
        String storeType = (String) parser.getOptionValue(storeTypeOption);
        String alias = (String) parser.getOptionValue(aliasOption);
        String storePass = (String) parser.getOptionValue(storePassOption);
        String keyPass = (String) parser.getOptionValue(keyPassOption);
        Boolean sign = (Boolean) parser.getOptionValue(signOption);
        if (sign != null && sign.booleanValue()) {
            KeyStore keystore = getKeyStore(keystorePath, storeType, storePass);
            PrivateKey signingKey = getPrivateKey(keystore, alias, keyPass);
            sign(metadata, signingKey);
        } else {
            if (keystorePath != null) {
                KeyStore keystore = getKeyStore(keystorePath, storeType, storePass);
                PublicKey verificationKey = getPublicKey(keystore, alias);
                verifySiganture(metadata, verificationKey);
            }
        }

        printMetadata(metadata, (String) parser.getOptionValue(outFileOption));
    }

    /**
     * Fetches metadata from either the given input file or URL.
     * 
     * @param inputFile the filesystem path to the metadata file.
     * @param inputURL the URL to the metadata file
     * 
     * @return the metadata
     * 
     * @throws MetadataProviderException thrown if the metadata can not be fetched
     */
    private static XMLObject fetchMetadata(String inputFile, String inputURL) throws MetadataProviderException {
        if (!DatatypeHelper.isEmpty(inputFile) && !DatatypeHelper.isEmpty(inputURL)) {
            log.error("inputfile and inputurl may not both be specified.");
        }

        if (!DatatypeHelper.isEmpty(inputFile)) {
            log.debug("Fetching metadata from file " + inputFile);
            return new FilesystemMetadataProvider(new File(inputFile)).getMetadata();
        } else {
            log.debug("Fetching metadata from URL " + inputURL);
            return new URLMetadataProvider(inputURL, 30 * 1000).getMetadata();
        }
    }

    /**
     * Gets the Java keystore.
     * 
     * @param keyStore path to the keystore
     * @param storeType keystore type
     * @param storePass keystore password
     * 
     * @return the keystore
     */
    private static KeyStore getKeyStore(String keyStore, String storeType, String storePass) {
        try {
            FileInputStream keyStoreIn = new FileInputStream(keyStore);
            KeyStore ks = KeyStore.getInstance(storeType);

            storePass = DatatypeHelper.safeTrimOrNullString(storePass);
            if (storePass != null) {
                ks.load(keyStoreIn, storePass.toCharArray());
                return ks;
            } else {
                log.error("No password provided for keystore");
                System.exit(1);
            }
        } catch (Exception e) {
            log.error("Unable to load keystore from file " + keyStore, e);
            System.exit(1);
        }

        return null;
    }

    /**
     * Gets the a private key from the keystore.
     * 
     * @param keystore keystore to fetch the key from
     * @param alias the key alias
     * @param keyPass password for the key
     * 
     * @return the private key or null
     */
    private static PrivateKey getPrivateKey(KeyStore keystore, String alias, String keyPass) {
        alias = DatatypeHelper.safeTrimOrNullString(alias);
        if (alias == null) {
            log.error("Key alias may not be null or empty");
            System.exit(1);
        }

        keyPass = DatatypeHelper.safeTrimOrNullString(keyPass);
        if (keyPass == null) {
            log.error("Private key password may not be null or empty");
            System.exit(1);
        }
        KeyStore.PasswordProtection keyPassParam = new KeyStore.PasswordProtection(keyPass.toCharArray());
        try {
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keystore.getEntry(alias, keyPassParam);
            return pkEntry.getPrivateKey();
        } catch (Exception e) {
            log.error("Unable to retrieve private key " + alias, e);
        }
        
        return null;
    }

    /**
     * Gets the public key associated with the named certificate.
     * 
     * @param keystore the keystore to get the key from
     * @param alias the name of the certificate to get the from
     * 
     * @return the public key or null
     */
    private static PublicKey getPublicKey(KeyStore keystore, String alias) {
        alias = DatatypeHelper.safeTrimOrNullString(alias);
        if (alias == null) {
            log.error("Key alias may not be null or empty");
            System.exit(1);
        }

        try{
            Certificate cert = keystore.getCertificate(alias);
            return cert.getPublicKey();
        }catch(Exception e){
            log.error("Unable to retrieve certificate " + alias, e);
            System.exit(1);
        }
        
        return null;
    }

    private static void sign(XMLObject metadata, PrivateKey signingKey) {

    }

    private static void verifySiganture(XMLObject metadata, PublicKey verificationKey) {

    }

    /**
     * Writes the given metadata out.
     * 
     * @param metadata the metadata to write
     * @param outputFile the file to write the metadata to, or null for STDOUT
     */
    private static void printMetadata(XMLObject metadata, String outputFile) {
        PrintStream out = System.out;

        try {
            if (!DatatypeHelper.isEmpty(outputFile)) {
                File outFile = new File(outputFile);
                outFile.createNewFile();
                out = new PrintStream(new File(outputFile));
            }
        } catch (Exception e) {
            log.error("Unable to write to output file", e);
        }

        out.print(XMLHelper.nodeToString(metadata.getDOM()));
    }

    /**
     * Configures the logging for this tool. Default logging level is error.
     * 
     * @param warningEnable whether to enable warning level
     */
    private static void configureLogging() {
        ConsoleAppender console = new ConsoleAppender();
        console.setWriter(new PrintWriter(System.err));
        console.setName("stderr");
        console.setLayout(new PatternLayout("%d{ABSOLUTE} %-5p [%c{1}] %m%n"));

        log = Logger.getLogger("org.opensaml");
        log.addAppender(console);
        log.setLevel(Level.ERROR);

        Logger.getRootLogger().setLevel(Level.OFF);
    }

    /**
     * Prints a help message to the given output stream.
     * 
     * @param out output to print the help message to
     */
    private static void printHelp(PrintStream out) {
        out.println("usage: java org.opensaml.security.MetadataTool");
        out.println();
        out.println("when signing:");
        out.println("  -f <path> | -u <url> -s -e <keystore> [-s <storetype>] -a <alias> -s <pass> [-k <pass>] [-o <outfile>]");
        out.println("when retrieving:");
        out.println("  -f <path> | -u <url> [-o <outfile>]");
        out.println("when retrieving and verifying signature:");
        out.println("  -f <path> | -u <url> -e <keystore> [-s <storetype>] -a <alias> [-o <outfile>]");
        out.println();
        out.println();
        out.println("  -f, --inputfile       filesystem path to metadata file");
        out.println("  -u, --inputurl        URL where metadata file will be fetched from");
        out.println("  -e, --keystore        filesystem path to Java keystore");
        out.println("  -t, --storetype       the keystore type (default: JKS)");
        out.println("  -a, --alias           alias of signing or verification key");
        out.println("  -p, --storepass       keystore password");
        out.println("  -k, --keypass         private key password");
        out.println("  -o, --outfile         filesystem path where metadata will be written");
        out.println("  -s, --sign            sign the input file and write out a signed version");
        out.println("  -h, --help            print this message");
        out.println();
    }
}