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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.wso2.synapse.unittest.client.data.holders.ArtifactData;
import org.wso2.synapse.unittest.client.data.holders.MockServiceData;
import org.wso2.synapse.unittest.client.data.holders.TestCaseData;
import org.wso2.synapse.unittest.client.mock.services.MockServiceCreator;


/**
 * Descriptor file read class in unit test framework.
 */
public class UnitTestClient {

    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());

    /**
     * Main method of the synapse unit testing client.
     */
    public static void main(String args[]) {

        logger.info("Unit testing client started");

        String descriptorFilePath = args[0];
        String synapseHost = args[1];
        String synapsePort = args[2];
        ArtifactData readArtifactData = null;
        TestCaseData readTestCaseData = null;
        MockServiceData readMockServiceData = null;

        //Read descriptor file and store data relevant data holders
        try {
            DescriptorFileReader descriptorReader = new DescriptorFileReader(descriptorFilePath);
            readArtifactData = descriptorReader.readArtifactData();
            readTestCaseData = descriptorReader.readTestCaseData();
            readMockServiceData = descriptorReader.readMockServiceData();

        } catch (NullPointerException e) {
            logger.error("Descriptor file read failed - " + e);
        }

        MessageConstructor deployableMessage = new MessageConstructor();
        JSONObject deployableJSON = deployableMessage.
                generateDeployMessage(readArtifactData, readTestCaseData, readMockServiceData);

        //create tcp connection, send deployable JSON and get the response from the server
        TCPClient tcpClient = new TCPClient(synapseHost, synapsePort);
        tcpClient.writeData(deployableJSON);
        tcpClient.readData();

        //stop mock services created
        MockServiceCreator.stopServices();
        logger.info("Unit testing client stopped");
    }

}
