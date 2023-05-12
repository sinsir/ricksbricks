package com.sullivan.ricksbricks.service;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import com.sullivan.ricksbricks.error.BrickOrderNotFoundException;
import com.sullivan.ricksbricks.repository.BrickOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class BrickOrderingService {
    private final BrickOrdersRepository brickOrdersRepository;

    @Autowired
    public BrickOrderingService(BrickOrdersRepository brickOrdersRepository) {
        this.brickOrdersRepository = brickOrdersRepository;
    }

    public int addBrickOrder(int numberOfBricks) {
        BrickOrderEntity brickOrderEntity = new BrickOrderEntity(numberOfBricks);
        BrickOrderEntity savedEntity = brickOrdersRepository.save(brickOrderEntity);
        return savedEntity.getOrderReference();
    }

    public Map<Integer, Integer> getBrickOrder(int orderReference) {
        Optional<BrickOrderEntity> foundEntity = brickOrdersRepository.findById(orderReference);

        return foundEntity.map(brickOrder -> Map.of(orderReference, brickOrder.getNumberOfBricksOrdered()))
                .orElseThrow(BrickOrderNotFoundException::new);

    }
}
