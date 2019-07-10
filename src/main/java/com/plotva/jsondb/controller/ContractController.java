package com.plotva.jsondb.controller;

import com.plotva.jsondb.domain.Contract;
import com.plotva.jsondb.service.ContractService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }
    @GetMapping("/list")
    public Iterable<Contract>list(){
        return contractService.list();
    }

}