package com.plotva.jsondb.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plotva.jsondb.domain.Contract;
import com.plotva.jsondb.form.UrlForm;
import com.plotva.jsondb.repository.ContractRepository;
import com.plotva.jsondb.service.ContractService;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContractController {

    private ContractService contractService;

    private ContractRepository contractRepository;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    private static List<Contract> contracts = new ArrayList<>();
    private static List<Contract> existContracts = new ArrayList<>();
    @Value("Success!")
    private String successMessage = "Success!";
    @Value("Error!")
    private String errorMessage = "Error!";
    @Value("All contracts exist!")
    private String emptyMessage = "All contracts exist!";


    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String addUrl(Model model) {

        UrlForm urlForm = new UrlForm();
        model.addAttribute("urlForm", urlForm);

        return "index";
    }

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String view(Model model) {

        model.addAttribute("contracts", contracts);

        return "view";
    }


    @PostMapping("/")
    public String create(Model model, @ModelAttribute("urlForm") UrlForm urlForm) throws Exception {
        String url = urlForm.getUrl();
        if (url.contains("https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/") && url.contains("/documents")) {
            ObjectMapper mapper = new ObjectMapper();
            OkHtmlController work = new OkHtmlController();
            TypeReference<List<Contract>> typeReference = new TypeReference<List<Contract>>() {
            };
            String response = work.run(url);
            JsonNode rootNode = mapper.readTree(response);
            JsonNode dataNode = rootNode.path("data");
            contracts = mapper.readValue(dataNode.toString(), typeReference);

            contracts.forEach(k -> {
                if (contractRepository.findContractById(k.getId()) != null)
                    contractRepository.deleteContractById(k.getId());
                existContracts.add(k);
            });
            if (contracts.size() == 0) {

                model.addAttribute("emptyMessage", emptyMessage);
                return "index";
            } else {
                view(model);
                contractService.save(contracts);
                model.addAttribute("successMessage", successMessage);
                return "index";

            }
        } else {
            model.addAttribute("errorMessage", errorMessage);
            return "index";
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        contracts = (List<Contract>) contractService.list();
        view(model);
        return "view";
    }

}