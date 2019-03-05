<template>
    <div>
        <Modal title="拜访" :value="value" @on-ok="save" @on-visible-change="visibleChange">
            <Form ref="channelForm" label-position="top" :rules="visitRule" :model="visit">
                <Row :gutter="16">
                    <FormItem label="拜访时长" prop="visitTime">
                         <Input placeholder="拜访时长" v-model="visit.time" :maxlength="32" />
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="拜访地点" prop="visitPlace">
                        <Input v-model="visit.visitPlace" :maxlength="32" />
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="拜访结果" prop="visitResult">
                        <Input v-model="visit.visitResult" type="textarea" :maxlength="32" :minlength="2" :autosize="{minRows: 2,maxRows: 5}"
                            placeholder="拜访结果"></Input>
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
        visit: any = {
        };
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