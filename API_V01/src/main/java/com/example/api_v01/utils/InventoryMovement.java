package com.example.api_v01.utils;

import com.example.api_v01.model.ProductStock;

public class InventoryMovement {

    public static ProductStock discountStock(ProductStock stock,Integer count) {
        if(stock.getCurrent_stock() >= count) {
            stock.setCurrent_stock( stock.getCurrent_stock() - count );
            stock.setTotal_sold( stock.getTotal_sold() + count );
            return stock;
        }
        return null;
    }

    public static ProductStock increaseStock(ProductStock stock ,Integer count) {
        stock.setIni_stock( stock.getIni_stock() + count );
        stock.setCurrent_stock( stock.getCurrent_stock() + count );
        return stock;
    }
}
