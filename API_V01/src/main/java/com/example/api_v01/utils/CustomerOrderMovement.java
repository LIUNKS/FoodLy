package com.example.api_v01.utils;

import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.model.Product;
import com.example.api_v01.model.ProductStock;

import java.util.List;

public class CustomerOrderMovement {

    public static CustomerOrder createCustomerOrder(OrderSet orderSet,Product product, Integer count) {
        if(product.getStock().getCurrent_stock() >= count){
            return CustomerOrder.builder()
                    .product(product)
                    .order(orderSet)
                    .count(count)
                    .total_rice( product.getPrice() * count )
                    .build();
        }
        return null;
    }

    public static Product rectificationStockCustomerOrder(CustomerOrder customerOrder)  {
        ProductStock stock = customerOrder.getProduct().getStock();
        Integer count = customerOrder.getCount();

        stock.setCurrent_stock(stock.getCurrent_stock() + count);
        stock.setTotal_sold( stock.getTotal_sold() - count );

        Product product = customerOrder.getProduct();
        product.setStock(stock);
        return product;
    }

    public static Tuple<CustomerOrder,Product> discountStockCustomerOrder(CustomerOrder customerOrder,Integer count)  {
        if(customerOrder.getCount() >= count){

            Integer discount = customerOrder.getCount() - count;
            customerOrder.setCount(customerOrder.getCount() - count); //retifica la cantidad de los que se añadieron

            Double total_discount = customerOrder.getProduct().getPrice() * count;
            Double total_sold = customerOrder.getTotal_rice() - total_discount;
            customerOrder.setTotal_rice(total_sold); // retifica el total del valor de todos los que se añadieron

            Product product = rectificationStockCustomerOrder(customerOrder);

            return new Tuple<>(customerOrder,product);
        }
        return null;
    }













    public static CustomerOrder createCustomerOrder2 (Integer stock, Product product, OrderSet order) {
        if(product.getStock().getCurrent_stock() >= stock) {
            return CustomerOrder.builder()
                    .product(product)
                    .count(stock)
                    .total_rice(product.getPrice() * stock)
                    .order(order)
                    .build();
        }
        System.out.println("Error: no se puede despachar mas productos que el stock actual");
        return null;
    }
    public static Double ContTotal (List<CustomerOrder> ListOrder) {
        Double total_rice = 0.0;
        for (CustomerOrder customerOrder : ListOrder) {
            total_rice += customerOrder.getTotal_rice();
        }
        return  total_rice;
    }
}
