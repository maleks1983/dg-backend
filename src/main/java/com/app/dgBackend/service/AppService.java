package com.app.dgBackend.service;

import com.app.dgBackend.entity.Product;
import com.app.dgBackend.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {
    private final ProductRepository repository;

    public AppService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getProducts() {
       return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
