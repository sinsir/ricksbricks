package com.sullivan.ricksbricks.data;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BrickOrderEntity {
    @Id
    private Long orderReference;
    private Integer numberOfBricksOrdered;
}
