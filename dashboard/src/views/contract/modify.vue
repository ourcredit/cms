<template>
    <div>
        <Tabs value="detail">
            <TabPane label="基本信息" name="detail">
                <Card>
                    <p slot="title">
                        <Icon type="ios-film-outline"></Icon>
                        基本信息
                    </p>
                    <Form ref="contractForm" label-position="top" :rules="contractRule" :model="contract">
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="合同编号" prop="contractCode">
                                <Input v-model="contract.contractCode" :maxlength="32" :minlength="2" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户编号" prop="customerCode">
                                <Input v-model="contract.customerCode" :maxlength="32" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户姓名" prop="customerName">
                                <Input v-model="contract.customerName" :maxlength="32" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户来源" prop="level">
                                <Select v-model="contract.resource" filterable>
                                    <Option v-for="item in contractType" :value="item.name" :key="item.name">{{item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="渠道" prop="type">
                                <Select v-model="contract.channel" filterable>
                                    <Option v-for="item in ChannelList" :value="item.key" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="业务类型" prop="business">
                                 <Select v-model="contract.business" filterable>
                                    <Option v-for="item in contractBusiness" :value="item.name" :key="item.name">{{item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户等级" prop="level">
                                <Select v-model="contract.level" filterable>
                                    <Option v-for="item in contractLevel" :value="item.name" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="渠道电话" prop="channelMobile">
                                <Input v-model="contract.channelMobile" :maxlength="32" />
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="手机号1" prop="mobile1">
                                <Input v-model="contract.mobile1" :maxlength="32" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="座机" prop="telphone">
                                <Input v-model="contract.telphone" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="身份证" prop="idCard">
                                <Input v-model="contract.idCard" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="性别" prop="gender">
                                <Select v-model="contract.gender" filterable>
                                    <Option v-for="item in Gender" :value="item.name" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="婚否" prop="maritalStatus">
                                <Select v-model="contract.maritalStatus" filterable>
                                    <Option v-for="item in Marriage" :value="item.name" :key="item.name">{{
                                        item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="成就日期" prop="creationTime">
                                <Input readonly v-model="contract.creationTime" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="贷款顾问" prop="creatorName">
                                <Input readonly v-model="contract.creatorName" :maxlength="32" />
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="业务团队" prop="term">
                                <Input readonly v-model="contract.term" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="业务部门" prop="orgName">
                                <Input readonly v-model="contract.orgName" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="收据编号" prop="number">
                                <Input v-model="contract.number" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                        </Row>
                    </Form>
                </Card>
                <Card>
                    <p slot="title">
                        <Icon type="ios-film-outline"></Icon>
                        贷款信息
                    </p>
                    <Form ref="daikuanxinxiForm" label-position="top" :rules="daikuanxinxiRule" :model="daikuanxinxi">
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="贷款金额" prop="price">
                                <InputNumber style="width:160px" :max="999" v-model="daikuanxinxi.price" :step="0.1"
                                    :min="0.1" :formatter="value => ` ${value}     万元`"></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="贷款年限" prop="year">
                                <InputNumber style="width:160px" :max="10" v-model="daikuanxinxi.year" :step="1" :min="1"
                                    :formatter="value => ` ${value}     年`"></InputNumber>

                                <span slot="append">年</span>
                                </Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="还款方式" prop="backType">
                                <Select v-model="daikuanxinxi.backType" filterable>
                                    <Option v-for="item in backType" :value="item.name" :key="item.name">{{ item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="用款时间" prop="useTime">
                                <InputNumber style="width:160px" :max="365" v-model="daikuanxinxi.useTime" :step="1"
                                    :min="1" :formatter="value => ` ${value}     天`"></InputNumber>

                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="贷款利率" prop="percent">
                                <InputNumber style="width:160px" :max="1" v-model="daikuanxinxi.percent" :step="0.1"
                                    :min="0.01" :formatter="value => ` ${value}     %/年`"></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="月供" prop="monthBack">
                                <InputNumber style="width:160px" v-model="daikuanxinxi.monthBack" :step="1" :min="0"
                                    :formatter="value => ` ${value}     元`"></InputNumber>

                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="贷款机构" prop="org">
                                <Select v-model="daikuanxinxi.org" filterable>
                                    <Option v-for="item in moneyOrg" :value="item.name" :key="item.name">{{
                                        item.name }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="客户经理" prop="jingli">
                                <Input v-model="daikuanxinxi.jingli" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                        </Row>

                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="联系电话" prop="mobile">
                                <Input v-model="daikuanxinxi.mobile" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="垫资需求" prop="dianzixuqiu">
                                <Select v-model="daikuanxinxi.dianzixuqiu" filterable>
                                    <Option v-for="item in dianzixuqiu" :value="item.name" :key="item.name">{{
                                        item.name }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="产品类型" prop="productType">
                                <Select v-model="daikuanxinxi.productType" filterable>
                                    <Option v-for="item in productType" :value="item.name" :key="item.name">{{
                                        item.name }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="垫资金额" prop="dianzijine">
                                <InputNumber style="width:160px" v-model="daikuanxinxi.dianzijine" :step="1" :min="0"
                                    :formatter="value => ` ${value}     元`"></InputNumber>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="还款日" prop="huankuanri">
                                <InputNumber style="width:160px" v-model="daikuanxinxi.huankuanri" :step="1" :min="0"
                                    :formatter="value => ` ${value}     元`"></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="到日期" prop="daoqiri">
                                <InputNumber style="width:160px" v-model="daikuanxinxi.daoqiri" :step="1" :min="0"
                                    :formatter="value => ` ${value}     元`"></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="负债" prop="fuzhai">
                                <InputNumber style="width:160px" v-model="daikuanxinxi.fuzhai" :step="1" :min="0"
                                    :formatter="value => ` ${value}     元`"></InputNumber>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col>
                            <FormItem label="备注" prop="remark">
                                <Input v-model="daikuanxinxi.remark" type="textarea" :maxlength="32" :minlength="2"
                                    :autosize="{minRows: 2,maxRows: 5}" placeholder="备注"></Input>
                            </FormItem>
                            </Col>

                        </Row>
                    </Form>
                </Card>
                <Card>
                    <p slot="title">
                        <Icon type="ios-film-outline"></Icon>
                        服务费信息
                    </p>
                    <Form ref="fuwufeiForm" label-position="top" :rules="fuwufeiRule" :model="fuwufei">
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="付款方式" prop="price">
                                <Select v-model="fuwufei.fukuanfangshi" filterable>
                                    <Option v-for="item in backType" :value="item.name" :key="item.name">{{ item.name
                                        }}</Option>
                                </Select>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="应收金额" prop="jine">
                                <InputNumber style="width:160px" :max="10" v-model="fuwufei.jine" :step="1" :min="1">
                                    <span slot="append">元</span></InputNumber>

                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="实收金额" prop="shishoujine">
                                <InputNumber style="width:160px" :max="10" v-model="fuwufei.shishoujine" :step="1" :min="1">
                                    <span slot="append">元</span></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="费率" prop="feilv">
                                <InputNumber style="width:160px" :max="365" v-model="fuwufei.feilv" :step="1" :min="1"
                                    :formatter="value => ` ${value}     天`"></InputNumber>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="欠费" prop="qianfei">
                                <InputNumber style="width:160px" :max="1" v-model="fuwufei.qianfei" :step="0.1" :min="0.01"
                                    :formatter="value => ` ${value}     %/年`"></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="返费" prop="fanfei">
                                <InputNumber style="width:160px" v-model="fuwufei.fanfei" :step="1" :min="0"></InputNumber>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="成本" prop="chengben">
                                <Input v-model="fuwufei.chengben" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="代收" prop="daishou">
                                <Input v-model="fuwufei.daishou" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                        </Row>

                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="补交时间" prop="bujiaoshijian">
                                <Input v-model="fuwufei.bujiaoshijian" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="补缴金额" prop="bujiaojine">
                                <InputNumber style="width:160px" v-model="fuwufei.bujiaojine" :step="1" :min="0"></InputNumber>
                            </FormItem>
                            </Col>
                        </Row>
                    </Form>
                </Card>
                <Card>
                    <p slot="title">
                        <Icon type="ios-film-outline"></Icon>
                        合作机构信息
                    </p>
                    <Form ref="hezuojigouForm" label-position="top" :rules="hezuojigouRule" :model="hezuojigou">
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="机构名称" prop="price">
                                <Input v-model="hezuojigou.jigoumingcheng" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="联系人" prop="year">
                                <Input v-model="hezuojigou.lianxiren" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="电话" prop="backType">
                                <Input v-model="hezuojigou.dianhua" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="开户行" prop="useTime">
                                <Input v-model="hezuojigou.kaihuhang" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col span="6">
                            <FormItem label="银行" prop="percent">
                                <Input v-model="hezuojigou.yinhang" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                            <Col span="6">
                            <FormItem label="银行卡号" prop="monthBack">
                                <Input v-model="hezuojigou.yinhangkahao" :maxlength="32"></Input>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row :gutter="16">
                            <Col>
                            <FormItem label="备注" prop="remark">
                                <Input v-model="hezuojigou.remark" type="textarea" :maxlength="32" :minlength="2"
                                    :autosize="{minRows: 2,maxRows: 5}" placeholder="备注"></Input>
                            </FormItem>
                            </Col>
                        </Row>
                    </Form>
                </Card>
            </TabPane>
            <TabPane label="房产信息" name="follow">
                房产信息
            </TabPane>
            <TabPane label="收入信息" name="talk">
                收入信息
            </TabPane>
            <TabPane label="征信信息" name="forother">
                征信信息
            </TabPane>
            <TabPane label="影像资料" name="forother">
                影像资料
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
    import SaleTable from "@/components/saletable.vue";
    import Util from "../../lib/util";
    import AbpBase from "../../lib/abpbase";
    import {
        debug
    } from "util";
    @Component({
        components: {
            SaleTable,
        }
    })
    export default class contractDetail extends AbpBase {
        filters: any = {}
        get contract() {
            var u = this.$store.state.contract.editContract;
            if (!u || !u.customerId) {
                u = {
                    type: "抵押贷款,信用贷",
                    customerId: 1,
                    customerCode: "aaa",
                    customerName: "张三"
                }
            }

            return u;
        }
        daikuanxinxi: any = {};
        fuwufei: any = {};
        hezuojigou: any = {};
        save() {
            (this.$refs.contractForm as any).validate(async (valid: boolean) => {
                if (valid) {

                    this.contract.daikuanxinxi = JSON.stringify(this.daikuanxinxi);
                    this.contract.fuwufei = JSON.stringify(this.fuwufei);
                    this.contract.hezuojigou = JSON.stringify(this.hezuojigou);

                    await this.$store.dispatch({
                        type: "contract/modify",
                        data: this.contract
                    });
                    (this.$refs.contractForm as any).resetFields();
                    (this.$refs.fuwufeiForm as any).resetFields();
                    (this.$refs.hezuojigouForm as any).resetFields();
                    (this.$refs.daikuanxinxiForm as any).resetFields();
                    this.$emit("save-success");
                    this.$emit("input", false);
                    this.$router.push({
                        name: "contractslist"
                    })
                }
            });
        }
        mounted() {
            if (!this.contract || !this.contract.customerId) {
                this.$router.push({
                    name: "contractslist"
                })
            }
        }
        async created() {}
        cancel() {
            (this.$refs.contractForm as any).resetFields();
            (this.$refs.fuwufeiForm as any).resetFields();
            (this.$refs.hezuojigouForm as any).resetFields();
            (this.$refs.daikuanxinxiForm as any).resetFields();
            this.$emit("input", false);
            this.$router.push({
                name: "contractslist"
            });
        }
        visibleChange(value: boolean) {
            if (!value) {
                this.$emit("input", value);
            }
        }
        contractRule = {
            mobile: [{
                required: true,
                message: "手机必填",
                trigger: "blur"
            }],
            contractName: [{
                required: true,
                message: "渠道姓名必填",
                trigger: "blur"
            }]
        };
        daikuanxinxiRule = {
            mobile: [{
                required: true,
                message: "手机必填",
                trigger: "blur"
            }],
            contractName: [{
                required: true,
                message: "渠道姓名必填",
                trigger: "blur"
            }]
        };
        hezuojigouRule = {
            mobile: [{
                required: true,
                message: "手机必填",
                trigger: "blur"
            }],
            contractName: [{
                required: true,
                message: "渠道姓名必填",
                trigger: "blur"
            }]
        };
        fuwufeiRule = {
            mobile: [{
                required: true,
                message: "手机必填",
                trigger: "blur"
            }],
            contractName: [{
                required: true,
                message: "渠道姓名必填",
                trigger: "blur"
            }]
        };
        columns = [{
                title: "拜访时间",
                key: "creationTime",
                render: (h: any, params: any) => {
                    return h(
                        "span",
                        new Date(params.row.creationTime).toLocaleDateString()
                    );
                }
            },
            {
                title: "拜访时长",
                key: "time"
            },
            {
                title: "拜访地点",
                key: "visitPlace"
            },
            {
                title: "拜访形式",
                key: "type",
                render: (h: any, params: any) => {
                    return h(
                        "span",
                        params.row.type == 1 ? '约饭' : '约谈'
                    );
                }
            },
            {
                title: "拜访结果",
                key: "visitResult"
            }
        ];
        get contractLevel() {
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
   
        get contractType() {
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
        get contractBusiness() {
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
        get Marriage() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "婚否") {
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
        get Gender() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "性别") {
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
    }
</script>