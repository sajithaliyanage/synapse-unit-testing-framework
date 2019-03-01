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

public class ArtifactData {

    private String artifactType;
    private String artifact;
    private int testCaseCount;
    private String artifactName;

    public String getArtifactType() {
        return artifactType;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public String getArtifact() {
        return artifact;
    }

    public int getTestCaseCount() {
        return testCaseCount;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public void setTestCaseCount(int testCaseCount) {
        this.testCaseCount =  testCaseCount;
    }
}
