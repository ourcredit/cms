<template>
  <Card dis-hover>
    <ButtonGroup v-if="state" >
        <Button @click="add" type="ghost" icon="plus"></Button>
        <Button @click="modify" type="ghost" icon="edit"></Button>
        <Button @click="del" type="ghost" icon="close"></Button>
    </ButtonGroup>
    <div class="page-body">
      <Tree @on-select-change="select" :data="tree" ></Tree>
    </div>
    <modify v-model="modifyShow" @save-success="init"></modify>
  </Card>
</template>
<script lang="ts">
import { Component, Vue, Inject, Prop, Watch } from "vue-property-decorator";
import AbpBase from "../lib/abpbase";
import Modify from "./treemodify.vue";
@Component({
  components: {
    Modify
  }
})
export default class OrgTree extends AbpBase {
  constructor() {
    super();
  }
  @Prop({
    type: Boolean,
    default: false
  })
  state: Boolean;
  modifyShow: boolean = false;
  async created() {
    this.init();
  }
  async init() {
    await this.$store.dispatch({
      type: "category/initTree",
      data: {}
    });
  }
  add() {
    this.$store.commit("category/setCurrent", { parentId: this.parent.id });
    this.modifyShow = true;
  }
  modify() {
    if (!this.org) return;
    this.modifyShow = true;
  }
  del() {
    if (!this.org) return;
    this.$Modal.confirm({
      title: "删除提示",
      content: "确认要删除么",
      okText: "是",
      cancelText: "否",
      onOk: async () => {
        await this.$store.dispatch({
          type: "category/delOrg",
          data: this.org.id
        });
        await this.init();
      }
    });
  }
  select(opt) {
    var temp = opt[0];
    if (temp) {
      this.$store.commit("category/setTree", temp);
      this.$store.commit("category/setCurrent", temp);
    } else {
      this.$store.commit("category/setTree", null);
    }
    this.$emit("complete");
  }

  get tree() {
    return this.$store.state.category.tree;
  }
  get org() {
    var t = this.$store.state.category.current;
    return t;
  }
  get parent() {
    var t = this.$store.state.category.currentOrg;
    return t;
  }
}
</script>