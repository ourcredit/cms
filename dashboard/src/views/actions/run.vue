<template>
  <div>
    <Card dis-hover>
      <div class="page-body">
        <Form slot="filter" ref="queryForm" :label-width="80" label-position="left" inline>
          <Row :gutter="16">
            <Col span="5">
            <FormItem label="设备名称:" style="width:100%">
              <Input v-model="filters.deviceNum"></Input>
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="设备编号" style="width:100%">
              <Input v-model="filters.deviceName"></Input>
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="日志信息" style="width:100%">
              <Input v-model="filters.message"></Input>
            </FormItem>
            </Col>
            <Col span="4">
            <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
            </Col>
          </Row>

        </Form>
        <SaleTable ref="table" :filters="filters"  type="error" :columns="columns"></SaleTable>
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
      logType: 1,
      deviceNum: "",
      deviceName: "",
      message:""
    };

    init() {
      var t: any = this.$refs.table;
      t.getpage();
    }
    columns = [{
        type: "selection",
        width: 60,
        align: "center"
      },
      {
        title: "设备编号",
        key: "deviceNum"
      },
      {
        title: "设备名称",
        key: "deviceName"
      },
      {
        title: "所属点位",
        key: "pointName"
      },
      {
        title: "运行日志",
        key: "message"
      },{
        title: "故障时间",
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