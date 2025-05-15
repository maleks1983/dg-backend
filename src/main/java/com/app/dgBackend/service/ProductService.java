package com.app.dgBackend.service;

import com.app.dgBackend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void updateProductSerial(Integer oldSerial, Integer newSerial) {
        int updated = productRepository.updateSerial(oldSerial, newSerial);
        if (updated == 0) {
            throw new EntityNotFoundException("Продукт із серійним номером " + oldSerial + " не знайдено");
        }
    }

    @Transactional
    public void deleteBySerial(Integer serial) {
        int deleted = productRepository.deleteBySerial(serial);
        if (deleted == 0) {
            throw new EntityNotFoundException("Продукт із серійним номером " + serial + " не знайдено");
        }
    }
}
