package com.sullivan.ricksbricks.service;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import com.sullivan.ricksbricks.error.BrickOrderNotFoundException;
import com.sullivan.ricksbricks.repository.BrickOrdersPagingRepository;
import com.sullivan.ricksbricks.repository.BrickOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrickOrderingService {
    private final BrickOrdersRepository brickOrdersRepository;

    private final BrickOrdersPagingRepository brickOrdersPagingRepository;

    private final int PAGE_SIZE = 2;

    @Autowired
    public BrickOrderingService(BrickOrdersRepository brickOrdersRepository,
                                BrickOrdersPagingRepository brickOrdersPagingRepository) {
        this.brickOrdersRepository = brickOrdersRepository;
        this.brickOrdersPagingRepository = brickOrdersPagingRepository;
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

    public Map<Integer, Integer> getBrickOrdersForPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<BrickOrderEntity> brickOrderEntities = brickOrdersPagingRepository.findAll(pageable);

        return brickOrderEntities.stream()
                .collect(Collectors.toMap(BrickOrderEntity::getOrderReference,
                        BrickOrderEntity::getNumberOfBricksOrdered));
    }
}
