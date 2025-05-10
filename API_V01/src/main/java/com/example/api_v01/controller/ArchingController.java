package com.example.api_v01.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Arching")
@RequiredArgsConstructor
public class ArchingController {

    //Implementar todos los servicios del aqueo.
    //¡También del servicio auxiliar ArchingProcessOrderSet su metodo closeArching es importante!!!
    //Seguir la sintaxis requeridad de para todos los controladores usando SuccessMessage para cada EndPoint
    //No olvidar agregarle el URI al metodo que guarda el arching,es para saber donde esta ubicado
    //(Usar UriGenric es un utils del proyecto propio)
}
