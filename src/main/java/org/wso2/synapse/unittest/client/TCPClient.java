/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import java.io.*;
import java.net.Socket;

/**
 * TCP client for initializing the socket and sending and receiving data through the socket
 */
public class TCPClient {

    private static Socket clientSocket;
    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());

    /**
     * Initializing the TCP socket
     * @param synapseHost
     * @param synapsePort
     */
    public TCPClient(String synapseHost, String synapsePort) {

        try {
            clientSocket = new Socket(synapseHost, Integer.parseInt(synapsePort));
            logger.info("TCP socket connection has been established");
        } catch (IOException e) {
            logger.error("Error in initializing the socket", e);
        }
    }

    /**
     * Method of sending the artifact and test data to the synapse unit test agent
     * Receive response from the server about unit test result
     * @param messageToBeSent deployable JSON object with artifact and test case data
     */
    public void sendData(JSONObject messageToBeSent) {
        try {
            OutputStream outToServer = clientSocket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(messageToBeSent.toString());

            logger.info("Artifact and Test case data sent to the Synapse unit test agent");
            logger.info("Waiting for server response");

            InputStream inFromServer = clientSocket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            logger.info("Response from server: " + in.readUTF());

        } catch (Exception e) {
            logger.error("Exception in writing data to the socket", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Method of closing connection of TCP
     */
    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }

}