package com.monkey.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.monkey.core.entity.Operater;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaohejing
 * @since 2018-11-07
 */
public interface OperaterRepository extends BaseMapper<Operater> {
    List<Operater> selectOperaterList(Pagination page, @Param("name") String name);

}
