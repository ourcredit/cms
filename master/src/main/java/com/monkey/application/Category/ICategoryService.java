package com.monkey.application.Category;


import com.baomidou.mybatisplus.extension.service.IService;
import com.monkey.application.Category.dtos.CategoryList;
import com.monkey.core.entity.Category;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-08-27
 */
public interface ICategoryService extends IService<Category> {
    List<CategoryList> BuildCategoryCache();
}
