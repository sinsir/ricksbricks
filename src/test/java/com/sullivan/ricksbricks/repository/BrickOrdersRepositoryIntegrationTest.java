package com.sullivan.ricksbricks.repository;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BrickOrdersRepositoryIntegrationTest {

    @Autowired
    private BrickOrdersRepository brickOrdersRepository;

    @Test
    void testSave() {
        int numBricks = 28;

        BrickOrderEntity savedEntity = brickOrdersRepository.save(new BrickOrderEntity(numBricks));
        assertThat(brickOrdersRepository.findAll().size()).isEqualTo(1);
        assertThat(savedEntity.getOrderReference()).isEqualTo(1);

        savedEntity = brickOrdersRepository.save(new BrickOrderEntity(numBricks));
        assertThat(brickOrdersRepository.findAll().size()).isEqualTo(2);
        assertThat(savedEntity.getOrderReference()).isEqualTo(2);
    }
}