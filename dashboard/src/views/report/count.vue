<template>
    <div>
        <Card dis-hover>
            <div class="page-body">
                <Form slot="filter" ref="queryForm" :label-width="80" label-position="left" inline>
                    <Row :gutter="16">
                        <Col span="5">
                        <FormItem label="设备名称:" style="width:100%">
                            <Input v-model="filters.deviceNum" />
                        </FormItem>
                        </Col>
                        <Col span="5">
                        <FormItem label="设备编号" style="width:100%">
                            <Input v-model="filters.deviceName" />
                        </FormItem>
                        </Col>
                        <Col span="5">
                        <FormItem label="日志信息" style="width:100%">
                            <Input v-model="filters.message" />
                        </FormItem>
                        </Col>
                        <Col span="4">
                        <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
                        </Col>
                    </Row>
                </Form>
                <SaleTable ref="table" :filters="filters" type="error" :columns="columns"></SaleTable>
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
            logType: 3,
            deviceNum: "",
            deviceName: "",
            message: ""
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
                title: "记录信息",
                key: "message"
            }
        ];
        async created() {}
    }
</script>