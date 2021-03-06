package com.monkey.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monkey.application.Category.ICategoryService;
import com.monkey.application.Channel.IChannelService;
import com.monkey.application.Channel.IFollowService;
import com.monkey.application.Channel.IVisitService;
import com.monkey.application.Customer.ICustomerService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.*;
import com.monkey.web.annotation.CurrentUser;
import com.monkey.web.annotation.Log;
import com.monkey.web.controller.dtos.ChannelDto;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.monkey.web.controller.BaseController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-28
 */
@RestController
@RequestMapping("api/channel")
public class ChannelController extends BaseController {
    @Autowired
    IChannelService _channelService;
    @Autowired
    IVisitService _visitService;
    @Autowired
    IFollowService _followService;
    @Autowired
    ICustomerService _customerService;

    @ApiOperation(value = "获取列表", notes = "渠道列表")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.list})
    public PublicResult<IPage<Channel>> pagecategorys(@RequestBody PagedAndFilterInputDto page) throws Exception {
        BuildWhere(page.where);
        IPage<Channel> res = _channelService.page(new Page<>(page.index, page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }

    @ApiOperation(value = "获取约谈列表", notes = "渠道列表")
    @RequestMapping(value = "/visits", method = RequestMethod.POST)
    public PublicResult<IPage<Visit>> pageVisits(@RequestBody PagedAndFilterInputDto page) throws Exception {
        BuildWhere(page.where);
        IPage<Visit> res = _visitService.page(new Page<>(page.index, page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }

    @ApiOperation(value = "获取渠道详情", notes = "渠道列表")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.first})
    public PublicResult<Channel> Category(@PathVariable String id) throws Exception {
        Channel m = _channelService.getById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }

    @Log(description = "渠道接口:/添加或编辑渠道")
    @ApiOperation(value = "添加或编辑渠道", notes = "渠道列表")
    @RequestMapping(method = RequestMethod.PUT)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.modify})
    public PublicResult<Object> insert(@RequestBody Channel model, @CurrentUser User current) throws Exception {
        if (model == null) return new PublicResult<>(PublicResultConstant.FAILED, "对象不可为空");
        if (model.getId() == null) {
            model.setCode(UUID.randomUUID().toString());
            model.setCreatorName(current.getUserName());
            model.setOrgName(current.getOrgName());
        }
        Boolean r = _channelService.saveOrUpdate(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

    @Log(description = "渠道接口:/删除渠道")
    @ApiOperation(value = "删除渠道", notes = "渠道列表")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.delete})
    public PublicResult<Object> delete(@PathVariable String id) throws Exception {
        Boolean r = _channelService.removeById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

    @Log(description = "渠道接口:/批量删除渠道")
    @ApiOperation(value = "批量删除渠道", notes = "渠道列表")
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.batch})
    public PublicResult<Object> batchdelete(@RequestBody List<String> ids) throws Exception {
        Boolean r = _channelService.removeByIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

    @Log(description = "渠道接口:/拜访")
    @ApiOperation(value = "拜访", notes = "渠道列表")
    @RequestMapping(value = "/visit", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.visit})
    public PublicResult<Object> visit(@RequestBody Visit model) throws Exception {
        if (model.getObjectId() == null) return new PublicResult<>(PublicResultConstant.FAILED, "对象不存在");
        Boolean r = _visitService.save(model);
        if (r) {
            if (model.getType() == 1) {
                Channel c = _channelService.getById(model.getObjectId());
                c.setVisitTime(LocalDateTime.now());
                c.setVisitCount(c.getVisitCount() + 1);
                _channelService.saveOrUpdate(c);
            } else if (model.getType() == 2) {
                Channel c = _channelService.getById(model.getObjectId());
                c.setLunchTime(LocalDateTime.now());
                c.setLunchCount(c.getLunchCount() + 1);
                _channelService.saveOrUpdate(c);
            } else {
                Customer cus = _customerService.getById(model.getObjectId());
                if (cus != null) {
                    cus.setVisitTime(new Date());
                    _customerService.saveOrUpdate(cus);
                }
            }
        }
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

    @Log(description = "渠道接口:/跟进")
    @ApiOperation(value = "跟进", notes = "渠道列表")
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.visit})
    public PublicResult<Object> follow(@RequestBody Follow model) throws Exception {
        if (model.getObjectId() == null) return new PublicResult<>(PublicResultConstant.FAILED, "对象不存在");
        Boolean r = _followService.save(model);
        if (r) {
            Customer cus = _customerService.getById(model.getObjectId());
            if (cus != null) {
                filter = new QueryWrapper();
                filter.eq("objectId", model.getObjectId());
                filter.orderByDesc("nextfollowTime");
                List<Follow> fl = _followService.list(filter);
                Follow curr=fl.get(0);
                if (fl != null) {
                    Date dt = curr.getFollowTime().compareTo(curr.getNextfollowTime()) >= 0 ? curr.getFollowTime() : curr.getNextfollowTime();
                    cus.setFollowTime(dt);
                    _customerService.saveOrUpdate(cus);
                }
            }
        }
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

}
