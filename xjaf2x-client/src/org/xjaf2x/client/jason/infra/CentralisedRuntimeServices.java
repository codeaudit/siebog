package org.xjaf2x.client.jason.infra;

import jason.JasonException;
import jason.architecture.AgArch;
import jason.asSemantics.Agent;
import jason.mas2j.AgentParameters;
import jason.mas2j.ClassParameters;
import jason.runtime.RuntimeServicesInfraTier;
import jason.runtime.Settings;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/** This class implements the centralised version of the runtime services. */
public class CentralisedRuntimeServices implements RuntimeServicesInfraTier {

    private static Logger logger = Logger.getLogger(CentralisedRuntimeServices.class.getName());
    
    private RunCentralisedMAS masRunner;
    
    public CentralisedRuntimeServices(RunCentralisedMAS masRunner) {
        this.masRunner = masRunner;
    }
    
    protected CentralisedAgArch newAgInstance() {
        return new CentralisedAgArch();
    }
    
    public String createAgent(String agName, String agSource, String agClass, List<String> archClasses, ClassParameters bbPars, Settings stts) throws Exception {
        if (logger.isLoggable(Level.FINE)) 
            logger.fine("Creating centralised agent " + agName + " from source " + agSource + " (agClass=" + agClass + ", archClass=" + archClasses + ", settings=" + stts);

        AgentParameters ap = new AgentParameters();
        ap.setAgClass(agClass);
        ap.addArchClass(archClasses);
        ap.setBB(bbPars);
        
        if (stts == null) 
            stts = new Settings();
        
        while (masRunner.getAg(agName) != null)
            agName += "_a";

        CentralisedAgArch agArch = newAgInstance();
        agArch.setAgName(agName);
        agArch.createArchs(ap.getAgArchClasses(), ap.agClass.getClassName(), ap.getBBClass(), agSource, stts, masRunner);
        agArch.setEnvInfraTier(masRunner.getEnvironmentInfraTier());
        agArch.setControlInfraTier(masRunner.getControllerInfraTier());
        masRunner.addAg(agArch);
        
        logger.fine("Agent " + agName + " created!");
        return agName;
    }
    
    public void startAgent(String agName) {
    }
    
    public AgArch clone(Agent source, List<String> archClasses, String agName) throws JasonException {
        // create a new infra arch
        CentralisedAgArch agArch = newAgInstance();
        agArch.setAgName(agName);
        agArch.setEnvInfraTier(masRunner.getEnvironmentInfraTier());
        agArch.setControlInfraTier(masRunner.getControllerInfraTier());
        masRunner.addAg(agArch);
        
        agArch.createArchs(archClasses, source, masRunner);

        startAgent(agName);
        return agArch.getUserAgArch();
    }

    public Set<String> getAgentsNames() {
        return masRunner.getAgs().keySet();
    }
    
    public int getAgentsQty() {
        return masRunner.getAgs().keySet().size();
    }

    public boolean killAgent(String agName, String byAg) {
        logger.fine("Killing centralised agent " + agName);
        CentralisedAgArch ag = masRunner.getAg(agName);
        if (ag != null && ag.getTS().getAg().killAcc(byAg)) {
            ag.stopAg();
            return true;
        }
        return false;
    }

    public void stopMAS() throws Exception {
        masRunner.finish();
    }
}