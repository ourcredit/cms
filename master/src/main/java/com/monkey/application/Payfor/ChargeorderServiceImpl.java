package com.monkey.application.Payfor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.monkey.common.wechatsdk.HttpUtil;
import com.monkey.common.wechatsdk.PayConfig;
import com.monkey.common.wechatsdk.PayToolUtil;
import com.monkey.common.wechatsdk.XMLUtil4jdom;
import com.monkey.core.entity.Chargeorder;
import com.monkey.core.mapper.ChargeorderRepository;
import com.monkey.web.controller.dtos.ChargeOrderInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-11-06
 */
@Service
public class ChargeorderServiceImpl extends ServiceImpl<ChargeorderRepository, Chargeorder> implements IChargeorderService {
    protected static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    ChargeorderRepository _chargeRepository;
    /*
     * 创建订单*/
    @Override
    public Chargeorder insertChargeOrder(ChargeOrderInput input,Boolean isCharge) throws Exception {
        Chargeorder o = new Chargeorder();
        o.setOrderState(0);
        o.setPayState(0);
        o.setOpenId(input.openId);
        if(input.count==0){
          Integer p=input.price==2000?1:(input.price==10000?6:13);
          o.setCount(p);
        }else{
            o.setCount(input.count);
        }
        if(input.count==1){
            o.setIsPayFor(isCharge?1:0);
        }else{
            o.setIsPayFor(1);
        }
        o.setCount(input.count);
        o.setPrice(input.price);
        o.setIsPayFor(input.isPayFor);
        o.setMachineOrder(input.machineOrder);
        o.setProductName(input.productName);
        _chargeRepository.insert(o);
        return o;
    }
    /**
     * @Description: 发起微信支付
     * @param input 订单
     */
    public SortedMap<String, Object> wxChargePay(Chargeorder input){
        try {
            String currTime = PayToolUtil.getCurrTime();
            String strTime = currTime.substring(8, currTime.length());
            String strRandom = PayToolUtil.buildRandom(4) + "";
            String nonce_str = strTime + strRandom;
            //商品名称
            String body = input.getProductName();
            // 获取发起电脑 ip
            String spbill_create_ip = PayConfig.Create_Ip;
            // 回调接口
            //组装参数，用户生成统一下单接口的签名
            SortedMap<Object, Object> packageParams = new TreeMap<>();
            packageParams.put("appid", PayConfig.WX_APPID);
            packageParams.put("mch_id", PayConfig.WX_SHOPID);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", input.getId());//商户订单号
            packageParams.put("total_fee", input.getPrice().toString());//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", PayConfig.WX_CHARGE_NOTIFY_URL);//支付成功后的回调地址
            packageParams.put("trade_type", "JSAPI");//支付方式
            packageParams.put("openid", input.getOpenId());
            String sign = PayToolUtil.createSign( packageParams, PayConfig.WX_PAYFOR);
            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + PayConfig.WX_APPID + "</appid>"
                    + "<body>"+body+"</body>"
                    + "<mch_id>" + PayConfig.WX_SHOPID + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + PayConfig.WX_CHARGE_NOTIFY_URL + "</notify_url>"
                    + "<openid>" + input.getOpenId() + "</openid>"
                    + "<out_trade_no>" + input.getId() + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + input.getPrice() + "</total_fee>"
                    + "<trade_type>JSAPI</trade_type>"
                    + "<sign>" + sign + "</sign>"
                    + "</xml>";
                    logger.error("调试模式_统一下单接口 请求XML数据：" + xml);
            //调用统一下单接口，并接受返回的结果
            String result = HttpUtil.postData(PayConfig.WX_PAYURL, xml);
            // 将解析结果存储在HashMap中
            Map map = XMLUtil4jdom.doXMLParse(result);
            logger.warn(JSON.toJSONString(map));
            String return_code = (String) map.get("return_code");//返回状态码

            SortedMap<String, Object> response = new TreeMap<String, Object>();
            response.put("appid", PayConfig.WX_APPID);
            if (return_code.equals("SUCCESS")) {
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                //拼接签名需要的参数
                packageParams = new TreeMap<>();
                packageParams.put("appId", PayConfig.WX_APPID);
                packageParams.put("nonceStr", nonce_str);
                packageParams.put("package", "prepay_id="+prepay_id);
                packageParams.put("signType", "MD5");
                packageParams.put("timeStamp", timeStamp+"");//商户订单号
                String paySign = PayToolUtil.createSign( packageParams, PayConfig.WX_PAYFOR);
                response.put("paySign", paySign);
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
