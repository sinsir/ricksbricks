package com.sullivan.ricksbricks.controllers;

import com.sullivan.ricksbricks.service.BrickOrderingService;
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
    public Map<String, Integer> addBrickOrder(@PathVariable("numberOfBricks") int numberOfBricks) {

        return Map.of("orderReference", brickOrderingService.addBrickOrder(numberOfBricks));
    }
}
