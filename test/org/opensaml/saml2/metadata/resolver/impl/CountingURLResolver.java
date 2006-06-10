package org.opensaml.saml2.metadata.resolver.impl;

import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.w3c.dom.Document;

/**
 * An extension to {@link URLResolver} that counts the number of times metadata was fetched from the URL.
 */
public class CountingURLResolver extends URLResolver{
    
    /** Number of resolutions made */
    private int resolutions;
    
    /**
     * Constructor
     *
     * @param resolverID ID of this resolver
     * @param metadataURL the URL to the metadata
     */
    public CountingURLResolver(String resolverID, String metadataURL){
        super(resolverID, metadataURL);
        resolutions = 0;
    }
    
    /**
     * Gets the number of times metadata was fetched from the URL.
     * 
     * @return the number of times metadata was fetched from the URL
     */
    public int getNumberOfResolutions(){
        return resolutions;
    }
    
    /** {@inheritDoc} */
    public Document retrieveDOM() throws ResolutionException {
        Document retrievedDOM = super.retrieveDOM();
        resolutions++;
        return retrievedDOM;
    }
}