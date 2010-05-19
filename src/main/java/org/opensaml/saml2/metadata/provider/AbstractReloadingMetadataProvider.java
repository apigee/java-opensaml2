/*
 * Copyright 2010 University Corporation for Advanced Internet Development, Inc.
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

import java.io.ByteArrayInputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.saml2.common.SAML2Helper;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.w3c.dom.Document;

/**
 * Base class for metadata providers that cache and periodically refresh their metadata.
 * 
 * This metadata provider periodically checks to see if the read metadata file has changed. The delay between each
 * refresh interval is calculated as follows. If no validUntil or cacheDuration is present then the
 * {@link #getMaxRefreshDelay()} value is used. Otherwise, the earliest refresh interval of the metadata file is checked
 * by looking for the earliest of all the validUntil attributes and cacheDuration attributes. If that refresh interval
 * is larger than the max refresh delay then {@link #getMaxRefreshDelay()} is used. If that number is smaller than the
 * min refresh delay then {@link #getMinRefreshDelay()} is used. Otherwise the calculated refresh delay multiplied by
 * {@link #getRefreshDelayFactor()} is used. By using this factor, the provider will attempt to be refresh before the
 * cache actually expires, allowing a some room for error and recovery. Assuming the factor is not exceedingly close to
 * 1.0 and a min refresh delay that is not overly large, this refresh will likely occur a few times before the cache
 * expires.
 */
public abstract class AbstractReloadingMetadataProvider extends AbstractObservableMetadataProvider {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(AbstractReloadingMetadataProvider.class);

    /** Timer used to schedule background metadata update tasks. */
    private Timer taskTimer;

    /** Factor used to compute when the next refresh interval will occur. Default value: {@value} */
    private float refreshDelayFactor = 0.75f;

    /**
     * Refresh interval used when metadata does not contain any validUntil or cacheDuration information. Default value:
     * * {@value} ms
     */
    private long maxRefreshDelay = 14400000;

    /** Floor, in milliseconds, for the refresh interval. Default value: {@value} ms */
    private int minRefreshDelay = 300000;

    /** Time when the currently cached metadata file expires. */
    private DateTime expirationTime;

    /** Last time the metadata was updated. */
    private DateTime lastUpdate;

    /** Last time a refresh cycle occurred. */
    private DateTime lastRefresh;

    /** Next time a refresh cycle will occur. */
    private DateTime nextRefresh;

    /** Last successfully read in metadata. */
    private XMLObject cachedMetadata;

    /** Constructor. */
    protected AbstractReloadingMetadataProvider() {
        taskTimer = new Timer(true);
    }

    /**
     * Constructor.
     * 
     * @param backgroundTaskTimer time used to schedule background refresh tasks
     */
    protected AbstractReloadingMetadataProvider(Timer backgroundTaskTimer) {
        if (backgroundTaskTimer == null) {
            throw new IllegalArgumentException("Task timer may not be null");
        }
        taskTimer = backgroundTaskTimer;
    }

    /**
     * Gets the time when the currently cached metadata expires.
     * 
     * @return time when the currently cached metadata expires, or null if no metadata is cached
     */
    public DateTime getExpirationTime() {
        return expirationTime;
    }

    /**
     * Gets the time that the currently available metadata was last updated. Note, this may be different than the time
     * retrieved by {@link #getLastUpdate()} is the metadata was known not to have changed during the last refresh
     * cycle.
     * 
     * @return time when the currently metadata was last update, 0 if metadata has never successfully been read in
     */
    public DateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Gets the time the last refresh cycle occurred.
     * 
     * @return time the last refresh cycle occurred
     */
    public DateTime getLastRefresh() {
        return lastRefresh;
    }

    /**
     * Gets the time when the next refresh cycle will occur.
     * 
     * @return time when the next refresh cycle will occur
     */
    public DateTime getNextRefresh() {
        return nextRefresh;
    }

    /**
     * Gets the maximum amount of time, in milliseconds, between refresh intervals.
     * 
     * @return maximum amount of time between refresh intervals
     */
    public long getMaxRefreshDelay() {
        return maxRefreshDelay;
    }

    /**
     * Sets the maximum amount of time, in milliseconds, between refresh intervals.
     * 
     * @param delay maximum amount of time, in milliseconds, between refresh intervals
     */
    public void setMaxRefreshDelay(long delay) {
        if (delay < 0) {
            throw new IllegalArgumentException("Maximum refresh delay must be greater than 0");
        }
        if (delay < minRefreshDelay) {
            throw new IllegalArgumentException(
                    "Maximum refresh delay must be greater than or equal to minimum refresh delay");
        }
        maxRefreshDelay = delay;
    }

    /**
     * Gets the delay factor used to compute the next refresh time.
     * 
     * @return delay factor used to compute the next refresh time
     */
    public float getRefreshDelayFactor() {
        return refreshDelayFactor;
    }

    /**
     * Sets the delay factor used to compute the next refresh time. The delay must be between 0.0 and 1.0, exclusive.
     * 
     * @param factor delay factor used to compute the next refresh time
     */
    public void setRefreshDelayFactor(float factor) {
        if (factor <= 0 || factor >= 1) {
            throw new IllegalArgumentException("Refresh delay factor must be a number between 0.0 and 1.0, exclusive");
        }

        refreshDelayFactor = factor;
    }

    /**
     * Gets the minimum amount of time, in milliseconds, between refreshes.
     * 
     * @return minimum amount of time, in milliseconds, between refreshes
     */
    public int getMinRefreshDelay() {
        return minRefreshDelay;
    }

    /**
     * Sets the minimum amount of time, in milliseconds, between refreshes.
     * 
     * @param delay minimum amount of time, in milliseconds, between refreshes
     */
    public void setMinRefreshDelay(int delay) {
        if (delay < 0) {
            throw new IllegalArgumentException("Minimum refresh delay must be greater than 0");
        }
        if (delay > maxRefreshDelay) {
            throw new IllegalArgumentException(
                    "Minimum refresh delay must be less than or equal to maximum refresh delay");
        }
        minRefreshDelay = delay;
    }

    /** {@inheritDoc} */
    public XMLObject getMetadata() throws MetadataProviderException {
        return cachedMetadata;
    }

    /** {@inheritDoc} */
    protected void doInitialization() throws MetadataProviderException {
        refresh();
    }

    /**
     * Refreshes the metadata from its source.
     * 
     * @throws MetadataProviderException thrown is there is a problem retrieving and processing the metadata
     */
    public void refresh() throws MetadataProviderException {
        DateTime now = new DateTime(ISOChronology.getInstanceUTC());
        String mdId = getMetadataIdentifier();

        log.debug("Beginning refresh of metadata from '{}'", mdId);
        try {
            byte[] mdBytes = fetchMetadata();
            if (mdBytes == null) {
                log.debug("Metadata from '{}' has not changed since last refresh", mdId);
                processCachedMetadata(mdId, now);
            } else {
                log.debug("New metadata from '{}' available, processing it", mdId);
                processNewMetadata(mdId, now, mdBytes);
            }

            lastRefresh = now;
        } catch (MetadataProviderException e) {
            log.debug("Error occurred while attempting metadata refresh, next refresh for metadata from '{}' will occur in approximately {}ms", mdId, minRefreshDelay);
            taskTimer.schedule(new RefreshMetadataTask(), minRefreshDelay);
            throw e;
        }
    }

    /**
     * Gets an identifier which may be used to distinguish this metadata in logging statements.
     * 
     * @return identifier which may be used to distinguish this metadata in logging statements
     */
    protected abstract String getMetadataIdentifier();

    /**
     * Fetches metadata from a source.
     * 
     * @return the fetched metadata, or null if the metadata is known not to have changed since the last retrieval
     * 
     * @throws MetadataProviderException thrown if there is a problem fetching the metadata
     */
    protected abstract byte[] fetchMetadata() throws MetadataProviderException;

    /**
     * Unmarshalls the given metadata bytes.
     * 
     * @param metadataBytes raw metadata bytes
     * 
     * @return the metadata
     * 
     * @throws MetadataProviderException thrown if the metadata can not be unmarshalled
     */
    protected XMLObject unmarshallMetadata(byte[] metadataBytes) throws MetadataProviderException {
        try {
            return unmarshallMetadata(new ByteArrayInputStream(metadataBytes));
        } catch (UnmarshallingException e) {
            String errorMsg = "Unable to unmarshall metadata";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }
    }

    /**
     * Processes a cached metadata document in order to determine, and schedule, the next time it should be refreshed.
     * 
     * @param metadataIdentifier identifier of the metadata source
     * @param refreshStart when the current refresh cycle started
     * 
     * @throws MetadataProviderException throw is there is a problem process the cached metadata
     */
    protected void processCachedMetadata(String metadataIdentifier, DateTime refreshStart)
            throws MetadataProviderException {
        log.debug("Computing new expiration time for cached metadata from '{}", metadataIdentifier);
        DateTime metadatedExpirationTime = SAML2Helper.getEarliestExpiration(cachedMetadata, refreshStart
                .plus(getMaxRefreshDelay()), refreshStart);
        log.debug("Expiration of cached metadata from '{}' will occur at '{}", metadataIdentifier,
                metadatedExpirationTime.toString());

        long nextRefreshDelay = computeNextRefreshDelay(expirationTime);
        log.debug("Next refresh for cached metadata from '{}' will occur in approximately {}ms", metadataIdentifier,
                nextRefresh);
        taskTimer.schedule(new RefreshMetadataTask(), nextRefreshDelay);
    }

    /**
     * Process a new metadata document. Processing include unmarshalling and filtering metadata, determining the next
     * time is should be refreshed and scheduling the next refresh cycle.
     * 
     * @param metadataIdentifier identifier of the metadata source
     * @param refreshStart when the current refresh cycle started
     * @param metadataBytes raw bytes of the new metadata document
     * 
     * @throws MetadataProviderException thrown if there is a problem unmarshalling or filtering the new metadata
     */
    protected void processNewMetadata(String metadataIdentifier, DateTime refreshStart, byte[] metadataBytes)
            throws MetadataProviderException {
        log.debug("Unmarshalling metadata from '{}'", metadataIdentifier);
        XMLObject metadata = unmarshallMetadata(metadataBytes);
        Document metadataDom = metadata.getDOM().getOwnerDocument();

        log.debug("Filtering metadata from '{}'", metadataIdentifier);
        try {
            filterMetadata(metadata);
        } catch (FilterException e) {
            String errMsg = MessageFormatter.format("Error filtering metadata from '{}'", metadataIdentifier);
            log.error(errMsg, e);
            throw new MetadataProviderException(errMsg, e);
        }

        log.debug("Release DOM for metadata from '{}'", metadataIdentifier);
        releaseMetadataDOM(metadata);

        log.debug("Post-processing metadata from '{}'", metadataIdentifier);
        postProcessMetadata(metadataBytes, metadataDom, metadata);

        log.debug("Computing expiration time for metadata from '{}", metadataIdentifier);
        DateTime metadatedExpirationTime = SAML2Helper.getEarliestExpiration(metadata, refreshStart
                .plus(getMaxRefreshDelay()), refreshStart);
        log.debug("Expiration of metadata from '{}' will occur at '{}", metadataIdentifier, metadatedExpirationTime
                .toString());

        cachedMetadata = metadata;
        lastUpdate = refreshStart;
        expirationTime = metadatedExpirationTime;

        long nextRefreshDelay = computeNextRefreshDelay(expirationTime);
        log.debug("Next refresh for metadata from '{}' will occur in approximately {}ms", metadataIdentifier,
                nextRefresh);
        taskTimer.schedule(new RefreshMetadataTask(), nextRefreshDelay);

        emitChangeEvent();
    }

    /**
     * Post-processing hook called after new metadata has been unmarshalled, filtered, and the DOM released (from the
     * {@link XMLObject}) but before the metadata is saved off. Any exception thrown by this hook will cause the
     * retrieved metadata to be discarded.
     * 
     * The default implementation of this method is a no-op
     * 
     * @param metadataBytes raw metadata bytes retrieved via {@link #fetchMetadata}
     * @param metadataDom metadata after it has been parsed in to a DOM document
     * @param metadata metadata after it has been run through all registered filters and its DOM released
     * 
     * @throws MetadataProviderException thrown if there is a problem with the provided data
     */
    protected void postProcessMetadata(byte[] metadataBytes, Document metadataDom, XMLObject metadata)
            throws MetadataProviderException {

    }

    /**
     * Computes the delay until the next refresh time based on the current metadata's expiration time and the refresh
     * interval floor.
     * 
     * @param expectedExpiration the time when the metadata is expected to expire and need refreshing
     * 
     * @return delay, in milliseconds, until the next refresh time
     */
    protected long computeNextRefreshDelay(DateTime expectedExpiration) {
        long now = new DateTime(ISOChronology.getInstanceUTC()).getMillis();

        long expireInstant = 0;
        if (expectedExpiration != null) {
            expireInstant = expectedExpiration.toDateTime(ISOChronology.getInstanceUTC()).getMillis();
        }
        long refreshDelay = (long) ((expireInstant - now) * getRefreshDelayFactor());

        // if the expiration time was null or the calculated refresh delay was less than the floor
        // use the floor
        if (refreshDelay < getMinRefreshDelay()) {
            refreshDelay = getMaxRefreshDelay();
        }

        return refreshDelay;
    }

    /** Background task that refreshes metadata. */
    private class RefreshMetadataTask extends TimerTask {

        /** {@inheritDoc} */
        public void run() {
            try {
                refresh();
            } catch (MetadataProviderException e) {
                // nothing to do, error message already logged by refreshMetadata()
                return;
            }
        }
    }
}
