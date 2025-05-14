package com.app.dgBackend.repository;

import com.app.dgBackend.entity.Batch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Integer> {

    @Query(value = """
            SELECT b.*
            FROM batch b
            JOIN product p ON b.id = p.batch_id
            WHERE p.serial = :serial
            """, nativeQuery = true)
    Optional<Batch> findByProductSerial(@Param("serial") Integer serial);



}
