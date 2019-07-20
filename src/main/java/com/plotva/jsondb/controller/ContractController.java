package com.plotva.jsondb.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plotva.jsondb.domain.Contract;
import com.plotva.jsondb.service.ContractService;
import okhttp3.HttpUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody HttpUrl url) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OkHtmlController work = new OkHtmlController();
        TypeReference<List<Contract>> typeReference = new TypeReference<>() {
        };
        String response = work.run(url);
        JsonNode rootNode = mapper.readTree(response);
        JsonNode dataNode = rootNode.path("data");
        List<Contract> contracts = mapper.readValue(dataNode.toString(),typeReference);
        contractService.save(contracts);
        return ResponseEntity.ok().body(contracts);
    }

    @GetMapping("/list")
    public Iterable<Contract>list(){
        return contractService.list();
    }

}