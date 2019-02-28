package com.monkey.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monkey.application.Category.ICategoryService;
import com.monkey.application.Channel.IChannelService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.Category;
import com.monkey.core.entity.Channel;
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
@RequestMapping("api/channel")
public class ChannelController extends BaseController {
    @Autowired
    IChannelService _channelService;
    @ApiOperation(value = "获取列表",notes = "商品列表")
    @RequestMapping(value = "",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.list})
    public PublicResult<IPage<Channel>> pagecategorys(@RequestBody PagedAndFilterInputDto page) throws Exception{
        BuildWhere(page.where);
        IPage<Channel> res= _channelService.page(new Page<>(page.index,page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @ApiOperation(value = "获取渠道详情",notes = "渠道列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.first})
    public PublicResult<Channel> Category(@PathVariable String id) throws Exception{
        Channel m=_channelService.getById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }
    @Log(description="渠道接口:/添加或编辑渠道")
    @ApiOperation(value = "添加或编辑渠道",notes = "渠道列表")
    @RequestMapping(method = RequestMethod.PUT)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.modify})
    public PublicResult<Object> insert(@RequestBody Channel model) throws Exception{
        Boolean r=_channelService.saveOrUpdate(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="渠道接口:/删除渠道")
    @ApiOperation(value = "删除渠道",notes = "渠道列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.delete})
    public PublicResult<Object> delete(@PathVariable String id) throws Exception{
        Boolean r=_channelService.removeById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="渠道接口:/批量删除渠道")
    @ApiOperation(value = "批量删除渠道",notes = "渠道列表")
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._channel._channelList.batch})
    public PublicResult<Object> batchdelete(@RequestBody List<String> ids) throws Exception{
        Boolean r=_channelService.removeByIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
}