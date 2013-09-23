package org.xjaf2x.server.messagemanager.fipaacl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.xjaf2x.server.agentmanager.agent.AID;

/**
 * Represents a FIPA ACL message. Refer to <a
 * href="http://www.fipa.org/specs/fipa00061/SC00061G.pdf">FIPA ACL Message
 * Structure Specification</a> for more details.
 * 
 * @author <a href="tntvteod@neobee.net">Teodor-Najdan Trifunov</a>
 * @author <a href="mitrovic.dejan@gmail.com">Dejan Mitrovic</a>
 */
public class ACLMessage implements Serializable
{
	private static final long serialVersionUID = 1L;

	// Denotes the type of the communicative act of the ACL message.
	private Performative performative;

	/* Participants in Communication */
	
	// Denotes the identity of the sender of the message.
	private AID sender;
	// Denotes the identity of the intended recipients of the message.
	private Set<AID> receivers;
	// This parameter indicates that subsequent messages in this conversation 
	// thread are to be directed to the agent named in the reply-to parameter, 
	// instead of to the agent named in the	sender parameter.
	private AID replyTo;

	/* Description of Content */

	// Denotes the content of the message; equivalently denotes the 
	// object of the action.
	private Serializable content;
	// Denotes the language in which the content parameter is expressed.
	private String language;
	// Denotes the specific encoding of the content language expression.
	private String encoding;
	// Denotes the ontology(s) used to give a meaning to the symbols in 
	// the content expression.
	private String ontology;
	
	/* Control of Conversation */

	// Denotes the interaction protocol that the sending agent is 
	// employing with this ACL message. 
	private String protocol;
	// Introduces an expression (a conversation identifier) which is used 
	// to identify the ongoing sequence of communicative acts that 
	// together form a conversation. 
	private String conversationId;
	// Introduces an expression that will be used by the responding 
	// agent to identify this message.
	private String replyWith;
	// Denotes an expression that references an earlier action to which 
	// this message is a reply. 
	private String inReplyTo;
	// Denotes a time and/or date expression which indicates the latest 
	// time by which the sending agent would like to receive a reply.
	private long replyBy;

	public ACLMessage()
	{
		this(Performative.NOT_UNDERSTOOD);
	}

	public ACLMessage(Performative performative)
	{
		this.performative = performative;
		receivers = new HashSet<AID>();
	}

	public Performative getPerformative()
	{
		return performative;
	}

	public void setPerformative(Performative performative)
	{
		this.performative = performative;
	}
	
	public ACLMessage makeReply()
	{
		return makeReply(performative);
	}
	
	public ACLMessage makeReply(Performative performative)
	{
		ACLMessage reply = new ACLMessage(performative);
		// receiver
		reply.addReceiver(replyTo != null ? replyTo : sender);
		// description of content
		reply.setLanguage(language);
		reply.setOntology(ontology);
		reply.setEncoding(encoding);
		// control of conversation
		reply.setProtocol(protocol);
		reply.setConversationId(conversationId);
		reply.setInReplyTo(replyWith);
		return reply;
	}

	public AID getSender()
	{
		return sender;
	}

	public void setSender(AID sender)
	{
		this.sender = sender;
	}

	public Serializable getContent()
	{
		return content;
	}

	public void setContent(Serializable content)
	{
		this.content = content;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	public String getOntology()
	{
		return ontology;
	}

	public void setOntology(String ontology)
	{
		this.ontology = ontology;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
	}

	public String getConversationId()
	{
		return conversationId;
	}

	public void setConversationId(String conversationId)
	{
		this.conversationId = conversationId;
	}

	public String getReplyWith()
	{
		return replyWith;
	}

	public void setReplyWith(String replyWith)
	{
		this.replyWith = replyWith;
	}

	public String getInReplyTo()
	{
		return inReplyTo;
	}

	public void setInReplyTo(String inReplyTo)
	{
		this.inReplyTo = inReplyTo;
	}

	public long getReplyBy()
	{
		return replyBy;
	}

	public void setReplyBy(long replyBy)
	{
		this.replyBy = replyBy;
	}

	public AID getReplyTo()
	{
		return replyTo;
	}

	public void setReplyTo(AID replyTo)
	{
		this.replyTo = replyTo;
	}

	public Set<AID> getReceivers()
	{
		return receivers;
	}

	public void setReceivers(Set<AID> receivers)
	{
		this.receivers = receivers;
	}
	
	public void addReceiver(AID receiver)
	{
		receivers.add(receiver);
	}
	
	public void removeReceiver(AID receiver)
	{
		receivers.remove(receiver);
	}
	
	public void clearReceivers()
	{
		receivers.clear();
	}
}