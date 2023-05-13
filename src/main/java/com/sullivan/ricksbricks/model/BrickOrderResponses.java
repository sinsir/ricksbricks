package com.sullivan.ricksbricks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrickOrderResponses {
    private List<BrickOrderResponse> orders;
}
