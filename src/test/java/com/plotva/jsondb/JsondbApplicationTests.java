package com.plotva.jsondb;


import com.plotva.jsondb.controller.ContractController;
import com.plotva.jsondb.repository.ContractRepository;
import com.plotva.jsondb.service.ContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ContractController.class)
public class JsondbApplicationTests {

    @MockBean
    ContractService service;
    @MockBean
    ContractRepository repository;

    @Autowired
    private MockMvc mvc;


    @Test
    public void createContractsFromEmptyUrl() throws Exception {

        mvc.perform(post("/")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().attribute("errorMessage", "Error!"))
                .andExpect(view().name("index"))
                .andExpect(status().is(200));


    }

    @Test
    public void createContractsFromWrongUrl() throws Exception {

        mvc.perform(post("/")
                .content("https://lb-api-sandbox.prozorro.")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().attribute("errorMessage", "Error!"))
                .andExpect(view().name("index"))
                .andExpect(status().is(200));


    }

    @Test
    public void createContractsFromUrlWithAllExistingContracts() throws Exception {

        mvc.perform(post("/")
                .content("https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/23567e24f52746ef92c470be6059d193/documents")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().attribute("emptyMessage", "All contracts exist!"))
                .andExpect(view().name("index"))
                .andExpect(status().is(200));


    }

    @Test
    public void createContractsFromNewUrl() throws Exception {
        mvc.perform(post("/")
                .content("https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/47fa8764e1b74f4db58f84c9db460566/documents ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().attribute("successMessage", "Success!"))
                .andExpect(view().name("index"))
                .andExpect(status().is(200));


    }

}
