package com.monkey.application.Menus;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.application.dtos.RoleMenuInput;
import com.monkey.core.entity.RoleMenu;
import com.monkey.core.mapper.RoleMenuRepository;
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
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuRepository, RoleMenu> implements  IRoleMenuService {

    @Override
    @Cacheable(value = "RoleToMenuServiceImpl:selectByRoleId", key = "'role_'.concat(#root.args[0])")
    public List<RoleMenu> selectByRoleId(Integer roleId) {
        QueryWrapper<RoleMenu> ew = new QueryWrapper<>();
        ew.eq("role_id", roleId);
        return this.list(ew);
    }

    @Override
    public Boolean insertRoleMenu(RoleMenuInput input) {
        QueryWrapper<RoleMenu> ew = new QueryWrapper<>();
        ew.eq("id", input.roleId);
        this.remove(ew);
        List<RoleMenu> urs=new ArrayList<>();
        for (Integer r : input.menus){
            RoleMenu u=new RoleMenu(input.roleId,r);
            urs.add(u);
        }
        return  this.saveBatch(urs);
    }

}
