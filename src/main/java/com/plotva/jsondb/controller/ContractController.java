package com.plotva.jsondb.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plotva.jsondb.domain.Contract;
import com.plotva.jsondb.form.UrlForm;
import com.plotva.jsondb.service.ContractService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        UrlForm urlForm = new UrlForm();
        model.addAttribute("urlForm", urlForm);

        return "index";
    }


    @PostMapping("/create")
    public String create(Model model, @ModelAttribute("urlForm") UrlForm urlForm) throws Exception {
        String url = urlForm.getUrl();
        ObjectMapper mapper = new ObjectMapper();
        OkHtmlController work = new OkHtmlController();
        TypeReference<List<Contract>> typeReference = new TypeReference<>() {
        };
        String response = work.run(url);
        JsonNode rootNode = mapper.readTree(response);
        JsonNode dataNode = rootNode.path("data");
        List<Contract> contracts = mapper.readValue(dataNode.toString(), typeReference);
        contractService.save(contracts);
        return "index";
    }

    @GetMapping("/list")
    public Iterable<Contract> list() {
        return contractService.list();
    }

}