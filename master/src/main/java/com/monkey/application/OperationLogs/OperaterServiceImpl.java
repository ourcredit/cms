package com.monkey.application.OperationLogs;

import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.core.entity.Operater;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.monkey.core.mapper.OperaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-11-07
 */
@Service
public class OperaterServiceImpl extends ServiceImpl<OperaterRepository, Operater> implements IOperaterService {
    @Autowired
    OperaterRepository _operaterRepository;
    @Override
    public Page<Operater> selectOperaterList(Page<Operater> page, String name) {
        Page<Operater> p = new Page<>();
        List<Operater> r = _operaterRepository.selectOperaterList(page, name);
        p.setRecords(r);
        p.setTotal(page.getTotal());
        return p;
    }

}
