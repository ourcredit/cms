<template>
    <div>
        <Modal :title="show" :value="value" @on-ok="save" @on-visible-change="visibleChange">
            <Form ref="channelForm" label-position="top" :rules="visitRule" :model="visit">
                <Row :gutter="16">
                    <FormItem :label="show+'时长'" prop="visitTime">
                        <InputNumber placeholder="时长" :step="1" v-model="visit.time" :max="999" :min="1"></InputNumber>
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem :label="show+'地点'" prop="visitPlace">
                        <Input v-model="visit.visitPlace" :maxlength="32" />
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="拜访类型" prop="type">
                        <Select v-model="type">
                            <Option v-for="item in visitType" :value="item.key" :key="item.key">{{ item.name }}</Option>
                        </Select>
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem :label="show+'结果'" prop="visitResult">
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
        @Prop({
            type: Number,
            default: false
        })
        type: Number;

        visit: any = {};
        visitType: Array < any >= [{
            key: 1,
            name: "拜访"
        }, {
            key: 2,
            name: "约饭"
        }];
        get channel() {
            var u = this.$store.state.channel.channel;
            return u;
        }
        get show() {
            return this.type == 1 ? '拜访' : '约饭';
        }
        save() {
            if (this.channel == null || this.channel.id == null) {
                this.$router.push({
                    name: 'channelslist'
                });
                return;
            }
            let _ = this;
            (this.$refs.channelForm as any).validate(async (valid: boolean) => {
                if (valid) {
                    _.visit.objectId = _.channel.id;
                    _.visit.type = 1;
                    await this.$store.dispatch({
                        type: "channel/visit",
                        data: _.visit
                    });
                    (this.$refs.channelForm as any).resetFields();
                    this.$emit("save-success");
                    this.$emit("input", false);
                }
            });
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