package com.monkey.core.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.monkey.core.dtos.*;
import com.monkey.core.entity.Order;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhaohejing
 * @since 2018-08-13
 */
public interface OrderRepository extends BaseMapper<Order> {
    TodayStatical getOrderStatical( @Param("start") String start, @Param("end") String end);

    List<SalePercentDto> getTodaySalePercent(@Param("start") String start, @Param("end") String end);

    List<SalePercentDto> getMonthSalePercent(@Param("start") String start, @Param("end") String end);

    List<SalePercentDto> getPointSalePercent(@Param("start") String start, @Param("end") String end);

    List<SalePercentDto> getPayTypePercent(@Param("start") String start, @Param("end") String end);


    List<DeviceSaleStatical> getDeviceSaleStatical(Pagination page, @Param("deviceName") String deviceName, @Param("pointName") String pointName, @Param("start") Date start, @Param("end") Date end);

    List<ProductSaleStatical> getProductSaleStatical( Pagination page,@Param("productName") String productName, @Param("deviceName") String deviceName, @Param("start") Date start, @Param("end") Date end);
    List<ProductSaleStatical> getPointSaleStatical( Pagination page,@Param("pointName") String pointName, @Param("start") String start, @Param("end") String end);
   
    
}
