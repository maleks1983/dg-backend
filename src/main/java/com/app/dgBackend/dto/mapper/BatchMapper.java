package com.app.dgBackend.dto.mapper;

import com.app.dgBackend.dto.BatchDTO;
import com.app.dgBackend.dto.RangeDTO;
import com.app.dgBackend.entity.Batch;
import com.app.dgBackend.entity.Product;
import com.app.dgBackend.entity.Status;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BatchMapper {

    public static @NotNull BatchDTO toDTO(@NotNull Batch batch) {
        List<Integer> rangesSerial = batch.getProducts().stream()
                .map(Product::getSerial)
                .toList();

        return new BatchDTO(
                batch.getId(),
                groupIntoRanges(rangesSerial),
                rangesSerial.size(), // або обчислити точну кількість
                batch.getFirmware()
        );
    }

    private static @NotNull Set<RangeDTO> groupIntoRanges(List<Integer> serials) {
        Set<RangeDTO> ranges = new LinkedHashSet<>();
        if (serials == null || serials.isEmpty()) return ranges;

        List<Integer> sorted = serials.stream().sorted().toList();

        int start = sorted.get(0);
        int end = start;

        for (int i = 1; i < sorted.size(); i++) {
            int current = sorted.get(i);
            if (current == end + 1) {
                end = current;
            } else {
                ranges.add(new RangeDTO(start, end));
                start = current;
                end = current;
            }
        }
        ranges.add(new RangeDTO(start, end));

        return ranges;
    }


    public static @NotNull Batch fromDTO(@NotNull BatchDTO dto) {
        Batch batch = new Batch();
        batch.setId(dto.getId());
        batch.setFirmware(dto.getFirmware());
        batch.setStatus(Status.STATUS_ADD);
        batch.setProducts(toProductSet(batch, dto.getRanges()));
        return batch;
    }

    private static @NotNull List<Product> toProductSet(Batch batch, @NotNull Set<RangeDTO> ranges) {
        List<Product> products = new ArrayList<>();
        for (RangeDTO range : ranges) {
            for (int i = range.getStart(); i <= range.getEnd(); i++) {
                Product p = new Product();
                p.setSerial(i);
                p.setBatch(batch);
                products.add(p);
            }
        }
        return products;
    }
}