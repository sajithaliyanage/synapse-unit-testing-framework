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
import org.w3c.dom.*;
import org.wso2.synapse.unittest.client.data.holders.MockServiceData;
import org.wso2.synapse.unittest.client.mock.services.MockServiceCreator;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

import static org.wso2.synapse.unittest.client.Constants.*;


/**
 * Class responsible for modify the artifact data
 */
public class ConfigModuler {

    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());

    public static String endPointModifier(String artifact, MockServiceData mockServiceData) {

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(new InputSource(new StringReader(artifact)));

            NodeList xmlElementNodes = document.getElementsByTagName("*");

            for (int i = 0; i < xmlElementNodes.getLength(); i++) {
                Node endPointNode = xmlElementNodes.item(i);

                if (endPointNode.getNodeName().equals(END_POINT)) {

                    NamedNodeMap attributeListOfEndPoint = endPointNode.getAttributes();
                    String valueOfName = attributeListOfEndPoint.getNamedItem("name").getNodeValue();

                    //check service name is exists in mock service data holder map
                    boolean isServiceExists = mockServiceData.isServiceNameExist(valueOfName);

                    if (isServiceExists) {
                        int serviceElementIndex = mockServiceData.getServiceNameIndex(valueOfName);
                        String serviceHostUrl = mockServiceData.getServiceHost(serviceElementIndex);
                        String serviceMethod = mockServiceData.getServiceType(serviceElementIndex);

                        NodeList childNodesOfEndPoint = endPointNode.getChildNodes();
                        Node addressNode = childNodesOfEndPoint.item(1);

                        NamedNodeMap attributeListOfAddress = addressNode.getAttributes();

                        for (int y = 0; y < attributeListOfAddress.getLength(); y++) {
                            Attr attribute = (Attr) attributeListOfAddress.item(y);

                            if (attribute.getNodeName().equals(URI)) {
                                attributeListOfAddress.getNamedItem(URI).setNodeValue(serviceHostUrl);
                                break;

                            } else if (attribute.getNodeName().equals(URI_TEMPLATE)) {
                                attributeListOfAddress.getNamedItem(URI_TEMPLATE).setNodeValue(serviceHostUrl);
                                attributeListOfAddress.getNamedItem(METHOD).setNodeValue(serviceMethod);
                                break;
                            }
                        }

                        logger.info("Mock service creator ready to start service for " + valueOfName);
                        MockServiceCreator.startServer();
                    }
                }
            }

            DOMSource domSource = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return  writer.toString();

        } catch (Exception e) {
            logger.error(e);
        }


        return artifact;
    }
}

