package com.monkey.web.controller;


import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.application.Device.IDeviceService;
import com.monkey.application.Device.IPointService;
import com.monkey.application.OperationLogs.IOperaterService;
import com.monkey.application.Payfor.ICustomerOrderService;
import com.monkey.application.Payfor.IOrderService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.Constant;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.common.wechatsdk.XMLUtil4jdom;
import com.monkey.core.entity.CustomerOrder;
import com.monkey.core.entity.Device;
import com.monkey.core.entity.Operater;
import com.monkey.core.entity.Order;
import com.monkey.web.annotation.Log;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-26
 */
@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    IOrderService _orderService;
    @Autowired
    IDeviceService _deviceService;
    @Autowired
    IPointService _pointService;
    @Autowired
    IOperaterService _operaterService;
    @Autowired
    ICustomerOrderService _customerOrderService;

    @ApiOperation(value = "获取订单列表", notes = "订单列表")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._orders._order.list})
    public PublicResult<Page<Order>> orders(@RequestBody PagedAndFilterInputDto page) throws Exception {
        EntityWrapper<Order> filter = new EntityWrapper<>();
        filter = ComUtil.genderFilter(filter, page.where, true);
        String code = (String) page.where.get("code");
        if (code != null && !code.isEmpty()) {
            if (code.equals(Constant.UnknownCode)) {
                filter.where("pointName is null").or("pointName=''");
            } else {
                List<Integer> ids = _pointService.selectPointIdsByCode(code);
                if (ids.isEmpty()) return new PublicResult<Page<Order>>(PublicResultConstant.SUCCESS, new Page<>());
                EntityWrapper ew = new EntityWrapper<>();
                ew.in("pointId", ids);
                List<Device> ds = _deviceService.selectList(ew);
                if (ds.isEmpty()) return new PublicResult<Page<Order>>(PublicResultConstant.SUCCESS, new Page<>());
                List<String> ss = new ArrayList<>();
                ds.forEach(w -> {
                    ss.add(w.getDeviceNum());
                });
                if (!ss.isEmpty()) {
                    filter.in("deviceNum", ss);
                } else {
                    return new PublicResult<Page<Order>>(PublicResultConstant.SUCCESS, new Page<>());
                }
            }
        }
        Page<Order> res = _orderService.selectPage(new Page<>(page.index, page.size), filter);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }

    @ApiOperation(value = "获取上门取货订单列表", notes = "订单列表")
    @RequestMapping(value = "/opeorders", method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._orders._order.list})
    public PublicResult<Page<CustomerOrder>> opeOrders(@RequestBody PagedAndFilterInputDto page) throws Exception {
        EntityWrapper<CustomerOrder> filter = new EntityWrapper<>();
        Object ope = page.where.get("operaterName");
        page.where.remove("operaterName");
        filter = ComUtil.genderFilter(filter, page.where, true);
        List<Operater> rr = _operaterService.selectList(null);
        if (ope != null && !ope.toString().isEmpty()) {
            List<Integer> ids = new ArrayList<>();

            rr.forEach(w -> {
                if (w.getName().contains(ope.toString())) {
                    ids.add(w.getId());
                }
            });
            if (!ids.isEmpty()) {
                filter.in("operaterId", ids);
            }
        }

        Page<CustomerOrder> res = _customerOrderService.selectPage(new Page<>(page.index, page.size), filter);
        res.getRecords().forEach(w -> {
            rr.forEach(d -> {
                if (d.getId() == w.getOperaterId()) {
                    w.setOperaterName(d.getName());
                }
            });
        });
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }

    @ApiOperation(value = "获取订单详情", notes = "订单列表")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._orders._order.list})
    public PublicResult<Order> Product(@PathVariable Integer id) throws Exception {
        Order m = _orderService.selectById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }
    @Log(description = "退款操作:/普通退款")
    @ApiOperation(value = "退款操作", notes = "订单列表")
    @RequestMapping(value = "/back/{orderId}", method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._orders._order.back})
    public PublicResult<Object> back(@PathVariable String orderId) throws Exception {
        try {
            Order r = _orderService.selectById(orderId);
            if (r != null) {
                if(r.getPayState()==2){
                    return new PublicResult<Object>(PublicResultConstant.FAILED, "该订单已完成退款", null);
                }
                if(r.getOrderState()>3){
                    return new PublicResult<Object>(PublicResultConstant.FAILED, "该订单已经完成,不可退款", null);
                }
                String result = "";
                AlipayTradeRefundResponse res = null;
                if (r.getPayType() == 1) {
                    result = _orderService.weixinBack(r);
                    if (result.isEmpty()) return new PublicResult<>(PublicResultConstant.FAILED, "退款申请请求失败",null);
                    // 将解析结果存储在HashMap中
                    Map map = XMLUtil4jdom.doXMLParse(result);
                    if (map.get("result_code").toString().equals("FAIL")) {
                        return new PublicResult<>(PublicResultConstant.FAILED, map.get("err_code_des").toString(), null);
                    }
                } else {
                    res = _orderService.aliback(r);
                    if (res == null || res.getCode().equals("10000")) {
                        return new PublicResult<>(PublicResultConstant.FAILED, res.getSubMsg(), null);
                    }
                }
                return new PublicResult<>(PublicResultConstant.SUCCESS, "退款申请请求成功",null);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, "暂无此订单信息",null);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }

    @Log(description = "直接退款接口:/直接退款操作")
    @ApiOperation(value = "直接退款操作", notes = "订单列表")
    @RequestMapping(value = "/superback/{orderId}", method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._orders._order.superBack})
    public PublicResult<Object> superback(@PathVariable String orderId) throws Exception {
        try {
            Order r = _orderService.selectById(orderId);
            if (r != null) {
                if(r.getPayState()==2){
                    return new PublicResult<Object>(PublicResultConstant.FAILED, "该订单已完成退款", null);
                }
                String result = "";
                AlipayTradeRefundResponse res = null;
                if (r.getPayType() == 1) {
                    result = _orderService.weixinBack(r);
                    if (result.isEmpty()) return new PublicResult<>(PublicResultConstant.FAILED, "退款申请请求失败",null);
                    // 将解析结果存储在HashMap中
                    Map map = XMLUtil4jdom.doXMLParse(result);
                    if (map.get("result_code").toString().equals("FAIL")) {
                        return new PublicResult<>(PublicResultConstant.FAILED, map.get("err_code_des").toString(), null);
                    }
                } else {
                    res = _orderService.aliback(r);
                    if (res == null || res.getCode().equals("10000")) {
                        return new PublicResult<>(PublicResultConstant.FAILED, res.getSubMsg(), null);
                    }
                }
                return new PublicResult<>(PublicResultConstant.SUCCESS, "退款申请请求成功",null);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, "暂无此订单信息",null);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }
}

