package com.sullivan.ricksbricks.data;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
public class BrickOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderReference;
    @NonNull
    private int numberOfBricksOrdered;
}
