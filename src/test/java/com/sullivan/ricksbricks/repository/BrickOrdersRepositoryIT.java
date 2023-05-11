package com.sullivan.ricksbricks.repository;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BrickOrdersRepositoryIT {

    @Autowired
    private BrickOrdersRepository brickOrdersRepository;

    @Test
    void testSave() {
        Long orderReference = 321L;
        Integer numBricks = 28;

        brickOrdersRepository.save(new BrickOrderEntity(orderReference, numBricks));
        assertThat(brickOrdersRepository.findAll().size()).isEqualTo(1);

        Long orderReference2 = 123L;
        brickOrdersRepository.save(new BrickOrderEntity(orderReference2, numBricks));
        assertThat(brickOrdersRepository.findAll().size()).isEqualTo(2);
    }
}