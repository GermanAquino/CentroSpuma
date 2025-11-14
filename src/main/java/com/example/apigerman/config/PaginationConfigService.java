package com.example.apigerman.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PaginationConfigService {

    @Value("${app.pagination.page-size:10}")
    private int defaultPageSize;

    @Value("${app.pagination.page-number:0}")
    private int defaultPageNumber;

    public Pageable defaultPageable() {
        return PageRequest.of(defaultPageNumber, defaultPageSize);
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }
}

