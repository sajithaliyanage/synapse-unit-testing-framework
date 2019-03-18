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

/**
 * SynapseTestCase file read class in unit test framework.
 */
public class UnitTestClient {

    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());

    /**
     * Main method of the synapse unit testing client.
     */
    public static void main(String[] args) {

        logger.info("Unit testing client started");

        try {

            if (args.length == 3) {
                String synapseTestCaseFilePath = args[0];
                String synapseHost = args[1];
                String synapsePort = args[2];

                //process SynapseTestCase data for send to the server
                String deployableMessage = SynapseTestCaseFileReader.processArtifactData(synapseTestCaseFilePath);

                if (deployableMessage != null) {
                    //create tcp connection, send SynapseTestCase file to server and get the response from the server
                    TCPClient tcpClient = new TCPClient(synapseHost, synapsePort);
                    tcpClient.writeData(deployableMessage);
                    tcpClient.readData();
                    tcpClient.closeSocket();

                } else {
                    logger.error("Error in creating deployable message");
                }
            } else {
                logger.error("Arguments of filepath or host or port not provided");
            }

        } catch (Exception e) {
            logger.error("Error while executing client", e);
        }

        logger.info("Unit testing client stopped");
    }

}
