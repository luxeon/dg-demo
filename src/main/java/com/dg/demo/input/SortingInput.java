package com.dg.demo.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class SortingInput {

    @NotNull
    @Size(min = 1, max = 100)
    private List<Integer> numbers;
    @NotNull
    private String order;

}
