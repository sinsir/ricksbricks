package com.sullivan.ricksbricks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrickOrderResponse {
    private Integer orderReference;
    private Integer numberOfBricksOrdered;
}
