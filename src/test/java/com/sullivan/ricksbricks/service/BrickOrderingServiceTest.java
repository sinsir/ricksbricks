package com.sullivan.ricksbricks.service;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import com.sullivan.ricksbricks.repository.BrickOrdersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BrickOrderingServiceTest {

    @Mock
    private BrickOrdersRepository brickOrdersRepository;
    @InjectMocks
    private BrickOrderingService brickOrderingService;

    @Test
    void addBrickOrder() {
        int orderReference = 21;
        int numBricks = 28;
        BrickOrderEntity savedBrickOrderEntity = new BrickOrderEntity(orderReference, numBricks);

        given(brickOrdersRepository.save(any(BrickOrderEntity.class))).willReturn(savedBrickOrderEntity);

        assertThat(brickOrderingService.addBrickOrder(numBricks)).isEqualTo(orderReference);
        verify(brickOrdersRepository).save(any());
    }
}