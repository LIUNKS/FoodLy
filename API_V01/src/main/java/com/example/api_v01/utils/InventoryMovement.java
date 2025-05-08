package com.example.api_v01.utils;

import com.example.api_v01.dto.ProductStockDTO;
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

    public static ProductStock ValidationStock(ProductStock stock, ProductStockDTO productStockDTO){
        if( productStockDTO.getIni_stock() != null && productStockDTO.getIni_stock() >=0 ){
            stock.setIni_stock(productStockDTO.getIni_stock());
        }
        if( productStockDTO.getCurrent_stock() != null && productStockDTO.getCurrent_stock() >=0 ){
            stock.setCurrent_stock(productStockDTO.getCurrent_stock());
        }
        if( productStockDTO.getTotal_sold() != null && productStockDTO.getTotal_sold() >= 0 ){
            stock.setTotal_sold(productStockDTO.getTotal_sold());
        }
        return stock;
    }

    public static ProductStock CleanStock(ProductStock stock){
        stock.setIni_stock(0);
        stock.setCurrent_stock(0);
        stock.setTotal_sold(0);
        return stock;
    }
}
