package org.springboot.eventbus.util;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.drools.core.WorkingMemoryEntryPoint;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieScanner;
import org.kie.api.command.Command;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.management.KieManagementAgentMBean;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.agent.KnowledgeAgent;
import org.kie.internal.agent.KnowledgeAgentFactory;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author rdas
 */
@Slf4j
@Getter
@Setter
public class RuleHelper {

    private KieSession kieSession;
    private KieBase kieBase;
    private WorkingMemoryEntryPoint testEntry;

    protected Resource[] ruleFiles;

    private org.kie.api.KieServices kieServices;


    /**
     *
     *
     * @return
     */

    public KieSession getKieSession() {

       // kieServices.getResources().newFileSystemResource()
        return kieBase.newKieSession();
    }

    public StatelessKieSession getStatelessKieSession() {

        return kieBase.newStatelessKieSession();
    }


    public KieBase getKieBase() {
        return kieBase;
    }


    /**
     *
     * @param kieSession
     * @param fact
     */
    public void insertFactToSession(KieSession kieSession, Object fact) {
        kieSession.insert(fact);
        kieSession.fireAllRules();
    }

    /**
     *
     * @param globalVariableMap
     * @param facts
     * @param <T>
     */
    public <T> void evaluateFacts(Map<String, Object> globalVariableMap, List<T> facts) {
        kieSession = getKieSession();
        if (MapUtils.isEmpty(globalVariableMap) || CollectionUtils.isEmpty(facts)) {
            log.debug("Neither  Global variable nor fact lists can be null ");
        }
        for (Map.Entry<String, Object> eachEntry : globalVariableMap.entrySet()) {
            kieSession.setGlobal(eachEntry.getKey(), eachEntry.getValue());
        }
        for (T fact : facts) {
            kieSession.insert(fact);
        }
        kieSession.fireAllRules();
        kieSession.dispose();
    }


    public <T> void evaluateFact(Map<String, Object> globalVariableMap, Map<String, List<T>> facts) {
        kieSession = getKieSession();
        for (Map.Entry<String, Object> eachEntry : globalVariableMap.entrySet()) {
            kieSession.setGlobal(eachEntry.getKey(), eachEntry.getValue());
        }

        for (Map.Entry<String, List<T>> entry : facts.entrySet()) {
            log.debug("!!!Product Type :->" + entry.getKey());
            List<?> factList = entry.getValue();
            log.debug("!!!!!!Product Type :->" + factList.size());
            factList.forEach(fact -> kieSession.insert(fact));
        }

        log.debug("!!!!!!Total Fact Count In session :->" + kieSession.getFactCount());
        Rule rule = kieSession.getKieBase().getRule("rules.marketdata.eligiblebond", "Processing eligibleBond");
        log.debug("!!!!!!Rule Inside Session " + rule.getName());

        kieSession.fireAllRules();
    }


    public <T> void evaluateStateFulFact(Map<String, Object> globalVariableMap, List<List<T>> facts) {
        kieSession = getKieSession();
        for (Map.Entry eachEntry : globalVariableMap.entrySet()) {
            kieSession.setGlobal((String) eachEntry.getKey(), eachEntry.getValue());
        }


        for (List<T> factList : facts) {
            log.debug("!!!!!!Product Type :->" + factList.size());
            factList.forEach(fact -> kieSession.insert(fact));
        }

        log.debug("!!!!!!Total Fact Count In session :->" + kieSession.getFactCount());
        kieSession.fireAllRules();
        kieSession.dispose();
    }


    public <T> List<Command> createCommand(List<T> facts) {
        List<Command> commands = new ArrayList<>();
        facts.forEach(fact -> commands.add(CommandFactory.newInsert(fact)));
        return commands;
    }

    public <T> void evaluateFact(Map<String, Object> globalVariableMap, T fact) {
        StatelessKieSession statelessKieSession = getStatelessKieSession();

        for (Map.Entry eachEntry : globalVariableMap.entrySet()) {
            statelessKieSession.setGlobal((String) eachEntry.getKey(), eachEntry.getValue());
        }

        List<Command> commands = new ArrayList<>();
        commands.add(CommandFactory.newInsert(fact));
        FireAllRulesCommand fireAllRulesCommand = new FireAllRulesCommand();
        commands.add(fireAllRulesCommand);
        statelessKieSession.execute(CommandFactory.newBatchExecution(commands));
    }


    /**
     *
     * @param fact
     */
    public void insertFactToStatelessSession(Object fact) {
        kieSession.insert(fact);
    }

    /**
     *
     * @param argumentMap
     * @param facts
     * @param <T>
     * @return
     */
    public <T> List<T> evaluateFact(Map<String, Object> argumentMap, List<T> facts) {
        StatelessKieSession statelessKieSession = getStatelessKieSession();

        for (Map.Entry eachEntry : argumentMap.entrySet()) {
            statelessKieSession.setGlobal((String) eachEntry.getKey(), eachEntry.getValue());
        }

        List<Command> commands = new ArrayList<>();
        facts.forEach(fact -> commands.add(CommandFactory.newInsert(fact)));
        FireAllRulesCommand fireAllRulesCommand = new FireAllRulesCommand();
        commands.add(fireAllRulesCommand);
        statelessKieSession.execute(CommandFactory.newBatchExecution(commands));

        return facts;
    }


    /**
     *
     * @throws Exception
     */
    public void initKieBase() throws IOException {
      /*  KieHelper kieHelper = new KieHelper();
        if (ruleFiles != null) {
            for (Resource ruleFile : ruleFiles) {
                kieHelper.addResource(ResourceFactory.newUrlResource(ruleFile.getURL()), ResourceType.DRL);
                log.debug("Rule {} is being added", ruleFile.getFilename());
            }
        }
        Results results = kieHelper.verify();
        results.getMessages().stream().forEach(message -> log.debug(">> Message ( {} ), {}: ", message.getLevel(), message.getText()));
        if (results.hasMessages(Message.Level.ERROR)) {
            log.error("error in creating Knoledge Base {}", results.getMessages(Message.Level.ERROR));
        }

        KieBaseConfiguration kibaseConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        kibaseConfig.setOption(EventProcessingOption.STREAM);

        kieBase = kieHelper.build(kibaseConfig);
*/



        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();

        if (ruleFiles != null) {
            for (Resource ruleFile : ruleFiles) {
                //kieHelper.addResource(ResourceFactory.newUrlResource(ruleFile.getURL()), ResourceType.DRL);
                kfs.write(ResourceFactory.newUrlResource(ruleFile.getURL()));
                log.debug("Rule {} is being added", ruleFile.getFilename());

            }
        }
       // Arrays.asList(ruleFiles).forEach(k->kfs.write(ResourceFactory.newUrlResource(k.getURL()), ResourceType.DRL));
        KieContainer kieContainer =
                kieServices.newKieContainer(
                        kieServices.getRepository().getDefaultReleaseId());

        KieBaseConfiguration config =
                KieServices.Factory.get().newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);

         kieBase = kieContainer.newKieBase(config);

         KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
         kieScanner.start(10000);

        KnowledgeAgent knowledgeAgent = KnowledgeAgentFactory.newKnowledgeAgent("Test");
        knowledgeAgent.monitorResourceChangeEvents(true);


        printRuleBase(kieBase);

    }


    private void printRuleBase(KieBase kiebase) {
        Collection<KiePackage> packages = kiebase.getKiePackages();
        packages.forEach(pkg -> {
            log.debug(" Rules package name {} ::", pkg.getName());
            Collection<Rule> rules = pkg.getRules();
            rules.forEach(rule -> log.debug(" Rule Name :{}", rule.getName()));
        });
    }
}
