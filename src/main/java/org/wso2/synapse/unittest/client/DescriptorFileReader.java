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
import org.wso2.synapse.unittest.client.data.holders.TestCaseData;

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
    ArtifactData readArtifactData(String descriptorFilePath){

        ArtifactData artifactDataHolder = new ArtifactData();

        try{
            String descriptorFileAsString = FileUtils.readFileToString(new File(descriptorFilePath));
            OMElement importXMLFile = AXIOMUtil.stringToOM(descriptorFileAsString);

            //Read test case count from descriptor file
            QName qualifiedTestCaseCount = new QName("", TEST_CASES_COUNT, "");
            OMElement testCaseCountNode = importXMLFile.getFirstChildWithName(qualifiedTestCaseCount);
            int testCasesCount = Integer.parseInt(testCaseCountNode.getText());
            artifactDataHolder.setTestCaseCount(testCasesCount);

            //Read artifact from descriptor file
            QName qualifiedArtifact = new QName("", ARTIFACT, "");
            OMElement artifactNode = importXMLFile.getFirstChildWithName(qualifiedArtifact);
            String artifact = artifactNode.getFirstElement().toString();
            artifactDataHolder.setArtifact(artifact);

            //Read artifact type from descriptor file
            QName qualifiedArtifactType = new QName("", ARTIFACT_TYPE, "");
            OMElement artifactTypeNode = importXMLFile.getFirstChildWithName(qualifiedArtifactType);
            String artifactType = (artifactTypeNode.getText());
            artifactDataHolder.setArtifactType(artifactType);

            //Read artifact type from descriptor file
            QName qualifiedArtifactName = new QName("", ARTIFACT_NAME, "");
            OMElement artifactNameNode = importXMLFile.getFirstChildWithName(qualifiedArtifactName);
            String artifactName = (artifactNameNode.getText());
            artifactDataHolder.setArtifactName(artifactName);

        }catch (FileNotFoundException e){
            logger.error("Descriptor file not found in given path");
        }catch (XMLStreamException e){
            logger.error(e);
        }catch (IOException e){
            logger.error(e);
        }catch (Exception e){
            logger.error(e);
        }

        logger.info("Artifact data from descriptor file read successfully");
        return artifactDataHolder;
    }

    /**
     * Read test-case data from the descriptor file.
     * Append test-case data into the test data holder object
     *
     * @param descriptorFilePath file path tom the descriptor file
     * @return testCaseDataHolder object with test case data
     */
    TestCaseData readTestCaseData(String descriptorFilePath){

        TestCaseData testCaseDataHolder = new TestCaseData();

        try {
            String descriptorFileAsString = FileUtils.readFileToString(new File(descriptorFilePath));
            OMElement importXMLFile = AXIOMUtil.stringToOM(descriptorFileAsString);

            //Read test case count from descriptor file
            QName qualifiedTestCaseCount = new QName("", TEST_CASES_COUNT, "");
            OMElement testCaseCountNode = importXMLFile.getFirstChildWithName(qualifiedTestCaseCount);
            int testCasesCount = Integer.parseInt(testCaseCountNode.getText());

            //Read test cases from descriptor file
            QName qualifiedTestCases = new QName("", TEST_CASES, "");
            OMElement testCasesNode = importXMLFile.getFirstChildWithName(qualifiedTestCases);

            //Iterate through test-cases in descriptor file
            for(int i=0; i < testCasesCount; i++){
                //Read test case from test-cases parent
                QName qualifiedTestCase = new QName("", TEST_CASE+"-"+(i+1), "");
                OMElement testCaseNode = testCasesNode.getFirstChildWithName(qualifiedTestCase);

                //Read input-xml-payload child attribute from test-case node
                QName qualifiedInputXMLPayload = new QName("", INPUT_XML_PAYLOAD, "");
                OMElement inputXMLPayloadNode = testCaseNode.getFirstChildWithName(qualifiedInputXMLPayload);
                String inputXMLPayload = inputXMLPayloadNode.getText();
                testCaseDataHolder.addInputXmlPayload(inputXMLPayload);

                //Read expected-payload child attribute from test-case node
                QName qualifiedExpectedPayload = new QName("", EXPECTED_PAYLOAD, "");
                OMElement ExpectedPayloadNode = testCaseNode.getFirstChildWithName(qualifiedExpectedPayload);
                String ExpectedPayload = ExpectedPayloadNode.getText();
                testCaseDataHolder.addExpectedPayload(ExpectedPayload);

                //Read expected-property-values child attribute from test-case node
                QName qualifiedExpectedPropertyValues = new QName("", EXPECTED_PROPERTY_VALUES, "");
                OMElement ExpectedPropertyValuesNode = testCaseNode.getFirstChildWithName(qualifiedExpectedPropertyValues);
                String ExpectedPropertyValues = ExpectedPropertyValuesNode.getText();
                testCaseDataHolder.addExpectedPropertyValues(ExpectedPropertyValues);
            }

        } catch (FileNotFoundException e) {
            logger.error("Descriptor file not found in given path");
        } catch (XMLStreamException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }

        logger.info("Test case data from descriptor file read successfully");
        return testCaseDataHolder;
    }

}
