package com.monkey.web.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.application.Payfor.IOrderService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.DateUtil;
import com.monkey.core.dtos.DeviceSaleStatical;
import com.monkey.core.dtos.ProductSaleStatical;
import com.monkey.core.entity.User;
import com.monkey.web.annotation.CurrentUser;
import com.monkey.web.annotation.Pass;
import com.monkey.web.controller.dtos.RequestDateDto;
import com.monkey.web.controller.dtos.StaticalInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    IOrderService _orderService;

    @ApiOperation(value = "获取首页统计", notes = "订单列表")
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._orders._order.statical})

    public PublicResult<Object> staticals() throws Exception {
        try {
            Map r = _orderService.getDashboard();
            if (r != null) {
                return new PublicResult<>(PublicResultConstant.SUCCESS, r);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, "获取统计失败");
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }

    @ApiOperation(value = "获取首页统计", notes = "订单列表")
    @RequestMapping(value = "/total", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._orders._order.statical})
    public PublicResult<Object> todays(@RequestBody RequestDateDto input,@CurrentUser User u) throws Exception {
        try {
            Map r = _orderService.getStaticial(input.start, input.end);
            if (r != null) {
                return new PublicResult<>(PublicResultConstant.SUCCESS, r);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, "获取统计失败");
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }
    @ApiOperation(value = "获取设备统计", notes = "订单列表")
    @RequestMapping(value = "/device", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._orders._order.statical})
    public PublicResult<Object> devices(@RequestBody PagedAndFilterInputDto page, @CurrentUser User u) throws Exception {
        try {
            StaticalInput inut=new StaticalInput();
            Page<DeviceSaleStatical> r = _orderService.getDeviceSaleStatical(new Page<>(page.index,page.size), inut);
            if (r != null) {
                return new PublicResult<>(PublicResultConstant.SUCCESS, r);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, "获取统计失败");
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }
    @ApiOperation(value = "获取产品统计", notes = "订单列表")
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._orders._order.statical})
    public PublicResult<Object> products(@RequestBody PagedAndFilterInputDto page, @CurrentUser User u) throws Exception {
        try {
            StaticalInput inut=new StaticalInput();
            Page<ProductSaleStatical> r = _orderService.getProductSaleStatical(new Page<>(page.index,page.size), inut);
            if (r != null) {
                return new PublicResult<>(PublicResultConstant.SUCCESS, r);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, "获取统计失败");
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }
    @ApiOperation(value = "获取点位统计", notes = "订单列表")
    @RequestMapping(value = "/point", method = RequestMethod.POST)
    public PublicResult<Object> points(@RequestBody PagedAndFilterInputDto page) throws Exception {
        try {
          Object pointName=  page.where.get("pointName");
          Object start=  page.where.get("start");
          Object end=  page.where.get("end");
            Page<ProductSaleStatical> r = _orderService.getPointSaleStatical(new Page<>(page.index,page.size),pointName==null||pointName.toString().isEmpty()?null: pointName.toString(),start==null?null: start.toString(),end==null?null:end.toString());
            if (r != null) {
                return new PublicResult<>(PublicResultConstant.SUCCESS, r);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, "获取统计失败");
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }
    @Pass
    @ApiOperation(value = "获取上位机订单统计", notes = "订单列表")
    @RequestMapping(value = "/machine", method = RequestMethod.POST)
    public PublicResult<Object> machines(@RequestBody StaticalInput input) throws Exception {
        try {
            if(input.deviceName==null|| input.deviceName.isEmpty()){
                return new PublicResult<>(PublicResultConstant.ERROR, "设备编号不可为空",null);
            }
       EntityWrapper ew=new EntityWrapper<>();
           ew.eq("deviceNum",input.deviceName);
            if(input.start!=null){
                ew.ge("creationTime", DateUtil.getStartTime(input.start) );
            }
            if(input.end!=null){
                ew.le("creationTime",DateUtil.getEndTime(input.end));
            }
            ew.eq("payState",1);
            Integer r = _orderService.selectCount(ew);
           return new PublicResult<>(PublicResultConstant.SUCCESS, r);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }
}

