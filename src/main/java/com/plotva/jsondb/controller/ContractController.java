package com.plotva.jsondb.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plotva.jsondb.domain.Contract;
import com.plotva.jsondb.form.UrlForm;
import com.plotva.jsondb.repository.ContractRepository;
import com.plotva.jsondb.service.ContractService;

import com.plotva.jsondb.service.OkHtml;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.util.function.Predicate;


@Controller
public class ContractController {
    private ContractService contractService;

    private ContractRepository contractRepository;

    private static List<Contract> contracts = new ArrayList<>();

    @Value("Success!")
    private String successMessage = "Success!";
    @Value("Error!")
    private String errorMessage = "Error!";
    @Value("All contracts exist!")
    private String emptyMessage = "All contracts exist!";

    public ContractController(ContractService contractService, ContractRepository contractRepository) {
        this.contractService = contractService;
        this.contractRepository = contractRepository;
    }

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
        if (url != null && url.contains("https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/") && url.contains("/documents")) {
            ObjectMapper mapper = new ObjectMapper();
            OkHtml work = new OkHtml();
            TypeReference<List<Contract>> typeReference = new TypeReference<List<Contract>>() {
            };
            String response = work.run(url);
            JsonNode rootNode = mapper.readTree(response);
            JsonNode dataNode = rootNode.path("data");
            contracts = mapper.readValue(dataNode.toString(), typeReference);


            Predicate<Contract> contractPredicate = c -> contractRepository.findFirstById(c.getId()) != null;

            contracts.removeIf(contractPredicate);

            if (contracts.isEmpty()) {
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