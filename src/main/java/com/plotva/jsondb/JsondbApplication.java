package com.plotva.jsondb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plotva.jsondb.controller.OkHtmlController;
import com.plotva.jsondb.domain.Contract;
import com.plotva.jsondb.service.ContractService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootApplication
public class JsondbApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsondbApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ContractService contractService){
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            OkHtmlController work = new OkHtmlController();
            TypeReference<List<Contract>> typeReference = new TypeReference<>() {
            };
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите URL");
            String url = reader.readLine();

            String response = work.run(url);
            JsonNode rootNode = mapper.readTree(response);
            JsonNode dataNode = rootNode.path("data");
            List<Contract> contracts = mapper.readValue(dataNode.toString(),typeReference);
            contractService.save(contracts);

        };
    }

}
