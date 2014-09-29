/**
 * Licensed to the Apache Software Foundation (ASF) under one 
 * or more contributor license agreements. See the NOTICE file 
 * distributed with this work for additional information regarding 
 * copyright ownership. The ASF licenses this file to you under 
 * the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may 
 * obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an 
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. 
 * 
 * See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package siebog;

import java.util.Arrays;
import org.jboss.ejb.client.DeploymentNodeSelector;

/**
 * Round-robin deployment node selector for distributing agents accross cluster nodes.
 * 
 * @author <a href="mitrovic.dejan@gmail.com">Dejan Mitrovic</a>
 */
public class RRDeploymentNodeSelector implements DeploymentNodeSelector {
	private int index;

	@Override
	public String selectNode(String[] eligibleNodes, String appName, String moduleName, String distinctName) {
		System.out.println("--------[" + Arrays.toString(eligibleNodes) + "]");
		for (String str : eligibleNodes)
			if (str.contains("slave"))
				return str;
		return eligibleNodes[0];
	}

}
