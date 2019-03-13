package com.monkey.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monkey.application.Customer.ISaleForceService;
import com.monkey.application.Customer.dtos.UserForceDto;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.Customer;
import com.monkey.core.entity.SaleForce;
import com.monkey.core.entity.User;
import com.monkey.web.annotation.CurrentUser;
import com.monkey.web.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-28
 */
@RestController
@RequestMapping("api/force")
public class SaleForceController extends BaseController {
    @Autowired
    ISaleForceService _forceService;

    @ApiOperation(value = "获取列表", notes = "目标列表")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._relation._force.list})
    public PublicResult<IPage<UserForceDto>> list(@RequestBody PagedAndFilterInputDto page) throws Exception {
        Object name = page.where.get("name");
        Object mobile = page.where.get("mobile");
        Object month = page.where.get("month");
        String n = name != null ? name.toString() : "";
        String m = mobile != null ? mobile.toString() : "";
        String mo = month != null ? month.toString() : "";
        IPage<UserForceDto> res = _forceService.pageForce(new Page<>(page.index, page.size), n, m, mo);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }

    @ApiOperation(value = "添加或编辑目标", notes = "目标列表")
    @RequestMapping(method = RequestMethod.PUT)
    @RequiresPermissions(value = {PermissionConst._relation._force.modify})
    public PublicResult<Object> insert(@RequestBody SaleForce model) throws Exception {
        filter = new QueryWrapper();
        filter.eq("showMonth", model.getShowMonth());
        filter.eq("userId", model.getUserId());
        SaleForce sf = _forceService.getOne(filter);
        if (sf != null) {
            sf.setShowForce(model.getShowForce());
        }else {
            sf=model;
        }
        Boolean r = _forceService.saveOrUpdate(sf);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
}
