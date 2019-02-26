<style lang="less">
  @import "./product.less";
</style>
<template>
  <div>
    <Modal title="编辑邮费" :value="value" @on-ok="save" @on-visible-change="visibleChange">
      <Form ref="productForm" label-position="top" :rules="productRule" :model="region">
        <FormItem label="城市名" prop="name">
          <InputNumber style="width:100%" readonly v-model="region.name" :min="0" />
        </FormItem>
        <FormItem label="价格" >
          <InputNumber style="width:100%" v-model="price" :min="0" />
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
  import Product from "@/store/entities/product";
  import auth from "@/lib/auth";
  @Component
  export default class CreateDevice extends AbpBase {
    @Prop({
      type: Boolean,
      default: false
    })
    value: boolean;
    price: Number = 0;
    get region() {
      var t = this.$store.state.region.editRegion;
      if (!t.price||t.price==0) {
        t.price = null;
      } else {
      this.price = t.price * 1.0 / 100
      }
      return t;
    }
    save() {
      if (!this.price) {
        this.$Message.info("请填写价格信息");
        return;
      }
      (this.$refs.productForm as any).validate(async (valid: boolean) => {
        if (valid) {
          this.region.price=this.price;
          await this.$store.dispatch({
            type: "region/modify",
            data: this.region
          });
          (this.$refs.productForm as any).resetFields();
          this.price=0;
          this.$emit("save-success");
          this.$emit("input", false);
          
        }
      });
    }
    mounted() {}
    created() {

    }
    cancel() {
      (this.$refs.productForm as any).resetFields();
      this.$emit("input", false);
    }
    visibleChange(value: boolean) {
      if (!value) {
        this.$emit("input", value);
      }
    }

    productRule = {
      productName: [{
        required: true,
        message: "商品名必填",
        trigger: "blur"
      }],
      productNum: [{
        required: true,
        message: "商品编号必填",
        trigger: "blur"
      }],
      productType: [{
        required: true,
        message: "商品类型必填",
        trigger: "blur"
      }]
    };
  }
</script>