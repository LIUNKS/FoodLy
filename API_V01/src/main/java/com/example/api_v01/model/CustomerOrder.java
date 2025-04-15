package com.example.api_v01.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "customer_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerOrder  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_customer_order")
    private UUID id_order;

    @Column(nullable = false)
    private String name_client;

    @Column(nullable = false)
    private LocalDate date_order;

    @Column(nullable = false)
    private LocalTime time_order;

    @Column(nullable = false)
    private Double total_order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_arching",referencedColumnName = "id_arching")
    private Arching arching;

}
