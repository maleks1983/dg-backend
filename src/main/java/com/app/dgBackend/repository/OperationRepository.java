package com.app.dgBackend.repository;

import com.app.dgBackend.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
    void deleteOperationById(int id);

    Operation findOperationById(int id);
}
