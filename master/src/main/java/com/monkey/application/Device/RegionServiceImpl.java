package com.monkey.application.Device;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.monkey.core.entity.Region;
import com.monkey.core.mapper.RegionRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-11-12
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionRepository, Region> implements IRegionService {

}
