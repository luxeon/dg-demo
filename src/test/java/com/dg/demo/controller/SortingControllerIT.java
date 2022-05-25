package com.dg.demo.controller;

import com.dg.demo.input.OrderType;
import com.dg.demo.input.SortingInput;
import com.dg.demo.service.SortingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.dg.demo.input.OrderType.ASC;
import static com.dg.demo.input.OrderType.DESC;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SortingController.class)
class SortingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSortListInDescOrder() throws Exception {
        mockMvc.perform(post("/sort").content(inputOf(DESC, 23243, 123, 5555, 12, 555))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[23243, 5555, 555, 123, 12]"));
    }

    @Test
    void shouldSortListInAscOrder() throws Exception {
        mockMvc.perform(post("/sort").content(inputOf(ASC, 23243, 123, 5555, 12, 555))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[12, 123, 555, 5555, 23243]"));
    }

    @Test
    void shouldReturnBadRequestWhenNoNumbersPresent() throws Exception {
        String[] numbers = null;
        mockMvc.perform(post("/sort").content(inputOf("asc", numbers))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"fieldName\":\"numbers\",\"message\":\"must not be null\"}]"));
    }

    @Test
    void shouldReturnBadRequestWhenMoreThen100Numbers() throws Exception {
        mockMvc.perform(post("/sort").content(inputOf(ASC, 89, 75, 93, 5, 49, 84, 72, 39, 81, 59, 21,
                                51, 52, 80, 95, 77, 23, 13, 74, 87, 32, 1, 69, 14, 17, 70, 82, 40, 90, 37, 45, 54, 50, 63,
                                53, 2, 91, 6, 27, 41, 58, 26, 12, 61, 86, 9, 78, 36, 7, 22, 44, 30, 28, 18, 11, 98, 47,
                                100, 68, 31, 76, 10, 64, 79, 88, 42, 38, 29, 67, 96, 62, 3, 83, 60, 8, 48, 24, 73, 71,
                                99, 33, 46, 20, 19, 57, 94, 34, 15, 4, 43, 97, 35, 85, 55, 56, 66, 25, 65, 101, 16, 92))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"fieldName\":\"numbers\",\"message\":\"size must be between 1 and 100\"}]"));
    }

    @Test
    void shouldReturnBadRequestWhenNoOrderTypePresent() throws Exception {
        mockMvc.perform(post("/sort").content(inputOf(null, 89, 75, 93, 5))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"fieldName\":\"order\",\"message\":\"must not be null\"}]"));
    }

    @Test
    void shouldReturnBadRequestInCaseOfCorruptedNumbers() throws Exception {
        mockMvc.perform(post("/sort").content(inputOf("asc", "1", "2", "abc"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"message\":\"Incorrect numbers.\"}"));
    }

    @Test
    void shouldReturnBadRequestInCaseOfIncorrectOrderType() throws Exception {
        mockMvc.perform(post("/sort").content(inputOf("incorrect", "3", "2", "5"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"message\":\"Incorrect order type.\"}"));
    }

    @SneakyThrows
    private String inputOf(OrderType orderType, Integer... numbers) {
        String type = orderType == null ? null : orderType.name().toLowerCase();
        return inputOf(type, Stream.of(numbers).map(String::valueOf).toArray(String[]::new));
    }

    @SneakyThrows
    private String inputOf(String orderType, String... numbers) {
        SortingInput input = new SortingInput();
        input.setOrder(orderType);
        input.setNumbers(numbers == null ? null : List.of(numbers));
        return objectMapper.writeValueAsString(input);
    }

    @TestConfiguration
    public static class Config {
        @Bean
        public SortingService sortingService() {
            return new SortingService();
        }
    }
}