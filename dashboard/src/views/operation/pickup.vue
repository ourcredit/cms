<template>
  <div>
    <Row>

      <Card dis-hover>
        <div class="page-body">
          <Form slot="filter" ref="queryForm" :label-width="70" label-position="left" inline>
            <Row :gutter="4">
              <Col span="18">
              <FormItem label="客户姓名:">
                <Input v-model="filters.customerName" />
              </FormItem>
              <FormItem label="电话:">
                <Input v-model="filters.mobile" />
              </FormItem>
              <FormItem label="织物类型:">
                <Select clearable v-model="filters.dryType" style="width:160px">
                  <Option v-for="item in dryTypes" :value="item.key" :key="item.key">{{ item.name }}</Option>
                </Select>
              </FormItem>
              <FormItem label="服务人员姓名:">
                <Input v-model="filters.operaterName" />
              </FormItem>
              <FormItem label="时间范围:">
                <DatePicker v-model="filters.creationTime" type="daterange" split-panels placeholder="选择范围" style="width: 200px"></DatePicker>
              </FormItem>
              </Col>
              <Col span="6">
              <Button icon="ios-search" type="primary" size="large" @click="getpage" class="toolbar-btn">查找</Button>
              </Col>
            </Row>
          </Form>
          <div class="margin-top-10">
            <Table ref="table" stripe border show-header :columns="columns" no-data-text="暂无数据" :data="list">
            </Table>
            <Page show-sizer class-name="fengpage" :total="totalCount" class="margin-top-10" @on-change="pageChange"
              @on-page-size-change="pagesizeChange" :page-size="pageSize" :current="currentPage"></Page>
          </div>
        </div>
      </Card>
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
  import Product from "@/store/entities/product";
  import Modify from "./modify.vue";

  @Component({
    components: {
      SaleTable,
      Modify
    }
  })
  export default class ProductC extends AbpBase {
    filters: Object = {
      customerName: "",
      mobile: "",
      operaterName: "",
      creationTime: [],
      dryType: null
    };
    get list() {
      return this.$store.state.order.opeOrders;
    }
    dryTypes: Array < any > = [{
      key: null,
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

    ModalShow: boolean = false;
    columns: Array < any > = [{
        type: "selection",
        width: 60,
        align: "center"
      },
      {
        title: "客户姓名",
        key: "customerName"
      },
      {
        title: "电话",
        key: "mobile"
      },
      {
        title: "地址",
        key: "address",
        render: (h: any, params: any) => {
          return h("span", params.row.area + " " + params.row.address);
        }
      },
      {
        title: "织物类型",
        key: "dryType",
        render: (h: any, params: any) => {
          return h("span", params.row.dryType == 1 ? '纯棉' : (params.row.dryType == 2 ? '化纤' : '其他'));
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
        title: "服务人员姓名",
        key: "operaterName"
      },
      {
        title: "服务人员电话",
        key: ""
      },

      {
        title: "创建时间",
        render: (h: any, params: any) => {
          return h(
            "span",
            new Date(params.row.creationTime).toLocaleDateString()
          );
        }
      }
    ];

    pageChange(page: number) {
      this.$store.commit(`order/setCurrentPage`, page);
      this.getpage();
    }
    pagesizeChange(pagesize: number) {
      this.$store.commit(`order/setPageSize`, pagesize);
      this.getpage();
    }
    async getpage() {
      let pagerequest: any = new PageRequest();
      pagerequest.size = this.pageSize;
      pagerequest.index = this.currentPage;
      pagerequest.where = this.filters;
      pagerequest.where['payState'] = 1;
      pagerequest.where['orderState'] = 1;
      await this.$store.dispatch({
        type: `order/getopeAll`,
        data: pagerequest
      });
    }
    get pageSize() {
      return this.$store.state[`order`].pageSize;
    }
    get totalCount() {
      return this.$store.state[`order`].totalCount;
    }
    get currentPage() {
      return this.$store.state[`order`].currentPage;
    }
    Modify() {}
    async created() {
      this.getpage();
    }
  }
</script>