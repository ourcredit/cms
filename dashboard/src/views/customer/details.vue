<template>
    <div>
        <Tabs value="detail">
            <TabPane label="客户信息" name="detail">

                <Card>
                    <p slot="title">
                        <Icon type="ios-film-outline"></Icon>
                        客户基本信息
                    </p>
                    <Form ref="customerForm" label-position="top" :rules="customerRule" :model="customer">
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="客户编号" prop="code">
                                <Input v-model="customer.code" :maxlength="32" :minlength="2" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户姓名" prop="customerName">
                                <Input v-model="customer.customerName" :maxlength="32" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户来源" prop="level">
                                <Select v-model="customer.resource" filterable>
                                    <Option v-for="item in customerType" :value="item.name" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="渠道" prop="type">
                                <Select v-model="customer.channel" filterable>
                                    <Option v-for="item in customerType" :value="item.name" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">

                            <Col span="6">
                            <FormItem label="业务类型" prop="business">
                                <Input v-model="customer.business" :maxlength="32" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户等级" prop="level">
                                <Select v-model="customer.level" filterable>
                                    <Option v-for="item in customerLevel" :value="item.name" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="渠道电话" prop="channelMobile">
                                <Input v-model="customer.channelMobile" :maxlength="32" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="手机号1" prop="mobile1">
                                <Input v-model="customer.mobile1" :maxlength="32" />
                            </FormItem>
                            </Col>
                        </Row>

                        <Row :gutter="16">

                            <Col span="6">
                            <FormItem label="座机" prop="telphone">
                                <Input v-model="customer.telphone" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="身份证" prop="idCard">
                                <Input v-model="customer.idCard" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="性别" prop="gender">
                                <Select v-model="customer.gender" filterable>
                                    <Option v-for="item in customerLevel" :value="item.name" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="婚否" prop="maritalStatus">
                                <Select v-model="customer.maritalStatus" filterable>
                                    <Option v-for="item in customerLevel" :value="item.name" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="录入时间" prop="creationTime">
                                <Input v-model="customer.creationTime" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="贷款顾问" prop="creatorName">
                                <Input v-model="customer.creatorName" :maxlength="32" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="业务团队" prop="term">
                                <Input v-model="customer.term" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="业务部门" prop="department">
                                <Input v-model="customer.term" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                        </Row>
                    </Form>
                </Card>
                <Card>
                    <p slot="title">
                        <Icon type="ios-film-outline"></Icon>
                        需求与方案
                    </p>
                    <Form ref="extendForm" label-position="top" :rules="extendRule" :model="extend">
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="贷款金额" prop="price">
                                <InputNumber style="width:160px" :max="999" v-model="extend.price" :step="0.1" :min="0.1"
                                    :formatter="value => ` ${value}     万元`"></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="贷款年限" prop="year">
                                <InputNumber style="width:160px" :max="10" v-model="extend.year" :step="1" :min="1"
                                    :formatter="value => ` ${value}     年`"></InputNumber>

                                <span slot="append">年</span>
                                </Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="还款方式" prop="backType">
                                <Select v-model="extend.backType" filterable>
                                    <Option v-for="item in backType" :value="item.name" :key="item.name">{{ item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="用款时间" prop="useTime">
                                <InputNumber style="width:160px" :max="365" v-model="extend.useTime" :step="1" :min="1"
                                    :formatter="value => ` ${value}     天`"></InputNumber>

                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="贷款利率" prop="percent">
                                <InputNumber style="width:160px" :max="1" v-model="extend.percent" :step="0.1" :min="0.01"
                                    :formatter="value => ` ${value}     %/年`"></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="月供" prop="monthBack">
                                <InputNumber style="width:160px" v-model="extend.monthBack" :step="1" :min="0"
                                    :formatter="value => ` ${value}     元`"></InputNumber>

                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="贷款机构" prop="org">
                                <Select v-model="extend.org" filterable>
                                    <Option v-for="item in moneyOrg" :value="item.name" :key="item.name">{{
                                        item.name }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户经理" prop="jingli">
                                <Input v-model="extend.jingli" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                        </Row>

                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="联系电话" prop="mobile">
                                <Input v-model="extend.mobile" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="垫资需求" prop="dianzixuqiu">
                                <Select v-model="extend.dianzixuqiu" filterable>
                                    <Option v-for="item in dianzixuqiu" :value="item.name" :key="item.name">{{
                                        item.name }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="产品类型" prop="productType">
                                <Select v-model="extend.productType" filterable>
                                    <Option v-for="item in productType" :value="item.name" :key="item.name">{{
                                        item.name }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="垫资金额" prop="dianzijine">
                                <InputNumber style="width:160px" v-model="extend.dianzijine" :step="1" :min="0"
                                    :formatter="value => ` ${value}     元`"></InputNumber>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col>
                            <FormItem label="备注" prop="remark">
                                <Input v-model="extend.remark" type="textarea" :maxlength="32" :minlength="2" :autosize="{minRows: 2,maxRows: 5}"
                                    placeholder="备注"></Input>
                            </FormItem>
                            </Col>

                        </Row>
                    </Form>
                </Card>
            </TabPane>
            <TabPane label="跟进信息" name="follow">
                跟进信息
            </TabPane>
            <TabPane label="约谈信息" name="talk">
                乐坛信息
            </TabPane>
            <TabPane label="转单信息" name="forother">
                转单信息
            </TabPane>
        </Tabs>



        <Row>
            <Col offset="8" span="2">
            <Button @click="cancel">关闭</Button>
            </Col>
            <Col span="2">
            <Button @click="save" type="primary">保存</Button>
            </Col>
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
    import Util from "../../lib/util";
    import AbpBase from "../../lib/abpbase";
    import Customer from "../../store/entities/customer";
    import {
        debug
    } from "util";
    @Component
    export default class CreateCustomer extends AbpBase {
        @Prop({
            type: Boolean,
            default: false
        })
        value: boolean;
        get customerType() {
            var t = this.$store.state.category.cateList;
            console.log(t);
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "客户来源") {
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
        get backType() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "还款方式") {
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
        get dianzixuqiu() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "垫资需求") {
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
        get productType() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "产品类型") {
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
        get moneyOrg() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "贷款机构") {
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
        get customerLevel() {
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
        get customerBusiness() {
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
        get customer() {
            var u = this.$store.state.customer.editCustomer;
            if (u.extendField) {
                this.extend = JSON.parse(u.extendField);
            }
            return u;
        }
        extend: any = {
            price: 1,
            year: 1,
            backType: "等额本金",
            useTime: 30,
            percent: 3.45,
            monthBack: 1,
            org: "",
            jingli: "",
            mobile: "",
            dianzixuqiu: "",
            productType: "",
            dianzijine: 0,
            remark: ""
        };
        save() {
            (this.$refs.customerForm as any).validate(async (valid: boolean) => {
                if (valid) {
                    (this.$refs.extendForm as any).validate(async (valid: boolean) => {
                        if (valid) {
                            this.customer.extendField = JSON.stringify(this.extend);
                            await this.$store.dispatch({
                                type: "customer/modify",
                                data: this.customer
                            });
                            (this.$refs.customerForm as any).resetFields();
                            (this.$refs.extendForm as any).resetFields();
                            this.$emit("save-success");
                            this.$emit("input", false);
                        }
                    })
                }
            });
        }
        cancel() {
            (this.$refs.customerForm as any).resetFields();
            this.extend = {};
            this.$emit("input", false);
        }
        visibleChange(value: boolean) {
            if (!value) {
                this.$emit("input", value);
            }
        }
        extendRule = {
            mobile: [{
                required: true,
                message: "手机必填",
                trigger: "blur"
            }]
        };
        customerRule = {
            mobile: [{
                required: true,
                message: "手机必填",
                trigger: "blur"
            }],
            customerName: [{
                required: true,
                message: "渠道姓名必填",
                trigger: "blur"
            }]
        };
    }
</script>