package com.example.api_v01.dto;

import com.example.api_v01.model.Admin;
import com.example.api_v01.model.ProductStock;
import com.example.api_v01.model.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name_product;

    private Double price;

    private String additional_observation;

    private Category category;


}
