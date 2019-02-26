package com.monkey.application.Menus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.core.entity.Menu;
import com.monkey.core.mapper.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
public class MenuServiceImpl extends ServiceImpl<MenuRepository, Menu> implements IMenuService {

    @Autowired
    private MenuRepository _menuRepository;

    @Override
    @Cacheable(value = "MenuServiceImpl:selectByIds", key = "'role_'.concat(#root.args[0])")
    public List<Menu> selectByIds(List<Integer> permissionIds) {
        QueryWrapper<Menu> ew = new QueryWrapper<>();
        ew.in("menu_id", permissionIds);
        return this.list(ew);
    }



    @Override
    @Cacheable(value = "MenuServiceImpl:findMenuByRoleId",key="'allmenus'")
    public List<Menu> findMenuByRoleId(List<Integer> roles) {
        return _menuRepository.selectList(null);
    }

    @Override
    @Cacheable(value = "MenuServiceImpl:findMenuByUserId", key = "'user_menu_'.concat(#root.args[0])")
    public List<Menu> findMenuByUserId(Integer userId) {
        return _menuRepository.selectCurrentMenus(userId);
    }
}
