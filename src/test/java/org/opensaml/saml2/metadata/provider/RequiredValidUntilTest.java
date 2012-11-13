/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml2.metadata.provider;

import java.io.File;
import java.net.URL;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.Configuration;
import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.saml2.metadata.EntitiesDescriptor;

/** Unit test for {@link RequiredValidUntilFilter}. */
public class RequiredValidUntilTest extends BaseTestCase {

    private File metadataFile;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();

        URL mdURL = FilesystemMetadataProviderTest.class
                .getResource("/data/org/opensaml/saml2/metadata/simple-metadata.xml");
        metadataFile = new File(mdURL.toURI());
    }

    public void testRequiredValidUntil() throws Exception {
        RequiredValidUntilFilter filter = new RequiredValidUntilFilter();

        FilesystemMetadataProvider metadataProvider = new FilesystemMetadataProvider(metadataFile);
        metadataProvider.setParserPool(parser);
        metadataProvider.setMetadataFilter(filter);
        try {
            metadataProvider.initialize();
        } catch (MetadataProviderException e) {
            fail("Filter disallowed metadata that contained a proper validUntil attribute");
        }
    }

    public void testRequiredValidUntilWithMaxValidity() throws Exception {
        RequiredValidUntilFilter filter = new RequiredValidUntilFilter(1);

        FilesystemMetadataProvider metadataProvider = new FilesystemMetadataProvider(metadataFile);
        metadataProvider.setParserPool(parser);
        metadataProvider.setMetadataFilter(filter);

        try {
            metadataProvider.initialize();
            fail("Filter accepted metadata with longer than allowed validity period.");
        } catch (MetadataProviderException e) {
            // we expect this
            return;
        }
    }
    
    public void testRequiredValidUntilAlreadyPast() throws Exception {
        SAMLObjectBuilder<EntitiesDescriptor> entitiesDescriptorBuilder = (SAMLObjectBuilder<EntitiesDescriptor>) Configuration.getBuilderFactory().getBuilder(EntitiesDescriptor.TYPE_NAME);
        EntitiesDescriptor descriptor = entitiesDescriptorBuilder.buildObject();
        descriptor.setValidUntil(new DateTime(ISOChronology.getInstanceUTC()).minus(10000));

        RequiredValidUntilFilter filter = new RequiredValidUntilFilter(-1);
        filter.doFilter(descriptor);
        
        filter = new RequiredValidUntilFilter();
        filter.doFilter(descriptor);
    }
}