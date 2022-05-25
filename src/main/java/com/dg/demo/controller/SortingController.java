package com.dg.demo.controller;

import com.dg.demo.exception.OrderTypeNotFoundException;
import com.dg.demo.exception.SortingException;
import com.dg.demo.input.OrderType;
import com.dg.demo.input.SortingInput;
import com.dg.demo.service.SortingService;
import com.dg.demo.util.IntUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SortingController {

    @NonNull
    private final SortingService sortingService;

    /**
     * <p>Endpoint to sort list of numbers in DESC/ASC order.</p>
     * <p>Max list size is 100 elements.</p>
     *
     * @param input is numbers to sort and order type
     * @return sorted list
     */
    @PostMapping("/sort")
    public List<Integer> sort(@RequestBody @Validated SortingInput input) {
        try {
            List<Integer> numbers = IntUtils.parse(input.getNumbers());
            OrderType orderType = OrderType.get(input.getOrder());
            return sortingService.sort(numbers, orderType);
        } catch (NumberFormatException e) {
            throw new SortingException("Incorrect numbers.");
        } catch (OrderTypeNotFoundException e) {
            throw new SortingException("Incorrect order type.");
        }
    }
}
