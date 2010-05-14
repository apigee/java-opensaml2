/*
 * Copyright 2006 University Corporation for Advanced Internet Development, Inc.
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
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

/**
 * A URL metadata provider that caches a copy of the retrieved metadata to disk so that, in the event that the metadata
 * may not be pulled from the URL it may be pulled from disk using the last fetched data. If the backing file does not
 * already exist it will be created.
 * 
 * It is the responsibility of the caller to re-initialize, via {@link #initialize()}, if any properties of this
 * provider are changed.
 */
public class FileBackedHTTPMetadataProvider extends HTTPMetadataProvider {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(FileBackedHTTPMetadataProvider.class);

    /** File containing the backup of the metadata. */
    private File metadataBackupFile;

    /**
     * Constructor.
     * 
     * @param metadataURL the URL to fetch the metadata
     * @param requestTimeout the time, in milliseconds, to wait for the metadata server to respond
     * @param backingFilePath the file that will keep a backup copy of the metadata,
     * 
     * @throws MetadataProviderException thrown if the URL is not a valid URL, the metadata can not be retrieved from
     *             the URL, the given file can not be created or written to
     */
    @Deprecated
    public FileBackedHTTPMetadataProvider(String metadataURL, int requestTimeout, String backingFilePath)
            throws MetadataProviderException {
        super(metadataURL, requestTimeout);

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
        } else {
            try {
                metadataBackupFile.createNewFile();
            } catch (IOException e) {
                log.error("Unable to create backing file " + backingFilePath, e);
                throw new MetadataProviderException("Unable to create backing file " + backingFilePath, e);
            }
        }
    }

    /**
     * Constructor.
     * 
     * @param client HTTP client used to fetch remove metadata
     * @param metadataURL the URL to fetch the metadata
     * @param backingFilePath the file that will keep a backup copy of the metadata,
     * 
     * @throws MetadataProviderException thrown if the URL is not a valid URL, the metadata can not be retrieved from
     *             the URL, the given file can not be created or written to
     */
    public FileBackedHTTPMetadataProvider(HttpClient client, String metadataURL, String backingFilePath)
            throws MetadataProviderException {
        super(client, metadataURL);

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
        } else {
            try {
                metadataBackupFile.createNewFile();
            } catch (IOException e) {
                log.error("Unable to create backing file " + backingFilePath, e);
                throw new MetadataProviderException("Unable to create backing file " + backingFilePath, e);
            }
        }
    }

    /** {@inheritDoc} */
    protected byte[] getMetadataFromUrl() throws IOException {
        try {
            return super.getMetadataFromUrl();
        } catch (IOException e) {
            if (metadataBackupFile.exists()) {
                return DatatypeHelper.fileToByteArray(metadataBackupFile);
            } else {
                log.error("Unable to read metadata from remote server and backup does not exist");
                throw new IOException("Unable to read metadata from remote server and backup does not exist");
            }
        }
    }

    /** {@inheritDoc} */
    protected void cacheMetadata(byte[] metadataBytes, XMLObject metadata) throws MetadataProviderException {
        try {
            FileOutputStream out = new FileOutputStream(metadataBackupFile);
            out.write(metadataBytes);
            out.flush();
            out.close();
            super.cacheMetadata(metadataBytes, metadata);
        } catch (IOException e) {
            String errMsg = MessageFormatter.format("Unable to write metadata to backup file {}", metadataBackupFile
                    .getAbsolutePath());
            log.error(errMsg);
            throw new MetadataProviderException(errMsg, e);
        }
    }
}