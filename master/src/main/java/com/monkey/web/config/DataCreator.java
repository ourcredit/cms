package com.monkey.web.config;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.monkey.application.Device.ICategoryService;
import com.monkey.application.Device.ITreeService;
import com.monkey.application.Menus.IMenuService;
import com.monkey.application.Menus.IRoleMenuService;
import com.monkey.application.Controls.IRoleService;
import com.monkey.application.Controls.IUserRoleService;
import com.monkey.application.Controls.IUserService;
import com.monkey.common.base.InitConst;
import com.monkey.core.entity.*;
import com.monkey.core.mapper.UserroleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DataCreator implements CommandLineRunner {

    @Autowired
    private Environment env;
    @Autowired
    IUserService _userService;
    @Autowired
    IRoleService _roleService;
    @Autowired
    IMenuService _menuService;

    @Autowired
    IUserRoleService _userRoleService;
    @Autowired
    IRoleMenuService _roleMenuService;
    @Autowired
    ITreeService _treeService;
    @Autowired
    ICategoryService _categoryService;

    /**
     * 初始化数据结构
     */
    @Override
    public void run(String... strings) throws Exception {
        String r = env.getProperty("isInitData");
        if (r.equals("true")) {
            createUserRoles();
            createRoleMenus();
            createDefaultCateAndPro();
        }
    }

    public void createDefaultCateAndPro() {
    }
    public void createUserRoles() {

        QueryWrapper ew = new QueryWrapper<User>();
        ew.eq("account", InitConst._defaultUser.admin);
        User u = _userService.getOne(ew);
        if (u == null) {
            Boolean b = _userService.save(new User(InitConst._defaultUser.admin,
                    InitConst._defaultUser.defaultPassword,
                    InitConst._defaultUser.admin, 1));
            if (b) {
                u = _userService.getOne(ew);
            }
        }
        QueryWrapper<Role> rw = new QueryWrapper<>();
        rw.eq("roleName", InitConst._defaultRole.admin);
        Role r = _roleService.getOne(rw);
        if (r == null) {
            Boolean b = _roleService.save(new Role(InitConst._defaultRole.admin, InitConst._defaultRole.admin, 1, 0));
            if (b) {
                r = _roleService.getOne(rw);
            }
        }
        rw = new QueryWrapper<>();
        rw.eq("roleName", InitConst._defaultRole.def);
        Role de = _roleService.getOne(rw);
        if (de == null) {
            Boolean b = _roleService.save(new Role(InitConst._defaultRole.def, InitConst._defaultRole.def, 1, 1));
            if (b) {
                de = _roleService.getOne(rw);
            }
        }
        List<Userrole> rels = new ArrayList<>();
        rels.add(new Userrole(u.getId(), r.getId()));
        rels.add(new Userrole(u.getId(), de.getId()));
        _userRoleService.remove(new QueryWrapper<>());
        _userRoleService.saveBatch(rels);
    }

    public void createRoleMenus() {
        List<InitConst._menu.MenuInfo> list = InitConst._menu.menuList;
        insertMenu(list, null);
        QueryWrapper e = new QueryWrapper<>();
        e.eq("roleName", InitConst._defaultRole.admin);
        Role r = _roleService.getOne(e);
        if (r != null) {
            List<Menu> lists = _menuService.list();
            List<Rolemenu> rms = new ArrayList<>();
            for (Menu m : lists) {
                rms.add(new Rolemenu(r.getId(), m.getId()));
            }
            _roleMenuService.saveBatch(rms);
        }
    }

    public void insertMenu(List<InitConst._menu.MenuInfo> list, Integer parentId) {
        QueryWrapper e;
        for (InitConst._menu.MenuInfo item : list) {
            e = new QueryWrapper<>();
            e.eq("name", item.Name);
            Menu m = _menuService.getOne(e);
            if (m == null) {
                m = new Menu(item.Name, item.Code, item.Url, item.Type, parentId);
                Boolean r = _menuService.save(m);
                if (r) {
                    m = _menuService.getOne(e);
                }
            }
            if (item.Children != null && !item.Children.isEmpty()) {
                List<InitConst._menu.MenuInfo> a = new ArrayList<>();
                List<Menu> b = new ArrayList<>();
                for (InitConst._menu.MenuInfo t : item.Children) {
                    if (t.Type == 1) {
                        a.add(t);
                    } else {
                        b.add(new Menu(t.Name, t.Code, t.Url, t.Type, m.getId()));
                    }
                }
                if (!b.isEmpty()) {
                    _menuService.saveBatch(b);
                }
                if (!a.isEmpty()) {
                    insertMenu(item.Children, m.getId());
                }
            }
        }

    }
}
