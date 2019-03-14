import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);
import app from "@/store/modules/app";
import session from "@/store/modules/session";
import account from "@/store/modules/account";
import user from "@/store/modules/user";
import role from "@/store/modules/role";
import log from "@/store/modules/log";
import menu from "@/store/modules/menus";
import product from "@/store/modules/product";
import tenant from "@/store/modules/tenant";
import order from "@/store/modules/order";
import dash from "@/store/modules/dash";
import category from "@/store/modules/category";
import serial from "@/store/modules/serial";
import region from "@/store/modules/region";
import error from "@/store/modules/error";
import channel from "@/store/modules/channel";
import visit from "@/store/modules/visit";
import customer from "@/store/modules/customer";
import force from "@/store/modules/force";
import contract from "@/store/modules/contract";
const store: any = new Vuex.Store({
  state: {
    //
  },
  mutations: {
    //
  },
  actions: {},
  modules: {
    app,
    session,
    account,
    user,
    role,
    log,
    menu,
    product,
    tenant,
    order,
    dash,
    category,
    serial,
    region,
    error,
    channel,
    visit,
    customer,
    force,
    contract
  }
});

export default store;
