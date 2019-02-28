package com.monkey.application.Controls;

import com.baomidou.mybatisplus.extension.service.IService;
import com.monkey.application.dtos.UserRoleInput;
import com.monkey.core.entity.UserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 根据用户ID查询人员角色
     * @param userId 用户ID
     * @return  结果
     */
    List<UserRole> selectByUserId(Integer userId);
    Boolean  insertUserRoles(UserRoleInput input);
}
