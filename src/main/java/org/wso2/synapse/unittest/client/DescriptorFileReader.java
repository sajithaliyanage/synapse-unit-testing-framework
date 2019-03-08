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
import org.wso2.synapse.unittest.client.data.holders.MockServiceData;
import org.wso2.synapse.unittest.client.data.holders.TestCaseData;


import java.io.File;
import java.io.IOException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

import static org.wso2.synapse.unittest.client.Constants.*;

/**
 * Descriptor file read class in unit test framework
 */
public class DescriptorFileReader {

    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());
    private OMElement importXMLFile = null;

    DescriptorFileReader(String descriptorFilePath) {
        try {
            String descriptorFileAsString = FileUtils.readFileToString(new File(descriptorFilePath));
            this.importXMLFile = AXIOMUtil.stringToOM(descriptorFileAsString);

        } catch (XMLStreamException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    /**
     * Read artifact data from the descriptor file.
     * Append artifact data into the data holder object
     *
     * @return dataHolder object with artifact data
     */
    ArtifactData readArtifactData() {
        ArtifactData artifactDataHolder = new ArtifactData();

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

        logger.info("Artifact data from descriptor file read successfully");
        return artifactDataHolder;
    }

    /**
     * Read test-case data from the descriptor file.
     * Append test-case data into the test data holder object
     *
     * @return testCaseDataHolder object with test case data
     */
    TestCaseData readTestCaseData() {

        TestCaseData testCaseDataHolder = new TestCaseData();

        //Read test case count from descriptor file
        QName qualifiedTestCaseCount = new QName("", TEST_CASES_COUNT, "");
        OMElement testCaseCountNode = importXMLFile.getFirstChildWithName(qualifiedTestCaseCount);
        int testCasesCount = Integer.parseInt(testCaseCountNode.getText());

        //Read test cases from descriptor file
        QName qualifiedTestCases = new QName("", TEST_CASES, "");
        OMElement testCasesNode = importXMLFile.getFirstChildWithName(qualifiedTestCases);

        //Iterate through test-cases in descriptor file
        for (int i = 0; i < testCasesCount; i++) {
            //Read test case from test-cases parent
            QName qualifiedTestCase = new QName("", TEST_CASE + "-" + (i + 1), "");
            OMElement testCaseNode = testCasesNode.getFirstChildWithName(qualifiedTestCase);

            //Read input-xml-payload child attribute from test-case node
            QName qualifiedInputXMLPayload = new QName("", INPUT_XML_PAYLOAD, "");
            OMElement inputXMLPayloadNode = testCaseNode.getFirstChildWithName(qualifiedInputXMLPayload);
            String inputXMLPayload = inputXMLPayloadNode.getText();
            testCaseDataHolder.addInputXmlPayload(inputXMLPayload);

            //Read expected-payload child attribute from test-case node
            QName qualifiedExpectedPayload = new QName("", EXPECTED_PAYLOAD, "");
            OMElement expectedPayloadNode = testCaseNode.getFirstChildWithName(qualifiedExpectedPayload);
            String expectedPayload = expectedPayloadNode.getText();
            testCaseDataHolder.addExpectedPayload(expectedPayload);

            //Read expected-property-values child attribute from test-case node
            QName qualifiedExpectedPropertyValues = new QName("", EXPECTED_PROPERTY_VALUES, "");
            OMElement expectedPropertyValuesNode = testCaseNode
                    .getFirstChildWithName(qualifiedExpectedPropertyValues);
            String expectedPropertyValues = expectedPropertyValuesNode.getText();
            testCaseDataHolder.addExpectedPropertyValues(expectedPropertyValues);
        }

        logger.info("Test case data from descriptor file read successfully");
        return testCaseDataHolder;
    }

    /**
     * Read mock-service data from the descriptor file.
     * Append mock-service data into the test data holder object
     *
     * @return mockServiceDataHolder object with test case data
     */
    MockServiceData readMockServiceData() {

        MockServiceData mockServiceDataHolder = new MockServiceData();

        //Read mock-services count from descriptor file
        QName qualifiedMockServiceCount = new QName("", MOCK_SERVICE_COUNT, "");
        OMElement mockServiceCountNode = importXMLFile.getFirstChildWithName(qualifiedMockServiceCount);
        int mockServiceCount = Integer.parseInt(mockServiceCountNode.getText());
        mockServiceDataHolder.setMockServicesCount(mockServiceCount);

        //Read test cases from descriptor file
        QName qualifiedMockServices = new QName("", MOCK_SERVICES, "");
        OMElement mockServicesNode = importXMLFile.getFirstChildWithName(qualifiedMockServices);

        //Iterate through mock-service in descriptor file
        for (int i = 0; i < mockServiceCount; i++) {
            //Read mock-service from mock services parent
            QName qualifiedMockService = new QName("", MOCK_SERVICE + "-" + (i + 1), "");
            OMElement mockServiceNode = mockServicesNode.getFirstChildWithName(qualifiedMockService);

            //Read service name child attribute from mock service node
            QName qualifiedServiceName = new QName("", SERVICE_NAME, "");
            OMElement serviceNameNode = mockServiceNode.getFirstChildWithName(qualifiedServiceName);
            String serviceName = serviceNameNode.getText();
            mockServiceDataHolder.addServiceName(serviceName, i);

            //Read service host child attribute from mock service node
            QName qualifiedServiceHost = new QName("", SERVICE_HOST, "");
            OMElement serviceHostNode = mockServiceNode.getFirstChildWithName(qualifiedServiceHost);
            String serviceHost = serviceHostNode.getText();
            mockServiceDataHolder.addServiceHost(serviceHost);

            //Read service port child attribute from mock service node
            QName qualifiedServicePort = new QName("", SERVICE_PORT, "");
            OMElement servicePortNode = mockServiceNode.getFirstChildWithName(qualifiedServicePort);
            int servicePort = Integer.parseInt(servicePortNode.getText());
            mockServiceDataHolder.addServicePort(servicePort);

            //Read service path child attribute from mock service node
            QName qualifiedServicePath = new QName("", SERVICE_PATH, "");
            OMElement servicePathNode = mockServiceNode.getFirstChildWithName(qualifiedServicePath);
            String servicePath = servicePathNode.getText();
            mockServiceDataHolder.addServicePath(servicePath);

            //Read service type child attribute from mock service node
            QName qualifiedServiceType = new QName("", SERVICE_TYPE, "");
            OMElement serviceTypeNode = mockServiceNode.getFirstChildWithName(qualifiedServiceType);
            String serviceType = serviceTypeNode.getText();
            mockServiceDataHolder.addServiceType(serviceType);

            //Read service input payload child attribute from mock service node
            QName qualifiedServicePayload = new QName("", SERVICE_PAYLOAD, "");
            OMElement servicePayloadeNode = mockServiceNode.getFirstChildWithName(qualifiedServicePayload);
            String servicePayload = servicePayloadeNode.getText();
            mockServiceDataHolder.addServicePayload(servicePayload);

            //Read service response child attribute from mock service node
            QName qualifiedServiceResponse = new QName("", SERVICE_RESPONSE, "");
            OMElement serviceResponseNode = mockServiceNode.getFirstChildWithName(qualifiedServiceResponse);
            String serviceResponse = serviceResponseNode.getText();
            mockServiceDataHolder.addServiceResponse(serviceResponse);
        }

        logger.info("Mock service data from descriptor file read successfully");
        return mockServiceDataHolder;
    }
}
