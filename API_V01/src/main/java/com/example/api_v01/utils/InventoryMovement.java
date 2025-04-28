package com.example.api_v01.utils;

import com.example.api_v01.model.Product;
import com.example.api_v01.model.ProductStock;

public class InventoryMovement {
    public static Product discountStock(Product product,Integer count) {
        ProductStock stock = product.getStock();
        if(stock.getCurrent_stock() >= count) {
            stock.setCurrent_stock(stock.getCurrent_stock() - count);
            stock.setTotal_sold(stock.getTotal_sold() + count);
            return product;
        }
        return null;
    }
    public static Product increaseStock(Product product ,Integer count) {
        ProductStock stock = product.getStock();
        stock.setIni_stock(stock.getIni_stock() + count);
        return product;
    }
}
