package com.monkey.web.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.monkey.application.Device.ICategoryService;
import com.monkey.application.Device.IProductService;
import com.monkey.application.Device.IRegionService;
import com.monkey.application.Device.ITreeService;
import com.monkey.application.Menus.IMenuService;
import com.monkey.application.Menus.IRoleMenuService;
import com.monkey.application.Controls.IRoleService;
import com.monkey.application.Controls.IUserRoleService;
import com.monkey.application.Controls.IUserService;
import com.monkey.common.base.InitConst;
import com.monkey.core.entity.*;
import org.apache.tools.ant.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.FileReader;
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
    IRegionService _regionService;
    @Autowired
    ICategoryService _categoryService;
    @Autowired
    IProductService _productService;

    /**
     * 初始化数据结构
     */
    @Override
    public void run(String... strings) throws Exception {
        String r = env.getProperty("isInitData");
        if (r.equals("true")) {
            createDefaultTree();
            createUserRoles();
            createRoleMenus();
            InitRegion();
            createDefaultCateAndPro();
        }
    }

    public void createDefaultCateAndPro() {
        List<Category> cates = new ArrayList<Category>() {{
            add(new Category("纯棉"));
            add(new Category("化纤"));
            add(new Category("其他"));
        }};
        _categoryService.insertBatch(cates);
        List<Product> procs = new ArrayList<Product>() {{
            add(new Product("纯棉", "cm", "纯棉", 1D));
            add(new Product("化纤", "hx", "化纤", 1D));
            add(new Product("其他", "qt", "其他", 1D));
        }};
        _productService.insertBatch(procs);
    }

    public void createDefaultTree() {
//        EntityWrapper<Tree> ew = new EntityWrapper<>();
//        ew.eq("name","未分配设备");
//        Tree  t = _treeService.selectOne(ew);
//        if(t==null){
//            _treeService.insert(new Tree("未分配设备",null,null));
//        }
    }

    public void InitRegion() {
        String path = getClass().getClassLoader().getResource("area.json").toString();
        path = path.replace("\\", "/");
        if (path.contains(":")) {
            //path = path.substring(6);// 1
            path = path.replace("file:/", "");// 2
        }
        JSONObject jsonObject = null;
        try {
            String input = FileUtils.readFully(new FileReader(path));
            jsonObject = JSONObject.parseObject(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Region> rs = new ArrayList<>();
        if (jsonObject != null) {
            Map<String, String> pl = (Map<String, String>) jsonObject.get("province_list");
            for (String key :
                    pl.keySet()) {
                String o = pl.get(key);
                rs.add(new Region(key, o, 1));
            }

            Map<String, String> cl = (Map<String, String>) jsonObject.get("city_list");
            for (String key :
                    cl.keySet()) {
                String o = cl.get(key);
                rs.add(new Region(key, o, 2));
            }
            Map<String, String> col = (Map<String, String>) jsonObject.get("county_list");
            for (String key :
                    col.keySet()) {
                String o = col.get(key).toString();
                rs.add(new Region(key, o, 3));
            }
        }
        Boolean r = _regionService.insertBatch(rs, 100);
    }

    public void createUserRoles() {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.eq("account", InitConst._defaultUser.admin);
        User u = _userService.selectOne(ew);
        if (u == null) {
            Boolean b = _userService.insert(new User(InitConst._defaultUser.admin,
                    InitConst._defaultUser.defaultPassword,
                    InitConst._defaultUser.admin, 1));
            if (b) {
                u = _userService.selectOne(ew);
            }
        }
        EntityWrapper<Role> rw = new EntityWrapper<>();
        rw.eq("roleName", InitConst._defaultRole.admin);
        Role r = _roleService.selectOne(rw);
        if (r == null) {
            Boolean b = _roleService.insert(new Role(InitConst._defaultRole.admin, InitConst._defaultRole.admin, 1, 0));
            if (b) {
                r = _roleService.selectOne(rw);
            }
        }
        rw = new EntityWrapper<>();
        rw.eq("roleName", InitConst._defaultRole.def);
        Role de = _roleService.selectOne(rw);
        if (de == null) {
            Boolean b = _roleService.insert(new Role(InitConst._defaultRole.def, InitConst._defaultRole.def, 1, 1));
            if (b) {
                de = _roleService.selectOne(rw);
            }
        }
        List<Userrole> rels = new ArrayList<>();
        rels.add(new Userrole(u.getId(), r.getId()));
        rels.add(new Userrole(u.getId(), de.getId()));
        _userRoleService.delete(new EntityWrapper<>());
        _userRoleService.insertBatch(rels);
    }

    public void createRoleMenus() {
        List<InitConst._menu.MenuInfo> list = InitConst._menu.menuList;
        insertMenu(list, null);
        EntityWrapper e = new EntityWrapper<>();
        Role r = _roleService.selectOne(e.eq("roleName", InitConst._defaultRole.admin));
        if (r != null) {
            List<Menu> lists = _menuService.selectList(new EntityWrapper<>());
            List<Rolemenu> rms = new ArrayList<>();
            for (Menu m : lists) {
                rms.add(new Rolemenu(r.getId(), m.getId()));
            }
            _roleMenuService.insertBatch(rms);
        }
    }

    public void insertMenu(List<InitConst._menu.MenuInfo> list, Integer parentId) {
        EntityWrapper e;
        for (InitConst._menu.MenuInfo item : list) {
            e = new EntityWrapper<>();
            e.eq("name", item.Name);
            Menu m = _menuService.selectOne(e);
            if (m == null) {
                m = new Menu(item.Name, item.Code, item.Url, item.Type, parentId);
                Boolean r = _menuService.insert(m);
                if (r) {
                    m = _menuService.selectOne(e);
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
                    _menuService.insertBatch(b);
                }
                if (!a.isEmpty()) {
                    insertMenu(item.Children, m.getId());
                }
            }
        }

    }
}
