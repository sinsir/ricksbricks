package com.sullivan.ricksbricks.controllers;

import com.sullivan.ricksbricks.repository.BrickOrdersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BrickOrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BrickOrdersRepository brickOrdersRepository;

    @Test
    public void addBrickOrder() throws Exception {
        int orderReference1 = 1;
        int brickQuantityOrder1 = 34;

        int orderReference2 = 2;
        int brickQuantityOrder2 = 88;

        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/order/bricks/%s", brickQuantityOrder1)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.orderReference", is(orderReference1)))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/order/bricks/%s", brickQuantityOrder2)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.orderReference", is((orderReference2))))
                .andReturn();

        assertThat(brickOrdersRepository.findById(orderReference1).get().getNumberOfBricksOrdered()).isEqualTo(brickQuantityOrder1);
        assertThat(brickOrdersRepository.findById(orderReference2).get().getNumberOfBricksOrdered()).isEqualTo(brickQuantityOrder2);
    }
}
