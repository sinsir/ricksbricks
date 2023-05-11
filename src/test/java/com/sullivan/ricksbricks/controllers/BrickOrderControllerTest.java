package com.sullivan.ricksbricks.controllers;

import com.sullivan.ricksbricks.service.BrickOrderingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BrickOrderControllerTest {

    @Mock
    private BrickOrderingService brickOrderingService;

    @InjectMocks
    private BrickOrderController brickOrderController;

    @BeforeEach
    public void setup() {
        brickOrderController = new BrickOrderController(brickOrderingService);
    }

    @Test
    public void addBrickOrder() {
        int orderReference = 123;
        int numberOfBricks = 732;
        given(brickOrderingService.addBrickOrder(numberOfBricks)).willReturn(orderReference);

        Map<String, Integer> controllerOrderReference = brickOrderController.addBrickOrder(numberOfBricks);

        assertThat(controllerOrderReference.get("orderReference")).isEqualTo(orderReference);
    }
}
