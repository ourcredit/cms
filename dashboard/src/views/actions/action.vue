<template>
  <div>
    <Card dis-hover>
      <div class="page-body">
        <Form slot="filter" ref="queryForm" :label-width="80" label-position="left" inline>
          <Row :gutter="16">
            <Col span="5">
            <FormItem label="方法:" style="width:100%">
              <Input v-model="filters.method" />
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="描述" style="width:100%">
              <Input v-model="filters.logDescription" />
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="时间范围" style="width:100%">
              <DatePicker v-model="filters.creationTime" type="datetimerange" format="yyyy-MM-dd" style="width:100%"
                placement="bottom-end" placeholder="选择时间"></DatePicker>
            </FormItem>
            </Col>
            <Col span="4">
            <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
            </Col>
          </Row>
        </Form>
        <SaleTable ref="table" :filters="filters" type="log" :columns="columns"></SaleTable>
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
  export default class OperateLog extends AbpBase {
    filters: Object = {
      method: "",
      logDescription: "",
      creationTime: ""
    };
    p: any = {
    };
    init() {
      var t: any = this.$refs.table;
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
              type: "log/batch",
              data: t.selections
            });
            await this.init();
          }
        });
      }
    }
    columns = [{
        type: "selection",
        width: 60,
        align: "center"
      },
      {
        type: "index",
        width: 60,
        align: "center"
      },
      {
        title: "模块",
        key: "className"
      },
      {
        title: "方法",
        key: "method"
      },

      {
        title: "请求地址",
        key: "ip"
      },
      {
        title: "接口描述",
        key: "logDescription"
      },
      {
        title: "请求时间",
        key: "createTime",
        render: (h: any, params: any) => {
          return h(
            "span",
            new Date(params.row.creationTime).toLocaleDateString() +
            new Date(params.row.creationTime).toLocaleTimeString()
          );
        }
      }
    ];
    async created() {}
  }
</script>