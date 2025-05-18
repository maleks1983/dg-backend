package com.app.dgBackend.controller;

import com.app.dgBackend.dto.ProductDTO;
import com.app.dgBackend.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;


    @PutMapping("/{serial}/{newSerial}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer serial, @PathVariable Integer newSerial) {
        try {
            productService.updateProductSerial(serial, newSerial);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        productService.save(productDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Продукт збережено!"));
    }


    @DeleteMapping("/{serial}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Integer serial) {
        productService.deleteBySerial(serial);
        return ResponseEntity.noContent().build();
    }
}
