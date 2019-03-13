package com.monkey.application.Customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.application.Customer.dtos.UserForceDto;
import com.monkey.core.entity.SaleForce;
import com.monkey.core.mapper.SaleForceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2019-03-13
 */
@Service
public class SaleForceService extends ServiceImpl<SaleForceRepository, SaleForce> implements ISaleForceService {

    @Autowired
    SaleForceRepository _forceRepository;
    @Override
    public IPage<UserForceDto> pageForce(Page page, String name,String mobile,String month) {
       IPage<UserForceDto> res= _forceRepository.pageForce(page,name,mobile,month);
        return  res;
    }
}
