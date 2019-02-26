package com.monkey.application.Payfor;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.application.Device.IDeviceService;
import com.monkey.application.Device.IProductService;
import com.monkey.common.util.DateTimeUtil;
import com.monkey.common.util.DateUtil;
import com.monkey.common.wechatsdk.*;
import com.monkey.core.dtos.DeviceSaleStatical;
import com.monkey.core.dtos.ProductSaleStatical;
import com.monkey.core.dtos.SalePercentDto;
import com.monkey.core.dtos.TodayStatical;
import com.monkey.core.entity.*;
import com.monkey.core.mapper.ChargeorderRepository;
import com.monkey.core.mapper.OrderRepository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.monkey.core.mapper.ProductRepository;
import com.monkey.web.aspect.WebSocketServer;
import com.monkey.web.controller.dtos.OrderInput;
import com.monkey.web.controller.dtos.StaticalInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-08-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderRepository, Order> implements IOrderService {
    //创建订单
    @Autowired
    OrderRepository _orderRepository;

    @Autowired
    IDeviceService _deviceService;
    @Autowired
    IProductService _productService;
    @Autowired
    ISerialService _serialService;
    @Autowired
    ChargeorderRepository _chargeOrderRepository;
    protected static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${pay.backFile}")
    private String file;

    /*
     * 创建订单*/
    @Override
    public Order insertOrder(OrderInput input) throws Exception {
        EntityWrapper ew = new EntityWrapper();
        String type = input.dryType == 1 ? "纯棉" : (input.payType == 2 ? "化纤" : "其他");
        ew.eq("productType", type);
        Product pd = _productService.selectOne(ew);
        if (pd == null) throw new Exception("商品不存在");

        ew = new EntityWrapper();
        ew.eq("deviceNum", input.deviceNum);
        Order o = new Order();
        o.setPayType(input.payType);
        o.setOrderState(OrderState.dhg);
        o.setPayState(0);
        o.setDryType(input.dryType);
        o.setDeviceNum(input.deviceNum);
        Double price = input.payType == 1 ? pd.getPrice() : pd.getPrice() / 100;
        String str = price.toString().substring(0, price.toString().indexOf(".")) ;
        o.setPrice(Integer.parseInt(str));
        Device d = _deviceService.selectOne(ew);
        if (d != null) {
            o.setPointName(d.getPointName());
            o.setDeviceName(d.getDeviceName());
        }
        _orderRepository.insert(o);
        return o;
    }

    @Override
    public Order insertOrderCount(OrderInput input) throws Exception {
        EntityWrapper ew = new EntityWrapper();
        String type = input.dryType == 1 ? "纯棉" : (input.payType == 2 ? "化纤" : "其他");
        ew.eq("productType", type);
        Product pd = _productService.selectOne(ew);
        if (pd == null) throw new Exception("商品不存在");
        ew = new EntityWrapper();
        ew.eq("deviceNum", input.deviceNum);

        Order o = new Order();
        o.setPayType(4);
        o.setOrderState(OrderState.dhg);
        o.setPayState(0);
        o.setDryType(input.dryType);
        o.setDeviceNum(input.deviceNum);
        Device d = _deviceService.selectOne(ew);
        if (d != null) {
            o.setPointName(d.getPointName());
            o.setDeviceName(d.getDeviceName());
            o.setPrice(d.getCount());
        }
        if(d.getCount()<=0){
            throw  new Exception("设备可用次数小于0");
        }
        _orderRepository.insert(o);
        return o;
    }

    /*微信支付*/
    @Override
    public String weixinPayQrCode(Order input) throws Exception {
        String out_trade_no = input.getId(); //订单号 （调整为自己的生产逻辑）
        // 账号信息
        String appid = PayConfig.WX_APPID;// appid
        //String appsecret = PayConfigUtil.APP_SECRET; // appsecret
        String mch_id = PayConfig.WX_SHOPID; // 商业号

        String currTime = PayToolUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayToolUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;

        // 获取发起电脑 ip
        String spbill_create_ip = PayConfig.Create_Ip;
        // 回调接口
        String notify_url = PayConfig.WX_ORDER_NOTIFY_URL;
        String trade_type = "NATIVE";

        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", input.getDeviceNum());  //（调整为自己的名称）
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", input.getPrice().toString()); //价格的单位为分
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        String sign = PayToolUtil.createSign(packageParams, PayConfig.WX_PAYFOR);
        packageParams.put("sign", sign);

        String requestXML = PayToolUtil.getRequestXml(packageParams);
        logger.error("微信扫码支付生成二维码回掉结果:" + requestXML);

        String resXml = HttpUtil.postData(PayConfig.WX_PAYURL, requestXML);

        Map map = XMLUtil4jdom.doXMLParse(resXml);
        logger.warn(JSON.toJSONString(map));
        String urlCode = (String) map.get("code_url");

        return urlCode;
    }

    /*
     * 阿里云支付*/
    @Override
    public String aliPay(Order product) throws Exception {
        //todo 配置阿里支付的参数
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", PayConfig.AL_APPID, PayConfig.PRIVATE_KEY,
                "json", "UTF-8", PayConfig.PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
        Map<String, String> r = new HashMap<>();
        r.put("out_trade_no", product.getId());
        r.put("total_amount", product.getPrice() + "");
        r.put("subject", product.getDryType() == 1 ? "纯棉" : (product.getDryType() == 2 ? "化纤" : "其他"));
        r.put("store_id", "");
        r.put("timeout_express", "120m");
        String w = JSON.toJSONString(r);
        request.setNotifyUrl(PayConfig.AL_ORDER_NOTIFY_URL);
        request.setBizContent(w);
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        logger.error("创建阿里支付订单:结果:" + response.getBody());
        //根据response中的结果继续业务逻辑处理
        return response.getQrCode();
    }

    /*
     * 阿里云退款*/
    @Override
    public AlipayTradeRefundResponse aliback(Order input) throws Exception {
        if (input.getOrderFrom() != 0) {
            throw new Exception("只能对上位机订单退款");
        }
        // String out_trade_no = input.getId(); //订单号 （调整为自己的生产逻辑）
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", PayConfig.AL_APPID, PayConfig.PRIVATE_KEY,
                "json", "UTF-8", PayConfig.PUBLIC_KEY, "RSA2");
        //获得初始化的AlipayClient
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();//创建API对应的request类
        Map<String, String> r = new HashMap<>();
        r.put("out_trade_no", input.getId());
        r.put("out_request_no", System.currentTimeMillis() + "");
        r.put("refund_amount", input.getPrice() + "");
        String w = JSON.toJSONString(r);
        request.setBizContent(w); //设置业务参数
        //   request.setNotifyUrl(PayConfig.Alibackurl);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        //通过alipayClient调用API，获得对应的response类
        logger.error("创建阿里退款订单:结果:" + response.getBody());
        if (response.getTradeNo() != null && !response.getTradeNo().isEmpty()) {
            Order o = _orderRepository.selectById(input.getId());
            o.setBackOrder(response.getTradeNo());
            o.setPayState(2);
            Integer rr = _orderRepository.updateById(o);
            logger.error("更新订单并插入流水:" + rr);
            insertSerial(input, response.getTradeNo());
        }
        return response;
    }

    ///插入流水表
    private void insertSerial(Order order, String backOrder) {
        Serial s = new Serial();
        s.setDeviceName(order.getDeviceName());
        s.setOrder(order.getId());
        s.setOrderType(2);
        s.setPayType(2);
        s.setProductName("支付宝退款");
        s.setPrice(order.getPrice());
        s.setBackOrder(backOrder);
        _serialService.insert(s);
    }

    @Override
    public Page<DeviceSaleStatical> getDeviceSaleStatical(Page<DeviceSaleStatical> page, StaticalInput input) {
        List<DeviceSaleStatical> t = _orderRepository.getDeviceSaleStatical(page, input.deviceName, input.pointName, input.start, input.end);
        return page.setRecords(t);
    }

    @Override
    public Page<ProductSaleStatical> getProductSaleStatical(Page<ProductSaleStatical> page, StaticalInput input) {
        List<ProductSaleStatical> t = _orderRepository.getProductSaleStatical(page, input.productName, input.deviceName, input.start, input.end);
        return page.setRecords(t);
    }
    @Override 
    public   Page<ProductSaleStatical> getPointSaleStatical(Page<ProductSaleStatical> page , String point, String start, String end){
        List<ProductSaleStatical> t = _orderRepository.getPointSaleStatical(page,point,start ,end);
        return page.setRecords(t);
    }
    @Override
    public Map<String, Object> getDashboard() {
        Map<String, Object> result = new HashMap<>();
        List<SalePercentDto> dr = _orderRepository.getTodaySalePercent(DateUtil.getStartTime(), DateUtil.getEndTime());
        List<SalePercentDto> mr = _orderRepository.getMonthSalePercent(DateUtil.getBeginDayOfMonth(), DateUtil.getEndDayOfMonth());
        List<SalePercentDto> pr = _orderRepository.getPayTypePercent(DateUtil.getBeginDayOfMonth(), DateUtil.getEndDayOfMonth());
        List<SalePercentDto> topdr = _orderRepository.getPointSalePercent(DateUtil.getStartTime(), DateUtil.getEndTime());
        List<SalePercentDto> topmr = _orderRepository.getPointSalePercent(DateUtil.getBeginDayOfMonth(), DateUtil.getEndDayOfMonth());
        result.put("todaySale", getDayKeysAndValues(dr));
        result.put("monthSale", getMonthKeysAndValues(mr));
        result.put("payType", pr);
        result.put("todayTop", getKeysAndValues(topdr));
        result.put("monthTop", getKeysAndValues(topmr));
        return result;
    }

    @Override
    public Map<String, Object> getStaticial(Date start, Date end) {
        Map<String, Object> result = new HashMap<>();
        TodayStatical tr = _orderRepository.getOrderStatical(DateUtil.getStartTime(), DateUtil.getEndTime());
        TodayStatical mr = _orderRepository.getOrderStatical(DateUtil.getStartTime(start), DateUtil.getEndTime(end));
        Integer count = WebSocketServer.getOnlineCount();
        tr.setDevice(count);
        result.put("today", tr);
        result.put("month", mr);
        return result;
    }

    /*
     * 微信退款功能
     * */
    @Override
    public String weixinBack(Order input) throws Exception {
        String out_trade_no = "";
        String price = "";
        if (input.getOrderFrom() == 0) {
            //设备上支付的退款
            price = input.getPrice() + "";
            out_trade_no = input.getId(); //订单号 （调整为自己的生产逻辑）
        } else if (input.getOrderFrom() == 2) {
            //充值一次订单的退款
            Chargeorder co = _chargeOrderRepository.selectById(input.getCustomerOrder());
            if (co != null) {
                price = co.getPrice() + "";
            }
            out_trade_no = input.getCustomerOrder();
        }else if(input.getOrderFrom()==4){
            //客户订单来的退款
            price=input.getPrice()+"";
            out_trade_no=input.getCustomerOrder();
        }
        // 账号信息
        String appid = PayConfig.WX_APPID;  // appid
        String mch_id = PayConfig.WX_SHOPID; // 商业号
        String key = PayConfig.WX_PAYFOR; // key
        String currTime = PayToolUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayToolUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        // 回调接口
        String notify_url = PayConfig.WX_BACK_NOTIFY_URL;
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("out_refund_no", input.getDeviceNum() + "" + System.currentTimeMillis());
        packageParams.put("refund_desc", input.getOrderFrom() + "");  //（调整为自己的名称）
        packageParams.put("total_fee", price); //价格的单位为分
        packageParams.put("refund_fee", price);
        packageParams.put("notify_url", notify_url);
        String sign = PayToolUtil.createSign(packageParams, key);
        packageParams.put("sign", sign);

        String requestXML = PayToolUtil.getRequestXml(packageParams);
        String resXml = HttpUtil.back(requestXML, file);
        logger.error("退款的微信回掉参数:" + resXml);
        return resXml;
    }

    private Map<String, Object> getDayKeysAndValues(List<SalePercentDto> list) {
        List<String> keys = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            String t = "";
            if (i <= 9) {
                t = "0" + i;
            } else {
                t = "" + i;
            }
            keys.add(t);
            Double v = 0.0;
            for (int j = 0; j < list.size(); j++) {
                SalePercentDto temp = list.get(j);
                if (temp.getTime().equals(t)) {
                    v = temp.getCount();
                }
            }
            values.add(v);
        }
        Map<String, Object> r = new HashMap<>();
        r.put("keys", keys);
        r.put("values", values);
        return r;
    }


    private Map<String, Object> getKeysAndValues(List<SalePercentDto> list) {
        List<String> keys = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            SalePercentDto temp = list.get(j);
            keys.add(temp.getTime());
            values.add(temp.getCount());
        }
        Map<String, Object> r = new HashMap<>();
        r.put("keys", keys);
        r.put("values", values);
        return r;
    }

    private Map<String, Object> getMonthKeysAndValues(List<SalePercentDto> list) {
        List<String> keys = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            String t = "";
            if (i <= 9) {
                t = "0" + i;
            } else {
                t = "" + i;
            }
            keys.add(t);
            Double v = 0.0;
            for (int j = 0; j < list.size(); j++) {
                SalePercentDto temp = list.get(j);
                if (temp.getTime().equals(t)) {
                    v = temp.getCount();
                }
            }
            values.add(v);
        }
        Map<String, Object> r = new HashMap<>();
        r.put("keys", keys);
        r.put("values", values);
        return r;
    }
}
