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
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A URL metadata provider that caches a copy of the retrieved metadata to disk so that, in the event that the metadata
 * may not be pulled from the URL it may be pulled from disk using the last fetched data. If the backing file does not
 * already exist it will be created.
 */
public class FileBackedURLMetadataProvider extends URLMetadataProvider {

    /** Logger */
    private final Logger log = Logger.getLogger(URLMetadataProvider.class);

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
        }
    }

    /**
     * Fetches the metadata from the given URL or, if that fails, from the backing file.
     */
    protected synchronized void refreshMetadata() throws MetadataProviderException {
        try {
            super.refreshMetadata();
            // TODO write metadata back to file
        } catch (MetadataProviderException mpe) {
            try {
                cachedMetadata = unmarshallMetadata(new FileInputStream(metadataBackupFile));
                filterMetadata(cachedMetadata);
                emitChangeEvent();
            } catch (FileNotFoundException e) {
                String errorMsg = "Unable to read metadata file";
                log.error(errorMsg, e);
                throw new MetadataProviderException(errorMsg, e);
            } catch (UnmarshallingException e) {
                String errorMsg = "Unable to unmarshall metadata";
                log.error(errorMsg, e);
                throw new MetadataProviderException(errorMsg, e);
            } catch (FilterException e) {
                String errorMsg = "Unable to filter metadata";
                log.error(errorMsg, e);
                throw new MetadataProviderException(errorMsg, e);
            }
        }
    }
}