<template>
  <div>
    <Card dis-hover>
      <div class="page-body">
        <Form slot="filter" ref="queryForm" :label-width="70" label-position="left" inline>
          <Row :gutter="4">
            <Col span="4">
            <FormItem label="设备名:">
              <Input v-model="filters.deviceName" />
            </FormItem>
            </Col>
            <Col span="4">
            <FormItem label="所属点位:">
              <Input v-model="filters.pointName" />
            </FormItem>
            </Col>
            <Col span="4">
            <FormItem label="商品名称:">
              <Input v-model="filters.productName" />
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="时间范围:">
              <DatePicker v-model="filters.creationTime" type="daterange" split-panels placeholder="选择范围" style="width: 160px"></DatePicker>
            </FormItem>
            </Col>
            <Col span="5">
            <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
            </Col>
          </Row>
        </Form>
        <SaleTable ref="table" :filters="filters" :type="'serial'" :columns="columns"></SaleTable>
      </div>
    </Card>
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
  import AbpBase from "../../lib/abpbase";
  @Component({
    components: {
      SaleTable
    }
  })
  export default class Serials extends AbpBase {
    filters: any = {
      deviceName: "",
      pointName: "",
      productName: "",
      creationTime: []
    };
    p: any = {
      list: this.hasPermission("serial:list")
    };
    ModalShow: boolean = false;
    columns: Array < any > = [{
        type: "selection",
        width: 60,
        align: "center"
      },
      {
        title: "订单号",
        key: "id"
      },
      {
        title: "金钱",
        key: "price",
        render: (h: any, params: any) => {
          var price=0;
            if(params.row.orderType == 3|| params.row.orderType==1){
                price=params.row.price * 1.0 / 100;
            }else{
              if(params.row.payType==2){
                 price=params.row.price * 1.0 / 100;
              }else{
                price=params.row.price;
              }
            }


          return h("span",price);
        }
      },
      {
        title: "商品名称",
        key: "productName"
      },
      {
        title: "设备名",
        key: "deviceName"
      },
      {
        title: "点位名",
        key: "pointName"
      },
      {
        title: "类型",
        key: "orderType",
        render: (h: any, params: any) => {
          return h("span", params.row.orderType == 1 || params.row.orderType == 2 ? (params.row.payType == 1 ?
            '支付' : '退款') : "会员充值");
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
      t.getpage();
    }
    async created() {}
  }
</script>