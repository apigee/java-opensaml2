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

/**
 * 
 */
package org.opensaml.saml2.metadata.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.resolver.MetadataResolver;
import org.opensaml.saml2.metadata.resolver.MetadataResolverFactory;
import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.opensaml.xml.DOMCachingXMLObject;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Document;

/**
 * A concrete implementation of Metadata Cache.
 * 
 * The whole is constructed carefully to be multithreaded and threadsafe.  It uses 
 * {@link java.lang.ref.SoftReference} to allow caching with an LRU.
 * 
 */
public class MetadataCacheImpl implements MetadataCache, Runnable {

    private static Logger log = Logger.getLogger(MetadataCacheImpl.class);
    
    /** The map gets us from URI to the entitiy with the SoftReference */
    private final HashMap <String, LookupEntry> map; 
    
    /** 
     * The unmarshaller factory gives us there wherewithall to get from 
     * Document to XMKLObject
     */
    private final UnmarshallerFactory unmarshallerFactory;
    
    /** The LRU list - this pins things into the cache */
    
    private final XMLObject[] lruList;
    
    /** How long to wait if no expirey or cache duration set (ms) */
    
    private final long defaultWait;
    
    /** the load & invalidate worker thread */
    
    private final Thread workerThread;
    
    /** how to exit the thread (helpful for the tests to not get too many threads */
    private boolean keepLooping = true;
    
    /**
     * Constructor
     *
     */
    public MetadataCacheImpl(UnmarshallerFactory unmarshallerFactory, int lruSize, long defaultWaitSecs) {
        map = new HashMap<String, LookupEntry>();
        this.unmarshallerFactory = unmarshallerFactory;
        defaultWait = defaultWaitSecs * 1000;
        
        lruList = new XMLObject[lruSize];
        workerThread = new Thread(this, "Metadata Cache Load and invalidate thread");
        workerThread.start();
    }
    
    /**
     * Default Constructor
     *
     */
    public MetadataCacheImpl(UnmarshallerFactory unmarshallerFactory ) {
        this(unmarshallerFactory, 2, 3600*6);
    }

    /*
     * @see org.opensaml.saml2.metadata.cache.MetadataCache#loadMetadata(java.lang.String, org.opensaml.saml2.metadata.resolver.MetadataResolverFactory)
     */
    public void loadMetadata(String metadataURI, MetadataResolverFactory resolverFactory) throws ResolutionException,
            UnmarshallingException {
        
        try {
            loadMetadata(metadataURI, resolverFactory, null);
        } catch (FilterException e) {
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.cache.MetadataCache#loadMetadata(java.lang.String, org.opensaml.saml2.metadata.resolver.MetadataResolverFactory, org.opensaml.saml2.metadata.cache.MetadataFilter)
     */
    public void loadMetadata(String metadataURI, MetadataResolverFactory resolverFactory, MetadataFilter filter)
            throws ResolutionException, UnmarshallingException, FilterException {
        
        LookupEntry entry = map.get(metadataURI);
        if (entry != null) {
            //
            // Non changing so no need to synchronize
            //
            entry = new LookupEntry(metadataURI, resolverFactory.createResolver(), filter);
            map.put(metadataURI, entry);
        } else {
            entry = new LookupEntry(metadataURI, resolverFactory.createResolver(), filter);
            synchronized (map) {
                map.put(metadataURI, entry);
            }
        }
        log.debug("Added " + metadataURI + " to cache");
        workerThread.interrupt();
    }

    /*
     * @see org.opensaml.saml2.metadata.cache.MetadataCache#retrieveEntities(java.lang.String)
     */
    public EntitiesDescriptor retrieveEntities(String metadataURI) {
        XMLObject object = null;
        LookupEntry entry = map.get(metadataURI);

        if (entry == null) {
            return null;
        }
        
        try {
            object = entry.get();
        } catch (ResolutionException e) {
            // some failure, remove the entry
            removeMetadata(metadataURI);
        } catch (UnmarshallingException e) {
            removeMetadata(metadataURI);
        } catch (FilterException e) {
            removeMetadata(metadataURI);
        }
        
        if (object == null) {
            return null;
        } else if (object instanceof EntitiesDescriptor) {
            return(EntitiesDescriptor) object;
        } else {
            return null;
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.cache.MetadataCache#retrieveEntity(java.lang.String)
     */
    public EntityDescriptor retrieveEntity(String metadataURI) {
        XMLObject object = null;
        LookupEntry entry = map.get(metadataURI);

        if (entry == null) {
            return null;
        }
        
        try {
            object = entry.get();
        } catch (ResolutionException e) {
            // some failure, remove the entry
            removeMetadata(metadataURI);
        } catch (UnmarshallingException e) {
            removeMetadata(metadataURI);
        } catch (FilterException e) {
            removeMetadata(metadataURI);
        }
        
        if (object == null) {
            return null;
        } else if (object instanceof EntityDescriptor) {
            return(EntityDescriptor) object;
        } else {
            return null;
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.cache.MetadataCache#removeMetadata(java.lang.String)
     */
    public void removeMetadata(String metadataURI) {

        LookupEntry entry;
        
        synchronized (map) {
            entry = map.remove(metadataURI);
        }
    
        if (entry != null) {
            entry.delete();
        }
    }
    
    /*
     * @see org.opensaml.saml2.metadata.cache.MetadataCache#invalidateMetadata(java.lang.String)
     */
    public void invalidateMetadata(String metadataURI) {
        
        LookupEntry entry = map.get(metadataURI);
        
        entry.clear();
        
        log.debug("Invalidated " + metadataURI);
        //
        // Start loading it again
        //
        workerThread.interrupt();
    }
    
    /*
     * @see org.opensaml.saml2.metadata.cache.MetadataCache#invalidateAllMetadata()
     */
    public void invalidateAllMetadata() {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * This is the reference which goes into the map.  It contains a weak reference to the
     * actual XML reference (so it might be null).  
     */
    private class LookupEntry {
        /*
         * Most of the caching smarts are contained within this reference.  
         * All of the methods can be called in multiple threads and so
         * we need to be careful about synchronization and even more about the java 
         * memory model which does not guarantee write ordering when seen 
         * from mutiple threads.
         * 
         * Thus a frequent paradigm is to copy a private field into a 
         * local variable (under a mutex because of the re-ordering constraints)
         * and then do something with it.  Others may be clearing the
         * field...
         */
        
        /** The reference to the object we are cachingm, access protected by mutex  */
        private SoftReference<XMLObject> reference = null;
        
        /** When the entry expires, access protected by mutex */
        private DateTime expires = null;
        
        /** Where we go to get the DOM */
        private final MetadataResolver resolver;
        
        /** What we do after we have got the object */
        private final MetadataFilter filter;
        
        /** The handle for the object */ 
        private String metadataURI;
        
        /** mutex is used to guarantee read ordering and keep elements mutually coherent */
        private final Object mutex;

        /** event to stop multiple loads */
        private Object loading;
        
        /** Generation count, to stop the clear thread from doing the wrong thing  - it is incremented
         * every time reference is changed 
         */
        private long generation;
        
        /**
         * Constructor
         *
         * @param metadataURI the (fixed) URI for the reference
         * @param resolverFactory the (fixed) factory to do the lookup
         * @param filter the (fixed) filter to do after lookup
         */
        protected LookupEntry(String metadataURI, MetadataResolver resolver, MetadataFilter filter) {
            
            mutex = new Object();
            
            this.metadataURI = metadataURI;
            this.resolver = resolver;
            this.filter = filter;
            loading = Boolean.FALSE;
            expires = null;
            generation = 0;
        }
        
        /**
         * Return a valid XMLObject for the URI.
         * @return the XML object
         * @throws FilterException 
         * @throws UnmarshallingException 
         * @throws ResolutionException 
         */
        private XMLObject get() throws ResolutionException, UnmarshallingException, FilterException {
            XMLObject retVal = null;
            SoftReference<XMLObject> ref;
            DateTime exp;
            
            /*
             * Java has a non intutitive memory model - loads can
             * happen out of order.  Hence it is possible to 
             * collect the expirely date after we have collected the XMLObject.  
             * This is bad because the expirey date may be for a more
             * recent version of the object and we will return bad data. Hence 
             * we have to synchronize and (to stop deadlock) we do so on an 
             * internal object 
             */
            synchronized (mutex) {
                exp = expires;
                ref = reference;
            }
            
            if (exp != null && exp.isBeforeNow()) {
                //
                // timed out
                //
                ref = null;
            }
            
            if (ref != null) {
                retVal = ref.get();
             }
            
            if (retVal == null) {
                /*
                 * Something has made the entry invalid - either a clear
                 * or a GC, or the expirey date.  We will do an inline
                 * load and return the value immediately. 
                 */
                retVal = load();
                
            }
            lruUpdate(retVal);
            return retVal;
        }
        
        /**
         * @return when this item will expire
         */
        private DateTime getExpires() {
            return expires;
        }
        
        /**
         * @return the URI for this entity
         */
        private String getURI() {
            return metadataURI;
        }
        
        
        /**
         * Clear invalidates out cache.  If we are lazy loading we start to load it up again
         */
        private void clear() {
            SoftReference<XMLObject> reference;
            /*
             * Do all the work under the mutex...
             */
            synchronized (mutex) {
                reference = this.reference;
                this.expires = null;
                this.reference = null;
                this.generation ++;
            }
            
            if (reference != null) {
                lruRemove(reference.get());
                reference.clear();
            }
        }
        
        /**
         * The element is about to be destroyed so lets clear up all our dangluing issues
         * However we cannot just call clear because that might trigger a reload...
         */
        private void delete() {
            //
            // The metadataURI is the signal that this LookupEntry is on the way out
            //
            metadataURI = null;
            clear();
        }
        
        /**
         * Load the specified data
         * @return the XMLobject
         * @throws ResolutionException 
         * @throws UnmarshallingException 
         * @throws FilterException 
         */
        private XMLObject load() throws ResolutionException, UnmarshallingException, FilterException {
           /*
            * Load is either called because we are not lazy loading and the data has gone invalid, or 
            * because the user needs the data and it is invalid.  It is therefore serialized and we take a
            * wee look at the data every time we get the lock (in case wehave just got through loading it.
            * 
            * It is not sufficient for load to just set up the soft reference - it might have been garbage
            * collected by the time the call returns.  Hence load sets up the soft reference *and* returns the 
            * value.
            *  
            *  NOTE the lock ordering.  Take (loading) first, then (mutex).
            */ 
            
            log.debug("Loading " + metadataURI);
            
            XMLObject retVal;

            synchronized (loading) {

                SoftReference<XMLObject> reference;
                DateTime expires;
                String metadataURI;
            
                synchronized (mutex) {
                    expires = this.expires;
                    reference = this.reference;
                    metadataURI = this.metadataURI;
                }
                
                if (metadataURI == null) {
                    return null;
                }
                
                retVal = null;
                
                if (expires != null && expires.isBeforeNow()) {
                    reference = null;
                }
                
                if (reference != null) {
                    retVal = reference.get();
                }
                if (retVal != null) {
                    //
                    // it is valid in cache, return it
                    //
                    log.debug("Still valid - load stopped: " + metadataURI);
                                      
                    return retVal;
                }
                
                Document doc;
                Unmarshaller unmarshaller;
                
                //
                // So, load the DOM
                //
                doc = resolver.resolve(metadataURI);
                
                // 
                // Find someone to unmarshall it
                //
                unmarshaller = unmarshallerFactory.getUnmarshaller(doc.getDocumentElement());
                //
                // And get the XML Object.
                //
                retVal = unmarshaller.unmarshall(doc.getDocumentElement());
        
                //
                // Do a filter
                //
                
                if (filter != null) {
                    filter.doFilter(retVal);
                }
                
                DateTime now = new DateTime();
                expires = expires(retVal, null, now);
                if (expires == null) {
                    expires = now.plus(defaultWait);
                }
                synchronized (mutex) {
                    this.reference = new SoftReference<XMLObject>(retVal);
                    this.expires = expires;
                    this.generation ++;
                   
                }
            
                //
                // If we get here we have just sucessfully loaded the object and we will return it
                // If it is appropriate we will kill off the DOM
                //
                if (retVal instanceof DOMCachingXMLObject) {
                    DOMCachingXMLObject domObject = (DOMCachingXMLObject) retVal;
                    
                    domObject.releaseChildrenDOM(true);
                    domObject.releaseDOM();
                }

                log.debug("Loaded " + metadataURI + " successfully ");
                
                //
                // Now tell the worker thread - we may be called in the mainline
                //
                
                workerThread.interrupt();
                
            }
            
            return retVal;
        }        
    }
    
    /**
     * Helper fuction for loading.  According to the spec cache data must be dropped if
     * either cacheDuration or validUntil.  This iterates down the objects finding the
     * earliest time when the parent should be made invalid.
     * @param object - the parent
     * @param earliestInvalidate the current earliest invalidate time (or null)  
     * @param now - when this was called
     * @return
     */
    private static DateTime expires(XMLObject object, DateTime earliestInvalidate, DateTime now) {
        
        if (object == null) {
            return earliestInvalidate;
        }
        DateTime currentMin = earliestInvalidate;
        
        if (object instanceof CacheableSAMLObject) {
            CacheableSAMLObject cacheInfo = (CacheableSAMLObject) object;
            
            if (cacheInfo.getCacheDuration() != null && cacheInfo.getCacheDuration().longValue() != 0) {

                currentMin = earlierOf(currentMin, now.plus(cacheInfo.getCacheDuration().longValue()));
            }
        }
        
        if (object instanceof TimeBoundSAMLObject) {
            TimeBoundSAMLObject timeBoundObject = (TimeBoundSAMLObject) object;

            currentMin = earlierOf(currentMin, timeBoundObject.getValidUntil()); 

        }
        
        for (XMLObject child : object.getOrderedChildren()) {
            currentMin = expires(child, currentMin, now);
        }
        return currentMin;
    }

    /**
     * Helper function for expires.  Returns the earlier of two dates, with null being
     * 'high;  
     * @param first
     * @param second
     * @return the earlier of first and second or the only non null value, or null.
     */
    private static DateTime earlierOf(DateTime first, DateTime second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (second.isBefore(first)) {
            return second;
        } else {
            return first;
        }
    }
        
    /**
     * Method to pin the 'n' most recent cache elements.
     * @param object
     */
    private void lruUpdate(XMLObject object) {
        
        //
        // No op for empty LRU list
        //
        if (lruList.length == 0) return;
        
        synchronized(lruList) {
            
            //
            // Take object out of the list
            //
            XMLObject last = object;
            int i;
            for (i = 0; (last != null) && (i < lruList.length); i ++) {
                //
                // Collect value to push down
                //
                XMLObject tmp = lruList[i];
                //
                // Assign in the value being pushed
                //
                lruList[i] = last;
                //
                // Only push down if we do not have a duplicate
                //
                if (tmp  == object) {
                    break;
                }
                //
                // set up value to push down on next iteration
                //
                last = tmp;
            }
        }
        
    }
 
    /**
     * remove from the LRU list
     * @param object
     */
    private void lruRemove(XMLObject object) {
        
        //
        // No op for empty LRU list
        //
        if (object == null || lruList.length == 0) return;

        synchronized(lruList) {
            
            //
            // Take object out of the list
            //
            boolean copy = false;
            
            for (int i = 0; i < (lruList.length-1) && (lruList[i] != null); i ++) {
                if (lruList[i] == object) {
                    //
                    // every entry from now one gets the value of the one after them
                    //
                    copy = true;
                }
                if (copy) {
                    lruList[i] = lruList[i+1];
                }
            }
            if (copy || (lruList[lruList.length-1] == object)) {
                //
                // One way or another we have found the value in the LRU so we
                // null out the last value
                //
                lruList[lruList.length-1] = null;
            }
        }
    }

    /** Helper routine for the tests to get the threads to dies quickly */
    
    protected void stopWorker() {
        log.debug("Instructing thread to exit");
        keepLooping = false;
        workerThread.interrupt();
    }
    
    /*
     * @see java.lang.Runnable#run()
     */
    public void run() {
       /*
        * This thread does the loading and invalidating for the cache
        */
        log.debug("Load thread started");
        while (keepLooping) {
            LookupEntry entry = null;
            DateTime time = null;
            
            // 
            // Go through our work list and find something needing immediate loading
            // or the date when the most likely member will awaken
            //
            
            log.debug("Considering my options");
            
            synchronized (map) {
                for (Map.Entry<String,LookupEntry> mapEntry : map.entrySet()) {
                    LookupEntry what = mapEntry.getValue(); 
                    if (what.getExpires() == null) {
                        entry = what;
                        time = null;
                        break;
                    }
                    if (time == null || time.isAfter(what.getExpires())) {
                        entry = what;
                        time = what.getExpires();
                    }
                }
            } // syncrhonized block
            if (entry == null) {
                //
                // Nothing to do.  Sleep for an hour
                //
                log.debug("No entries to load, sleeping an hour");
                try {
                    Thread.sleep(1000*3600);
                } catch (InterruptedException e) {
                    log.debug("Awoken");
                    ; // nothing
                }
            } else if (time == null || time.isBeforeNow()) {
                //
                // immediate load
                //
                try {
                    entry.load();
                } catch (ResolutionException e) {
                    log.warn("Load of " + entry.getURI() + " threw " + e.toString());
                } catch (UnmarshallingException e) {
                    log.warn("Load of " + entry.getURI() + " threw " + e.toString());
                } catch (FilterException e) {
                    log.warn("Load of " + entry.getURI() + " threw " + e.toString());
                }
            } else {
                //
                // Do a sleep for the correct amount of time plus one second
                //
                long count = time.getMillis() - (new DateTime()).getMillis();
                log.debug("Sleeping until " + entry.getURI() + " is invalid : " + count/1000 + "sec");
                try {
                    Thread.sleep(count);
                } catch (InterruptedException e) {
                    log.debug("Awoken");
                    ; // nothing
                }
            }
        }
        log.debug("Thread Exit");
    }
}
