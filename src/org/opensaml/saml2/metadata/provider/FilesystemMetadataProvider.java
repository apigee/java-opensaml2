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
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A metadata provider that pulls metadata from a file on the local filesystem. Metadata is cached and automatically
 * refreshed when the file changes.
 */
public class FilesystemMetadataProvider extends AbstractObservableMetadataProvider {

    /** Logger */
    private final Logger log = Logger.getLogger(FilesystemMetadataProvider.class);

    /** The metadata file */
    private File metadataFile;
    
    /** Whether cached metadata should be discarded if it expires and can't be refreshed */
    private boolean maintainExpiredMetadata;

    /** Last time the cached metadata was updated */
    private long lastUpdate;

    /** Cached metadata */
    private XMLObject cachedMetadata;

    /**
     * Constructor
     * 
     * @param metadataFile the metadata file
     * @param maintainExpiredMetadata whether cached metadata should be discarded if it expires and can not be refreshed
     * 
     * @throws IllegalArgumentException thrown if the given file path is null, does not exist, or does not represent a file
     * @throws MetadataProviderException thrown if the metadata can not be parsed
     */
    public FilesystemMetadataProvider(File metadataFile) throws IllegalArgumentException, MetadataProviderException{
        super();
        
        if(metadataFile == null){
            throw new IllegalArgumentException("Give metadata file may not be null");
        }
        
        if(!metadataFile.exists()){
            throw new IllegalArgumentException("Give metadata file, " + metadataFile.getAbsolutePath() + " does not exist");
        }
        
        if(!metadataFile.isFile()){
            throw new IllegalArgumentException("Give metadata file, " + metadataFile.getAbsolutePath() + " is not a file");
        }
        
        this.metadataFile = metadataFile;
        maintainExpiredMetadata = true;

        refreshMetadata();
    }
    
    /**
     * Gets whether cached metadata should be discarded if it expires and can not be refreshed.
     * 
     * @return whether cached metadata should be discarded if it expires and can not be refreshed
     */
    public boolean maintainExpiredMetadata() {
        return maintainExpiredMetadata;
    }

    /**
     * Sets whether cached metadata should be discarded if it expires and can not be refreshed
     * 
     * @param maintainExpiredMetadata whether cached metadata should be discarded if it expires and can not be refreshed
     */
    public void setMaintainExpiredMetadata(boolean maintainExpiredMetadata) {
        this.maintainExpiredMetadata = maintainExpiredMetadata;
    }
    
    /** {@inheritDoc} */
    public void setMetadataFilter(MetadataFilter newFilter) throws MetadataProviderException{
        super.setMetadataFilter(newFilter);
        refreshMetadata();
    }

    /** {@inheritDoc} */
    public XMLObject getMetadata() throws MetadataProviderException {
        if (lastUpdate < metadataFile.lastModified()) {
            refreshMetadata();
        }

        return cachedMetadata;
    }

    /**
     * Retrieves, unmarshalls, and filters the metadata from the metadata file.
     */
    private synchronized void refreshMetadata() throws MetadataProviderException{
        if (lastUpdate >= metadataFile.lastModified()) {
            // In case other requests stacked up behind the synchronize lock
            return;
        }
        
        try {
            cachedMetadata = unmarshallMetadata(new FileInputStream(metadataFile));
            filterMetadata(cachedMetadata);
            lastUpdate = metadataFile.lastModified();
            emitChangeEvent();
        }catch(FileNotFoundException e){
            String errorMsg = "Unable to read metadata file";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }catch(UnmarshallingException e){
            String errorMsg = "Unable to unmarshall metadata";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }catch(FilterException e){
            String errorMsg = "Unable to filter metadata";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }
    }
}