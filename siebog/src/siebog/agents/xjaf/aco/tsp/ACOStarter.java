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

package siebog.agents.xjaf.aco.tsp;

import siebog.SiebogClient;
import siebog.core.Global;
import siebog.utils.ObjectFactory;
import siebog.xjaf.agentmanager.AgentInitArgs;
import siebog.xjaf.agentmanager.AgentManager;
import siebog.xjaf.core.AgentClass;

/**
 * Entry point for ACO example.
 * 
 * @author <a href="mailto:tntvteod@neobee.net">Teodor Najdan Trifunov</a>
 * @author <a href="mailto:milan.laketic@yahoo.com">Milan Laketic</a>
 */
public class ACOStarter {
<<<<<<< HEAD
	public static void main(String[] args) throws NamingException, IOException, ParserConfigurationException,
			SAXException {
		int nAnts = 0;
		String path = "";
=======
	public static void main(String[] args) {
>>>>>>> d352a0c2ec2f6c23dbf2c44e8bf0d85b1b7b6994
		if (args.length != 2) {
			System.out.println("I need 2 arguments: NumberOfAnts PathToMapFile");
			nAnts = 5;
			path = "C:/Users/Milos/Desktop/Fakultet/Agenti/GITHUB/siebog/siebog/src/siebog/agents/xjaf/aco/tsp/maps/eil51.tsp";
		} else {
			nAnts = Integer.parseInt(args[0].toString());
			path = args[1];
		}

		SiebogClient.connect("localhost");

		final AgentManager agm = ObjectFactory.getAgentManager();
		AgentClass mapClass = new AgentClass(Global.SIEBOG_MODULE, "Map");
<<<<<<< HEAD
		AgentInitArgs mapArgs = new AgentInitArgs("fileName=" + path);
=======
		AgentInitArgs mapArgs = new AgentInitArgs("fileName=" + args[1]);
>>>>>>> d352a0c2ec2f6c23dbf2c44e8bf0d85b1b7b6994
		agm.startAgent(mapClass, "Map", mapArgs);

		for (int i = 1; i <= nAnts; ++i) {
			AgentClass agClass = new AgentClass(Global.SIEBOG_MODULE, "Ant");
			agm.startAgent(agClass, "Ant" + i, null);
		}
	}
}
