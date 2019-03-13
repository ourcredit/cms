package com.monkey.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monkey.application.Customer.dtos.UserForceDto;
import com.monkey.core.dtos.RoleDto;
import com.monkey.core.entity.Role;
import com.monkey.core.entity.SaleForce;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaohejing
 * @since 2019-03-13
 */
public interface SaleForceRepository extends BaseMapper<SaleForce> {
     IPage<UserForceDto> pageForce(@Param("page") Page page, @Param("name") String name, @Param("mobile") String mobile, @Param("month") String month);
}
