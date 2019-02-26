<style lang="less">
@import "./device.less";
</style>
<template>
  <div>
    <Modal title="编辑设备" :value="value" @on-ok="save" @on-visible-change="visibleChange">
      <Form ref="deviceForm" label-position="top" :rules="deviceRule" :model="device">
        <FormItem label="设备名" prop="deviceName">
          <Input v-model="device.deviceName" readonly :maxlength="32" :minlength="2" />
        </FormItem>
        <FormItem label="设备编号" prop="deviceNum">
          <Input v-model="device.deviceNum" readonly readonly :maxlength="32" :minlength="2" />
        </FormItem>
          <FormItem label="剩余次数" prop="passWord">
             <InputNumber style="width:100%" v-model="device.count"  :min="0"  />
        </FormItem>
      </Form>
      <div slot="footer">
        <Button @click="cancel">关闭</Button>
        <Button @click="save" type="primary">保存</Button>
      </div>
    </Modal>
  </div>
</template>
<script lang="ts">
import { Component, Vue, Inject, Prop, Watch } from "vue-property-decorator";
import Util from "@/lib/util";
import AbpBase from "@/lib/abpbase";
import PageRequest from "../../store/entities/page-request";
import Point from "@/store/entities/device";
@Component
export default class CreateDevice extends AbpBase {
  @Prop({
    type: Boolean,
    default: false
  })
  value: boolean;
  get device() {
    return this.$store.state.device.editDevice;
  }

  save() {
    (this.$refs.deviceForm as any).validate(async (valid: boolean) => {
      if (valid) {
        if(this.device.count<0){
          return;
        }
        await this.$store.dispatch({
          type: "device/addCount",
          data: this.device
        });
        (this.$refs.deviceForm as any).resetFields();
        this.$emit("save-success");
        this.$emit("input", false);
      }
    });
  }
  created() {
  }
  cancel() {
    (this.$refs.deviceForm as any).resetFields();
    this.$emit("input", false);
  }
  visibleChange(value: boolean) {
    if (!value) {
      this.$emit("input", value);
    }
  }

  deviceRule = {
    deviceName: [
      {
        required: true,
        message: "设备名必填",
        trigger: "blur"
      }
    ],
    deviceNum: [
      {
        required: true,
        message: "设备编号必填",
        trigger: "blur"
      }
    ],
    deviceType: [
      {
        required: true,
        message: "设备类型必填",
        trigger: "blur"
      }
    ]
  };
}
</script>