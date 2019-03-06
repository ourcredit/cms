import { Store, Module, ActionContext } from "vuex";
import ListModule from "@/store/modules/base/list-module";
import IListState from "@/store/modules/base/list-state";
import Ajax from "@/lib/ajax";
import Customer from "@/store/entities/customer";
import PageResult from "@/store/entities/page-result";
import ListMutations from "@/store/modules/base/list-mutations";
interface ICustomerState extends IListState<Customer> {
  editCustomer: Customer;
}
class CustomerMutations extends ListMutations<Customer> { }
class CustomerModule extends ListModule<ICustomerState, any, Customer> {
  state = {
    totalCount: 0,
    currentPage: 1,
    pageSize: 10,
    list: new Array<Customer>(),
    loading: false,
    editCustomer: new Customer(),
  };
  actions = {
    async getAll(
      context: ActionContext<ICustomerState, any>,
      payload: any
    ): Promise<any> {
      context.state.loading = true;
      let reponse: any = await Ajax.post("/api/customer", payload.data);
      context.state.loading = false;
      let page: PageResult<Customer> = reponse.data as PageResult<Customer>;
      context.state.totalCount = page.total;
      context.state.list = page.records;
    },

    async modify(
      context: ActionContext<ICustomerState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.put("/api/customer", payload.data);
    },

    async delete(
      context: ActionContext<ICustomerState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.delete("/api/customer/" + payload.data.id);
    },

    async batch(
      context: ActionContext<ICustomerState, any>,
      payload: any
    ): Promise<any> {
      await Ajax.post("/api/customer/batch", payload.data);
    },
    async get(
      context: ActionContext<ICustomerState, any>,
      payload: any
    ): Promise<any> {
      let reponse: any = await Ajax.get("/api/customer/" + payload.data);
      context.state.editCustomer = reponse.data as Customer;
    }
  };
  mutations = {
    setCurrentPage(state: ICustomerState, page: number): void {
      state.currentPage = page;
    },
    setPageSize(state: ICustomerState, pagesize: number): void {
      state.pageSize = pagesize;
    },
    edit(state: ICustomerState, de: Customer): void {
      state.editCustomer = de;
    }
  };
}
const cateModule: CustomerModule = new CustomerModule();
export default cateModule;
