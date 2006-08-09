package org.opensaml.saml2.binding;

import org.opensaml.common.SAMLArtifact;

/**
 * SAML 2 Artifact.  SAML 2 artifacts contains a 2 byte type code with a value of 4 follwed by a 2 byte 
 * endpoint index followed by a 20 byte source ID followed by a 20 byte message handle.
 */
public class SAML2Artifact extends SAMLArtifact {
    
    /** SAML 2 artifact type code (0x0004) */
    public final static byte[] TYPE_CODE = {0,4};
    
    /** 2 byte artifact endpoint index */
    private byte[] endpointIndex;
    
    /**
     * Constructor
     *
     * @param endpointIndex 2 byte endpoint index of the artifact
     * @param sourceID 20 byte source ID of the artifact
     * @param messageHandle 20 byte message handle of the artifact
     * 
     * @throws IllegalArgumentException thrown if the endpoint index, source ID, or message handle arrays are not of the right size
     */
    public SAML2Artifact(byte[] endpointIndex, byte[] sourceID, byte[] messageHandle) throws IllegalArgumentException{
        setTypeCode(TYPE_CODE);
        setEndpointIndex(endpointIndex);
        setSourceID(sourceID);
        setMessageHandle(messageHandle);
    }
    
    /**
     * Constructs a SAML 2 artifact from it's byte array representation.
     *
     * @param artifact the byte array representing the artifact
     * 
     * @throws IllegalArgumentException thrown if the artifact is not the right type or lenght (44 bytes)
     */
    public SAML2Artifact(byte[] artifact) throws IllegalArgumentException{
        if(artifact.length != 44){
            throw new IllegalArgumentException("Artifact length must be 44 bytes it was " + artifact.length + "bytes");
        }
        
        byte[] typeCode = {artifact[0], artifact[1]};
        if(typeCode != TYPE_CODE){
            throw new IllegalArgumentException("Illegal artifact type code");
        }
        setTypeCode(typeCode);
        
        byte[] endpointIndex = {artifact[2], artifact[3]};
        setEndpointIndex(endpointIndex);
        
        byte[] sourceID = new byte[20];
        for(int i = 0, j = 4; j < 24; i++, j++){
            sourceID[i] = artifact[j];
        }
        setSourceID(sourceID);
        
        byte[] messageHandle = new byte[20];
        for(int i = 0, j = 24; j < 44; i++, j++){
            messageHandle[i] = artifact[j];
        }
        setMessageHandle(messageHandle);
    }
    
    /** {@inheritDoc} */
    public byte[] getArtifactBytes() {
        byte[] artifact = new byte[44];
        
        byte[] typeCode = getTypeCode();
        byte[] endpointIndex = getEndpointIndex();
        byte[] sourceID = getSourceID();
        byte[] messageHandle = getMessageHandle();
        
        artifact[0] = typeCode[0];
        artifact[1] = typeCode[1];
        artifact[2] = endpointIndex[0];
        artifact[3] = endpointIndex[1];
        
        for(int i = 4, j = 0; i < 24; i++, j++){
            artifact[i] = sourceID[j];
        }
        
        for(int i = 24, j = 0; i < 44; i++, j++){
            artifact[i] = messageHandle[j];
        }
        
        return artifact;
    }

    /**
     * Gets the 2 byte endpoint index for this artifact.
     * 
     * @return 2 byte endpoint index for this artifact
     */
    public byte[] getEndpointIndex(){
        return endpointIndex;
    }
    
    /**
     * Sets the 2 byte endpoint index for this artifact.
     * 
     * @param newIndex 2 byte endpoint index for this artifact
     * 
     * @throws IllegalArgumentException thrown if the given index is not 2 bytes
     */
    protected void setEndpointIndex(byte[] newIndex) throws IllegalArgumentException{
        if(newIndex.length != 2){
            throw new IllegalArgumentException("Artifact endpoint index must be two bytes long");
        }
        
        endpointIndex = newIndex;
    }
}