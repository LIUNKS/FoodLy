package com.example.api_v01.utils;


public interface ExceptionMessage {
    final String ATM_NOT_FOUND = "ATM no encontrado";
    final String STOCK_NOT_FOUND = "Stock no encontrado";
    final String DISCOUNT_STOCK = "La cantida ingresada a desconstar supera la actual del stock";
    final String INI_STOCK_INVALID = "El stock inicial no puede ser null o negativo";
    final String CURRENT_STOCK_INVALID = "El stock actual no puede ser null o negativo";
    final String TOTAL_SOLD_INVALID = "El total vendido no puede ser null o negativo";
    final String USER_NOT_FOUND = "Usuario no encontrado";
    final String USER_USERNAME_INVALID = "El username no puede ser null";
    final String USER_PASSWORD_INVALID = "El password no puede ser null";
    final String USER_ROLE_INVALID = "El role no puede ser null";
    final String PRODUCT_NOT_FOUND = "El producto no encontrado";
    final String BOX_NOT_FOUND = "El box no encontrado";
    final String ARCHING_NOT_FOUND = "El arqueo no encontrado";
    final String ATM_ARCHING_NOT_FOUND = "No se contro arqueos de este cajero";
}