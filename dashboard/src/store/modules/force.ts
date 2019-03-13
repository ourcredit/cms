import { Store, Module, ActionContext } from "vuex";
import ListModule from "@/store/modules/base/list-module";
import IListState from "@/store/modules/base/list-state";
import Ajax from "@/lib/ajax";
import SaleForce from "@/store/entities/saleForce";
import PageResult from "@/store/entities/page-result";
import ListMutations from "@/store/modules/base/list-mutations";
interface ISaleForceState extends IListState<SaleForce> {
  editSaleForce: SaleForce;
}
class SaleForceMutations extends ListMutations<SaleForce> { }
class SaleForceModule extends ListModule<ISaleForceState, any, SaleForce> {
  state = {
    totalCount: 0,
    currentPage: 1,
    pageSize: 10,
    list: new Array<SaleForce>(),
    loading: false,
    editSaleForce: new SaleForce(),
  };
  actions = {
    async getAll(
      context: ActionContext<ISaleForceState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      let reponse: any = await Ajax.post("/api/force", payload.data);
      context.state.loading = false;
      let page: PageResult<SaleForce> = reponse.data as PageResult<SaleForce>;
      context.state.totalCount = page.total;
      context.state.list = page.records;
    },

    async modify(
      context: ActionContext<ISaleForceState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.put("/api/force", payload.data);
    },

    async delete(
      context: ActionContext<ISaleForceState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.delete("/api/force/" + payload.data.id);
    },

    async batch(
      context: ActionContext<ISaleForceState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.post("/api/force/batch", payload.data);
    },
    async get(
      context: ActionContext<ISaleForceState, any>,
      payload: any
    ): Promise<any> {
      let reponse: any = await Ajax.get("/api/force/" + payload.data);
      context.state.editSaleForce = reponse.data as SaleForce;
    }
  };
  mutations = {
    setCurrentPage(state: ISaleForceState, page: number): void {
      state.currentPage = page;
    },
    setPageSize(state: ISaleForceState, pagesize: number): void {
      state.pageSize = pagesize;
    },
    edit(state: ISaleForceState, de: SaleForce): void {
      state.editSaleForce = de;
    }
  };
}
const cateModule: SaleForceModule = new SaleForceModule();
export default cateModule;
