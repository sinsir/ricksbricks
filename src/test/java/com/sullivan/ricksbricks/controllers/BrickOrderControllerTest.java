package com.sullivan.ricksbricks.controllers;

import com.sullivan.ricksbricks.error.BrickOrderNotFoundException;
import com.sullivan.ricksbricks.model.BrickOrderResponse;
import com.sullivan.ricksbricks.service.BrickOrderingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BrickOrderControllerTest {

    private static final int ORDER_REFERENCE = 123;
    private static final int NUMBER_OF_BRICKS = 732;

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
        given(brickOrderingService.addBrickOrder(NUMBER_OF_BRICKS)).willReturn(ORDER_REFERENCE);

        Map<String, Integer> controllerOrderReference = brickOrderController.addBrickOrder(NUMBER_OF_BRICKS);

        assertThat(controllerOrderReference.get("orderReference")).isEqualTo(ORDER_REFERENCE);
    }

    @Test
    public void getBrickOrder() {
        given(brickOrderingService.getBrickOrder(ORDER_REFERENCE)).willReturn(Map.of(ORDER_REFERENCE,NUMBER_OF_BRICKS));

        BrickOrderResponse brickOrder = brickOrderController.getBrickOrder(ORDER_REFERENCE);

        assertThat(brickOrder.getOrderReference()).isEqualTo(ORDER_REFERENCE);
        assertThat(brickOrder.getNumberOfBricksOrdered()).isEqualTo(NUMBER_OF_BRICKS);
    }

    @Test
    public void getBrickOrderNoOrderFound() {
        given(brickOrderingService.getBrickOrder(ORDER_REFERENCE)).willThrow(BrickOrderNotFoundException.class);

        assertThrows(BrickOrderNotFoundException.class, () -> brickOrderController.getBrickOrder(ORDER_REFERENCE));
    }

    @Test
    public void getAllBrickOrders() {
        Map<Integer, Integer> orders = Map.of(1, 123, 2, 456, 3, 789);
        given(brickOrderingService.getBrickOrdersForPage(0)).willReturn(orders);

        List<BrickOrderResponse> allBrickOrders = brickOrderController.getAllBrickOrders(1).getOrders();

        assertThat(allBrickOrders.size()).isEqualTo(orders.size());

        allBrickOrders.forEach(brickOrderResponse ->
                assertThat(brickOrderResponse.getNumberOfBricksOrdered())
                    .isEqualTo(
                            orders.get(brickOrderResponse.getOrderReference())) );
    }
}
