package com.example.eventms.common.adapter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestPage<T> {
    long pageNum;
    long pageSize;
    long totalElements;
    long totalPages;
    List<T> content;

    public static <T> RestPage<T> of(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page) {
        RestPage<T> result = new RestPage<>();
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotalElements(page.getTotal());
        result.setTotalPages(page.getTotal() / page.getSize() + 1);
        result.setContent(page.getRecords());
        return result;
    }

    public static <T> RestPage<T> of(org.springframework.data.domain.Page<T> page) {
        RestPage<T> result = new RestPage<>();
        result.setPageNum(page.getNumber());
        result.setPageSize(page.getSize());
        result.setTotalElements(page.getTotalElements());
        result.setTotalPages(page.getTotalPages());
        result.setContent(page.getContent());
        return result;
    }
}
