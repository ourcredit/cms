package com.monkey.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.monkey.core.entity.CustomerOrder;
import com.monkey.core.entity.Operater;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaohejing
 * @since 2018-11-08
 */
public interface CustomerOrderRepository extends BaseMapper<CustomerOrder> {
    List<CustomerOrder> selectCustomerOrderList(Pagination page, @Param("operaterId") String oid);
}
