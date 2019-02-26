<template>
  <div>
    <Row>
      <Card dis-hover>
        <div class="page-body">
          <Form slot="filter" ref="queryForm" :label-width="70" label-position="left" inline>
            <Row :gutter="4">
              <Col span="18">
              <!-- <FormItem label="省:">
                <Input v-model="filters.provence" />
              </FormItem>
              <FormItem label="市:">
                <Input v-model="filters.city" />
              </FormItem> -->
              <FormItem label="区:">
                <Input v-model="filters.name" />
              </FormItem>
              </Col>
              <Col span="6">
              <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
              <Button type="primary" size="large" @click="endisable" class="toolbar-btn">启用/禁用</Button>
              </Col>
            </Row>
          </Form>
          <SaleTable ref="table" :filters="filters" :type="'region'" :columns="columns"></SaleTable>
        </div>
      </Card>
    </Row>

    <modify v-model="ModalShow" @save-success="init"></modify>
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
  import Region from "@/store/entities/region";
  import Modify from "./doormodify.vue";

  @Component({
    components: {
      SaleTable,
      Modify
    }
  })
  export default class ProductC extends AbpBase {
    filters: Object = {
      // provence: "",
      // city:"",
      name:""
    };
    ModalShow: boolean = false;
    columns: Array < any > = [{
        type: "selection",
        width: 60,
        align: "center"
      },
      {
        title: "省",
        key: "provence"
      },
      {
        title: "市",
        key: "city"
      },
      {
        title: "区",
        key: "country"
      },
      {
        title: "价格",
        key: "price",
        render: (h: any, params: any) => {
          return h("span", params.row.price * 1.0 / 100);
        }
      },
      {
        title: "创建时间",
        render: (h: any, params: any) => {
          return h(
            "span",
            new Date(params.row.creationTime).toLocaleDateString()
          );
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
              },
              style: {
                marginRight: "5px"
              },
              on: {
                click: () => {
                  this.$store.dispatch({
                    type: "region/get",
                    data: params.row.id
                  });
                  this.Modify();
                }
              }
            },
            "编辑"
          );

          var en = h(
            "Button", {
              props: {
                type: "primary",
                size: "small",
              },
              style: {
                marginRight: "5px"
              },
              on: {
                click: async () => {
                  await this.$store.dispatch({
                    type: "region/endisable",
                    data: [params.row.id]
                  });
                  await this.init();
                }
              }
            },
            "启用"
          );
          var dis = h(
            "Button", {
              props: {
                type: "primary",
                size: "small",
              },
              style: {
                marginRight: "5px"
              },
              on: {
                click: async () => {
                  await this.$store.dispatch({
                    type: "region/endisable",
                    data: [params.row.id]
                  });
                  await this.init();
                }
              }
            },
            "禁用"
          );
          var t = [ed];
          if (params.row.enable == 1) {
            t.push(dis);
          } else {
            t.push(en);
          }
          return h("div", t);
        }
      }
    ];
    init() {
      var t: any = this.$refs.table;
      t.getpage();
    }
    async endisable() {
      var t: any = this.$refs.table;
      await this.$store.dispatch({
        type: "region/endisable",
        data: t.selections
      });
      await this.init();
    }
    Modify() {
      this.ModalShow = true;
    }
    async created() {}
  }
</script>