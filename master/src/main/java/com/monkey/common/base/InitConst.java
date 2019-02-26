package com.monkey.common.base;


import java.util.ArrayList;
import java.util.List;
public interface InitConst {
    public static class _defaultTenant {
        public static final String admin = "default";
    }
    public static class _defaultUser {
        public static final String admin = "admin";
        public static final String def = "user";
        public static final String defaultPassword = "1234567";
    }

    public static class _defaultRole {
        public static final String admin = "admin";
        public static final String def = "default";
    }
    public static class _menu {
        public static List<MenuInfo> menuList = new ArrayList<MenuInfo>() {{
            add(new MenuInfo("首页", PermissionConst._dashboard.list, "/dashboard", 1, null));
            add(new MenuInfo("渠道", PermissionConst._orders.list, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("渠道列表", PermissionConst._orders._order.list, "/order/list", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("查看订单", PermissionConst._orders._order.list, "", 2, null));
                    add(new MenuInfo("退款", PermissionConst._orders._order.back, "", 2, null));
                    add(new MenuInfo("直接退款", PermissionConst._orders._order.superBack, "", 2, null));
                    add(new MenuInfo("统计", PermissionConst._orders._order.statical, "", 2, null));
                }}));
            }}));
            add(new MenuInfo("客户", PermissionConst._pointer.list, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("客户列表", PermissionConst._pointer._point.list, "/pointer/point", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑点位", PermissionConst._pointer._point.modify, "", 2, null));
                    add(new MenuInfo("删除点位", PermissionConst._pointer._point.delete, "", 2, null));
                    add(new MenuInfo("批量删除点位", PermissionConst._pointer._point.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._pointer._point.first, "", 2, null));
                }}));
                add(new MenuInfo("共享列表", PermissionConst._pointer._point.list, "/pointer/point", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑点位", PermissionConst._pointer._point.modify, "", 2, null));
                    add(new MenuInfo("删除点位", PermissionConst._pointer._point.delete, "", 2, null));
                    add(new MenuInfo("批量删除点位", PermissionConst._pointer._point.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._pointer._point.first, "", 2, null));
                }}));
                add(new MenuInfo("目标管理", PermissionConst._pointer._point.list, "/pointer/point", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑点位", PermissionConst._pointer._point.modify, "", 2, null));
                    add(new MenuInfo("删除点位", PermissionConst._pointer._point.delete, "", 2, null));
                    add(new MenuInfo("批量删除点位", PermissionConst._pointer._point.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._pointer._point.first, "", 2, null));
                }}));
                add(new MenuInfo("共享日志", PermissionConst._pointer._point.list, "/pointer/point", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑点位", PermissionConst._pointer._point.modify, "", 2, null));
                    add(new MenuInfo("删除点位", PermissionConst._pointer._point.delete, "", 2, null));
                    add(new MenuInfo("批量删除点位", PermissionConst._pointer._point.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._pointer._point.first, "", 2, null));
                }}));
            }}));
            add(new MenuInfo("合同", PermissionConst._devices.list, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("合同列表", PermissionConst._devices._device.list, "/device/list", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑设备", PermissionConst._devices._device.modify, "", 2, null));
                }}));
                add(new MenuInfo("转单日志", PermissionConst._devices._device.list, "/device/list", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑设备", PermissionConst._devices._device.modify, "", 2, null));
                }}));
            }}));

            add(new MenuInfo("报表", PermissionConst._products.list, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("约谈报表", PermissionConst._products._product.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                 }}));
                add(new MenuInfo("渠道报表", PermissionConst._products._product.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("单量报表", PermissionConst._products._product.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("业绩报表", PermissionConst._products._product.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("客户统计列表", PermissionConst._products._product.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("成单统计列表", PermissionConst._products._product.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
            }}));
            add(new MenuInfo("系统管理", PermissionConst._system.list, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("用户管理", PermissionConst._system._user.list, "/system/user", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑用户", PermissionConst._system._user.modify, "", 2, null));
                    add(new MenuInfo("删除用户", PermissionConst._system._user.delete, "", 2, null));
                    add(new MenuInfo("批量删除用户", PermissionConst._system._user.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._system._user.first, "", 2, null));
                }}));
                add(new MenuInfo("分类管理", PermissionConst._system._category.list, "/system/user", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑分类", PermissionConst._system._category.modify, "", 2, null));
                    add(new MenuInfo("删除分类", PermissionConst._system._category.delete, "", 2, null));
                    add(new MenuInfo("批量删除分类", PermissionConst._system._category.batch, "", 2, null));
                    add(new MenuInfo("获取分类", PermissionConst._system._category.first, "", 2, null));
               }}));
                add(new MenuInfo("角色管理", PermissionConst._system._role.list, "/system/role", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑角色", PermissionConst._system._role.modify, "", 2, null));
                    add(new MenuInfo("删除角色", PermissionConst._system._role.delete, "", 2, null));
                    add(new MenuInfo("批量删除角色", PermissionConst._system._role.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._system._role.first, "", 2, null));
                }}));
                add(new MenuInfo("组织机构管理", PermissionConst._system._role.list, "/system/role", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑角色", PermissionConst._system._role.modify, "", 2, null));
                    add(new MenuInfo("删除角色", PermissionConst._system._role.delete, "", 2, null));
                    add(new MenuInfo("批量删除角色", PermissionConst._system._role.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._system._role.first, "", 2, null));
                }}));
            }}));


        }};

        public static class MenuInfo {
            public MenuInfo() {
            }

            public MenuInfo(String name, String code, String url, Integer type, List<MenuInfo> childs) {
                Name = name;
                Type = type;
                Code = code;
                Url = url;
                Children = childs;
            }
            public String Name;
            public String Code;
            public Integer Type;

            public String Url;
            public List<MenuInfo> Children;
        }
    }

}