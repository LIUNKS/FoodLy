package com.example.api_v01.repository;

import com.example.api_v01.model.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ATMRepository extends JpaRepository<ATM, UUID> {
    // Metodo para buscar el ATM por nombre, alias o dni
    Optional<ATM> findByNameAtmOrAliasOrDni(String nameAtm, String alias, String dni);
    Optional<ATM> findByNameAtm(String nameAtm);
}
