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

/**
 * Descriptor file read class in unit test framework
 */
public class UnitTestClient {

    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());


    public static void main(String args[]) {

        logger.info("Unit testing client started");

        String descriptorFilePath = args[0];
        String synapseHost = args[1];
        String synapsePort = args[2];

        //create DescriptorFileReader object to read the descriptor file
        DescriptorFileReader descriptorReader = new DescriptorFileReader(descriptorFilePath);
        ArtifactData readArtifactData = descriptorReader.readArtifactData();
        TestCaseData readTestCaseData = descriptorReader.readTestCaseData();
        MockServiceData readMockServiceData = descriptorReader.readMockServiceData();

        MessageConstructor deployableMessage = new MessageConstructor();
        JSONObject deployableJSON = deployableMessage.
                generateDeployMessage(readArtifactData, readTestCaseData, readMockServiceData);
//
//        TCPClient tcpClient = new TCPClient(synapseHost, synapsePort);
//        tcpClient.sendData(deployableJSON);

        logger.info("Unit testing client stopped");
    }

}
