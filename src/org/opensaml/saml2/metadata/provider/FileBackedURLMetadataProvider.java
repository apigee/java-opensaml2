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

package org.opensaml.saml2.metadata.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 * A URL metadata provider that caches a copy of the retrieved metadata to disk so that, in the event that the metadata
 * may not be pulled from the URL it may be pulled from disk using the last fetched data. If the backing file does not
 * already exist it will be created.
 */
public class FileBackedURLMetadataProvider extends URLMetadataProvider {

    /** Logger */
    private final Logger log = Logger.getLogger(FileBackedURLMetadataProvider.class);

    /** File containing the backup of the metadata */
    private File metadataBackupFile;

    /**
     * Constructor
     * 
     * @param metadataURL the URL to fetch the metadata
     * @param requestTimeout the time, in milliseconds, to wait for the metadata server to respond
     * @param backingFilePath the file that will keep a backup copy of the metadata,
     * 
     * @throws MetadataProviderException thrown if the URL is not a valid URL, the metadata can not be retrieved from
     *             the URL, the given file can not be created or written to
     */
    public FileBackedURLMetadataProvider(String metadataURL, int requestTimeout, String backingFilePath)
            throws MetadataProviderException {
        super(metadataURL, requestTimeout, false);

        metadataBackupFile = new File(backingFilePath);
        if (metadataBackupFile.exists()) {
            if (metadataBackupFile.isDirectory()) {
                throw new MetadataProviderException("Filepath " + backingFilePath
                        + " is a directory and may not be used as a backing metadata file");
            }
            if (!metadataBackupFile.canRead()) {
                throw new MetadataProviderException("Filepath " + backingFilePath
                        + " exists but can not be read by this user");
            }
            if (!metadataBackupFile.canWrite()) {
                throw new MetadataProviderException("Filepath " + backingFilePath
                        + " exists but can not be written to by this user");
            }
        }else{
            try{
                metadataBackupFile.createNewFile();
            }catch(IOException e){
                log.error("Unable to create backing file " + backingFilePath, e);
                throw new MetadataProviderException("Unable to create backing file");
            }
        }
        
        refreshMetadata();
    }
    
    /**
     * Fetches the metadata from the remote server or from the local filesystem if it can not be retrieved remotely.
     * 
     * @return the unmarshalled metadata
     * 
     * @throws IOException thrown if the metadata can not be fetched from the remote server or local filesystems
     * @throws UnmarshallingException thrown if the metadata can not be unmarshalled
     */
    protected XMLObject fetchMetadata() throws IOException, UnmarshallingException{
        XMLObject metadata;
        boolean readLocal = false;
        try{
            metadata = super.fetchMetadata();
        }catch(Exception e){
            if(log.isDebugEnabled()){
                log.debug("Unable to read metadata from remote server, attempting to read it from local backup", e);
            }
            metadata = getLocalMetadata();
            readLocal = true;
        }
        
        // If we read the metadata from the remote server then write it to disk
        if(!readLocal){
            if(log.isDebugEnabled()){
                log.debug("Writting retrieved metadata to backup file " + metadataBackupFile.getAbsolutePath());
            }
            try{
                writeMetadataToFile(metadata);
            }catch(Exception e){
                log.error("Unable to write metadata to backup file", e);
                throw new IOException("Unable to write metadata to backup file: " + e.getMessage());
            }
        }
        
        return metadata;
    }

    /**
     * Reads filtered metadata from the backup file.
     * 
     * @throws MetadataProviderException thrown if the backup file can not be read or contains invalid metadata
     */
    protected XMLObject getLocalMetadata() throws IOException, UnmarshallingException {
        if (!(metadataBackupFile.exists() && metadataBackupFile.canRead())) {
            throw new IOException("Unable to read metadata from backup file "
                    + metadataBackupFile.getAbsolutePath());
        }

        FileInputStream in = new FileInputStream(metadataBackupFile);
        return unmarshallMetadata(in);
    }

    /**
     * Writes the currently cached metadata to file.
     * 
     * @throws MetadataProviderException thrown if metadata can not be written to disk
     */
    protected void writeMetadataToFile(XMLObject metadata) throws MetadataProviderException {
        if (!(metadataBackupFile.exists() && metadataBackupFile.canWrite())) {
            throw new MetadataProviderException("Unable to write to metadata backup file "
                    + metadataBackupFile.getAbsolutePath());
        }

        try {
            Element metadataElement;
            
            // The metadata object should still have its DOM
            // but we'll create it if it doesn't
            if(metadata.getDOM() != null){
                metadataElement = metadata.getDOM();
            }else{
                Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(metadata);
                metadataElement = marshaller.marshall(metadata);
            }

            if(log.isDebugEnabled()){
                log.debug("Converting DOM to a string");
            }
            XMLHelper.writeNode(metadataElement, new FileWriter(metadataBackupFile));
        } catch (IOException e) {
            log.error("Unable to write metadata to file " + metadataBackupFile.getAbsolutePath(), e);
            throw new MetadataProviderException("Unable to write metadata to file");
        } catch (MarshallingException e) {
            log.error("Unable to marshall metadata in order to write it to file", e);
            throw new MetadataProviderException("Unable to marshall metadata in order to write it to file");
        }
    }
}