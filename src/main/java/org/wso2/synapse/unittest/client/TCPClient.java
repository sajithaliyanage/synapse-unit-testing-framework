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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.Socket;

/**
 * TCP client for initializing the socket and sending and receiving data through the socket.
 */
class TCPClient {

    private Socket clientSocket;
    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());

    /**
     * Initializing the TCP socket.
     * @param synapseHost socket initializing host
     * @param synapsePort socket initializing port
     */
    TCPClient(String synapseHost, String synapsePort) {

        try {
            clientSocket = new Socket(synapseHost, Integer.parseInt(synapsePort));
            logger.info("TCP socket connection has been established");
        } catch (IOException e) {
            logger.error("Error in initializing the socket", e);
        }
    }

    /**
     * Method of receiving response from the synapse unit test agent.
     */
    void readData() {

        logger.info("Waiting for synapse unit test agent response");

        try {
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            String response = (String) objectInputStream.readObject();
            logger.info("Received unit test agent response - " + response);

        }  catch (Exception e) {
            logger.error("Error in getting response from the synapse unit test agent", e);

        } finally {
            closeSocket();
        }
    }

    /**
     * Method of sending the artifact and test data to the synapse unit test agent.
     * @param messageToBeSent deployable JSON object with artifact and test case data
     */
    void writeData(String messageToBeSent) {

        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(messageToBeSent);
            outputStream.flush();

            logger.info("Artifact data and test cases data send to the synapse agent successfully");
        } catch (Exception e) {
            logger.error("Error while sending deployable data to the synapse agent ", e);
        }
    }

    /**
     * Method of closing connection of TCP.
     */
    private void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.error("Error while closing TCP client socket connection", e);
        }
    }
}
