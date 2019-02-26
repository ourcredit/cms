package com.monkey.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monkey.application.OperationLogs.IErrorlogService;
import com.monkey.application.OperationLogs.IOperationLogService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.Errorlog;
import com.monkey.core.entity.Log;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 操作日志 前端控制器
 * </p>
 *
 * @author liugh123
 * @since 2018-05-08
 */
@RestController
@RequestMapping("/api/log")
public class LogController {
    @Autowired
    IOperationLogService _logService;
    @Autowired
    IErrorlogService _errorlogService;
    // @Log(description="获取用户列表:/list")
    @ApiOperation(value = "获取日志列表",notes = "日志列表")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public PublicResult<IPage<Log>> logs(@RequestBody PagedAndFilterInputDto page) throws Exception{
        QueryWrapper<Log> filter = new QueryWrapper<>();
      filter=  ComUtil.genderFilter(filter,page.where,true);
        IPage<Log> res= _logService.page(new Page<>(page.index,page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @ApiOperation(value = "获取日志详情",notes = "日志列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public PublicResult<Object> log(@PathVariable String id) throws Exception{
        Log l=_logService.getById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, l);
    }
    @ApiOperation(value = "删除日志",notes = "日志列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public PublicResult<Object> delete(@PathVariable String id) throws Exception{
        Boolean r=_logService.removeById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @ApiOperation(value = "批量删除日志",notes = "日志列表")
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    public PublicResult<Object> batchdelete(@RequestBody List<String> ids) throws Exception{
        Boolean r=_logService.removeByIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @ApiOperation(value = "获取日志信息", notes = "运维人员")
    @RequestMapping(value = "/elogs", method = RequestMethod.POST)
    public PublicResult<IPage<Errorlog>> errorlogs(@RequestBody PagedAndFilterInputDto page) throws Exception {
        QueryWrapper<Errorlog> filter = new QueryWrapper<>();
        filter=  ComUtil.genderFilter(filter,page.where,true);
        IPage<Errorlog> res = _errorlogService.page(new Page<>(page.index, page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
}

