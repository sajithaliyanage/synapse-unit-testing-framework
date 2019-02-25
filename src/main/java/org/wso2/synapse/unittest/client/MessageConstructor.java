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
import org.wso2.synapse.unittest.client.data.holders.TestCaseData;

import static org.wso2.synapse.unittest.client.Constants.*;

/**
 * Class of the message constructor for Synapse unit test framework
 * Create deployable JSON object from data holders
 * Update deployable JSON object as Config Modifier
 */

public class MessageConstructor {

    private static Logger logger = LogManager.getLogger(UnitTestClient.class.getName());

    /**
     * Read artifact data from the artifactDataHolder.
     * Append artifact data into the JSON object
     *
     * @param artifactDataHolder object of ArtifactData which contains artifact data read from descriptor file
     * @return JSONObject which is ready to deploy via TCP server
     */
    public JSONObject generateDeployMessage(ArtifactData artifactDataHolder, TestCaseData testCaseDataHolder){

        JSONConstructor jsonDataHolder = new JSONConstructor();

        try{
            jsonDataHolder.initialize();

            //Add artifact data from data holder to json object
            jsonDataHolder.setAttribute(ARTIFACT, artifactDataHolder.getArtifact());
            jsonDataHolder.setAttribute(ARTIFACT_TYPE, artifactDataHolder.getArtifactType());
            jsonDataHolder.setAttribute(TEST_CASES_COUNT, artifactDataHolder.getTestCaseCount());

            //Add  test-case data from data holder to json object
            JSONConstructor jsonTestCaseDataHolderArray = new JSONConstructor();
            jsonTestCaseDataHolderArray.initializeArray();

            for(int i=0; i < artifactDataHolder.getTestCaseCount(); i++){
                JSONConstructor jsonTestCaseDataHolder = new JSONConstructor();
                jsonTestCaseDataHolder.initialize();

                jsonTestCaseDataHolder.setAttribute(INPUT_XML_PAYLOAD, testCaseDataHolder.getInputXmlPayload(i));
                jsonTestCaseDataHolder.setAttribute(EXPECTED_PROPERTY_VALUES, testCaseDataHolder.getExpectedPropertyValues(i));
                jsonTestCaseDataHolder.setAttribute(EXPECTED_PAYLOAD, testCaseDataHolder.getExpectedPayload(i));

                //Add test-case attributes to JSON array
                jsonTestCaseDataHolderArray.setAttributeForArray(jsonTestCaseDataHolder.getJSONDataHolder());
            }

            jsonDataHolder.setAttribute(TEST_CASES, jsonTestCaseDataHolderArray.getJSONArrayDataHolder());

            logger.info("Deployable JSON artifact data object created");

        }catch(Exception e){
            logger.error(e);
        }

        return jsonDataHolder.getJSONDataHolder();
    }
}
