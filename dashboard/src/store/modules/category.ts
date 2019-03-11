import { Store, Module, ActionContext } from "vuex";
import ListModule from "@/store/modules/base/list-module";
import IListState from "@/store/modules/base/list-state";
import Ajax from "@/lib/ajax";
import Category from "@/store/entities/category";
import PageResult from "@/store/entities/page-result";
import ListMutations from "@/store/modules/base/list-mutations";
import util from "@/lib/util";
interface ICategoryState extends IListState<Category> {
  editCategory: Category;
  cateList: Array<any>;
  tree: Array<any>;
  currentOrg: String;
  current: any;
}
class CategoryMutations extends ListMutations<Category> { }
class CategoryModule extends ListModule<ICategoryState, any, Category> {
  state = {
    totalCount: 0,
    currentPage: 1,
    pageSize: 10,
    list: new Array<Category>(),
    cateList: new Array<any>(),
    loading: false,
    editCategory: new Category(),
    tree: new Array<any>(),
    currentOrg: "",
    current: {}
  };
  actions = {
    async getAll(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      let reponse: any = await Ajax.post("/api/category", payload.data);
      context.state.loading = false;
      let page: PageResult<Category> = reponse.data as PageResult<Category>;
      context.state.totalCount = page.total;
      context.state.list = page.records;
    },
    async init(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      let reponse: any = await Ajax.post("/api/category/all");
      context.state.loading = false;
      let page: Array<any> = reponse.data as Array<any>;
      context.state.cateList = page;
    },
    async initTree(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      let reponse: any = await Ajax.post("/api/tree");
      context.state.loading = false;
      let list: Array<any> = reponse.data as Array<any>;
      context.state.tree = util.genderMenu(list, "parentId", null);
    },
    async modify(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.put("/api/category", payload.data);
    },

    async delete(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.delete("/api/category/" + payload.data.id);
    },

    async batch(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.post("/api/category/batch", payload.data);
    },
    async get(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      let reponse: any = await Ajax.get("/api/category/" + payload.data);
      context.state.editCategory = reponse.data as Category;
    },
    async modifyOrg(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      await Ajax.put("/api/tree", payload.data);
    },
    async delOrg(
      context: ActionContext<ICategoryState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.delete("/api/tree/" + payload.data);
    },
  };
  mutations = {
    setCurrentPage(state: ICategoryState, page: number): void {
      state.currentPage = page;
    },
    setPageSize(state: ICategoryState, pagesize: number): void {
      state.pageSize = pagesize;
    },
    edit(state: ICategoryState, de: Category): void {
      state.editCategory = de;
    },
    setTree(state: ICategoryState, treeId: String): void {
      state.currentOrg = treeId;
    },
    setCurrent(state: ICategoryState, model: any) {
      state.current = { id: model.id, name: model.title, parentId: model.parentId };
    }
  };
}
const cateModule: CategoryModule = new CategoryModule();
export default cateModule;
