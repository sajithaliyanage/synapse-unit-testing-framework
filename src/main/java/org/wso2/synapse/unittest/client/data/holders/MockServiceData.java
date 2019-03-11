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
import java.util.HashMap;
import java.util.Map;


/**
 * Mock service data holder class in unit test framework.
 */
public class MockServiceData extends Thread {

    private int mockServicesCount = 0;
    private Map<String, Integer> serviceNameMap = new HashMap<String, Integer>();
    private ArrayList<String> serviceHost = new ArrayList<String>();
    private ArrayList<Integer> servicePort = new ArrayList<Integer>();
    private ArrayList<String> servicePath = new ArrayList<String>();
    private ArrayList<String> serviceType = new ArrayList<String>();
    private ArrayList<String> servicePayload = new ArrayList<String>();
    private ArrayList<String> serviceResponse = new ArrayList<String>();

    public int getMockServicesCount() {
        return this.mockServicesCount;
    }

    public int getServiceNameIndex(String serviceName) {
        return serviceNameMap.get(serviceName);
    }

    public String getServiceHost(int elementIndex) {
        return serviceHost.get(elementIndex);
    }

    public int getServicePort(int elementIndex) {
        return servicePort.get(elementIndex);
    }

    public String getServicePath(int elementIndex) {
        return servicePath.get(elementIndex);
    }

    public String getServiceType(int elementIndex) {
        return serviceType.get(elementIndex);
    }

    public String getServicePayload(int elementIndex) {
        return servicePayload.get(elementIndex);
    }

    public String getServiceResponse(int elementIndex) {
        return serviceResponse.get(elementIndex);
    }

    public void addServiceName(String serviceName, int indexValue) {
        serviceNameMap.put(serviceName, indexValue);
    }

    public void addServiceHost(String serviceHost) {
        this.serviceHost.add(serviceHost);
    }

    public void addServicePort(int servicePort) {
        this.servicePort.add(servicePort);
    }

    public void addServicePath(String servicePath) {
        this.servicePath.add(servicePath);
    }

    public void addServiceType(String serviceType) {
        this.serviceType.add(serviceType);
    }

    public void addServicePayload(String servicePayload) {
        this.servicePayload.add(servicePayload);
    }

    public void addServiceResponse(String serviceResponse) {
        this.serviceResponse.add(serviceResponse);
    }

    public void setMockServicesCount(int mockServiceCount) {
        this.mockServicesCount = mockServiceCount;
    }

    public boolean isServiceNameExist(String serviceName) {
        boolean isExist = false;

        if (serviceNameMap.containsKey(serviceName)) {
            isExist = true;
        }

        return isExist;
    }
}
