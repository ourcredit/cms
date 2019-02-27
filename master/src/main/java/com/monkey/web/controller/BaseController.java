package com.monkey.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkey.core.entity.Category;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    public QueryWrapper<Category> filter = new QueryWrapper<>();
}
