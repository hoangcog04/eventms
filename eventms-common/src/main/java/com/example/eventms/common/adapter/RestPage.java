package com.example.eventms.common.adapter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    long objectCount;
    long pageCount;
    List<T> content;

    public static <T> RestPage<T> of(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page) {
        RestPage<T> result = new RestPage<>();
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setObjectCount(page.getTotal());
        result.setPageCount(page.getTotal() / page.getSize() + 1);
        result.setContent(page.getRecords());
        return result;
    }

    public static <T> RestPage<T> of(org.springframework.data.domain.Page<T> page) {
        RestPage<T> result = new RestPage<>();
        result.setPageNum(page.getNumber());
        result.setPageSize(page.getSize());
        result.setObjectCount(page.getTotalElements());
        result.setPageCount(page.getTotalPages());
        result.setContent(page.getContent());
        return result;
    }

    public static <T> RestPage<T> of(IPage<T> ipage) {
        RestPage<T> result = new RestPage<>();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page = (Page<T>) ipage;
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setObjectCount(page.getTotal());
        result.setPageCount(page.getTotal() / page.getSize() + 1);
        result.setContent(page.getRecords());
        return result;
    }
}
