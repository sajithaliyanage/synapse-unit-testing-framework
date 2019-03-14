/*
 Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.synapse.unittest.client;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Iterator;
import javax.xml.namespace.QName;

import static org.wso2.synapse.unittest.client.Constants.ARTIFACT;
import static org.wso2.synapse.unittest.client.Constants.ARTIFACT_FILE;

/**
 * Descriptor file read class in unit test framework.
 */
class DescriptorFileReader {

    private static Logger logger = LogManager.getLogger(DescriptorFileReader.class.getName());
    private OMElement importedXMLFile = null;

    /**
     * Constructor of the DescriptorFileReader class.
     * @param descriptorFilePath path of the descriptor file
     */
    DescriptorFileReader(String descriptorFilePath) {
        try {
            String descriptorFileAsString = FileUtils.readFileToString(new File(descriptorFilePath));
            this.importedXMLFile = AXIOMUtil.stringToOM(descriptorFileAsString);

        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * Check that descriptor data includes artifact data.
     * If not read artifact from given file and append data into the artifact data
     *
     * @return  descriptor data which is ready to send to the server
     */
    String processArtifactData() {

        logger.info("Checking descriptor file contains artifact data");

        //Read artifact from descriptor file
        QName qualifiedArtifact = new QName("", ARTIFACT, "");
        OMElement artifactNode = importedXMLFile.getFirstChildWithName(qualifiedArtifact);

        //check if artifact or artifact-file available in descriptor file
        if (artifactNode == null) {

            logger.info("Descriptor file does not contains artifact data. Checking for artifact file data");
            QName qualifiedArtifactFile = new QName("", ARTIFACT_FILE, "");
            OMElement artifactFileNode = importedXMLFile.getFirstChildWithName(qualifiedArtifactFile);
            String artifactFilePath = artifactFileNode.getText();

            try {
                String artifactFileAsString = FileUtils.readFileToString(new File(artifactFilePath));
                artifactNode = AXIOMUtil.stringToOM(artifactFileAsString);

                //check artifact-file contains artifact data
                if (artifactNode == null) {
                    logger.error("Descriptor file does not contains artifact data or artifact file details");
                    throw new NullPointerException();

                } else {

                    logger.info("Artifact file data imported to the descriptor file data");

                    //create new tag name artifact to add artifact-file data as a child
                    OMFactory fac = OMAbstractFactory.getOMFactory();
                    OMElement artifactNewNode = fac.createOMElement(new QName(ARTIFACT));
                    artifactNewNode.addChild(artifactNode);

                    //add artifact node to the descriptor data
                    importedXMLFile.addChild(artifactNewNode);

                    //remove ARTIFACT-FILE tag from descriptor data
                    Iterator item = importedXMLFile.getChildrenWithName(new QName(ARTIFACT_FILE));
                    if (item.hasNext()) {
                        item.next();
                        item.remove();
                    }
                }
            } catch (Exception e) {
                logger.error("Artifact file reading failed", e);
            }
        }

        return importedXMLFile.toString();
    }

}
