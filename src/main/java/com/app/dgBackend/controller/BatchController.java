package com.app.dgBackend.controller;

import com.app.dgBackend.dto.ApiResponse;
import com.app.dgBackend.dto.BatchDTO;
import com.app.dgBackend.dto.PageResponse;
import com.app.dgBackend.service.BatchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batch")
public class BatchController {

    private final BatchService batchService;

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
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Не знайдено продукт або партію"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addBatch(@RequestBody BatchDTO batchDTO) {
        batchService.saveFromDTO(batchDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse("Партію збережено!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBatch(@PathVariable Integer id, @RequestBody BatchDTO dto) {
        batchService.updateFromDTO(id, dto);
        return ResponseEntity.ok(new ApiResponse("Партію оновлено!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBatch(@PathVariable Integer id) {
        try {
            batchService.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Партію видалено!"));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Не знайдено продукт або партію"));
        }
    }
}
