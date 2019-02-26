<style lang="less">
  @import "./operation.less";
</style>
<template>
  <div>
    <Modal title="编辑运维区域" :value="value" @on-ok="save" @on-visible-change="visibleChange">
      <Form ref="fffff" label-position="top" :rules="productRule" :model="operater">
        <Tree @on-select-change="selectNode" :data="areas"></Tree>
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
  import Util from "@/lib/util";
  import AbpBase from "@/lib/abpbase";
  import PageRequest from "../../store/entities/page-request";
  import auth from "@/lib/auth";
  @Component
  export default class CreateDevice extends AbpBase {
    @Prop({
      type: Boolean,
      default: false
    })
    value: boolean;
    select: string = "";
    path: string = "";
    get operater() {
      return this.$store.state.operater.editOperater;
    }
    get areas() {
      return this.$store.state.operater.areas as object[] ;
    }
    selectNode(node) {
      console.log(node[0]);
      if (node[0].level != 3) return;
      this.select = node[0].key;
    }
    save() {
      if (this.select == "") {
        this.$Message.loading({
          content: "请选择底层节点"
        });
        return;
      }
      this.operater.region = this.select;
      this.$store.dispatch({
        type: "operater/modify",
        data: this.operater
      });
      (this.$refs.fffff as any).resetFields();
      this.$emit("save-success");
      this.sleep(1000);
      this.$emit("input", false);
    }
    sleep(n) {
      var start = new Date().getTime();
      //  console.log('休眠前：' + start);
      while (true) {
        if (new Date().getTime() - start > n) {
          break;
        }
      }
      // console.log('休眠后：' + new Date().getTime());
    }
    cancel() {
      (this.$refs.fffff as any).resetFields();
      this.$emit("input", false);
    }
    visibleChange(value: boolean) {
      if (!value) {
        this.$emit("input", value);
      }
    }
    productRule = {
      password: [{
        required: true,
        message: "密码必填",
        trigger: "blur"
      }]
    };
  }
</script>