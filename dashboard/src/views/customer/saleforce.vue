<template>
    <div>
        <Modal title="设置目标" :value="value" @on-ok="save" @on-visible-change="visibleChange">
            <Form ref="channelForm" label-position="top" :rules="forceRule" :model="force">
                <Row :gutter="16">
                    <FormItem label="年月" prop="month">
                       <DatePicker @on-change="time1" format="yyyyMM" v-model="force.showMonth" placeholder="年月" type="month" style="width: 200px"></DatePicker>
                    </FormItem>
                </Row>
                <Row :gutter="16">
                    <FormItem label="目标" prop="force">
                         <InputNumber style="width:160px" v-model="force.showForce" :step="0.01" :min="1"></InputNumber>万
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
    export default class Createforce extends AbpBase {
        @Prop({
            type: Boolean,
            default: false
        })
        value: boolean;
        force: any = {};
     
        get user() {
            var u = this.$store.state.user.editUser;
            return u;
        }
        save() {
            if (this.user == null || this.user.id == null) {
               this.$Message.error("请选择员工再设置目标");
                return;
            }
            let _ = this;
            (this.$refs.channelForm as any).validate(async (valid: boolean) => {
                if (valid) {
                    _.force.userId = _.user.id;
                    await this.$store.dispatch({
                        type: "force/modify",
                        data: _.force
                    });
                    (this.$refs.channelForm as any).resetFields();
                    this.$emit("save-success");
                    this.$emit("input", false);
                }
            });
        }
        time1(e){
            this.force.showMonth=e;
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
        forceRule = {
          
        };
    }
</script>