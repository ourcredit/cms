<template>
  <div>
    <Card dis-hover>
      <div class="page-body">
        <Form ref="queryForm" :label-width="80" label-position="left" inline>
          <Row :gutter="16">
            <Col span="5">
            <FormItem label="贷款顾问:" style="width:100%">
              <Input v-model="filters.userName"></Input>
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="联系电话" style="width:100%">
              <Input v-model="filters.mobile"></Input>
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="年月" style="width:100%">
              <DatePicker @on-change="time1" format="yyyyMM" v-model="filters.showMonth" placeholder="年月" type="month" style="width: 200px"></DatePicker>
            </FormItem>
            </Col>
            <Col span="4">
            <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
            </Col>
          </Row>
        </Form>
      </div>
    </Card>
    <Card dis-hover>
      <div class="page-body">
        <div class="margin-top-10">
          <SaleTable ref="table" :filters="filters" :type="'force'" :columns="columns"></SaleTable>
        </div>
      </div>
    </Card>
    <SaleForce v-model="ModalShow" @save-success="init"></SaleForce>
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
  import AbpBase from "../../lib/abpbase";
  import PageRequest from "../../store/entities/page-request";
  import Customer from "@/store/entities/customer";
  import SaleTable from "@/components/saletable.vue";
  import SaleForce from "./saleforce.vue";
  @Component({
    components: {
      SaleTable,
      SaleForce
    }
  })
  export default class Users extends AbpBase {
    filters: any = {
      userName: "",
      showMonth: "",
      mobile: ""
    };

    ModalShow: boolean = false;
    Modify() {
      this.ModalShow = true;
    }
    init() {
      var t: any = this.$refs.table;
      if (t) {
        t.getpage();
      }
    }
    time1(e){
      this.filters.showMonth=e;
    }
    columns = [{
        type: 'selection',
        width: 60,
        align: 'center'
      }, {
        title: "贷款顾问",
        key: "userName"
      },
      {
        title: "业务团队",
        key: "orgName"
      },
      {
        title: "联系电话",
        key: "mobile"
      },
      {
        title: "年月",
        key: "showMonth"
      },
      {
        title: "销售目标",
        key: "showForce"
      },
      {
        title: "已完成",
        key: "comeTrue"
      },
      {
        title: "操作",
        key: "Actions",
        width: 150,
        render: (h: any, params: any) => {
          return h("div", [
            h(
              "Button", {
                props: {
                  type: "primary",
                  size: "small"
                },
                style: {
                  marginRight: "5px"
                },
                on: {
                  click: () => {
                    this.$store.dispatch({
                      type: "user/get",
                      data: params.row
                    });
                    this.Modify();
                  }
                }
              },
              "设置销售目标"
            )
          ]);
        }
      }
    ];
    async created() {
      this.init();
    }
  }
</script>