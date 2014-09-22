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

package siebog.jasonee;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.mas2j.AgentParameters;
import jason.mas2j.MAS2JProject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import siebog.core.FileUtils;
import siebog.jasonee.control.ExecutionControl;
import siebog.jasonee.control.ExecutionControlAccessor;
import siebog.jasonee.control.ReasoningCycleMessage;
import siebog.jasonee.environment.ActionFeedbackMessage;
import siebog.jasonee.environment.Environment;
import siebog.jasonee.environment.EnvironmentAccessor;
import siebog.jasonee.environment.EnvironmentChangedMessage;
import siebog.xjaf.core.Agent;
import siebog.xjaf.core.XjafAgent;
import siebog.xjaf.fipa.ACLMessage;
import siebog.xjaf.managers.AgentInitArgs;

/**
 * 
 * @author <a href="mitrovic.dejan@gmail.com">Dejan Mitrovic</a>
 */
@Stateful
@Remote(Agent.class)
public class JasonEEAgent extends XjafAgent {
	private static final long serialVersionUID = 1L;
	private String execCtrlName;
	private transient ExecutionControl control;
	private String envName;
	private transient Environment env;
	private JasonEEAgArch arch;
	private boolean syncMode;
	private int asyncCycleNum;
	private boolean sleeping = true;

	@Override
	protected void onInit(AgentInitArgs args) {
		execCtrlName = args.get("execCtrlName");
		envName = args.get("envName");

		AgentParameters agp = null;
		try {
			File mas2jFile = FileUtils.createTempFile(args.get("mas2jSource"));
			agp = getAgentParams(args.get("agentName"), mas2jFile);
			agp.asSource = FileUtils.createTempFile(args.get("agentSource"));
		} catch (IOException ex) {
			throw new IllegalStateException("Cannot store agent source in a temporary file.", ex);
		}

		arch = new JasonEEAgArch();
		try {
			arch.setAgent(this);
			arch.init(args, agp);
		} catch (Exception ex) {
			final String msg = "Error while initializing agent architecture.";
			logger.log(Level.SEVERE, msg, ex);
			throw new IllegalStateException(msg, ex);
		}
		syncMode = arch.getTS().getSettings().isSync();
		wakeUp();
	}

	private AgentParameters getAgentParams(String agentName, File mas2jFile) {
		MAS2JProject project = JasonEEProject.loadFromFile(mas2jFile).getMas2j();
		AgentParameters agp = project.getAg(agentName);
		if (agp == null)
			throw new IllegalArgumentException("Agent " + agentName + " is not defined.");
		return agp;
	}

	@Override
	protected void onMessage(ACLMessage msg) {
		if (msg instanceof ReasoningCycleMessage)
			performCycle(((ReasoningCycleMessage) msg).cycleNum);
		else if (msg instanceof ActionFeedbackMessage)
			arch.onActionFeedback((ActionFeedbackMessage) msg);
		else if (msg instanceof EnvironmentChangedMessage)
			wakeUp();
		else
			arch.onMessage(msg);
	}

	@Override
	protected boolean onHeartbeat(String content) {
		if (!sleeping) {
			++asyncCycleNum;
			performCycle(asyncCycleNum);
			return true;
		}
		return false;
	}

	private void performCycle(int cycleNum) {
		arch.reasoningCycle();
		if (syncMode) {
			boolean isBreakpoint;
			try {
				isBreakpoint = arch.getTS().getC().getSelectedOption().getPlan().hasBreakpoint();
			} catch (NullPointerException ex) {
				isBreakpoint = false;
			}
			executionControl().agentCycleFinished(myAid, isBreakpoint, cycleNum);
		}
	}

	public void sleep() {
		if (!sleeping) {
			sleeping = true;
			if (syncMode)
				executionControl().removeAgent(myAid);
		}
	}

	public void wakeUp() {
		if (sleeping) {
			sleeping = false;
			if (syncMode)
				executionControl().addAgent(myAid);
			else
				registerHeartbeat();
		}
	}

	public ACLMessage ask(ACLMessage msg) {
		return null;
	}

	@Override
	public void onTerminate() {
		if (syncMode)
			executionControl().removeAgent(myAid);
	}

	public void scheduleAction(Structure action, String replyWith) {
		env().scheduleAction(myAid, action, replyWith);
	}

	public List<Literal> getPercepts() {
		return env().getPercepts(myAid);
	}

	private ExecutionControl executionControl() {
		if (control == null)
			control = ExecutionControlAccessor.getExecutionControl(execCtrlName);
		return control;
	}

	private Environment env() {
		if (env == null)
			env = EnvironmentAccessor.getEnvironment(envName);
		return env;
	}
}