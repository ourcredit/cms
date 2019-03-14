import { Store, Module, ActionContext } from "vuex";
import ListModule from "@/store/modules/base/list-module";
import IListState from "@/store/modules/base/list-state";
import Ajax from "@/lib/ajax";
import Contract from "@/store/entities/contract";
import PageResult from "@/store/entities/page-result";
import ListMutations from "@/store/modules/base/list-mutations";
import util from "@/lib/util";
interface IContractState extends IListState<Contract> {
  editContract: Contract;
  current: any;
}
class ContractMutations extends ListMutations<Contract> { }
class ContractModule extends ListModule<IContractState, any, Contract> {
  state = {
    totalCount: 0,
    currentPage: 1,
    pageSize: 10,
    list: new Array<Contract>(),
    loading: false,
    editContract: new Contract(),
  };
  actions = {
    async getAll(
      context: ActionContext<IContractState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      let reponse: any = await Ajax.post("/api/contract", payload.data);
      context.state.loading = false;
      let page: PageResult<Contract> = reponse.data as PageResult<Contract>;
      context.state.totalCount = page.total;
      context.state.list = page.records;
    },
    async modify(
      context: ActionContext<IContractState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.put("/api/contract", payload.data);
    },

    async delete(
      context: ActionContext<IContractState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.delete("/api/contract/" + payload.data.id);
    },

    async batch(
      context: ActionContext<IContractState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.post("/api/contract/batch", payload.data);
    },
    async get(
      context: ActionContext<IContractState, any>,
      payload: any
    ): Promise<any> {
      let reponse: any = await Ajax.get("/api/contract/" + payload.data);
      context.state.editContract = reponse.data as Contract;
    },
  };
  mutations = {
    setCurrentPage(state: IContractState, page: number): void {
      state.currentPage = page;
    },
    setPageSize(state: IContractState, pagesize: number): void {
      state.pageSize = pagesize;
    },
    edit(state: IContractState, de: Contract): void {
      state.editContract = de;
    },
    setContract(state: IContractState, de: any): void {
      state.editContract = de;
    }
  };
}
const cateModule: ContractModule = new ContractModule();
export default cateModule;
