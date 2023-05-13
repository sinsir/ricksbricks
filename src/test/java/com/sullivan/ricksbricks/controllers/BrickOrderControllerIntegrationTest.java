package com.sullivan.ricksbricks.controllers;

import com.sullivan.ricksbricks.repository.BrickOrdersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sullivan.ricksbricks.util.TestConstants.DELETE_TEST_DATA;
import static com.sullivan.ricksbricks.util.TestConstants.INSERT_TEST_DATA_SCRIPT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BrickOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BrickOrdersRepository brickOrdersRepository;

    @Test
    public void testAddBrickOrderReturns200() throws Exception {
        int orderReference1 = 1;
        int brickQuantityOrder1 = 34;

        int orderReference2 = 2;
        int brickQuantityOrder2 = 88;

        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/order/bricks/%s", brickQuantityOrder1)))
                .andExpect(status().is(OK.value()))
                .andExpect(jsonPath("$.orderReference", is(orderReference1)))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/order/bricks/%s", brickQuantityOrder2)))
                .andExpect(status().is(OK.value()))
                .andExpect(jsonPath("$.orderReference", is((orderReference2))))
                .andReturn();

        assertThat(brickOrdersRepository.findById(orderReference1).get().getNumberOfBricksOrdered()).isEqualTo(brickQuantityOrder1);
        assertThat(brickOrdersRepository.findById(orderReference2).get().getNumberOfBricksOrdered()).isEqualTo(brickQuantityOrder2);
    }

    @ParameterizedTest
    @Sql({DELETE_TEST_DATA, INSERT_TEST_DATA_SCRIPT})
    @CsvSource({"1,345", "2,999", "3,2", "4,3465"})
    public void testGetBrickOrderReturnsOrderDetails(String orderReference, String brickQuantity) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/order/bricks/%s", orderReference)))
                .andExpect(status().is(OK.value()))
                .andExpect(jsonPath("$.orderReference", is(Integer.valueOf(orderReference))))
                .andExpect(jsonPath("$.numberOfBricksOrdered", is(Integer.valueOf(brickQuantity))))
                .andReturn();
    }

    @Test
    public void testGetBrickOrderReturnsEmptyBrickOrderResponseWhenOrderReferenceNotFound() throws Exception {

        int nonExistentOrderReference = 8765;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/order/bricks/%s", nonExistentOrderReference)))
                .andExpect(status().is(NOT_FOUND.value()))
                .andReturn();
    }

    @Test
    @Sql({DELETE_TEST_DATA, INSERT_TEST_DATA_SCRIPT})
    public void testGetAllBrickOrders() throws Exception {

        int pageNumber = 1;
        int expectedOrderNumber1 = 1;
        int expectedOrderNumber2 = 2;

        int expectedNumBricks1 = 345;
        int expectedNumBricks2 = 999;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/order/bricks?pageNumber=%s", pageNumber)))
                .andExpect(status().is(OK.value()))
                .andExpect((jsonPath("$.orders").isArray()))
                .andExpect(jsonPath("$.orders[*].orderReference", containsInAnyOrder(expectedOrderNumber1, expectedOrderNumber2)))
                .andExpect(jsonPath("$.orders[*].numberOfBricksOrdered", containsInAnyOrder(expectedNumBricks1, expectedNumBricks2)))
                .andReturn();
    }
}
