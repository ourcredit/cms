package com.monkey.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monkey.application.Device.ICategoryService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.Category;
import com.monkey.web.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-26
 */
@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    ICategoryService _categoryService;
    @ApiOperation(value = "获取分类全部",notes = "分类列表")
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._system._category.list})
    public PublicResult<List<Category>> categorys() throws Exception{
        QueryWrapper<Category> filter = new QueryWrapper<>();
        List<Category> res= _categoryService.list( filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @ApiOperation(value = "获取分类列表",notes = "商品列表")
    @RequestMapping(value = "",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._system._category.list})
    public PublicResult<IPage<Category>> pagecategorys(@RequestBody PagedAndFilterInputDto page) throws Exception{
        QueryWrapper<Category> filter = new QueryWrapper<>();
        filter=  ComUtil.genderFilter(filter,page.where,true);
        IPage<Category> res= _categoryService.page(new Page<>(page.index,page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @ApiOperation(value = "获取分类详情",notes = "分类列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._system._category.first})
    public PublicResult<Category> Category(@PathVariable String id) throws Exception{
        Category m=_categoryService.getById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }

    @Log(description="分类接口:/添加或编辑分类")
    @ApiOperation(value = "添加或编辑分类",notes = "分类列表")
    @RequestMapping(method = RequestMethod.PUT)
    @RequiresPermissions(value = {PermissionConst._system._category.modify})
    public PublicResult<Object> insert(@RequestBody Category model) throws Exception{
        Boolean r=_categoryService.saveOrUpdate(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="分类接口:/删除分类")
    @ApiOperation(value = "删除分类",notes = "分类列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @RequiresPermissions(value = {PermissionConst._system._category.delete})
    public PublicResult<Object> delete(@PathVariable String id) throws Exception{
        Boolean r=_categoryService.removeById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="分类接口:/批量删除分类")
    @ApiOperation(value = "批量删除分类",notes = "分类列表")
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._system._category.batch})
    public PublicResult<Object> batchdelete(@RequestBody List<String> ids) throws Exception{
        Boolean r=_categoryService.removeByIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
}

