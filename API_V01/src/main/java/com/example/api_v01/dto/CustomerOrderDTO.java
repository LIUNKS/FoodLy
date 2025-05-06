package com.example.api_v01.dto;

import com.example.api_v01.model.OrderSet;
import com.example.api_v01.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerOrderDTO {

    private ProductDTO product;

    private Integer count;

    private Double total_rice;

    private OrderSetDTO order;
}
