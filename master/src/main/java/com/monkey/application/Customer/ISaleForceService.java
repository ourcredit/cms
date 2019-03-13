package com.monkey.application.Customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.monkey.application.Customer.dtos.UserForceDto;
import com.monkey.core.entity.SaleForce;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaohejing
 * @since 2019-03-13
 */
public interface ISaleForceService extends IService<SaleForce> {
        IPage<UserForceDto> pageForce(Page page, String name,String mobile,String month);
}
