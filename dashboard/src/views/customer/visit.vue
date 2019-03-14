<template>
    <div>
        <Modal title="新增约谈" :value="value" @on-ok="save" @on-visible-change="visibleChange">
            <Form ref="channelForm" label-position="top" :rules="visitRule" :model="visit">
                <Row :gutter="16">
                    <FormItem label="约谈时长" prop="visitTime">
                        <InputNumber placeholder="时长" :step="1" v-model="visit.time" :max="999" :min="1"></InputNumber>
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="约谈地点" prop="visitPlace">
                        <Input v-model="visit.visitPlace" :maxlength="32" />
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="约谈结果" prop="visitResult">
                        <Input v-model="visit.visitResult" type="textarea" :maxlength="32" :minlength="2" :autosize="{minRows: 2,maxRows: 5}"
                            placeholder="结果"></Input>
                    </FormItem>
                </Row>
            </Form>
            <div slot="footer">
                <Button @click="cancel">关闭</Button>
                <Button @click="save" type="primary">保存</Button>
            </div>
        </Modal>


        <Modal title="是否新增合同" :value="showComfirm" @on-ok="goto" @on-visible-change="visibleChange">
            <Select v-model="type" filterable>
                <Option v-for="item in ContractType" :value="item.name" :key="item.name">{{ item.name }}</Option>
            </Select>
            <div slot="footer">
                <Button @click="cancel">关闭</Button>
                <Button @click="goto" type="primary">保存</Button>
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
    import Util from "../../lib/util";
    import AbpBase from "../../lib/abpbase";
    import Channel from "../../store/entities/channel";
    import {
        debug
    } from "util";
    @Component
    export default class CreateVisit extends AbpBase {
        @Prop({
            type: Boolean,
            default: false
        })
        value: boolean;
        visit: any = {};
        visitType: Array < any >= [{
            key: 1,
            name: "拜访"
        }, {
            key: 2,
            name: "约饭"
        }];
        comfirmShow() {
            this.showComfirm = true;
        }
        showComfirm: boolean = false;
        type: String = "";
        get ContractType() {
            var t = this.$store.state.category.cateList;
            var res = new Array < any > ();
            t.forEach(a => {
                if (a.code == "合同类型") {
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
            return u;
        }
        save() {
            if (this.customer == null || this.customer.id == null) {
                return;
            }
            let _ = this;
            (this.$refs.channelForm as any).validate(async (valid: boolean) => {
                if (valid) {
                    _.visit.objectId = _.customer.id;
                    _.visit.type = 3;
                    await this.$store.dispatch({
                        type: "channel/visit",
                        data: _.visit
                    });
                    this.comfirmShow();
                }
            });
        }
        async goto() {
            if (this.type&&this.type!="") {
                this.$store.commit("customer/setContract", {
                    type: this.type,
                    customerId: this.customer.id,
                    customerCode: this.customer.code,
                    customerName: this.customer.name
                });
                this.$router.push({
                    name: "contractmodify"
                });
            }
            (this.$refs.channelForm as any).resetFields();
            this.$emit("save-success");
            this.$emit("input", false);
            this.showComfirm = false;
        }
        cancel() {
            (this.$refs.channelForm as any).resetFields();
            this.$emit("input", false);
        }
        visibleChange(value: boolean) {
            if (!value) {
                this.$emit("input", value);
            }
        }
        visitRule = {
            visitResult: [{
                required: true,
                message: "拜访结果必填",
                trigger: "blur"
            }]
        };
    }
</script>