package com.monkey.web.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.monkey.application.OperationLogs.IOperaterService;
import com.monkey.application.Payfor.IChargeorderService;
import com.monkey.application.Payfor.ICustomerOrderService;
import com.monkey.application.Payfor.IOrderService;
import com.monkey.application.Payfor.ISerialService;
import com.monkey.application.customer.ICustomerService;
import com.monkey.common.base.SocketConstant;
import com.monkey.common.util.CipherTextUtil;
import com.monkey.common.util.QyChatHelper;
import com.monkey.common.wechatsdk.OrderState;
import com.monkey.common.wechatsdk.PayConfig;
import com.monkey.common.wechatsdk.XMLUtil4jdom;
import com.monkey.core.entity.*;
import com.monkey.web.annotation.Pass;
import com.monkey.web.aspect.WebSocketServer;
import com.monkey.web.controller.dtos.WebSocketMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping(value = "/pay")
public class NotifyController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    IOrderService _orderService;
    @Autowired
    IChargeorderService _chargeOrderService;
    @Autowired
    ICustomerService _customerService;
    @Autowired
    ICustomerOrderService _customerOrderService;
    @Autowired
    ISerialService _serialService;
    @Autowired
    IOperaterService _operaterService;
    private final String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
    private final String susscessXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

    private SortedMap<Object, Object> getparams(Map<String, String> m) {
        //过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        return packageParams;
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public String insert(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String userAgent = request.getHeader("User-Agent");
            String agent = userAgent.toLowerCase();
            logger.error(agent);
            Order r = _orderService.selectById(id);
            if (!r.getId().isEmpty()) {
                String result = "";
                if (agent.indexOf("micromessenger") > 0) {
                    result = _orderService.weixinPayQrCode(r);
                } else if (agent.indexOf("alipayclient") > 0) {
                    result = _orderService.aliPay(r);
                }
                if (!result.isEmpty()) {
                    return result;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }


    ///插入流水表
    private void insertCustomerOrderSerial(CustomerOrder order, Integer type, String backOrder) {
        Serial s = new Serial();
        s.setDeviceName("");
        s.setOrderType(1);
        s.setPayType(type);
        s.setOrder(order.getId());
        s.setPrice(order.getPrice());
        s.setBackOrder(backOrder);
        s.setProductName("上门取货" + (order.getDryType() == 1 ? "纯棉" : (order.getDryType() == 2 ? "化纤" : "其他")));
        _serialService.insert(s);
    }

    ///插入流水表
    private void insertOrderSerial(Order order, Integer type, String backOrder) {
        Serial s = new Serial();
        s.setDeviceName(order.getDeviceName());
        s.setPointName(order.getPointName());
        s.setProductName(order.getDryType() == 1 ? "纯棉" : (order.getDryType() == 2 ? "化纤" : "其他"));
        s.setOrderType(2);
        s.setPayType(type);
        s.setOrder(order.getId());
        s.setPrice(order.getPrice());
        s.setBackOrder(backOrder);
        _serialService.insert(s);
    }

    ///插入流水表
    private void insertChargeSerial(Chargeorder order, Integer type, String backOrder) {
        Serial s = new Serial();
        s.setProductName(order.getProductName());
        s.setOrderType(3);
        s.setPayType(type);
        s.setOrder(order.getId());
        s.setPrice(order.getPrice());
        s.setBackOrder(backOrder);
        _serialService.insert(s);
    }

    @RequestMapping(value = "/wx/back/notify")
    public void weixin_back(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.error("调用退款回调方法----------------------------------");
        //获取退款的参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = XMLUtil4jdom.doXMLParse(sb.toString());
        SortedMap<Object, Object> packageParams = getparams(m);
        //处理业务开始
        //------------------------------
        if ("SUCCESS".equals(packageParams.get("return_code"))) {
            //解密
            String text = packageParams.get("req_info").toString();
            text = CipherTextUtil.dedede(text, PayConfig.WX_PAYFOR);
            m = XMLUtil4jdom.doXMLParse(text);
            EntityWrapper ew = new EntityWrapper();
            packageParams = getparams(m);
            if ("SUCCESS".equals(packageParams.get("refund_status"))) {
                logger.error("微信回掉参数:" + JSON.toJSONString(packageParams));
                // 这里是退款成功
                String out_trade_no = (String) packageParams.get("out_trade_no");
                String back_id = (String) packageParams.get("out_refund_no");
                Order o = _orderService.selectById(out_trade_no);
                if (o != null&&o.getPayState()!=2) {
                    o.setPayState(2);
                    o.setBackOrder(back_id);
                    _orderService.updateById(o);
                    insertOrderSerial(o,2,back_id);
                } else {
                    Chargeorder cho = _chargeOrderService.selectById(out_trade_no);
                    if (cho != null&&cho.getPayState()!=2) {
                        cho.setPayState(2);
                        _chargeOrderService.updateById(cho);
                        ew.eq("orderFrom", 2).eq("customerOrder", out_trade_no);
                        o = _orderService.selectOne(ew);
                        if (o != null) {
                            o.setPayState(2);
                            o.setBackOrder(back_id);
                            _orderService.updateById(o);
                        }
                        insertChargeSerial(cho,2,back_id);
                    } else {
                        CustomerOrder co = _customerOrderService.selectById(out_trade_no);
                        if (co !=null &&co.getPayState()!=2) {
                            co.setPayState(2);
                            _customerOrderService.updateById(co);
                            ew = new EntityWrapper();
                            ew.eq("orderFrom", 4).eq("customerOrder", out_trade_no);
                            o = _orderService.selectOne(ew);
                            if (o != null) {
                                o.setPayState(2);
                                o.setBackOrder(back_id);
                                _orderService.updateById(o);
                            }
                            insertCustomerOrderSerial(co,2,back_id);
                        }
                    }

                }
                if (o != null) {
                    insertOrderSerial(o, 2, back_id);
                }
                responesWechat(response, susscessXml, "通知微信.异步确认成功");
            } else {
                responesWechat(response, resXml, "退款款解密内容退款状态失败");
            }
        } else {
            responesWechat(response, resXml, "微信退款-----回调请求失败,");
        }
    }

    ///订单支付回掉
    @RequestMapping(value = "/wx/customer/notify")
    public void weixin_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.error("用户订单 回掉开始----------------------------------");
        //读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = XMLUtil4jdom.doXMLParse(sb.toString());
        SortedMap<Object, Object> packageParams = getparams(m);
        //处理业务开始
        //------------------------------
        if ("SUCCESS".equals(packageParams.get("result_code"))) {
            // 这里是支付成功
            //////////执行自己的业务逻辑////////////////
            String out_trade_no = (String) packageParams.get("out_trade_no");
            String transaction_id = (String) packageParams.get("transaction_id");
            //////////执行自己的业务逻辑（报存订单信息到数据库）////////////////
            CustomerOrder o = _customerOrderService.selectById(out_trade_no);
            if (o != null) {
                EntityWrapper e = new EntityWrapper();
                o.setPayState(1);
                o.setOrderNum(transaction_id);
                //扣减次数
                if (o.getIsTime() == 1) {
                    e.eq("openId", o.getOpenId());
                    Customer c = _customerService.selectOne(e);
                    if (c != null) {
                        Integer lessMore = c.getBalance() - 1;
                        c.setBalance(lessMore);
                        _customerService.updateById(c);
                    } else {
                        responesWechat(response, resXml, "微信回调-扣减次数-用户信息不存在");
                    }
                }
                e = new EntityWrapper();
                e.eq("region", o.getRegionCode());
                //分配订单
                List<Operater> os = _operaterService.selectList(e);
                if (!os.isEmpty() && os.size() > 0) {
                    Operater ope = os.get(0);
                    o.setOperaterId(ope.getId());
                    QyChatHelper.SendMessage(ope.getAccount());
                }
                Boolean r = _customerOrderService.updateById(o);
                if (r) {
                    insertCustomerOrderSerial(o, 1, "");
                    responesWechat(response, susscessXml, "微信--客户订单--通知微信确认成功");
                }
            }
        } else {
            responesWechat(response, resXml, "微信--客户订单--回调失败");
        }
    }

    //orderFrom状态=2 充值
    ///充值
    @RequestMapping(value = "/wx/charge/notify")
    public void weixin_charge_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.error("用户充值回掉----------------------------------");
        //读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = XMLUtil4jdom.doXMLParse(sb.toString());
        SortedMap<Object, Object> packageParams = getparams(m);
        //------------------------------
        if ("SUCCESS".equals(packageParams.get("result_code"))) {
            // 这里是支付成功
            //////////执行自己的业务逻辑////////////////
            String out_trade_no = (String) packageParams.get("out_trade_no");
            String transaction_id = (String) packageParams.get("transaction_id");
            //////////执行自己的业务逻辑（报存订单信息到数据库）////////////////
            Chargeorder o = _chargeOrderService.selectById(out_trade_no);
            if (o != null) {
                o.setPayState(1);
                o.setWechatOrder(transaction_id);
                Boolean r = _chargeOrderService.updateById(o);
                if (r) {
                    EntityWrapper e = new EntityWrapper();
                    e.eq("openId", o.getOpenId());
                    Customer c = _customerService.selectOne(e);
                    if (c != null) {
                        Integer count = o.getCount() + c.getBalance();
                        if(o.getMachineOrder()!=null&&!o.getMachineOrder().isEmpty()){
                            Order oo = _orderService.selectById(o.getMachineOrder());
                            if (oo != null) {
                                oo.setPayState(1);
                                oo.setOrderFrom(2);
                                oo.setOrderState(1);
                                oo.setCustomerOrder(o.getId());
                                oo.setOrderNum(transaction_id);
                                _orderService.updateById(oo);
                                WebSocketServer ws = WebSocketServer.getClient(oo.getDeviceNum());
                                if (ws == null) {
                                    responesWechat(response, resXml, "设备支付回调-" + oo.getDeviceNum() + "-该设备未在线");
                                    return;
                                }
                                WebSocketMessage mes = new WebSocketMessage(oo.getDeviceNum(), oo, SocketConstant.NOTIFYORDERSTATE);
                                ws.sendMessageTo(mes);
                                responesWechat(response, susscessXml, "订单号:" + out_trade_no + "扫码支付-.异步确认成功");
                            }
                        }
                        count = count - 1;
                        logger.error("用户次数累加");
                        c.setBalance(count);
                        _customerService.updateById(c);
                       
                    } else {
                        responesWechat(response, resXml, "用户充值 累加次数失败--用户不存在");
                    }
                    insertChargeSerial(o, 1, "");
                    responesWechat(response, susscessXml, "订单号:" + out_trade_no + "充值服务-通知微信.异步确认成功");
                }
            }
        } else {
            responesWechat(response, resXml, "微信充值---回调失败");
        }
    }

    ///扫码支付回掉
    @RequestMapping(value = "/wx/order/notify")
    public void weixin_order_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.error("设备扫码支付回调----------------------------------");
        //读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = XMLUtil4jdom.doXMLParse(sb.toString());
        SortedMap<Object, Object> packageParams = getparams(m);
        //------------------------------
        if ("SUCCESS".equals(packageParams.get("result_code"))) {
            // 这里是支付成功
            //////////执行自己的业务逻辑////////////////
            String out_trade_no = (String) packageParams.get("out_trade_no");
            String transaction_id = (String) packageParams.get("transaction_id");
            //////////执行自己的业务逻辑（报存订单信息到数据库）////////////////
            Order o = _orderService.selectById(out_trade_no);
            if (o != null) {
                o.setPayState(1);
                o.setOrderState(OrderState.dhg);
                o.setOrderNum(transaction_id);
                Boolean r = _orderService.updateById(o);
                if (r) {
                    WebSocketServer ws = WebSocketServer.getClient(o.getDeviceNum());
                    if (ws == null) {
                        responesWechat(response, resXml, "设备支付回调-" + o.getDeviceNum() + "-该设备未在线");
                        return;
                    }
                    WebSocketMessage mes = new WebSocketMessage(o.getDeviceNum(), o, SocketConstant.NOTIFYORDERSTATE);
                    ws.sendMessageTo(mes);
                    insertOrderSerial(o, 1, "");
                    responesWechat(response, susscessXml, "订单号:" + out_trade_no + "扫码支付-.异步确认成功");
                }
            }
        } else {
            responesWechat(response, resXml, "设备支付---回调失败");
        }
    }

    private void responesWechat(HttpServletResponse response, String xml, String error) throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(xml.getBytes());
        out.flush();
        out.close();
        logger.error(error);
    }

    /**
     * 支付宝支付回调
     *
     * @param request
     * @param response
     * @throws Exception void
     * @Author 小柒
     * @Date 2016年10月31日 更新日志 2016年10月31日 小柒 首次创建
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ali/order/notify")
    public String alipay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.error("调用支付宝支付成功回调方法");
        String message = "success";
        Map<String, String> params = new HashMap<String, String>();
// 取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        boolean signVerified = false;
        logger.error("支付宝订单参数" + JSON.toJSONString(params));

        try {
            // 计算得出通知验证结果
            signVerified = AlipaySignature.rsaCheckV1(params, PayConfig.PUBLIC_KEY, "utf-8", "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
            message = "failed";
        }
        if (signVerified) {
            // 若参数中的appid和填入的appid不相同，则为异常通知
            if (!PayConfig.AL_APPID.equals(params.get("app_id"))) {
                message = "failed";
            } else {
                String outtradeno = params.get("out_trade_no");
                //在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
                String status = params.get("trade_status");
                if (status.equals("WAIT_BUYER_PAY")) { // 如果状态是正在等待用户付款
                } else if (status.equals("TRADE_CLOSED")) { // 如果状态是未付款交易超时关闭，或支付完成后全额退款
                } else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) {
                    //////////执行自己的业务逻辑（报存订单信息到数据库）////////////////
                    Order o = _orderService.selectById(outtradeno);
                    String trade_no = params.get("trade_no");
                    if (o != null) {
                        if (o.getPayState() == 2) {
                            return "success";
                        }
                        o.setPayState(1);
                        o.setOrderState(OrderState.dhg);
                        o.setOrderNum(trade_no);
                        Boolean r = _orderService.updateById(o);
                        if (r) {
                            logger.error("更新订单状态并推送消息");
                            WebSocketServer ws = WebSocketServer.getClient(o.getDeviceNum());
                            if (ws == null) {
                                logger.error("设备支付回调-" + o.getDeviceNum() + "-该设备未在线");
                                return "success";
                            }
                            WebSocketMessage mes = new WebSocketMessage(o.getDeviceNum(), o, SocketConstant.NOTIFYORDERSTATE);
                            ws.sendMessageTo(mes);
                            insertOrderSerial(o, 1, "");
                            logger.error("订单号:" + outtradeno + "扫码支付-.异步确认成功");
                            return "success";
                        }
                    }
                }
                logger.error(outtradeno + "订单的状态已经修改为" + status);
            }
        } else { // 如果验证签名没有通过
            message = "failed";
            logger.error("验证签名失败！");
        }
        return message;
    }
}
