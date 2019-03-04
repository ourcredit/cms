<template>
    <div>
        <Modal title="约饭" :value="value" @on-ok="save" @on-visible-change="visibleChange">
            <Form ref="channelForm" label-position="top" :rules="channelRule" :model="channel">
                <Row :gutter="16">
                    <FormItem label="约饭时长" prop="lunchTime">
                        <DatePicker v-model="lunch.lunchTime" type="date" placeholder="约饭时间" width="100%"></DatePicker>
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="约饭地点" prop="visitPlace">
                        <Input v-model="lunch.lunchPlace" :maxlength="32" />
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="约饭结果" prop="visitResult">
                        <Input v-model="lunch.lunchResult" type="textarea" :maxlength="32" :minlength="2" :autosize="{minRows: 2,maxRows: 5}"
                            placeholder="约饭结果"></Input>
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
        lunch: any = {};
        get channel() {
            var u = this.$store.state.channel.channel;
            return u;
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
                    _.lunch.objectId = _.channel.id;
                    await this.$store.dispatch({
                        type: "channel/lunch",
                        data: this.lunch
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
        channelRule = {
            visitTime: [{
                required: true,
                message: "拜访时间必填",
                trigger: "blur"
            }],
            visitResult: [{
                required: true,
                message: "拜访结果必填",
                trigger: "blur"
            }]
        };
    }
</script>