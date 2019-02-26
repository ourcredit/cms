package com.monkey.application.OperationLogs;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.monkey.core.entity.Operater;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-11-07
 */
public interface IOperaterService extends IService<Operater> {
    Page<Operater> selectOperaterList(Page<Operater> page ,  String name);
}
