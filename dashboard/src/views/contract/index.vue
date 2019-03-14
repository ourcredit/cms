<template>
    <div>
        <Card dis-hover>
            <div class="page-body">
                <Form ref="queryForm" :label-width="80" label-position="left" inline>
                    <Row :gutter="16">
                        <Col span="5">
                        <FormItem label="用户名:" style="width:100%">
                            <Input v-model="filters.account"></Input>
                        </FormItem>
                        </Col>
                        <Col span="5">
                        <FormItem label="姓名" style="width:100%">
                            <Input v-model="filters.userName"></Input>
                        </FormItem>
                        </Col>
                        <Col span="5">
                        <FormItem label="渠道类型:">
                            <Select clearable v-model="filters.type" style="width:160px">
                                <Option v-for="item in ChannelType" :value="item.name" :key="item.name">{{ item.name }}</Option>
                            </Select>
                        </FormItem>
                        </Col>
                        <Col span="5">
                        <FormItem label="创建时间" style="width:100%">
                            <DatePicker v-model="filters.creationTime" type="datetimerange" format="yyyy-MM-dd" style="width:100%"
                                placement="bottom-end" placeholder="选择时间"></DatePicker>
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
                <Row :gutter="16">
                    <Col span="2">
                    <Button @click="ModelShow('giveto',true)" type="primary">转单</Button>
                    </Col>
                </Row>
                <div class="margin-top-10">
                    <SaleTable ref="table" :filters="filters" :type="'contract'" :columns="columns"></SaleTable>
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
    import Customer from "@/store/entities/customer";
    import SaleTable from "@/components/saletable.vue";
    @Component({
        components: {
            SaleTable
        }
    })
    export default class Users extends AbpBase {
        filters: Object = {
            account: "",
            userName: "",
            creationTime: null
        };

        get ChannelType() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "渠道类型") {
                    a.dic.forEach(b => {
                        res.push({
                            key: b.key,
                            name: b.name
                        });
                    });
                }
            });
            return res;
        }
        get ChannelLevel() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "渠道等级") {
                    a.dic.forEach(b => {
                        res.push({
                            key: b.key,
                            name: b.name
                        });
                    });
                }
            });
            return res;
        }
        get ChannelBusiness() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "主营业务") {
                    a.dic.forEach(b => {
                        res.push({
                            key: b.key,
                            name: b.name
                        });
                    });
                }
            });
            return res;
        }
        get ChannelList() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "渠道列表") {
                    a.dic.forEach(b => {
                        res.push({
                            key: b.key,
                            name: b.name
                        });
                    });
                }
            });
            return res;
        }
        ModalShow: boolean = false;
        VisitShow: boolean = false;
        FollowShow: boolean = false;
        ExtendShow: boolean = false;
        GiveToShow: boolean = false;
        get list() {
            return this.$store.state.customer.list;
        }
        get loading() {
            return this.$store.state.customer.loading;
        }

        Create() {
            var u = new Customer();
            this.$store.commit("customer/edit", u);
            this.ModalShow = true;
        }
        Modify() {
            this.ModalShow = true;
        }
        ModelShow(type: String, state: boolean) {
            debugger;
            if (type == "visit") {
                this.VisitShow = state;
            } else if (type == "follow") {
                this.FollowShow = state;
            } else if (type == "extend") {
                this.ExtendShow = state;
            } else if (type == "giveto") {
                this.GiveToShow = state;
            }
        }
        Details() {
            this.$router.push({
                name: "customermodify"
            })
        }
        init() {
            var t: any = this.$refs.table;
            if (t) {
                t.getpage();
            }
        }
        columns = [{
                type: 'selection',
                width: 60,
                align: 'center'
            }, {
                title: "合同编号",
                key: "code"
            },
            {
                title: "贷款顾问",
                key: "creatorName"
            },
            {
                title: "业务部门",
                key: "teamName"
            },
            {
                title: "业务团队",
                key: "orgName"
            },
            {
                title: "客户编号",
                key: "customerCode"
            },
            {
                title: "客户姓名",
                key: "customerName"
            },

            {
                title: "客户来源",
                key: "resource"
            },
            {
                title: "渠道",
                key: "channelName"
            },
            {
                title: "业务类型",
                key: "business"
            },
            {
                title: "贷款金额",
                render: (h: any, params: any) => {
                    let t = params.row.extendField;
                    if (t) {
                        var val = JSON.parse(t);
                        return h(
                            "span",
                            val.price
                        );
                    }
                }
            },
            {
                title: "垫资金额",
                render: (h: any, params: any) => {
                    let t = params.row.extendField;
                    if (t) {
                        var val = JSON.parse(t);
                        return h(
                            "span",
                            val.dianzijine
                        );
                    }
                }
            },
            {
                title: "成交日期",
                key: "creationTime",
                render: (h: any, params: any) => {
                    return h(
                        "span",
                        new Date(params.row.creationTime).toLocaleDateString()
                    );
                }
            },
            {
                title: "贷款机构",
                key: "visitTime",
                render: (h: any, params: any) => {
                    return h(
                        "span",
                        new Date(params.row.visitTime).toLocaleDateString()
                    );
                }
            },
            {
                title: "贷款子机构",
                key: "followTime",
                render: (h: any, params: any) => {
                    return h(
                        "span",
                        new Date(params.row.followTime).toLocaleDateString()
                    );
                }
            },
            {
                title: "付款方式",
                key: "focus",
                render: (h: any, params: any) => {
                    return h(
                        "span",
                        params.row.focus == 1 ? '是' : "否"
                    );
                }
            }, {
                title: "欠费",
                key: "focus",
                render: (h: any, params: any) => {
                    return h(
                        "span",
                        params.row.focus == 1 ? '是' : "否"
                    );
                }
            },
            {
                title: "关注",
                key: "focus",
                render: (h: any, params: any) => {
                    return h(
                        "span",
                        params.row.focus == 1 ? '是' : "否"
                    );
                }

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
                                            type: "customer/get",
                                            data: params.row.id
                                        });
                                        this.ModelShow("visit", true);
                                    }
                                }
                            },
                            "详情"
                        ),
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
                                            type: "customer/get",
                                            data: params.row.id
                                        });
                                        this.ModelShow("visit", true);
                                    }
                                }
                            },
                            "新合同"
                        ),
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
                                            type: "customer/get",
                                            data: params.row.id
                                        });
                                        this.ModelShow("follow", true);
                                    }
                                }
                            },
                            "关注"
                        )
                    ]);
                }
            }
        ];
        async batchShare() {
            var t: any = this.$refs.table;
            if (t.selections && t.selections.length != 0) {
                this.$Modal.confirm({
                    title: "删除提示",
                    content: `确认要共享${t.selections.length}条客户数据么`,
                    okText: "是",
                    cancelText: "否",
                    onOk: async () => {
                        await this.$store.dispatch({
                            type: "customer/share",
                            data: t.selections
                        });
                        await this.init();
                    }
                });
            } else {
                this.$Message.info("请选择要分享的客户数据");
            }
        }
        async created() {
            this.init();
        }
    }
</script>