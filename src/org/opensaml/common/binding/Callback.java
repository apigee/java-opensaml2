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

package org.opensaml.common.binding;

/**
 * Callback interface used to process messages asynchronously
 * 
 * @param <MessageType> the message type the callback processes
 */
public interface Callback<MessageType> {

    /**
     * Checks to see if this callback has completed processing its message.
     * 
     * @return true if the callback has completed processing its message, false if not
     */
    public boolean isComplete();

    /**
     * Sets whether this callback completed processing its message.
     * 
     * @param complete true if the callback has completed processing its message, false if not
     */
    public void setComplete(boolean complete);

    /**
     * Invoked to start the processing of a received message by this callback.
     * 
     * @param message the message to process
     */
    public void processMessage(MessageType message);

    /**
     * Invoked when there was an error receiving the message to be processed. Some examples errors may be that the
     * request timed out or that the returned message was not the expected type.
     * 
     * @param e the exception encountered when trying to receive the message
     */
    public void processError(Exception e);
}