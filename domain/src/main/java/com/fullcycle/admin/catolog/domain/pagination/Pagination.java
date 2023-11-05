package com.fullcycle.admin.catolog.domain.pagination;

import java.util.List;

public record Pagination<T>(
        int currentPage,
        int perPage,
        long total,
        List<T> items
) {
}
