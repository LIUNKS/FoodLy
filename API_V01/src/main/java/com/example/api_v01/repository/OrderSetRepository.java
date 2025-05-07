package com.example.api_v01.repository;

import com.example.api_v01.model.OrderSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderSetRepository extends JpaRepository<OrderSet, UUID> {

    @Query("SELECT o FROM OrderSet o WHERE o.name_client=?1")
    List<OrderSet> findByNameClient(String name_client);

}
