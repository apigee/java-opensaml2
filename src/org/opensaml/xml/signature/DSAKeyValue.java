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

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Digital Signature, version 20020212, DSAKeyValue element.
 */
public interface DSAKeyValue extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "DSAKeyValue";

    /**
     * Gets the P component for the DSA key.
     * 
     * @return the P component for the DSA key
     */
    public DSAKeyPComponent getPComponent();

    /**
     * Sets the P component for the DSA key.
     * 
     * @param newPComponent the P component for the DSA key
     */
    public void setPComponent(DSAKeyPComponent newPComponent);

    /**
     * Gets the Q component for the DSA key.
     * 
     * @return the Q component for the DSA key
     */
    public DSAKeyQComponent getQComponent();

    /**
     * Sets the Q component for the DSA key.
     * 
     * @param newQComponent Q component for the DSA key
     */
    public void setQComponent(DSAKeyQComponent newQComponent);

    /**
     * Gets the G component of the DSA key.
     * 
     * @return the G component of the DSA key
     */
    public DSAKeyGComponent getGComponent();

    /**
     * Sets the G component of the DSA key.
     * 
     * @param newGComponent the G component of the DSA key
     */
    public void setGComponent(DSAKeyGComponent newGComponent);

    /**
     * Gets the Y component of the DSA key.
     * 
     * @return the Y component of the DSA key
     */
    public DSAKeyYComponent getYComponent();

    /**
     * Sets the Y component of the DSA key.
     * 
     * @param newYComponent the Y component of the DSA key
     */
    public void setYComponent(DSAKeyYComponent newYComponent);

    /**
     * Gets the J component of the DSA key.
     * 
     * @return the J component of the DSA key
     */
    public DSAKeyJComponent getJComponent();

    /**
     * Sets the J component of the DSA key.
     * 
     * @param newJComponent the J component of the DSA key
     */
    public void setJComponent(DSAKeyJComponent newJComponent);

    /**
     * Gets the seed of the DSA key.
     * 
     * @return the seed of the DSA key
     */
    public DSAKeySeed getSeed();

    /**
     * Sets the seed of the DSA key.
     * 
     * @param newSeed the seed of the DSA key
     */
    public void setDSAKeySeed(DSAKeySeed newSeed);

    /**
     * Gets the pgen counter of the DSA key.
     * 
     * @return the pgen counter of the DSA key
     */
    public DSAKeyPgenCounter getPgenCounter();

    /**
     * Sets the pgen counter of the DSA key.
     * 
     * @param newPgenCounter the pgen counter of the DSA key
     */
    public void setPgenCounter(DSAKeyPgenCounter newPgenCounter);
}