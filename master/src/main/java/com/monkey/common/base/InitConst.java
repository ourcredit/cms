package com.monkey.common.base;


import java.util.ArrayList;
import java.util.List;
public interface InitConst {
    class _defaultTenant {
        public static final String admin = "default";
    }
    class _defaultUser {
        public static final String admin = "admin";
        public static final String def = "user";
        public static final String defaultPassword = "1234567";
    }

    class _defaultRole {
        public static final String admin = "admin";
        public static final String def = "default";
    }
    class _menu {
        public static List<MenuInfo> menuList = new ArrayList<MenuInfo>() {{
            add(new MenuInfo("首页", PermissionConst._dashboard.list, "/dashboard", 1, null));
            add(new MenuInfo("渠道", PermissionConst._channel.show, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("渠道列表", PermissionConst._channel._channelList.list, "/order/list", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("查看订单", PermissionConst._channel._channelList.list, "", 2, null));
                    add(new MenuInfo("编辑", PermissionConst._channel._channelList.modify, "", 2, null));
                    add(new MenuInfo("批量删除", PermissionConst._channel._channelList.batch, "", 2, null));
                    add(new MenuInfo("删除", PermissionConst._channel._channelList.delete, "", 2, null));
                }}));
            }}));
            add(new MenuInfo("客户", PermissionConst._relation.show, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("客户列表", PermissionConst._relation._customer.list, "/pointer/point", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑客户", PermissionConst._relation._customer.modify, "", 2, null));
                    add(new MenuInfo("删除客户", PermissionConst._relation._customer.delete, "", 2, null));
                    add(new MenuInfo("批量删除客户", PermissionConst._relation._customer.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._relation._customer.first, "", 2, null));
                }}));
                add(new MenuInfo("共享列表", PermissionConst._relation._share.list, "/pointer/point", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑共享", PermissionConst._relation._share.modify, "", 2, null));
                    add(new MenuInfo("删除共享", PermissionConst._relation._share.delete, "", 2, null));
                    add(new MenuInfo("批量删除共享", PermissionConst._relation._share.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._relation._share.first, "", 2, null));
                }}));
                add(new MenuInfo("目标管理", PermissionConst._relation._force.list, "/pointer/point", 1, new ArrayList<MenuInfo>() {{
                    add(new MenuInfo("编辑目标", PermissionConst._relation._force.modify, "", 2, null));
                    add(new MenuInfo("删除目标", PermissionConst._relation._force.delete, "", 2, null));
                    add(new MenuInfo("批量删除目标", PermissionConst._relation._force.batch, "", 2, null));
                    add(new MenuInfo("获取详情", PermissionConst._relation._force.first, "", 2, null));
                }}));
                add(new MenuInfo("共享日志", PermissionConst._relation._share.list, "/pointer/point", 1, new ArrayList<MenuInfo>() {{
                }}));
            }}));
            add(new MenuInfo("合同", PermissionConst._contract.show, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("合同列表", PermissionConst._contract._contractList.list, "/device/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("转单日志", PermissionConst._contract._contractList.list, "/device/list", 1, new ArrayList<MenuInfo>() {{
                }}));
            }}));

            add(new MenuInfo("报表", PermissionConst._report.show, "", 1, new ArrayList<MenuInfo>() {{
                add(new MenuInfo("约谈报表", PermissionConst._report._a.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                 }}));
                add(new MenuInfo("渠道报表", PermissionConst._report._a.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("单量报表",PermissionConst._report._a.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("业绩报表", PermissionConst._report._a.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("客户统计列表", PermissionConst._report._a.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
                }}));
                add(new MenuInfo("成单统计列表", PermissionConst._report._a.list, "/product/list", 1, new ArrayList<MenuInfo>() {{
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