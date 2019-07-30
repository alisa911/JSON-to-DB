package com.plotva.jsondb;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JsondbApplicationTests {


    @Autowired
    private MockMvc mvc;


    @Test
    public void createContractsFromEmptyUrl() throws Exception {

        mvc.perform(post("/")
                .param("url", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().attribute("errorMessage", "Error!"))
                .andExpect(view().name("index"))
                .andExpect(status().is(200));


    }

    @Test
    public void createContractsFromWrongUrl() throws Exception {

        mvc.perform(post("/")
                .param("url", "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/23567e24f52746ef92c470be6059d193")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().attribute("errorMessage", "Error!"))
                .andExpect(view().name("index"))
                .andExpect(status().is(200));


    }

    @Test
    public void createContractsFromUrlWithAllExistingContracts() throws Exception {

        mvc.perform(post("/")
                .param("url", "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/23567e24f52746ef92c470be6059d193/documents")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().attribute("emptyMessage", "All contracts exist!"))
                .andExpect(view().name("index"))
                .andExpect(status().is(200));


    }

    @Test
    public void createContractsFromNewUrl() throws Exception {
        mvc.perform(post("/")
                .param("url", "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/4805f381d48948b1b34d6ea2daa029a3/documents ")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().attribute("successMessage", "Success!"))
                .andExpect(view().name("index"))
                .andExpect(status().is(200));


    }

}
