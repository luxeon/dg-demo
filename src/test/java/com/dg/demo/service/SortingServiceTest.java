package com.dg.demo.service;

import com.dg.demo.input.OrderType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SortingServiceTest {

    @InjectMocks
    private SortingService service;

    @Test
    void shouldSortInAscOrder() {
        List<Integer> sorted = service.sort(List.of(3, 1, 2, 4, 1), OrderType.DESC);
        assertThat(sorted).isEqualTo(List.of(1, 1, 2, 3, 4));
    }

    @Test
    void shouldSortInDescOrder() {
        List<Integer> sorted = service.sort(List.of(3, 1, 2, 4, 1), OrderType.ASC);
        assertThat(sorted).isEqualTo(List.of(4, 3, 2, 1, 1));
    }

    @Test
    void shouldThrowNpeWhenNumberListIsNull() {
        assertThrows(NullPointerException.class, () -> service.sort(null, OrderType.ASC));
    }

    @Test
    void shouldThrowNpeWhenOrderTypeIsNull() {
        assertThrows(NullPointerException.class, () -> service.sort(List.of(1, 5, 3, 2), null));
    }
}