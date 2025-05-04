package com.example.api_v01.utils;


public interface ExceptionMessage {
    final String STOCK_NOT_FOUND = "Stock no encontrado";
    final String INI_STOCK_INVALID = "El stock inicial no puede ser null o negativo";
    final String CURRENT_STOCK_INVALID = "El stock actual no puede ser null o negativo";
    final String TOTAL_SOLD_INVALID = "El total vendido no puede ser null o negativo";
    final String USER_NOT_FOUND = "Usuario no encontrado";
    final String USER_USERNAME_INVALID = "El username no puede ser null";
    final String USER_PASSWORD_INVALID = "El password no puede ser null";
    final String USER_ROLE_INVALID = "El role no puede ser null";
    final String PRODUCT_NOT_FOUND = "El producto no encontrado";
}
