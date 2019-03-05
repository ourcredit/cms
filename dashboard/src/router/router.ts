declare global {
  interface ISystem {
    import(request: string): Promise < any > ;
  }
  var System: ISystem;
}
export const locking: any = {
  path: "/locking",
  name: "locking",
  component: () =>
    System.import("../components/lockscreen/components/locking-page.vue")
};
export const loginRouter: any = {
  path: "/",
  name: "login",
  meta: {
    title: "LogIn"
  },
  component: () => System.import("../views/login.vue")
};
export const otherRouters: any = {
  path: "/main",
  name: "main",
  meta: {
    title: "管理页"
  },
  component: () => System.import("../views/main.vue"),
  children: [{
      path: "/dashboard",
      meta: {
        title: "首页"
      },
      name: "dashboard",
      permission: "dashboard:show",
      component: () => System.import("../views/home/dashboard.vue")
    },
    {
      path: "/channel/modify",
      meta: {
        title: "渠道编辑"
      },
      permission: "channels:modify",
      name: "channelsmodify",
      component: () => System.import("../views/channel/modify.vue")
    }, {
      path: "/channel/first",
      meta: {
        title: "渠道详情"
      },
      permission: "channels:first",
      name: "channelsfirst",
      component: () => System.import("../views/channel/details.vue")
    }
  ]
};
export const appRouters: Array < any > = [{
    path: "/channel",
    name: "channel",
    meta: {
      title: "渠道"
    },
    permission: "channel:show",
    icon: "&#xeb1c;",
    component: () => System.import("../views/main.vue"),
    children: [{
      path: "list",
      meta: {
        title: "渠道列表"
      },
      permission: "channels:list",
      name: "channelslist",
      component: () => System.import("../views/channel/index.vue")
    }]
  },
  {
    path: "/customer",
    name: "customer",
    meta: {
      title: "客户"
    },
    permission: "relation:show",
    icon: "&#xeb33;",
    component: () => System.import("../views/main.vue"),
    children: [{
        path: "list",
        meta: {
          title: "客户列表"
        },
        permission: "customer:list",
        name: "customerlist",
        component: () => System.import("../views/customer/index.vue")
      },
      {
        path: "share",
        meta: {
          title: "共享列表"
        },
        permission: "share:list",
        name: "sharelist",
        component: () => System.import("../views/customer/index.vue")
      },
      {
        path: "force",
        meta: {
          title: "目标管理"
        },
        permission: "force:list",
        name: "forcelist",
        component: () => System.import("../views/customer/index.vue")
      },
      {
        path: "sharelog",
        meta: {
          title: "共享日志"
        },
        permission: "share:list",
        name: "sharelog",
        component: () => System.import("../views/customer/index.vue")
      }
    ]
  },
  {
    path: "/contract",
    name: "contract",
    meta: {
      title: "合同"
    },
    permission: "contract:show",
    icon: "&#xeac8;",
    component: () => System.import("../views/main.vue"),
    children: [{
        path: "list",
        meta: {
          title: "合同列表"
        },
        permission: "contracts:list",
        name: "contractslist",
        component: () => System.import("../views/customer/index.vue")
      },
      {
        path: "conlog",
        meta: {
          title: "转单日志"
        },
        permission: "conLog:list",
        name: "conLoglist",
        component: () => System.import("../views/customer/index.vue")
      }
    ]
  },
  {
    path: "",
    name: "report",
    meta: {
      title: "报表系统"
    },
    permission: "report:show",
    icon: "&#xeaf5;",
    component: () => System.import("../views/main.vue"),
    children: [{
        path: "/report/a",
        meta: {
          title: "流水信息"
        },
        permission: "a:list",
        name: "a.serial",
        component: () => System.import("../views/report/serial.vue")
      },
      {
        path: "/report/b",
        meta: {
          title: "设备故障记录"
        },
        permission: "b:list",
        name: "b.de",
        component: () => System.import("../views/report/de.vue")
      },
      {
        path: "/report/point",
        meta: {
          title: "点位销量"
        },
        permission: "pointsale:list",
        name: "report.point",
        component: () => System.import("../views/report/point.vue")
      },
      {
        path: "/report/count",
        meta: {
          title: "设备次数统计"
        },
        permission: "count:list",
        name: "report.count",
        component: () => System.import("../views/report/count.vue")
      }
    ]
  },
  {
    path: "/setting",
    name: "setting",
    permission: "system:show",
    meta: {
      title: "系统管理"
    },
    icon: "&#xeacc;",
    component: () => System.import("../views/main.vue"),
    children: [{
        path: "user",
        permission: "user:list",
        meta: {
          title: "用户管理"
        },
        name: "user",
        component: () => System.import("../views/setting/user/user.vue")
      },
      {
        path: "role",
        permission: "role:list",
        meta: {
          title: "角色管理"
        },
        name: "role",
        component: () => System.import("../views/setting/role/role.vue")
      },
      {
        path: "category",
        permission: "category:list",
        meta: {
          title: "分类管理"
        },
        name: "category",
        component: () => System.import("../views/setting/category/index.vue")
      },
      {
        path: "org",
        permission: "org:list",
        meta: {
          title: "组织机构管理"
        },
        name: "org",
        component: () => System.import("../views/setting/category/index.vue")
      }
    ]
  }
];
export const routers: Array < any > = [
  loginRouter,
  locking,
  ...appRouters,
  otherRouters
];