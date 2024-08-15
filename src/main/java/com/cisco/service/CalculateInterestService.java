package com.cisco.service;

import com.cisco.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CalculateInterestService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Boolean isVIP = (Boolean) execution.getVariable("isVIP");
        Client client = (Client) execution.getVariable("client");
        if(!isVIP) {
            log.info("calculating interest of the loan for " + client.getName()+ " who  is NOT  VIP");
        }
    }

}