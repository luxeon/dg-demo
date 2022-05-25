package com.dg.demo.util;

import lombok.experimental.UtilityClass;

import java.util.List;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class IntUtils {

    public static List<Integer> parse(List<String> values) {
        return values.stream().map(Integer::valueOf).collect(toList());
    }
}
