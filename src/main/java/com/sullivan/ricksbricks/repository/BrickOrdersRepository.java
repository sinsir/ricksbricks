package com.sullivan.ricksbricks.repository;

import com.sullivan.ricksbricks.data.BrickOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrickOrdersRepository extends JpaRepository<BrickOrderEntity, Long> {
}
