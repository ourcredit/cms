package com.monkey.web.controller;



import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.core.entity.User;
import com.monkey.web.annotation.CurrentUser;
import com.monkey.web.controller.dtos.RequestDateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/api/dash")
@Api("首页汇总")
public class DashController  {
   

    @ApiOperation(value = "获取首页统计", notes = "订单列表")
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  //  @RequiresPermissions(value = {PermissionConst._orders._order.statical})

    public PublicResult<Object> staticals() throws Exception {
        return new PublicResult<>(PublicResultConstant.ERROR, "获取统计失败");
    }

    @ApiOperation(value = "获取首页统计", notes = "订单列表")
    @RequestMapping(value = "/total", method = RequestMethod.POST)
 //   @RequiresPermissions(value = {PermissionConst._orders._order.statical})
    public PublicResult<Object> todays(@RequestBody RequestDateDto input,@CurrentUser User u) throws Exception {
        return new PublicResult<>(PublicResultConstant.ERROR, "获取统计失败");
    }
  
}

