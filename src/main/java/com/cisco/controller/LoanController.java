package com.cisco.controller;

import com.cisco.model.Client;
import com.cisco.model.Loan;
import com.cisco.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/")
    public Loan requestLoan(@RequestBody(required = true) Client client){
        return loanService.requestLoan(client);
    }
}
