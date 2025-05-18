package com.app.dgBackend.service;

import com.app.dgBackend.dto.BatchDTO;
import com.app.dgBackend.dto.PageResponse;
import com.app.dgBackend.dto.mapper.BatchMapper;
import com.app.dgBackend.entity.Batch;
import com.app.dgBackend.entity.Product;
import com.app.dgBackend.repository.BatchRepository;
import com.app.dgBackend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchService {
    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;

    public void update(@NotNull Batch batch) {
        if (batch.getId() == null || !batchRepository.existsById(batch.getId())) {
            throw new EntityNotFoundException("Партія не знайдена");
        }
        batchRepository.save(batch);

    }

    public PageResponse<BatchDTO> findAll(int page, int size) {
        Page<Batch> batches = batchRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))
        );

        return new PageResponse<>(
                batches.getContent().stream().map(BatchMapper::toDTO).toList(),
                batches.getNumber(),
                batches.getSize(),
                batches.getTotalElements(),
                batches.getTotalPages()
        );
    }

    public BatchDTO findById(Integer id) {
        return BatchMapper.toDTO(batchRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }


    @Transactional
    public void saveFromDTO(BatchDTO batchDTO) {
        Batch batch = BatchMapper.fromDTO(batchDTO);
        batchRepository.save(batch);
    }

    @Transactional
    public void updateFromDTO(Integer id, BatchDTO dto) {
        Batch batch = batchRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Batch not found"));
        if (batch != null && dto.getRanges() != null) {
            Batch newBatch = BatchMapper.fromDTO(dto);

            List<Product> existingProducts = batch.getProducts().stream()
                    .sorted(Comparator.comparing(Product::getSerial, Comparator.nullsLast(Integer::compareTo)))
                    .toList();
            List<Product> newProducts = newBatch.getProducts().stream()
                    .sorted(Comparator.comparing(Product::getSerial, Comparator.nullsLast(Integer::compareTo)))
                    .toList();

            int size = Math.min(existingProducts.size(), newProducts.size());
            for (int i = 0; i < size; i++) {
                Integer newSerial = newProducts.get(i).getSerial();
                Integer oldSerial = existingProducts.get(i).getSerial();
                if (!Objects.equals(existingProducts.get(i).getSerial(), newProducts.get(i).getSerial())) {
                    productRepository.updateSerial(oldSerial, newSerial);
                }
            }
        }

    }

    public BatchDTO findByProductSerial(Integer serial) {
        Optional<Batch> b = batchRepository.findByProductSerial(serial);
        if (b.isEmpty()) {
            throw new EntityNotFoundException("Продукт не знайдено");
        }
        return BatchMapper.toDTO(b.get());

    }

    public void deleteById(Integer id) {
        if (batchRepository.existsById(id)) {
            batchRepository.deleteById(id);
        } else throw new EntityNotFoundException("Партія не знайдена");

    }
}
