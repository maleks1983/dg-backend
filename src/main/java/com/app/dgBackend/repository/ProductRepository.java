package com.app.dgBackend.repository;

import com.app.dgBackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Modifying
    @Query("UPDATE Product p SET p.serial = :newSerial WHERE p.serial = :oldSerial ")
    int updateSerial(@Param("oldSerial") Integer oldSerial, @Param("newSerial") Integer newSerial);

    int deleteBySerial(int serial);

}
