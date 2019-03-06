<template>
  <div>
    <Card dis-hover>
      <div class="page-body">
        <Form ref="queryForm" :label-width="80" label-position="left" inline>
          <Row :gutter="16">
            <Col span="5">
            <FormItem label="用户名:" style="width:100%">
              <Input v-model="filters.account"></Input>
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="姓名" style="width:100%">
              <Input v-model="filters.userName"></Input>
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="渠道类型:">
              <Select clearable v-model="filters.type" style="width:160px">
                <Option v-for="item in ChannelType" :value="item.name" :key="item.name">{{ item.name }}</Option>
              </Select>
            </FormItem>
            </Col>
            <Col span="5">
            <FormItem label="创建时间" style="width:100%">
              <DatePicker v-model="filters.creationTime" type="datetimerange" format="yyyy-MM-dd" style="width:100%"
                placement="bottom-end" placeholder="选择时间"></DatePicker>
            </FormItem>
            </Col>
            <Col span="4">
            <Button icon="ios-search" type="primary" size="large" @click="init" class="toolbar-btn">查找</Button>
            </Col>
          </Row>
        </Form>
      </div>
    </Card>
    <Card dis-hover>
      <div class="page-body">
        <Row :gutter="16">
          <Col span="20">
          <Tag color="success" type="border">本月约饭量 300</Tag>
          <Tag color="success" type="border">累计约饭量 3000</Tag>
          <Tag color="success" type="border">本月约谈量 200</Tag>
          <Tag color="success" type="border">累计约谈量 2000</Tag>
          </Col>
          <Col span="3">
          <Button @click="Create" icon="android-add" type="primary" size="large">新增</Button>
          </Col>
        </Row>
        <div class="margin-top-10">
          <SaleTable ref="table" :filters="filters" :type="'channel'" :columns="columns"></SaleTable>
        </div>
      </div>
    </Card>
    <Modify v-model="ModalShow" @save-success="init"></Modify>
    <Visit :type="type" v-model="VisitShow" @save-success="init"></Visit>
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
  import AbpBase from "../../lib/abpbase";
  import PageRequest from "../../store/entities/page-request";
  import Modify from "./modify.vue";
  import Visit from "./visit.vue";
  import Lunch from "./lunch.vue";
  import User from "@/store/entities/user";
  import SaleTable from "@/components/saletable.vue";
  @Component({
    components: {
      Modify,
      SaleTable,
      Visit
    }
  })
  export default class Users extends AbpBase {
    filters: Object = {
      account: "",
      userName: "",
      creationTime: null
    };

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
    ModalShow: boolean = false;
    VisitShow: boolean = false;
    type:Number=1;
    get list() {
      return this.$store.state.user.list;
    }
    get loading() {
      return this.$store.state.channel.loading;
    }
    Create() {
      var u = new User();
      this.$store.commit("channel/edit", u);
      this.ModalShow = true;
    }
    Modify() {
      this.ModalShow = true;
    }
    VisitModify() {
      this.type=1;
      this.VisitShow = true;
    }
    LunchModify() {
       this.type=2;
      this.VisitShow = true;
    }
    Details() {
      this.$router.push({
        name: "channelsfirst"
      })
    }
    init() {
      var t: any = this.$refs.table;
      if(t){
      t.getpage();
      }
    }
    columns = [{
        title: "渠道编号",
        key: "code",
        render: (h: any, params: any) => {
          return h(
            "Button", {
              props: {
                type: "primary",
                size: "small"
              },
              style: {
                marginRight: "5px"
              },
              on: {
                click: () => {
                  this.$store.dispatch({
                    type: "channel/get",
                    data: params.row
                  });
                  this.Details();
                }
              }
            },
            params.row.code
          );
        }
      },
      {
        title: "录入日期",
        key: "creationTime"
      },
      {
        title: "业务部门",
        key: "post"
      },
      {
        title: "贷款顾问",
        key: "creatorName"
      },
      {
        title: "渠道姓名",
        key: "channelName"
      },
      {
        title: "渠道等级",
        key: "level"
      },
      {
        title: "渠道类型",
        key: "type"
      },
      {
        title: "渠道单位",
        key: "workUnit"
      },
      {
        title: "主营业务",
        key: "business"
      },
      {
        title: "拜访日期",
        key: "visitTime",
        render: (h: any, params: any) => {
          return h(
            "span",
            new Date(params.row.visitTime).toLocaleDateString()
          );
        }
      },
      {
        title: "拜访量",
        key: "visitCount"
      },
      {
        title: "约饭日期",
        key: "lunchTime",
        render: (h: any, params: any) => {
          return h(
            "span",
            new Date(params.row.lunchTime).toLocaleDateString()
          );
        }
      },
      {
        title: "约饭量",
        key: "lunchCount"
      },
      {
        title: "操作",
        key: "Actions",
        width: 150,
        render: (h: any, params: any) => {
          return h("div", [
            h(
              "Button", {
                props: {
                  type: "primary",
                  size: "small"
                },
                style: {
                  marginRight: "5px"
                },
                on: {
                  click: () => {
                    this.$store.dispatch({
                      type: "channel/get",
                      data: params.row
                    });
                    this.VisitModify();
                  }
                }
              },
              "拜访"
            ),
            h(
              "Button", {
                props: {
                  type: "primary",
                  size: "small"
                },
                style: {
                  marginRight: "5px"
                },
                on: {
                  click: () => {
                    this.$store.dispatch({
                      type: "channel/get",
                      data: params.row
                    });
                    this.LunchModify();
                  }
                }
              },
              "约饭"
            ),
            h(
              "Button", {
                props: {
                  type: "primary",
                  size: "small"
                },
                style: {
                  marginRight: "5px"
                },
                on: {
                  click: () => {
                    this.$store.dispatch({
                      type: "channel/get",
                      data: params.row
                    });
                    this.Modify();
                  }
                }
              },
              "新增客户"
            )
          ]);
        }
      }
    ];
    async created() {
      this.init();
    }
  }
</script>