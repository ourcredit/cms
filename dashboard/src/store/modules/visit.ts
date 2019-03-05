import { Store, Module, ActionContext } from "vuex";
import ListModule from "@/store/modules/base/list-module";
import IListState from "@/store/modules/base/list-state";
import Ajax from "@/lib/ajax";
import Visit from "@/store/entities/visit";
import PageResult from "@/store/entities/page-result";
import ListMutations from "@/store/modules/base/list-mutations";
interface IVisitState extends IListState<Visit> {
  visit: Visit;
}
class CategoryMutations extends ListMutations<Visit> { }
class VisitModule extends ListModule<IVisitState, any, Visit> {
  state = {
    totalCount: 0,
    currentPage: 1,
    pageSize: 10,
    list: new Array<Visit>(),
    loading: false,
    visit: new Visit(),
  };
  actions = {
    async getAll(
      context: ActionContext<IVisitState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      let reponse: any = await Ajax.post("/api/channel/visits", payload.data);
      context.state.loading = false;
      let page: PageResult<Visit> = reponse.data as PageResult<Visit>;
      context.state.totalCount = page.total;
      context.state.list = page.records;
    }
  };
  mutations = {
    setCurrentPage(state: IVisitState, page: number): void {
      state.currentPage = page;
    },
    setPageSize(state: IVisitState, pagesize: number): void {
      state.pageSize = pagesize;
    },
    edit(state: IVisitState, de: Visit): void {
      state.visit = de;
    }
  };
}
const visit: VisitModule = new VisitModule();
export default visit;
