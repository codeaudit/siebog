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

package siebog.interaction.contractnet;

import java.io.Serializable;

import siebog.agents.AID;

/**
 * @author <a href="jovanai.191@gmail.com">Jovana Ivkovic<a>
 */
public class CallForProposal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long replyBy;
	private String content;
	private Serializable contentObj;
	private AID initiator;
	
	
	public CallForProposal(AID myAid,String content) {
		super();
		this.initiator=myAid;
		this.content = content;
	}
	public long getReplyBy() {
		return replyBy;
	}
	public void setReplyBy(long replyBy) {
		this.replyBy = replyBy;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public AID getInitiator() {
		return initiator;
	}
	public void setInitiator(AID initiator) {
		this.initiator = initiator;
	}
	public Serializable getContentObj() {
		return contentObj;
	}
	public void setContentObj(Serializable contentObj) {
		this.contentObj = contentObj;
	}
	
	
	
}
