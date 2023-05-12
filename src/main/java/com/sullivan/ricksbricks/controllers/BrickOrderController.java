package com.sullivan.ricksbricks.controllers;

import com.sullivan.ricksbricks.model.BrickOrderResponse;
import com.sullivan.ricksbricks.service.BrickOrderingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("order/bricks")
public class BrickOrderController {

    private final BrickOrderingService brickOrderingService;

    @Autowired
    public BrickOrderController(BrickOrderingService brickOrderingService) {
        this.brickOrderingService = brickOrderingService;
    }

    @PostMapping("{numberOfBricks}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Submit new orders for bricks",
            description = "Submit new orders for bricks to start customers’ orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Brick order added successfully")
            })
    public Map<String, Integer> addBrickOrder(@PathVariable("numberOfBricks") int numberOfBricks) {

        return Map.of("orderReference", brickOrderingService.addBrickOrder(numberOfBricks));
    }

    @GetMapping("{orderReference}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve order for bricks",
            description = "Retrieve a specified order for bricks",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Brick order retrieved successfully")
            })
    public BrickOrderResponse getBrickOrder(@PathVariable("orderReference") int orderReference) {

        Map<Integer, Integer> brickOrder = brickOrderingService.getBrickOrder(orderReference);

        return new BrickOrderResponse(orderReference, brickOrder.get(orderReference));
    }
}
