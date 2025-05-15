package com.app.dgBackend.service;

import com.app.dgBackend.entity.Operation;
import com.app.dgBackend.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;


    public Operation save(@NotNull Operation operation) {
        if (!operation.isNew()) {
            Operation op = operationRepository.findById(operation.getId())
                    .orElseThrow(() -> new RuntimeException("Operation not found"));
            op.setName(operation.getName());

        }
        return operationRepository.save(operation);
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public Operation findById(int id) {
        return operationRepository.findById(id).orElseThrow(() -> new RuntimeException("Operation not found"));
    }

    public void delete(int id) {
        operationRepository.deleteById(id);
    }
}
