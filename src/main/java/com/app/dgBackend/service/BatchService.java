package com.app.dgBackend.service;

import com.app.dgBackend.dto.BatchDTO;
import com.app.dgBackend.dto.PageResponse;
import com.app.dgBackend.dto.mapper.BatchMapper;
import com.app.dgBackend.entity.Batch;
import com.app.dgBackend.repository.BatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchService {
    private final BatchRepository batchRepository;

    public void update(Batch batch) {
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

    public Batch saveFromDTO(BatchDTO batchDTO) {
        Batch batch = BatchMapper.fromDTO(batchDTO);
        return batchRepository.save(batch);
    }

    public Batch updateFromDTO(Integer id, BatchDTO dto) {
        return null;
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
