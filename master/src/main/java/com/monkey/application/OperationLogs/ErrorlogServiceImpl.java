package com.monkey.application.OperationLogs;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.monkey.core.entity.Errorlog;
import com.monkey.core.mapper.ErrorlogRepository;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-12-10
 */
@Service
public class ErrorlogServiceImpl extends ServiceImpl<ErrorlogRepository, Errorlog> implements IErrorlogService {

}
