package com.dg.demo.service;

import com.dg.demo.input.OrderType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class SortingService {

    /**
     * <p>Sorts integers depending on order type.</p>
     * @param numbers list to be sorted
     * @param orderType descending or ascending type
     * @return new sorted list
     */
    public List<Integer> sort(@NonNull List<Integer> numbers, @NonNull OrderType orderType) {
        List<Integer> copy = new ArrayList<>(numbers);
        switch (orderType) {
            case DESC -> copy.sort(Comparator.naturalOrder());
            case ASC -> copy.sort(Comparator.reverseOrder());
            default -> throw new IllegalArgumentException("Unsupported order type '" + orderType + "'.");
        }
        return copy;
    }
}
