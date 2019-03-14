package com.monkey.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monkey.application.Contracts.IContractService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.core.entity.Contract;
import com.monkey.web.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-26
 */
@RestController
@RequestMapping("api/contract")
public class ContractController extends BaseController {
    @Autowired
    IContractService _contractService;

    @ApiOperation(value = "获取合同列表", notes = "合同列表")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._contract._contractList.list})
    public PublicResult<IPage<Contract>> pageContracts(@RequestBody PagedAndFilterInputDto page) throws Exception {
        BuildWhere(page.where);
        IPage<Contract> res = _contractService.page(new Page<>(page.index, page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }

    @ApiOperation(value = "获取合同详情", notes = "合同列表")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._contract._contractList.first})
    public PublicResult<Contract> Contract(@PathVariable String id) throws Exception {
        Contract m = _contractService.getById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }
    @Log(description = "合同接口:/添加或编辑合同")
    @ApiOperation(value = "添加或编辑合同", notes = "合同列表")
    @RequestMapping(method = RequestMethod.PUT)
    @RequiresPermissions(value = {PermissionConst._contract._contractList.modify})
    public PublicResult<Object> insert(@RequestBody Contract model) throws Exception {
        Boolean r = _contractService.saveOrUpdate(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description = "合同接口:/删除合同")
    @ApiOperation(value = "删除合同", notes = "合同列表")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions(value = {PermissionConst._contract._contractList.delete})
    public PublicResult<Object> delete(@PathVariable String id) throws Exception {
        Boolean r = _contractService.removeById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description = "合同接口:/批量删除合同")
    @ApiOperation(value = "批量删除合同", notes = "合同列表")
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._contract._contractList.batch})
    public PublicResult<Object> batchdelete(@RequestBody List<String> ids) throws Exception {
        Boolean r = _contractService.removeByIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
}
