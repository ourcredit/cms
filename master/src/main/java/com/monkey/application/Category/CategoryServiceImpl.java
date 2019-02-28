package com.monkey.application.Category;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.application.Category.dtos.CategoryList;
import com.monkey.application.Category.dtos.CategoryModel;
import com.monkey.common.util.CollectionHelper;
import com.monkey.common.util.Interfaces.IMatch;
import com.monkey.core.entity.Category;
import com.monkey.core.mapper.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-08-27
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryRepository, Category> implements ICategoryService {

    public List<CategoryList> BuildCategoryCache() {
        List<Category> cates = baseMapper.selectList(new QueryWrapper<>());
        if (!cates.isEmpty()) {
            List<CategoryList> res = new ArrayList<>();
            for (Category c :
                    cates) {
                CategoryList mo = new CategoryList();
                mo.code = c.getCode();
                mo.dic = new ArrayList<>();
                cates.forEach(w -> {
                    if (w.getCode() == mo.code && w.getParent() == null) {
                        CategoryModel cm = new CategoryModel();
                        cm.name = w.getName();
                        cm.key = w.getId();
                        cm.child = new ArrayList<>();
                        cates.forEach(d -> {
                            if (w.getParent() != null && d.getParent() == w.getId()) {
                                CategoryModel ww = new CategoryModel();
                                ww.name = w.getName();
                                ww.key = w.getId();
                                cm.child.add(ww);
                            }
                        });
                        mo.dic.add(cm);
                    }
                });
                 if(!CollectionHelper.any(res, temp -> mo.code==temp.code)) {
                     res.add(mo);
                }
            }
            return res;
        }
        return new ArrayList<>();
    }
}
