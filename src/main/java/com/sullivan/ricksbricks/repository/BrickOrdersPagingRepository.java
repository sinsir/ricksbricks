package com.sullivan.ricksbricks.repository;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrickOrdersPagingRepository extends PagingAndSortingRepository<BrickOrderEntity, Integer> {

}
