<template>
    <div>
        <Card dis-hover>
            <div class="page-body">
                <Form slot="filter" ref="queryForm" :label-width="80" label-position="left" inline>
                    <Row :gutter="16">
                        <Col span="5">
                        <FormItem label="点位名称:">
                            <Input v-model="filters.pointName" />
                        </FormItem>
                        </Col>
                        <Col span="5">
                        <FormItem label="时间范围:">
                            <DatePicker v-model="times" type="daterange" split-panels placeholder="选择范围" style="width: 200px"></DatePicker>
                        </FormItem>
                        </Col>
                        <Col span="5">
                        <Button icon="ios-search" type="primary" size="large" @click="current" class="toolbar-btn">当月</Button>
                        <Button icon="ios-search" type="primary" size="large" @click="preone" class="toolbar-btn">前一月</Button>
                        <Button icon="ios-search" type="primary" size="large" @click="prethree" class="toolbar-btn">前三月</Button>
                        </Col>


                        <Col span="4">
                        <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
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
    @Component({
        components: {}
    })
    export default class OperateLog extends AbpBase {
        filters: any = {
            pointName: "",
            start: null,
            end: null
        };
        fmt:string = "yyyy-MM-dd hh:mm:ss";
        times: Array < any >= null;
        init() {
            this.getpage();
        }
        columns = [{
                type: "selection",
                width: 60,
                align: "center"
            },
            {
                title: "点位名",
                key: "productName"
            },
            {
                title: "退款金额",
                key: "saleMoney",
        render: (h: any, params: any) => {
          return h("span", params.row.saleMoney * 1.0 / 100);
        }
            },
            {
                title: "销量(次)",
                key: "saleCount"
            },
        ];
        current() {
           var now = new Date();
            var left = now.setDate(1);
            this.filters.start = this.dateFormat(this.fmt,new Date(left));
            this.filters.end = this.dateFormat(this.fmt,new Date());
            this.getpage();
        }
        preone() {
            var now = new Date();
            var left = now.setMonth(now.getMonth() - 1);
            this.filters.start = this.dateFormat(this.fmt,new Date(left));
            this.filters.end = this.dateFormat(this.fmt,new Date());
            this.getpage();
        }
        prethree() {
            var now = new Date();
            var left = now.setMonth(now.getMonth() - 3);
            this.filters.start =this.dateFormat(this.fmt,new Date(left));
            this.filters.end = this.dateFormat(this.fmt,new Date());
            this.getpage();
        }
        get list() {
            return this.$store.state.serial.pointsales;
        }
        pageChange(page: number) {
            this.$store.commit(`serial/setCurrentPage`, page);
            this.getpage();
        }
        pagesizeChange(pagesize: number) {
            this.$store.commit(`serial/setPageSize`, pagesize);
            this.getpage();
        }
        async getpage() {
            let pagerequest: any = new PageRequest();
            pagerequest.size = this.pageSize;
            pagerequest.index = this.currentPage;
            if(this.times!=null){
                this.filters.start=this.dateFormat(this.fmt,this.times[0]) ;
                this.filters.end=this.dateFormat(this.fmt,this.times[1]) ;
            }
            pagerequest.where = this.filters;
            await this.$store.dispatch({
                type: `serial/getAllSales`,
                data: pagerequest
            });
        }
        get pageSize() {
            return this.$store.state[`serial`].pageSize;
        }
        get totalCount() {
            return this.$store.state[`serial`].totalCount;
        }
        get currentPage() {
            return this.$store.state[`serial`].currentPage;
        }
        async created() {
            this.getpage();
        }
    }
</script>