package com.cisco.service;

import com.cisco.model.Client;
import com.cisco.model.Loan;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.springframework.stereotype.Service;
import spinjar.com.fasterxml.jackson.core.JsonProcessingException;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Service
public class LoanService {

    private static String LOAN_PROCESS="Loan-process-1";

    public Loan requestLoan(Client client) {
        Map<String,Object> results = this.runBusinessProcess(client);
        Boolean isLoanApproved=(Boolean) results.get("isLoanApproved");
        return new Loan(client.getName(), 2000.0D,isLoanApproved);
    }

    private Map<String,Object> runBusinessProcess(Client client){
        // Define process variables if needed
        Map<String,Object> results = new HashMap<>();
        Map<String, Object> variables = new HashMap<>();
        ObjectMapper om = new ObjectMapper();
        String clientJson= null;
        try {
            clientJson = om.writeValueAsString(client);
            log.info(clientJson);
            variables.put("client", client);
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            // Get the RuntimeService from the process engine
            RuntimeService runtimeService = processEngine.getRuntimeService();
            // Start the process instance by process definition key
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(LOAN_PROCESS, variables);
            // Output the process instance ID
            System.out.println("Started process instance with ID: " + processInstance.getId());
            HistoricVariableInstance loanApprovedVariable = processEngine.getHistoryService().createHistoricVariableInstanceQuery()
                    .executionIdIn(processInstance.getId()).variableName("isLoanApproved").singleResult();
            Boolean isLoanApproved = null;
            if (loanApprovedVariable != null) {
                isLoanApproved = (Boolean) loanApprovedVariable.getValue();
                System.out.println("Loan Approved: " + isLoanApproved);
            } else {
                System.out.println("No loanApproved variable found.");
            }
            results.put("isLoanApproved",isLoanApproved);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return results;
    }
}
