package com.sullivan.ricksbricks.repository;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static com.sullivan.ricksbricks.util.TestConstants.DELETE_TEST_DATA;
import static com.sullivan.ricksbricks.util.TestConstants.INSERT_TEST_DATA_SCRIPT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BrickOrdersRepositoryIntegrationTest {

    @Autowired
    private BrickOrdersRepository brickOrdersRepository;

    @Test
    @Sql(DELETE_TEST_DATA)
    void testSave() {
        int numBricks = 28;

        BrickOrderEntity savedEntity = brickOrdersRepository.save(new BrickOrderEntity(numBricks));
        assertThat(brickOrdersRepository.findAll().size()).isEqualTo(1);
        assertThat(savedEntity.getOrderReference()).isEqualTo(1);

        savedEntity = brickOrdersRepository.save(new BrickOrderEntity(numBricks));
        assertThat(brickOrdersRepository.findAll().size()).isEqualTo(2);
        assertThat(savedEntity.getOrderReference()).isEqualTo(2);
    }

    @Sql({DELETE_TEST_DATA, INSERT_TEST_DATA_SCRIPT})
    @ParameterizedTest
    @CsvSource({"1,345", "2,999", "3,2", "4,3465"})
    void testGetById(String orderReference, String numberOfBricks) {

        Optional<BrickOrderEntity> brickOrderEntity = brickOrdersRepository.findById(Integer.valueOf(orderReference));
        assertThat(brickOrderEntity.isPresent()).isTrue();

        assertThat(brickOrderEntity.get().getOrderReference())
                .isEqualTo((Integer.valueOf(orderReference)));
        assertThat(brickOrderEntity.get().getNumberOfBricksOrdered())
                .isEqualTo((Integer.valueOf(numberOfBricks)));
    }
}