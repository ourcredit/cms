<template>
    <div>
        <Modal width="70%" :value="value" @on-ok="save" @on-visible-change="visibleChange">
            <Card dis-hover>
                <div class="page-body">
                    <Form ref="queryForm" :label-width="80" label-position="left" inline>
                        <Row :gutter="16">
                            <Col span="5">
                            <FormItem label="贷款顾问" style="width:100%">
                                <Input v-model="filters.userName" placeholder="销售姓名/手机号码/账户"></Input>
                            </FormItem>
                            </Col>
                            <Col span="4">
                            <Button icon="ios-search" type="primary" size="large" @click="getpage" class="toolbar-btn">查找</Button>
                            </Col>
                        </Row>
                    </Form>
                    <div class="margin-top-10">
                        <Table  :loading="loading" :columns="columns"
                            no-data-text="暂无数据" border :data="list">
                        </Table>
                        <Page show-sizer class-name="fengpage" :total="totalCount" class="margin-top-10" @on-change="pageChange"
                            @on-page-size-change="pagesizeChange" :page-size="pageSize" :current="currentPage"></Page>
                    </div>
                </div>
            </Card>
            <div slot="footer">
                <Button @click="cancel">关闭</Button>
                <Button @click="save" type="primary">保存</Button>
            </div>
        </Modal>
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
    import User from "@/store/entities/user";
    @Component({
        components: {}
    })
    export default class Users extends AbpBase {
        filters: Object = {
            userName: ""
        };
        @Prop({
            type: Boolean,
            default: false
        })
        value: boolean;
        get list() {
            return this.$store.state.user.list;
        }
        get loading() {
            return this.$store.state.user.loading;
        }
        pageChange(page: number) {
            this.$store.commit("user/setCurrentPage", page);
            this.getpage();
        }
        pagesizeChange(pagesize: number) {
            this.$store.commit("user/setPageSize", pagesize);
            this.getpage();
        }
        async getpage() {
            let pagerequest = new PageRequest();
            pagerequest.size = this.pageSize;
            pagerequest.index = this.currentPage;
            pagerequest.where = this.filters;
            await this.$store.dispatch({
                type: "user/getAll",
                data: pagerequest
            });
        }
        selections: any = {};
     
        get pageSize() {
            return this.$store.state.user.pageSize;
        }
        get totalCount() {
            return this.$store.state.user.totalCount;
        }
        get currentPage() {
            return this.$store.state.user.currentPage;
        }
        async save() {
            debugger;
            if (this.selections && this.selections.id) {
                await this.$store.dispatch({
                    type: "customer/share",
                    data: this.selections.id
                });
                this.$emit("save-success");
                this.$emit("input", false);
            } else {
                this.$Message.info("请选择要分享的客户数据");
            }
        }
        cancel() {
            this.$emit("input", false);
        }
        visibleChange(value: boolean) {
            if (!value) {
                this.$emit("input", value);
            }
        }
        columns = [{
                title: 'key',
                align: 'center',
                key: 'checkBox',
                render: (h, params) => {
                    return h('div', [
                        h('Checkbox', {
                            props: {
                                value: params.row.checkBox
                            },
                            on: {
                                'on-change': (e) => {
                                    this.list.forEach((items) => { //先取消所有对象的勾选，checkBox设置为false
                                        this.$set(items, 'checkBox', false)
                                    });
                                    this.selections=this.list[params.index];
                                    console.log(this.selections);
                                    this.list[params.index].checkBox = e; //再将勾选的对象的checkBox设置为true
                                }
                            }
                        })
                    ])
                }
            },
            {
                title: "销售顾问",
                key: "userName"
            },
            {
                title: "业务团队",
                key: "orgName"
            },
            {
                title: "员工账户",
                key: "account"
            },
            {
                title: "手机号码",
                key: "mobile"
            }
        ];
        async created() {
            this.getpage();
        }
    }
</script>