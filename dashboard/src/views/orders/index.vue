<template>
  <div>
    <Row>
      <Col span="5">
      <OrgTree @complete="init"></OrgTree>
      </Col>
      <Col span="19">
      <Card dis-hover>
        <div class="page-body">
          <Form slot="filter" ref="queryForm" :label-width="70" label-position="left" inline>
            <Row :gutter="4">
              <Col span="22">
              <FormItem label="设备名称:">
                <Input v-model="filters.deviceName" />
              </FormItem>
              <FormItem label="设备编号:">
                <Input v-model="filters.deviceNum" />
              </FormItem>
              <FormItem label="所属点位:">
                <Input v-model="filters.pointName" />
              </FormItem>
              <FormItem label="时间范围:">
                <DatePicker v-model="filters.creationTime" type="daterange" split-panels placeholder="选择范围" style="width: 200px"></DatePicker>
              </FormItem>
              <FormItem label="烘干类型:">
                <Select clearable v-model="filters.dryType" style="width:160px">
                  <Option v-for="item in dryTypes" :value="item.key" :key="item.key">{{ item.name }}</Option>
                </Select>
              </FormItem>
              <FormItem label="订单状态:">
                <Select clearable v-model="filters.payState" style="width:160px">
                  <Option v-for="item in orderStates" :value="item.key" :key="item.key">{{ item.name }}</Option>
                </Select>
              </FormItem>
              </Col>
              <Col span="2">
              <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
              </Col>
            </Row>
          </Form>
          <SaleTable ref="table" :filters="filters" :type="'order'" :columns="columns"></SaleTable>
        </div>
      </Card>
      </Col>
    </Row>
  </div>
</template>
<script lang="ts">
  import {
    Component,
    Vue,
    Inject,
    Prop,
    Watch
  } from "vue-property-decorator";
  import SaleTable from "@/components/saletable.vue";
  import AbpBase from "@/lib/abpbase";
  import PageRequest from "../../store/entities/page-request";
  import Util from "../../lib/util";
  import OrgTree from "@/components/orgtree.vue";
  @Component({
    components: {
      SaleTable,
      OrgTree
    }
  })
  export default class Orders extends AbpBase {
    filters: any = {
      deviceName: "",
      pointName: "",
      creationTime: null
    };
    get tree() {
      return this.$store.state.device.tree;
    }
    orderStates: Array < any > = [{
      key: '',
      name: "全部"
    }, {
      key: 0,
      name: "未支付"
    }, {
      key: 1,
      name: "已支付"
    }, {
      key: 2,
      name: "已退款"
    }];
    dryTypes: Array < any > = [{
      key: '',
      name: "全部"
    }, {
      key: 1,
      name: "纯棉"
    }, {
      key: 2,
      name: "化纤"
    }, {
      key: 3,
      name: "其他"
    }];
    p: any = {
      list: this.hasPermission("order:list"),
      back: this.hasPermission("order:back"),
      superback: this.hasPermission("order:superBack")
    };
    get current() {
      return this.$store.state.device.currentOrg;
    }
    get cates() {
      return this.$store.state.device.deviceCate;
    }
    get canBack() {
      return this.$store.state.session.canBack;
    }
    ModalShow: boolean = false;
    columns: Array < any > = [{
        type: "selection",
        width: 60,
        align: "center"
      },
      {
        title: "订单编号",
        key: "id"
      },
      {
        title: "烘干类型",
        key: "dryType",
        render: (h: any, params: any) => {
          return h("span", params.row.dryType == 1 ? '纯棉' : (params.row.dryType == 2 ? '化纤' : '其他'));
        }
      },
      {
        title: "设备名称",
        key: "deviceName"
      },
      {
        title: "设备编号",
        key: "deviceNum"
      },
      {
        title: "所属点位",
        key: "pointName"
      },
      {
        title: "订单类型",
        key: "payType",
        render: (h: any, params: any) => {
          var temp = "";
          switch (params.row.payType) {
            case 1:
              temp = "微信支付";
              break;
            case 2:
              temp = "支付宝支付";
              break;
            case 3:
              temp = "客服支付";
              break;
            case 4:
              temp = "次数支付";
              break;
          }
          return h("span", temp);
        }
      },
      {
        title: "金额",
        key: "price",
        render: (h: any, params: any) => {
          var temp = "";
          switch (params.row.payType) {
            case 1:
              temp = params.row.price * 1.0 / 100 + "";
              break;
            case 2:
              temp = params.row.price + "";
              break;
            case 3:
              temp = params.row.price + "";
              break;
            case 4:
              temp = "-";
              break;
          }
          return h("span", temp);
        }
      },
      {
        title: "状态",
        key: "payState",
        render: (h: any, params: any) => {
          if (params.row.payState == 2) return h("span", "已退款");
          let t = params.row.payState == 1 ? "已支付" : "未支付";
          return h("span", t);
        }
      },
      {
        title: "创建时间",
        render: (h: any, params: any) => {
          return h("span", new Date(params.row.creationTime).toLocaleString());
        }
      },
      {
        title: "操作",
        key: "Actions",
        width: 150,
        render: (h: any, params: any) => {
          var ed = h(
            "Button", {
              props: {
                type: "primary",
                size: "small",
                disabled: !this.p.back
              },
              style: {
                marginRight: "5px"
              },
              on: {
                click: () => {
                  if (!this.canBack) {
                    this.$Modal.error({
                      title: "还未开通退款操作权限",
                      content: "请先上传退款证书"
                    });
                  }
                  this.$Modal.confirm({
                    title: "警告提示",
                    content: `确认要对当前订单执行退款操作么`,
                    okText: "是",
                    cancelText: "否",
                    onOk: () => {
                      this.$store.dispatch({
                        type: "order/back",
                        data: params.row.id
                      });
                      this.init();
                    }
                  });
                }
              }
            },
            "退款"
          );
          var superBack = h(
            "Button", {
              props: {
                type: "primary",
                size: "small",
                disabled: !this.p.superback
              },
              style: {
                marginRight: "5px"
              },
              on: {
                click: () => {
                  if (!this.canBack) {
                    this.$Modal.error({
                      title: "还未开通退款操作权限",
                      content: "请先上传退款证书"
                    });
                  }
                  this.$Modal.confirm({
                    title: "警告提示",
                    content: `确认要对当前订单执行直接退款操作么`,
                    okText: "是",
                    cancelText: "否",
                    onOk: () => {
                      this.$store.dispatch({
                        type: "order/superback",
                        data: params.row.id
                      });
                      this.init();
                    }
                  });
                }
              }
            },
            "直接退款"
          );
          var t = [];
          if (params.row.payState == 1 && params.row.orderFrom != 1 && params.row.orderState <= 3 && params.row.payType !=
            4) {
            t.push(ed);
          }
          if (params.row.payState == 1 && params.row.orderFrom != 1 && params.row.orderState > 3 && params.row.payType !=
            4) {
            console.log(this.p.superback);
            t.push(superBack);
          }
          return h("div", t);
        }
      }
    ];
    init() {
      var t: any = this.$refs.table;
      if (this.current) {
        this.filters.code = this.current.code;
      }
      t.getpage();
    }
    async batchDelete() {
      var t: any = this.$refs.table;
      if (t.selections) {
        this.$Modal.confirm({
          title: "删除提示",
          content: `确认要删除${t.selections.length}条数据么`,
          okText: "是",
          cancelText: "否",
          onOk: async () => {
            await this.$store.dispatch({
              type: "product/batch",
              data: t.selections
            });
            await this.init();
          }
        });
      }
    }
    async created() {}
  }
</script>