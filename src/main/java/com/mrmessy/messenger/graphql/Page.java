package com.mrmessy.messenger.graphql;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import static java.util.Collections.emptyList;

@Data
@Builder
public class Page<T> {
    private long    totalElements;
    private int     page;
    private int     size;
    private List<T> content;

    public static <T> Page<T> count(long totalElements) {
        return Page.<T>builder()
                .totalElements(totalElements)
                .content(emptyList())
                .page(0)
                .size(0)
                .build();
    }

    public static <T> Page<T> empty() {
        return Page.<T>builder()
                .totalElements(0)
                .page(0)
                .size(0)
                .content(emptyList())
                .build();
    }
}
