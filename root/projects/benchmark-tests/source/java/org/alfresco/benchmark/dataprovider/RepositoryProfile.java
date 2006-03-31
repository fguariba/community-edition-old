/*
 * Copyright (C) 2005 Alfresco, Inc.
 *
 * Licensed under the Mozilla Public License version 1.1 
 * with a permitted attribution clause. You may obtain a
 * copy of the License at
 *
 *   http://www.alfresco.org/legal/license.txt
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package org.alfresco.benchmark.dataprovider;

import com.sun.japex.TestCase;

public class RepositoryProfile
{
    private static final String PARAM_PREFIX = "alfrsco.repositoryProfile.";
    private static final String PARAM_FOLDER_DEPTH_AVERAGE = PARAM_PREFIX + "folderDepth.average";
    private static final String PARAM_FOLDER_DEPTH_VARIATION = PARAM_PREFIX + "folderDepth.variation";
    private static final String PARAM_DOCUMENTS_IN_FOLDER_COUNT_AVERAGE = PARAM_PREFIX + "documentsInFolderCount.average";
    private static final String PARAM_DOCUMENTS_IN_FOLDER_COUNT_VARIATION = PARAM_PREFIX + "documentsInFolderCount.variation";
    private static final String PARAM_SUB_FOLDERS_COUNT_AVERAGE = PARAM_PREFIX + "subFoldersCount.average";
    private static final String PARAM_SUB_FOLDERS_COUNT_VARIATION = PARAM_PREFIX + "subFoldersCount.variation";
    
    private int folderDepthAverage = 3;
    private int folderDepthVariation = 1;
    
    private int documentsInFolderCountAverage = 5;
    private int documentsInFolderCountVariation = 1;
    
    private int subFoldersCountAverage = 3;
    private int subFoldersCountVariation = 1;
    
    public static RepositoryProfile createRespoitoryProfile(TestCase testCase)
    {
        RepositoryProfile repositoryProfile = new RepositoryProfile();
        
        // Check the test case for profile parameters
        if (testCase.hasParam(PARAM_FOLDER_DEPTH_AVERAGE) == true)
        {
            repositoryProfile.setFolderDepthAverage(testCase.getIntParam(PARAM_FOLDER_DEPTH_AVERAGE));
        }
        if (testCase.hasParam(PARAM_FOLDER_DEPTH_VARIATION) == true)
        {
            repositoryProfile.setFolderDepthVariation(testCase.getIntParam(PARAM_FOLDER_DEPTH_VARIATION));
        }
        if (testCase.hasParam(PARAM_DOCUMENTS_IN_FOLDER_COUNT_AVERAGE) == true)
        {
            repositoryProfile.setDocumentsInFolderCountAverage(testCase.getIntParam(PARAM_DOCUMENTS_IN_FOLDER_COUNT_AVERAGE));
        }
        if (testCase.hasParam(PARAM_DOCUMENTS_IN_FOLDER_COUNT_VARIATION) == true)
        {
            repositoryProfile.setDocumentsInFolderCountVariation(testCase.getIntParam(PARAM_DOCUMENTS_IN_FOLDER_COUNT_VARIATION));
        }
        if (testCase.hasParam(PARAM_SUB_FOLDERS_COUNT_AVERAGE) == true)
        {
            repositoryProfile.setSubFoldersCountAverage(testCase.getIntParam(PARAM_SUB_FOLDERS_COUNT_AVERAGE));
        }
        if (testCase.hasParam(PARAM_SUB_FOLDERS_COUNT_VARIATION) == true)
        {
            repositoryProfile.setSubFoldersCountVariation(testCase.getIntParam(PARAM_SUB_FOLDERS_COUNT_VARIATION));
        }
        
        return repositoryProfile;
    }
    
    public int getFolderDepthAverage()
    {
        return folderDepthAverage;
    }
    
    public void setFolderDepthAverage(int averageFolderDepth)
    {
        this.folderDepthAverage = averageFolderDepth;
    }
    
    public int getFolderDepthVariation()
    {
        return folderDepthVariation;
    }
    
    public void setFolderDepthVariation(int folderDepthVariation)
    {
        this.folderDepthVariation = folderDepthVariation;
    }
    
    public int getDocumentsInFolderCountAverage()
    {
        return documentsInFolderCountAverage;
    }
    
    public void setDocumentsInFolderCountAverage(int averageFolderSize)
    {
        this.documentsInFolderCountAverage = averageFolderSize;
    }
    
    public int getDocumentsInFolderCountVariation()
    {
        return documentsInFolderCountVariation;
    }
    
    public void setDocumentsInFolderCountVariation(int folderSizeVariation)
    {
        this.documentsInFolderCountVariation = folderSizeVariation;
    }
    
    public int getSubFoldersCountAverage()
    {
        return subFoldersCountAverage;
    }
    
    public void setSubFoldersCountAverage(int averageNumberOfSubFolders)
    {
        this.subFoldersCountAverage = averageNumberOfSubFolders;
    }
    
    public int getSubFoldersCountVariation()
    {
        return subFoldersCountVariation;
    }
    
    public void setSubFoldersCountVariation(int numberOfSubFoldersVariation)
    {
        this.subFoldersCountVariation = numberOfSubFoldersVariation;
    }
}