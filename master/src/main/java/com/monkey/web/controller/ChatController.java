package com.monkey.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.application.Payfor.IChargeorderService;
import com.monkey.application.Payfor.ICustomerOrderService;
import com.monkey.application.Payfor.IOrderService;
import com.monkey.application.customer.ICustomerService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.Constant;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.base.SocketConstant;
import com.monkey.common.util.AesCbcUtil;
import com.monkey.common.util.CacheManager;
import com.monkey.common.util.DateTimeUtil;
import com.monkey.common.wechatsdk.HttpUtil;
import com.monkey.common.wechatsdk.PayConfig;
import com.monkey.core.entity.Chargeorder;
import com.monkey.core.entity.Customer;
import com.monkey.core.entity.CustomerOrder;
import com.monkey.core.entity.Order;
import com.monkey.web.annotation.Log;
import com.monkey.web.annotation.Pass;
import com.monkey.web.aspect.WebSocketServer;
import com.monkey.web.controller.dtos.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.SortedMap;


/**
 * @author liugh
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ICustomerService _customerService;
    @Autowired
    ICustomerOrderService _customerOrderService;
    @Autowired
    IOrderService _orderService;
    @Autowired
    IChargeorderService _chargeOrderService;

    @Pass
    @ApiOperation(value = "小程序解密", notes = "小程序")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public PublicResult<Customer> customer(@RequestBody WechatCodeInput input) {
        //登录凭证不能为空
        if (input.getCode() == null || input.getCode().length() == 0) {
            return new PublicResult<Customer>(PublicResultConstant.INVALID_TENANT_NAME, null);
        }
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + PayConfig.MP_CUSTOMER_APPID + "&secret=" + PayConfig.MP_CUSTOMER_SECRET + "&js_code=" + input.getCode() + "&grant_type=" + Constant.grant_type;
        //发送请求
        String sr = HttpUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        EntityWrapper ew = new EntityWrapper();
        ew.eq("openId", openid);
        Customer c = _customerService.selectOne(ew);
        if (input.getEncryptedData().isEmpty() || input.getIv().isEmpty()) {
            if (c == null) {
                c = new Customer();
                c.setOpenId(openid);
                c.setBalance(0);
                _customerService.insert(c);
            }
            return new PublicResult<Customer>(PublicResultConstant.SUCCESS, c);
        }
        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(input.getEncryptedData(), session_key, input.getIv(), "UTF-8");
            if (null != result && result.length() > 0) {
                JSONObject userInfoJSON = JSONObject.parseObject(result);
                if (c == null) {
                    c = new Customer();
                    c.setBalance(0);
                    c.setNickName(userInfoJSON.get("nickName").toString());
                    c.setOpenId(userInfoJSON.get("openId").toString());
                    c.setAddress(userInfoJSON.get("country") + userInfoJSON.get("province").toString() + userInfoJSON.get("city"));
                    c.setMobile("");
                    c.setAvatarUrl(userInfoJSON.get("avatarUrl").toString());
                    c.setUnionId(userInfoJSON.get("unionId").toString());
                    _customerService.insert(c);
                }
                return new PublicResult<Customer>(PublicResultConstant.SUCCESS, c);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new PublicResult<Customer>(PublicResultConstant.FAILED, null);
    }
    @Pass
    @Log(description = "小程序:/客户下单操作")
    @ApiOperation(value = "客户下单操作", notes = "小程序")
    @RequestMapping(value = "/make", method = RequestMethod.POST)
    public PublicResult<Object> insert(@RequestBody CustomerOrderInput model) throws Exception {
        logger.error("订单请求参数" + JSON.toJSONString(model));
        try {
            CustomerOrder r = _customerOrderService.insertOrder(model);
            if (!r.getId().isEmpty()) {
                SortedMap<String, Object> result;
                result = _customerOrderService.wxPay(r);
                logger.error("微信下单结果" + result);
                if (!result.isEmpty()) {
                    return new PublicResult<>(PublicResultConstant.SUCCESS, result);
                }
            }
            return new PublicResult<>(PublicResultConstant.ERROR, r);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }
    @Pass
    @Log(description = "小程序:/客户充值操作")
    @ApiOperation(value = "客户充值操作", notes = "小程序")
    @RequestMapping(value = "/charge", method = RequestMethod.POST)
    public PublicResult<Object> charge(@RequestBody ChargeOrderInput model) throws Exception {
        try {
            Chargeorder r = _chargeOrderService.insertChargeOrder(model,true);
            if (!r.getId().isEmpty()) {
                SortedMap<String, Object> result;
                result = _chargeOrderService.wxChargePay(r);
                if (!result.isEmpty()) {
                    return new PublicResult<>(PublicResultConstant.SUCCESS, result);
                }
                return new PublicResult<>(PublicResultConstant.ERROR, r);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, r);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }
    @Pass
    @Log(description = "小程序:/充值带支付")
    @ApiOperation(value = "充值带支付", notes = "小程序")
    @RequestMapping(value = "/chargebuy", method = RequestMethod.POST)
    public PublicResult<Object> chargeAndBuy(@RequestBody ChargeOrderInput model) throws Exception {
        try {
            Order o = _orderService.selectById(model.machineOrder);
            if (o == null) return new PublicResult<>(PublicResultConstant.FAILED, null);
            if(o.getPayState()!=0)return  new PublicResult<>(PublicResultConstant.ORDER_HAD_BIND, null);
            Chargeorder r = _chargeOrderService.insertChargeOrder(model,false);
            if (!r.getId().isEmpty()) {
                SortedMap<String, Object> result;
                result = _chargeOrderService.wxChargePay(r);
                if (!result.isEmpty()) {
                    return new PublicResult<>(PublicResultConstant.SUCCESS, result);
                }
                return new PublicResult<>(PublicResultConstant.ERROR, r);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, r);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }

    @Pass
    @ApiOperation(value = "获取订单和余额", notes = "小程序")
    @RequestMapping(value = "/info/{openId}", method = RequestMethod.GET)
    public PublicResult<CustomerInfoDto> back(@PathVariable String openId) throws Exception {
        try {
            CustomerInfoDto res = new CustomerInfoDto(openId, 0, 0);
            EntityWrapper ew = new EntityWrapper();
            ew.eq("openId", openId);
            Customer r = _customerService.selectOne(ew);
            if (r != null) {
                ew.eq("payState",1);
                Integer count = _customerOrderService.selectCount(ew);
                res.setBalance(r.getBalance());
                res.setOrder(count);
                return new PublicResult<>(PublicResultConstant.SUCCESS, res);
            }
            return new PublicResult<>(PublicResultConstant.ERROR, null);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, null);
        }
    }

    @Pass
    @ApiOperation(value = "获取订单列表", notes = "小程序")
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public PublicResult<Page<CustomerOrder>> orders(@RequestBody PagedAndFilterInputDto page) throws Exception {
        EntityWrapper<CustomerOrder> filter = new EntityWrapper<>();
        String openId = (String) page.where.get("openId");
        Integer oId = (Integer) page.where.get("operaterId");
        if (openId != null && !openId.isEmpty()) {
            filter.eq("openId", openId);
        }
        if (oId != null && oId != 0) {
            filter.eq("operaterId", oId);
        }
        filter.eq("payState", 1);
        filter.orderBy("creationTime", false);
        if ((openId == null || openId.isEmpty()) && (oId == null || oId == 0))
            return new PublicResult<>(PublicResultConstant.NORESULTS, null);
        Page<CustomerOrder> res = _customerOrderService.selectPage(new Page<>(page.index, page.size), filter);
        res.getRecords().forEach(w -> {
            w.setTimeShow(DateTimeUtil.formatDateTimetoString(w.getCreationTime(), "yyyy年MM月dd日"));
        });
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }

    @Pass
    @ApiOperation(value = "获取订单详情", notes = "小程序")
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public PublicResult<CustomerOrder> order(@PathVariable String id) throws Exception {
        CustomerOrder o = _customerOrderService.selectById(id);
        if (o == null) return new PublicResult<>(PublicResultConstant.FAILED, null);
        return new PublicResult<>(PublicResultConstant.SUCCESS, o);
    }

    @Pass
    @ApiOperation(value = "更新订单进度", notes = "小程序")
    @RequestMapping(value = "/order/{id}/{state}", method = RequestMethod.PUT)
    public PublicResult<CustomerOrder> update(@PathVariable String id, @PathVariable Integer state) throws Exception {
        CustomerOrder o = _customerOrderService.selectById(id);
        if (o == null) return new PublicResult<>(PublicResultConstant.FAILED, null);
        o.setOrderState(state);
        EntityWrapper ew = new EntityWrapper();
        ew.eq("payState", 1).eq("orderState", 3).eq("customerOrder", o.getId());
        Order or = _orderService.selectOne(ew);
        _customerOrderService.updateById(o);
        if (or != null) {
            or.setOrderState(state);
            _orderService.updateById(or);
        }
        return new PublicResult<>(PublicResultConstant.SUCCESS, o);
    }
    //orderFrom 的定义 余额支付=1 
    @Pass
    @Log(description = "小程序:/余额支付")
    @ApiOperation(value = "使用余额支付", notes = "小程序")
    @RequestMapping(value = "/lesspay", method = RequestMethod.PUT)
    public PublicResult<Boolean> lesspay(@RequestBody CustomerOrderInput input) throws Exception {
        Order o = _orderService.selectById(input.machineOrder);
        if (o == null) return new PublicResult<>(PublicResultConstant.ORDER_NOT_FOUND, null);
        if(o.getPayState()!=0)return  new PublicResult<>(PublicResultConstant.ORDER_HAD_BIND, null);
        //扣减余额
        EntityWrapper ew = new EntityWrapper();
        ew.eq("openId", input.openId);
        Customer c = _customerService.selectOne(ew);
        if (c == null) return new PublicResult<>(PublicResultConstant.INVALID_USER, null);
        if (c.getBalance() <= 0) return new PublicResult<>(PublicResultConstant.USER_BALANCE_EMPTY, null);
        CustomerOrder order = new CustomerOrder();
        order.setAddress("-");
        order.setOrderState(1);
        order.setArea("-");
        order.setCommunity("-");
        order.setCustomerName(input.openId);
        order.setDryType(o.getDryType());
        order.setIsTime(1);
        order.setMobile("-");
        order.setOpenId(input.openId);
        order.setPayState(1);
        order.setPrice(o.getPrice());
        boolean res = _customerOrderService.insertOrUpdate(order);
        if (res) {
            o.setCustomerOrder(order.getId());
            o.setPayState(1);
            o.setOrderFrom(1);
            o.setOrderState(1);
            _orderService.updateById(o);
            c.setBalance(c.getBalance() - 1);
            _customerService.updateById(c);
            return new PublicResult<Boolean>(PublicResultConstant.SUCCESS, res);
        }
        return new PublicResult<>(PublicResultConstant.FAILED, null);
    }
    @Pass
    @Log(description = "烘干机:/次数支付")
    @ApiOperation(value = "烘干机次数支付操作", notes = "烘干机")
    @RequestMapping(value = "/machinecount", method = RequestMethod.POST)
    public PublicResult<Object> insertmachinecount(@RequestBody OrderInput model) throws Exception {
        try {
            Order r = _orderService.insertOrderCount(model);
            return new PublicResult<>(PublicResultConstant.SUCCESS, r);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }

    @Pass
    @Log(description = "烘干机:/下单操作")
    @ApiOperation(value = "烘干机下单操作", notes = "烘干机")
    @RequestMapping(value = "/machineorder", method = RequestMethod.POST)
    public PublicResult<Object> insertmachineorder(@RequestBody OrderInput model) throws Exception {
        try {
            Order r = _orderService.insertOrder(model);
            if (!r.getId().isEmpty()) {
                if (model.payType == 1) {
                    String token = getAccessToken();
                    String result = HttpUtil.genderQrcode(token, r.getId());
                    if (!result.isEmpty()) {
                        r.setPayUrl(result);
                    }
                } else if (model.payType == 2) {
                    String url = _orderService.aliPay(r);
                    if (!url.isEmpty()) {
                        r.setPayUrl(url);
                    } else {
                        return new PublicResult<>(PublicResultConstant.CREATE_ORDER_ERROR, null);
                    }
                }

            } else {
                return new PublicResult<>(PublicResultConstant.CREATE_ORDER_ERROR, null);
            }
            return new PublicResult<>(PublicResultConstant.SUCCESS, r);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }


    @Pass
    @Log(description = "管理员:/绑定用户订单操作")
    @ApiOperation(value = "绑定用户订单操作", notes = "烘干机")
    @RequestMapping(value = "/bindorder/{customerOrderId}/{machineOrderId}", method = RequestMethod.GET)
    public PublicResult<Object> bindOrder(@PathVariable String customerOrderId, @PathVariable String machineOrderId) throws Exception {
        try {
            Order r = _orderService.selectById(machineOrderId);
            CustomerOrder c = _customerOrderService.selectById(customerOrderId);
            if (r == null || c == null) return new PublicResult<>(PublicResultConstant.CREATE_ORDER_ERROR, null);
            if (r.getCustomerOrder() != null && !r.getCustomerOrder().isEmpty())
                return new PublicResult<>(PublicResultConstant.ORDER_HAD_BIND, null);
            if (c.getPayState() != 1) return new PublicResult<>(PublicResultConstant.ORDER_NOT_PAY, null);
            WebSocketServer s = WebSocketServer.getClient(r.getDeviceNum());
            if (s == null) return new PublicResult<>(PublicResultConstant.DEVICE_OFFLINE, null);
            r.setCustomerOrder(c.getId());
            r.setPayState(c.getPayState());
            r.setPrice(c.getPrice());
            r.setOrderFrom(4);
            r.setDryType(c.getDryType());
            _orderService.updateById(r);
            WebSocketMessage message = new WebSocketMessage(r.getDeviceNum(), r, SocketConstant.UPDATEORDERSTATE);
            s.sendMessageTo(message);
            return new PublicResult<>(PublicResultConstant.SUCCESS, r);
        } catch (Exception e) {
            return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
        }
    }

    ///获取access_token
    private String getAccessToken() {
        Object model = CacheManager.get("SP_AccessToken");
        if (model != null) return model.toString();
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String result = HttpUtil.sendGet(url, "grant_type=client_credential&appid=" + PayConfig.MP_CUSTOMER_APPID + "&secret=" + PayConfig.MP_CUSTOMER_SECRET);
        JSONObject o = JSONObject.parseObject(result);
        String token = o.getString("access_token");
        if (!token.isEmpty()) {
            CacheManager.put("SP_AccessToken", token,1000*60*60);
            return token;
        } else {
            return "";
        }
    }
}
