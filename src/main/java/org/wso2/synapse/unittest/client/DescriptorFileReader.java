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

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.synapse.unittest.client.data.holders.ArtifactData;


import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.wso2.synapse.unittest.client.Constants.*;

/**
 * Descriptor file read class in unit test framework
 */
public class DescriptorFileReader {

    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());

    /**
     * Read artifact data from the descriptor file.
     * Append artifact data into the data holder object
     *
     * @param descriptorFilePath file path tom the descriptor file
     * @return dataHolder object with artifact data
     */
    String readArtifactData(String descriptorFilePath){

        ArtifactData artifactDataHolder = new ArtifactData();

        String inputXmlPayload;
        String artifactType;
        String artifact;
        String fileName;
        String expectedPropertyValues;
        String expectedPayload;

        try{
            String descriptorFileAsString = FileUtils.readFileToString(new File(descriptorFilePath));
            OMElement importXMLFile = AXIOMUtil.stringToOM(descriptorFileAsString);

            QName qualifiedTestCaseCount = new QName("", TEST_CASES_COUNT, "");
            OMElement testCaseCountTag = importXMLFile.getFirstChildWithName(qualifiedTestCaseCount);
            int testCaseCount = Integer.parseInt(testCaseCountTag.getText());
            artifactDataHolder.setTestCaseCount(testCaseCount);
            System.out.println(artifactDataHolder.getTestCaseCount());


        }catch (FileNotFoundException e){
            logger.error("Descriptor file not found in given path");
        }catch (XMLStreamException e){
            logger.error(e);
        }catch (IOException e){
            logger.error(e);
        }

        logger.info("yes");

        return "";
    }

}
