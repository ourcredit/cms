package com.monkey.application.Controls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.application.Menus.IRoleMenuService;
import com.monkey.core.dtos.RoleDto;
import com.monkey.core.entity.Menu;
import com.monkey.core.entity.Role;
import com.monkey.core.entity.Rolemenu;
import com.monkey.core.mapper.MenuRepository;
import com.monkey.core.mapper.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleRepository, Role> implements IRoleService {
    @Autowired
    RoleRepository _roleRepository;
    @Autowired
    IRoleMenuService _roleMenuService;
    @Autowired
    MenuRepository _menuRepository;

    @Override
    public RoleDto selectRoleAndPermissionsById(Integer id) {
        return _roleRepository.selectRoleAndPermissionsById(id);
    }
    @Override
    @CachePut(value = "allPermissions")
    public List<String> getAllPermissions(){
        List result=new ArrayList<String>();
        QueryWrapper e=new QueryWrapper<>();
       List<Menu> l= _menuRepository.selectList(e);
       for(Menu m :l){
           result.add(m.getCode());
       }
       return  result;
    }
    @Override
    public Boolean insertRoleAndPermissions(RoleDto model) {

        Role role = new Role(model.getRoleName(), model.getDisplayName(),model.getIsActive(),model.getIsStatic());
        role.setId(model.getId());
        role.setDescription(model.getDescription());
        QueryWrapper ew = new QueryWrapper();

        if (model.getId() == null || model.getId() == 0) {
            _roleRepository.insert(role);
        } else {
            ew.eq("Id", model.getId());
            _roleRepository.update(role, ew);
        }
        if (!model.getPermissions().isEmpty()) {
            ew = new QueryWrapper();
            ew.eq("roleId", role.getId());
            _roleMenuService.remove(ew);
            ew = new QueryWrapper();
            ew.in("code", model.getPermissions());
            List<Menu> lists = _menuRepository.selectList(ew);
            List<Rolemenu> rms=new ArrayList<>();
            for (Menu x:lists){
                rms.add(new Rolemenu(role.getId(),x.getId()));
            }
            _roleMenuService.saveBatch(rms);
        }
        return role.getId() != null && role.getId() != 0;
    }
}
