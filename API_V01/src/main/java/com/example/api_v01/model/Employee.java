package com.example.api_v01.model;

import java.time.LocalDate;
import java.util.UUID;

public class Employee {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id_atm")
    private UUID id_atm;

    private String name;

    private LocalDate date;

    private String alias;

    private String email;

    private String phone;

    private String dni;

    private Admin admin;
}
