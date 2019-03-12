/*
 * *
 *  * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *  *
 *  * WSO2 Inc. licenses this file to you under the Apache License,
 *  * Version 2.0 (the "License"); you may not use this file except
 *  * in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package org.wso2.synapse.unittest.client.mock.services;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.synapse.unittest.client.mock.services.core.Emulator;

import java.util.ArrayList;
import java.util.Set;

import static org.wso2.synapse.unittest.client.Constants.GET_METHOD;
import static org.wso2.synapse.unittest.client.Constants.POST_METHOD;
import static org.wso2.synapse.unittest.client.mock.services.http.dsl.dto.consumer.IncomingMessage.request;
import static org.wso2.synapse.unittest.client.mock.services.http.dsl.dto.consumer.OutgoingMessage.response;


/**
 * Class is responsible for creating mock services as request in descriptor file.
 */
public class MockServiceCreator {

    private static Logger logger = LogManager.getLogger(MockServiceCreator.class.getName());
    public static ArrayList<Long> mockServiceThreadIds = new ArrayList<Long>();

    /**
     * Start service for given parameters using emulator.
     *
     * @param mockServiceName endpoint name given in descriptor file
     * @param host domain of the url
     * @param path path of the url
     * @param serviceMethod service method of the service
     * @param inputPayload service expected input payload
     * @param responseBody expected response of the service
     */
    public static void startServer(String mockServiceName, String host, int port, String path, String serviceMethod,
                                   String inputPayload, String responseBody) {

        try {

            switch (serviceMethod.toUpperCase()) {
                case GET_METHOD :
                    Emulator.getHttpEmulator()
                            .consumer()
                            .host(host)
                            .port(port)
                            .context(path)
                            .when(request().withMethod(HttpMethod.GET))
                            .respond(response().withBody(responseBody).withStatusCode(HttpResponseStatus.OK))
                            .operations().start();
                    break;

                case POST_METHOD :
                    Emulator.getHttpEmulator()
                            .consumer()
                            .host(host)
                            .port(port)
                            .context(path)
                            .when(request().withMethod(HttpMethod.POST).withBody(inputPayload))
                            .respond(response().withBody(responseBody).withStatusCode(HttpResponseStatus.OK))
                            .operations().start();
                    break;

                default:
                    Emulator.getHttpEmulator()
                            .consumer()
                            .host(host)
                            .port(port)
                            .context(path)
                            .when(request().withMethod(HttpMethod.GET))
                            .respond(response().withBody(responseBody).withStatusCode(HttpResponseStatus.OK))
                            .operations().start();
            }


            logger.info("Mock service started for " + mockServiceName + "as [" + serviceMethod.toUpperCase() +
                            "] in - http://" + host + ":" + port + path);


        } catch (Exception e) {
            logger.error("Error in initiating mock service named " + mockServiceName , e);
        }

    }

    /**
     * Stop all services created from the emulator by checking thread-id.
     */
    public static void stopServices() {
        //set of threads currently running
        Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();

        //find the thread from thread-id and interrupt it
        for (int x = 0; x < mockServiceThreadIds.size(); x++) {
            for (Thread thread : setOfThread) {
                if (thread.getId() == mockServiceThreadIds.get(x)) {
                    thread.interrupt();
                    break;
                }
            }
        }


    }
}
