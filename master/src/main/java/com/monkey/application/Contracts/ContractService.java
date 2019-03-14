package com.monkey.application.Contracts;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.core.entity.Contract;
import com.monkey.core.mapper.ContractRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2019-03-14
 */
@Service
public class ContractService extends ServiceImpl<ContractRepository, Contract> implements IContractService {

}
