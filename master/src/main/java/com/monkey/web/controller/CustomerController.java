package com.monkey.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monkey.application.Customer.ICustomerService;
import com.monkey.application.Customer.ICustomerService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.Customer;
import com.monkey.core.entity.Customer;
import com.monkey.web.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.monkey.web.controller.BaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-28
 */
@RestController
@RequestMapping("api/customer")
public class CustomerController extends BaseController {
    @Autowired
    ICustomerService _customerService;
    @ApiOperation(value = "获取列表",notes = "商品列表")
    @RequestMapping(value = "",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._relation._customer.list})
    public PublicResult<IPage<Customer>> pagecategorys(@RequestBody PagedAndFilterInputDto page) throws Exception{
        QueryWrapper<Customer> filter = new QueryWrapper<>();
        filter=  ComUtil.genderFilter(filter,page.where,true);
        IPage<Customer> res= _customerService.page(new Page<>(page.index,page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @ApiOperation(value = "获取客户详情",notes = "客户列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._relation._customer.first})
    public PublicResult<Customer> Category(@PathVariable String id) throws Exception{
        Customer m=_customerService.getById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }
    @Log(description="客户接口:/添加或编辑客户")
    @ApiOperation(value = "添加或编辑客户",notes = "客户列表")
    @RequestMapping(method = RequestMethod.PUT)
    @RequiresPermissions(value = {PermissionConst._relation._customer.modify})
    public PublicResult<Object> insert(@RequestBody Customer model) throws Exception{
        Boolean r=_customerService.saveOrUpdate(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="客户接口:/删除客户")
    @ApiOperation(value = "删除客户",notes = "客户列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @RequiresPermissions(value = {PermissionConst._relation._customer.delete})
    public PublicResult<Object> delete(@PathVariable String id) throws Exception{
        Boolean r=_customerService.removeById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="客户接口:/批量删除客户")
    @ApiOperation(value = "批量删除客户",notes = "客户列表")
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._relation._customer.batch})
    public PublicResult<Object> batchdelete(@RequestBody List<String> ids) throws Exception{
        Boolean r=_customerService.removeByIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
}
