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
@Table(name = "arching")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Arching {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_arching")
    private UUID id_arching;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime star_time;

    @Column(nullable = false)
    private LocalTime end_time;

    @Column(nullable = false)
    private Double init_amount;

    @Column(nullable = false)
    private Double final_amount;

    @Column(nullable = false)
    private Double total_amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_box",referencedColumnName = "id_box")
    private Box box;

}
