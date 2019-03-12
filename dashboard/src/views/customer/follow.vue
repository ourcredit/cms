<template>
    <div>
        <Modal title="新增跟进" :value="value" @on-ok="save" @on-visible-change="visibleChange">
            <Form ref="channelForm" label-position="top" :rules="followlRule" :model="follow">
                <Row :gutter="16">
                    <FormItem label="下次跟进时间" prop="nextfollowTime">
                        <DatePicker v-model="follow.nextfollowTime" type="date" placeholder="下次跟进时间" style="width: 200px"></DatePicker>
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="跟进内容" prop="visitContent">
                        <Input v-model="follow.visitContent" :maxlength="32" />
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="指定跟进时间" prop="followTime">
                        <DatePicker v-model="follow.followTime" type="date" placeholder="下次跟进时间" style="width: 200px"></DatePicker>
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
        follow: any = {
            type: 3
        };
        get customer() {
            var u = this.$store.state.customer.editCustomer;
            return u;
        }
        save() {
            if (this.customer == null || this.customer.id == null) {
                this.$router.push({
                    name: 'customerlist'
                });
                return;
            }
            let _ = this;
            (this.$refs.channelForm as any).validate(async (valid: boolean) => {
                if (valid) {
                    debugger;
                    _.follow.objectId = _.customer.id;
                    _.follow.type = 3;
                    // _.follow.followTime =new Date( _.follow.followTime).toLocaleDateString();
                    // if (_.follow.nextfollowTime) {
                    //     _.follow.nextfollowTime =new Date( _.follow.nextfollowTime).toLocaleDateString();
                    // }
                    await this.$store.dispatch({
                        type: "channel/follow",
                        data: this.follow
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
        followlRule = {
            visitContent: [{
                required: true,
                message: "跟进结果必填",
                trigger: "blur"
            }]
        };
    }
</script>