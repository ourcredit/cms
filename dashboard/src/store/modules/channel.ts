import {
    Store,
    Module,
    ActionContext
} from "vuex";
import ListModule from "@/store/modules/base/list-module";
import IListState from "@/store/modules/base/list-state";
import Ajax from "@/lib/ajax";
import Channel from "@/store/entities/channel";
import PageResult from "@/store/entities/page-result";
import ListMutations from "@/store/modules/base/list-mutations";
interface IChannelState extends IListState < Channel > {
    channel: Channel;
    cateList: Array < any > ;
}
class CategoryMutations extends ListMutations < Channel > {}
class ChannelModule extends ListModule < IChannelState, any, Channel > {
    state = {
        totalCount: 0,
        currentPage: 1,
        pageSize: 10,
        list: new Array < Channel > (),
        cateList: new Array < any > (),
        loading: false,
        channel: new Channel(),
    };
    actions = {
        async getAll(
            context: ActionContext < IChannelState, any > ,
            payload: any
        ): Promise < any > {
            context.state.loading = true;
            let reponse: any = await Ajax.post("/api/channel", payload.data);
            context.state.loading = false;
            let page: PageResult < Channel > = reponse.data as PageResult < Channel > ;
            context.state.totalCount = page.total;
            context.state.list = page.records;
        },
        async init(
            context: ActionContext < IChannelState, any > ,
            payload: any
        ): Promise < any > {
            context.state.loading = true;
            let reponse: any = await Ajax.post("/api/channel");
            context.state.loading = false;
            let page: Array < any > = reponse.data as Array < any > ;
            context.state.cateList = page;
        },
        async modify(
            context: ActionContext < IChannelState, any > ,
            payload: any
        ): Promise < any > {
            await Ajax.put("/api/channel", payload.data);
        },

        async delete(
            context: ActionContext < IChannelState, any > ,
            payload: any
        ): Promise < any > {
            await Ajax.delete("/api/channel/" + payload.data.id);
        },
        async batch(
            context: ActionContext < IChannelState, any > ,
            payload: any
        ): Promise < any > {
            await Ajax.post("/api/channel/batch", payload.data);
        },
        async get(
            context: ActionContext < IChannelState, any > ,
            payload: any
        ): Promise < any > {
            let reponse: any = await Ajax.get("/api/channel/" + payload.data.id);
            context.state.channel = reponse.data as Channel;
        },
        async visit(
            context: ActionContext < IChannelState, any > ,
            payload: any
        ): Promise < any > {
            let reponse: any = await Ajax.post("/api/channel/visit", payload.data);
            context.state.channel = reponse.data as Channel;
        },
        async lunch(
            context: ActionContext < IChannelState, any > ,
            payload: any
        ): Promise < any > {
            let reponse: any = await Ajax.post("/api/channel/lunch" , payload.data);
            context.state.channel = reponse.data as Channel;
        }
    };
    mutations = {
        setCurrentPage(state: IChannelState, page: number): void {
            state.currentPage = page;
        },
        setPageSize(state: IChannelState, pagesize: number): void {
            state.pageSize = pagesize;
        },
        edit(state: IChannelState, de: Channel): void {
            state.channel = de;
        }
    };
}
const channel: ChannelModule = new ChannelModule();
export default channel;