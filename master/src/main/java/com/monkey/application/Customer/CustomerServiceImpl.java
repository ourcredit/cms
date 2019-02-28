package com.monkey.application.Customer;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.core.entity.Customer;
import com.monkey.core.mapper.CustomerRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-28
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerRepository, Customer> implements ICustomerService {

}
