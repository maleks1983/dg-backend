package com.app.dgBackend.repository;

import com.app.dgBackend.entity.Product;
import lombok.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @NonNull
    List<Product> findAll(@NonNull Sort sort);
}
