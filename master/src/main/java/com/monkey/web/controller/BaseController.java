package com.monkey.web.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.Category;
import com.monkey.core.entity.Customer;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

public class BaseController<T> {
    public QueryWrapper<T> filter = new QueryWrapper<>();

    public void BuildWhere(Map<String, Object> m) {
        BuildWhere(m,true);
    }
    public void BuildWhere(Map<String, Object> m, Boolean isSort) {
        QueryWrapper filter = new QueryWrapper<T>();
        filter = ComUtil.genderFilter(filter, m, isSort);
    }
}
