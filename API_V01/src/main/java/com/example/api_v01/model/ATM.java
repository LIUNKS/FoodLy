package com.example.api_v01.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "atm")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_atm")
    private UUID id_atm;

    @Column(nullable = false)
    private String name_atm;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String dni;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user",referencedColumnName = "id_user")
    private User user_atm;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_admin",referencedColumnName = "id_admin")
    private Admin admin;

}
