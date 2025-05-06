package com.example.api_v01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductStockDTO {

    private Integer ini_stock;

    private Integer current_stock;

    private Integer total_sold;

}
