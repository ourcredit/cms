import {  ActionContext } from "vuex";
import ListModule from "@/store/modules/base/list-module";
import IListState from "@/store/modules/base/list-state";
import Ajax from "@/lib/ajax";
import Region from "@/store/entities/region";
import PageResult from "@/store/entities/page-result";
interface IPostState extends IListState<Region> {
  editRegion: Region;
}
class PostModule extends ListModule<IPostState, any, Region> {
  state = {
    totalCount: 0,
    currentPage: 1,
    pageSize: 10,
    list: new Array<Region>(),
    loading: false,
    editRegion: new Region(),
  };
  actions = {
    async getAll(
      context: ActionContext<IPostState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      let reponse: any = await Ajax.post("/api/region", payload.data);
      context.state.loading = false;
      let page: PageResult<Region> = reponse.data as PageResult<Region>;
      context.state.totalCount = page.total;
      context.state.list = page.records;
    },
    async modify(
      context: ActionContext<IPostState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.put("/api/region", payload.data);
    },
    async endisable(
      context: ActionContext<IPostState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.post("/api/region/batch", payload.data);
    },
    async get(
      context: ActionContext<IPostState, any>,
      payload: any
    ): Promise<any> {
      let reponse: any = await Ajax.get("/api/region/" + payload.data);
      context.state.editRegion = reponse.data as Region;
    }
  };
  mutations = {
    setCurrentPage(state: IPostState, page: number): void {
      state.currentPage = page;
    },
    setPageSize(state: IPostState, pagesize: number): void {
      state.pageSize = pagesize;
    },
    edit(state: IPostState, de: Region): void {
      state.editRegion = de;
    }
  };
}
const cateModule: PostModule = new PostModule();
export default cateModule;
