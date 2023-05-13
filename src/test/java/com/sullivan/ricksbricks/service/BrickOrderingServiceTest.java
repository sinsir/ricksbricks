package com.sullivan.ricksbricks.service;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import com.sullivan.ricksbricks.error.BrickOrderNotFoundException;
import com.sullivan.ricksbricks.repository.BrickOrdersPagingRepository;
import com.sullivan.ricksbricks.repository.BrickOrdersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BrickOrderingServiceTest {

    public static final int ORDER_REFERENCE = 21;
    public static final int NUM_BRICKS = 28;
    public static final BrickOrderEntity BRICK_ORDER_ENTITY = new BrickOrderEntity(ORDER_REFERENCE, NUM_BRICKS);
    @Mock
    private BrickOrdersRepository brickOrdersRepository;

    @Mock
    private Page<BrickOrderEntity> page;

    @Mock
    private BrickOrdersPagingRepository brickOrdersPagingRepository;

    @InjectMocks
    private BrickOrderingService brickOrderingService;

    @Test
    void testAddBrickOrder() {
        given(brickOrdersRepository.save(any(BrickOrderEntity.class))).willReturn(BRICK_ORDER_ENTITY);

        assertThat(brickOrderingService.addBrickOrder(NUM_BRICKS)).isEqualTo(ORDER_REFERENCE);
        verify(brickOrdersRepository).save(any());
    }

    @Test
    void testGetBrickOrderOrderReferenceExists() {
        given(brickOrdersRepository.findById(ORDER_REFERENCE)).willReturn(Optional.of(BRICK_ORDER_ENTITY));
        assertThat(brickOrderingService.getBrickOrder(ORDER_REFERENCE))
                .isEqualTo(Map.of(ORDER_REFERENCE, NUM_BRICKS));
        verify(brickOrdersRepository).findById(ORDER_REFERENCE);

    }

    @Test
    void testGetBrickOrderOrderReferenceDoesNotExist() {
        given(brickOrdersRepository.findById(ORDER_REFERENCE)).willReturn(Optional.empty());

        assertThrows(BrickOrderNotFoundException.class, () -> brickOrderingService.getBrickOrder(ORDER_REFERENCE));

        verify(brickOrdersRepository).findById(ORDER_REFERENCE);
    }

    @Test
    void testGetBrickOrdersForPage() {
        int orderRef1 = 1;
        int orderRef2 = 2;

        int numBricksOrdered1 = 31;
        int numBricksOrdered2 = 666;

        List<BrickOrderEntity> brickOrderEntities =
                List.of(new BrickOrderEntity(orderRef1, numBricksOrdered1),
                        new BrickOrderEntity(orderRef2, numBricksOrdered2));

        given(page.stream()).willReturn(brickOrderEntities.stream());
        given(brickOrdersPagingRepository.findAll(any(Pageable.class))).willReturn(page);

        Map<Integer, Integer> brickOrdersForPage = brickOrderingService.getBrickOrdersForPage(1);

        assertThat(brickOrdersForPage.keySet().containsAll(List.of(orderRef1, orderRef2))).isTrue();
        assertThat(brickOrdersForPage.values().containsAll(List.of(numBricksOrdered1, numBricksOrdered2))).isTrue();
    }
}