package com.monkey.application.Payfor;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.core.dtos.DeviceSaleStatical;
import com.monkey.core.dtos.ProductSaleStatical;
import com.monkey.core.entity.Order;
import com.baomidou.mybatisplus.service.IService;
import com.monkey.web.controller.dtos.OrderInput;
import com.monkey.web.controller.dtos.StaticalInput;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-08-13
 */
public interface IOrderService extends IService<Order> {
    Order insertOrder(OrderInput input) throws Exception;
    Order insertOrderCount(OrderInput input) throws Exception;
    String weixinPayQrCode(Order input) throws Exception;
    String weixinBack(Order input) throws  Exception;
    String aliPay(Order input) throws Exception;
    AlipayTradeRefundResponse aliback(Order input)throws  Exception;

    Map<String,Object> getDashboard();
    Map<String,Object> getStaticial(Date start,Date end);
     Page<ProductSaleStatical> getPointSaleStatical(Page<ProductSaleStatical> page,String point,String start,String end);
    Page<DeviceSaleStatical>  getDeviceSaleStatical(Page<DeviceSaleStatical> page, StaticalInput input );
    Page<ProductSaleStatical>  getProductSaleStatical(Page<ProductSaleStatical> page, StaticalInput input );
}
