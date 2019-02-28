package com.monkey.application.Controls;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.application.dtos.UserRoleInput;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.UserRole;
import com.monkey.core.mapper.UserroleRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserroleRepository, UserRole> implements IUserRoleService {

    @Override
    @Cacheable(value = "UserToRoleServiceImpl:selectByUserId", key = "'user_role_'.concat(#root.args[0])")
    public List<UserRole> selectByUserId(Integer userId) {
        QueryWrapper<UserRole> ew = new QueryWrapper<>();
        ew.eq("userId", userId);
        List<UserRole> userToRoleList = this.list(ew);
        return ComUtil.isEmpty(userToRoleList)? null: userToRoleList;
    }
    @Override
    public Boolean  insertUserRoles(UserRoleInput input){
        QueryWrapper<UserRole> ew = new QueryWrapper<>();
        ew.eq("id", input.userId);
            this.remove(ew);
        List<UserRole> urs=new ArrayList<>();
        for (Integer r : input.roles){
            UserRole u=new UserRole(input.userId,r);
            urs.add(u);
        }
          return  this.saveBatch(urs);
    }
}
