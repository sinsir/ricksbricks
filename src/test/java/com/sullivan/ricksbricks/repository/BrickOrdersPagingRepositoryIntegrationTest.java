package com.sullivan.ricksbricks.repository;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import static com.sullivan.ricksbricks.util.TestConstants.DELETE_TEST_DATA;
import static com.sullivan.ricksbricks.util.TestConstants.INSERT_TEST_DATA_SCRIPT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BrickOrdersPagingRepositoryIntegrationTest {

    @Autowired
    private BrickOrdersPagingRepository brickOrdersPagingRepository;

    private static final int PAGE_SIZE = 2;
    private static final Long TOTAL_NUMBER_OF_ELEMENTS = 4L;

    @Sql({DELETE_TEST_DATA, INSERT_TEST_DATA_SCRIPT})
    @Test
    void testFindAllWithPageable() {
        // get first page and check it contains order Ids of first two orders
        Pageable brickOrderEntityPage = PageRequest.of(0, PAGE_SIZE);
        Page<BrickOrderEntity> brickOrderEntities = brickOrdersPagingRepository.findAll(brickOrderEntityPage);

        assertThat(brickOrderEntities.getTotalElements()).isEqualTo(TOTAL_NUMBER_OF_ELEMENTS);
        assertThat(brickOrderEntities.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(brickOrderEntities.stream()
                .anyMatch(brickOrderEntity ->
                        brickOrderEntity.getOrderReference() == 1 || brickOrderEntity.getOrderReference() == 2))
                .isTrue();

        // get second page and check it contains order Ids of second two orders
        brickOrderEntityPage = PageRequest.of(1, PAGE_SIZE);
        brickOrderEntities = brickOrdersPagingRepository.findAll(brickOrderEntityPage);

        assertThat(brickOrderEntities.getTotalElements()).isEqualTo(TOTAL_NUMBER_OF_ELEMENTS);
        assertThat(brickOrderEntities.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(brickOrderEntities.stream()
                .anyMatch(brickOrderEntity ->
                        brickOrderEntity.getOrderReference() == 3 || brickOrderEntity.getOrderReference() == 4))
                .isTrue();

        // get third  page and check it is empty
        brickOrderEntityPage = PageRequest.of(2, PAGE_SIZE);
        brickOrderEntities = brickOrdersPagingRepository.findAll(brickOrderEntityPage);

        assertThat(brickOrderEntities.getTotalElements()).isEqualTo(TOTAL_NUMBER_OF_ELEMENTS);
        assertThat(brickOrderEntities.getNumberOfElements()).isEqualTo(0);
    }
}