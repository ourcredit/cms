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
import { Component, Vue, Inject, Prop, Watch } from "vue-property-decorator";
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
    wechatOrder: "",
    productName: "",
    deviceName: "",
    pointName: "",
    deviceType: "",
    creationTime: null,
    payState: 2
  };
  get tree() {
    return this.$store.state.device.tree;
  }
   dryTypes: Array<any> = [{key:'',name:"全部"},{key:1,name:"纯棉"},{key:2,name:"化纤"},{key:3,name:"其他"}];
  p: any = {
    list: this.hasPermission("order:list")
  };
  get cates() {
    return this.$store.state.device.deviceCate;
  }
  get current() {
    return this.$store.state.device.currentOrg;
  }
  ModalShow: boolean = false;
  columns: Array<any> = [
    {
      type: "selection",
      width: 60,
      align: "center"
    },
    {
      title: "订单编号",
      key: "id"
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
      title: "烘干类型",
      key: "dryType",
      render: (h: any, params: any) => {
        return h("span", params.row.dryType==1?'纯棉':(params.row.dryType==2?'化纤':'其他'));
      }
    },
    {
      title: "所属点位",
      key: "pointName"
    },
    {
      title: "金额",
      key: "price",
      render: (h: any, params: any) => {
      return h("span",params.row.payType==1?  params.row.price * 1.0 / 100:params.row.price);
      }
    },
    {
      title: "状态",
      key: "payState",
      render: (h: any, params: any) => {
        if (params.row.payState == 2) return h("span", "已退款");
      }
    },
    {
      title: "创建时间",
      render: (h: any, params: any) => {
        return h("span", new Date(params.row.creationTime).toLocaleString());
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