package com.sullivan.ricksbricks.controllers;

import com.sullivan.ricksbricks.model.BrickOrderResponse;
import com.sullivan.ricksbricks.model.BrickOrderResponses;
import com.sullivan.ricksbricks.service.BrickOrderingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController()
@RequestMapping("order/bricks")
public class BrickOrderController {

    private final BrickOrderingService brickOrderingService;

    @Autowired
    public BrickOrderController(BrickOrderingService brickOrderingService) {
        this.brickOrderingService = brickOrderingService;
    }

    @PostMapping("{numberOfBricks}")
    @Operation(summary = "Submit new orders for bricks",
            description = "Submit new orders for bricks to start customers’ orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Brick order added successfully")
            })
    public Map<String, Integer> addBrickOrder(@PathVariable("numberOfBricks") int numberOfBricks) {

        return Map.of("orderReference", brickOrderingService.addBrickOrder(numberOfBricks));
    }

    @GetMapping("{orderReference}")
    @Operation(summary = "Retrieve order for bricks",
            description = "Retrieve a specified order for bricks",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Brick order retrieved successfully")
            })
    public BrickOrderResponse getBrickOrder(@PathVariable("orderReference") int orderReference) {

        Map<Integer, Integer> brickOrder = brickOrderingService.getBrickOrder(orderReference);

        return new BrickOrderResponse(orderReference, brickOrder.get(orderReference));
    }

    @GetMapping()
    @Operation(summary = "Retrieve all brick orders",
            description = "Retrieve all brick order for the specified page",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Brick order retrieved successfully")
            })
    public BrickOrderResponses getAllBrickOrders(@RequestParam("pageNumber") int pageNumber) {

        Map<Integer, Integer> brickOrder = brickOrderingService.getBrickOrdersForPage(pageNumber - 1);

        List<BrickOrderResponse> orderResponses = brickOrder.entrySet().stream()
                .map(entrySet -> new BrickOrderResponse(entrySet.getKey(), entrySet.getValue()))
                .collect(toList());

        return new BrickOrderResponses(orderResponses);

    }
}
