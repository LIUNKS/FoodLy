package com.example.api_v01.repository;

import com.example.api_v01.model.Arching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArchingRepository extends JpaRepository<Arching, UUID> {
}
