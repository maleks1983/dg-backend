package com.app.dgBackend.controller;

import com.app.dgBackend.dto.BatchDTO;
import com.app.dgBackend.dto.PageResponse;
import com.app.dgBackend.entity.Batch;
import com.app.dgBackend.service.BatchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<BatchDTO>> getAllBatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        PageResponse<BatchDTO> all = batchService.findAll(page, size);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchDTO> getBatch(@PathVariable Integer id) {
        BatchDTO batchDTO = batchService.findById(id);
        return ResponseEntity.ok(batchDTO);
    }

    @GetMapping("/by-product/{serial}")
    public ResponseEntity<?> findByProductSerial(@PathVariable Integer serial) {
        try {
            BatchDTO dto = batchService.findByProductSerial(serial);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Не знайдено продукт або партію");
        }
    }

    @PostMapping
    public ResponseEntity<Batch> addBatch(@RequestBody BatchDTO batchDTO) {
        Batch saved = batchService.saveFromDTO(batchDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Batch> updateBatch(@PathVariable Integer id, @RequestBody BatchDTO dto) {
        Batch updated = batchService.updateFromDTO(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Integer id) {
        batchService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

