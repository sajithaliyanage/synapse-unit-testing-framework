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
package org.wso2.synapse.unittest.client.data.holders;

import java.util.ArrayList;

public class TestCaseData {

    private ArrayList<String> inputXmlPayloadArray = new ArrayList<String>();
    private ArrayList<String> expectedPropertyValuesArray = new ArrayList<String>();
    private ArrayList<String> expectedPayloadArray = new ArrayList<String>();

    public String getInputXmlPayload(int elementIndex) {
        return inputXmlPayloadArray.get(elementIndex);
    }

    public String getExpectedPropertyValues(int elementIndex) {
        return expectedPropertyValuesArray.get(elementIndex);
    }

    public String getExpectedPayload(int elementIndex) {
        return expectedPayloadArray.get(elementIndex);
    }

    public void addInputXmlPayload(String inputXmlPayload) {
        this.inputXmlPayloadArray.add(inputXmlPayload);
    }

    public void addExpectedPropertyValues(String expectedPropertyValues) {
        this.expectedPropertyValuesArray.add(expectedPropertyValues);
    }

    public void addExpectedPayload(String expectedPayload) {
        this.expectedPayloadArray.add(expectedPayload);
    }

}
