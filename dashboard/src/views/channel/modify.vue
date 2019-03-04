<template>
  <div>
    <Modal width="50%" title="添加渠道" :value="value" @on-ok="save" @on-visible-change="visibleChange">
      <Form ref="channelForm" label-position="top" :rules="channelRule" :model="channel">
        <Row :gutter="16">
          <Col span="8">
          <FormItem label="渠道姓名" prop="channelName">
            <Input v-model="channel.channelName" :maxlength="32" :minlength="2" />
          </FormItem>
          </Col>
          <Col span="8">
          <FormItem label="电话" prop="mobile">
            <Input v-model="channel.mobile" :maxlength="32" />
          </FormItem>
          </Col>
          <Col span="8">
          <FormItem label="渠道等级" prop="level">
            <Select v-model="channel.level" filterable>
              <Option v-for="item in ChannelLevel" :value="item.name" :key="item.name">{{ item.name }}</Option>
            </Select>
          </FormItem>
          </Col>
        </Row>
        <Row :gutter="16">
          <Col span="8">
          <FormItem label="渠道类型" prop="type">
            <Select v-model="channel.type" filterable>
              <Option v-for="item in ChannelType" :value="item.name" :key="item.name">{{ item.name }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="8">
          <FormItem label="工作单位" prop="workUnit">
            <Input v-model="channel.workUnit" :maxlength="32" />
          </FormItem>
          </Col>
          <Col span="8">
          <FormItem label="岗位" prop="post">
            <Input v-model="channel.post" :maxlength="32"></Input>
          </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col span="8">
          <FormItem label="主营业务" prop="business">
            <Select v-model="channel.business" filterable>
              <Option v-for="item in ChannelBusiness" :value="item.name" :key="item.name">{{ item.name }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="8">
          <FormItem label="单位地址" prop="address">
            <Input v-model="channel.address" :maxlength="32" />
          </FormItem>
          </Col>
          <Col span="8">
          </Col>
        </Row>
        <Row :gutter="16">
          <Col>
          <FormItem label="备注" prop="remark">
            <Input v-model="channel.remark" type="textarea" :maxlength="32" :minlength="2" :autosize="{minRows: 2,maxRows: 5}"
              placeholder="备注"></Input>
          </FormItem>
          </Col>

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
  export default class Createchannel extends AbpBase {
    @Prop({
      type: Boolean,
      default: false
    })
    value: boolean;
    selectRoles: Array < any > = new Array < any > ();
    get ChannelType() {
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
    get ChannelLevel() {
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
    get ChannelBusiness() {
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
    get channel() {
      var u = this.$store.state.channel.channel;
      return u;
    }
    save() {
      (this.$refs.channelForm as any).validate(async (valid: boolean) => {
        if (valid) {
          this.channel.roles = this.selectRoles;
          await this.$store.dispatch({
            type: "channel/modify",
            data: this.channel
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
      mobile: [{
        required: true,
        message: "手机必填",
        trigger: "blur"
      }],
      channelName: [{
        required: true,
        message: "渠道姓名必填",
        trigger: "blur"
      }]
    };
  }
</script>