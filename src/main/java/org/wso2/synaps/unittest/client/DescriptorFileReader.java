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

package org.wso2.synaps.unittest.client;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Descriptor file read class in unit test framework
 */
public class DescriptorFileReader {

    private static Logger log = Logger.getLogger(DescriptorFileReader.class.getName());

    /**
     * Read artifact data from the descriptor file.
     * Append artifact data into the data holder object
     *
     * @param descriptorFilePath file path tom the descriptor file
     * @return dataHolder object with artifact data
     */
    String readArtifactData(String descriptorFilePath){

        BasicConfigurator.configure();

        String inputXmlPayload;
        String artifactType;
        String artifact;
        String fileName;
        String expectedPropertyValues;
        String expectedPayload;

        try{
            String fileString = FileUtils.readFileToString(new File(descriptorFilePath));

            log.info(fileString);

        }catch (FileNotFoundException e){
            log.error("Descriptor file not found in given path");
        }catch (IOException e){
            log.error(e);
        }
        return "";
    }

}