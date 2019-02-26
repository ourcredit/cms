package com.monkey.application.Menus;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.application.dtos.RoleMenuInput;
import com.monkey.core.entity.Rolemenu;
import com.monkey.core.mapper.RolemenuRepository;
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
public class RoleMenuServiceImpl extends ServiceImpl<RolemenuRepository, Rolemenu> implements  IRoleMenuService {

    @Override
    @Cacheable(value = "RoleToMenuServiceImpl:selectByRoleId", key = "'role_'.concat(#root.args[0])")
    public List<Rolemenu> selectByRoleId(Integer roleId) {
        QueryWrapper<Rolemenu> ew = new QueryWrapper<>();
        ew.eq("role_id", roleId);
        return this.list(ew);
    }

    @Override
    public Boolean insertRoleMenu(RoleMenuInput input) {
        QueryWrapper<Rolemenu> ew = new QueryWrapper<>();
        ew.eq("id", input.roleId);
        this.remove(ew);
        List<Rolemenu> urs=new ArrayList<>();
        for (Integer r : input.menus){
            Rolemenu u=new Rolemenu(input.roleId,r);
            urs.add(u);
        }
        return  this.saveBatch(urs);
    }

}
