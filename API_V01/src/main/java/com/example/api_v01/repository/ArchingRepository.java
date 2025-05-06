package com.example.api_v01.repository;

import com.example.api_v01.model.Arching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArchingRepository extends JpaRepository<Arching, UUID> {

    @Query("SELECT a FROM Arching a WHERE a.box.atm.id_atm=?1")
    Optional<List<Arching>> findArchingByATM(UUID id_atm);

}
