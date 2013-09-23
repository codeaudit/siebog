package org.xjaf2x.server.agents.jason;

import jason.asSyntax.Literal;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import org.jboss.ejb3.annotation.Clustered;
import org.xjaf2x.server.agentmanager.agent.Agent;
import org.xjaf2x.server.agentmanager.agent.jason.JasonAgentI;
import org.xjaf2x.server.messagemanager.fipaacl.ACLMessage;

@Stateful
@Remote(JasonAgentI.class)
@Clustered
public class HelloJason extends Agent implements JasonAgentI
{
	private static final long serialVersionUID = 1L;

	@Override
	public void init(Map<String, Object> args) throws Exception
	{
	}
	
	@Override
	public void onMessage(ACLMessage message)
	{
	}
	
	@Override
	@Remove
	public void terminate()
	{
	}

	@Override
	public List<Literal> perceive()
	{
		System.out.println("perceive(): " + aid.getRuntimeName() + " " + System.getProperty("jboss.node.name"));
		return null;
	}

	@Override
	public boolean act(String functor)
	{
		System.out.println("act(): " + aid);
		return true;
	}
}