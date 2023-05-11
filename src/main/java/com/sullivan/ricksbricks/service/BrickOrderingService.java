package com.sullivan.ricksbricks.service;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import com.sullivan.ricksbricks.repository.BrickOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
