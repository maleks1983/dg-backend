package com.app.dgBackend.controller;

import com.app.dgBackend.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @PutMapping("/{serial}/{newSerial}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer serial, @PathVariable Integer newSerial) {
        try {
            service.updateProductSerial(serial, newSerial);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @DeleteMapping("/{serial}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Integer serial) {
        service.deleteBySerial(serial);
        return ResponseEntity.noContent().build();
    }
}
