<template>
    <div>
        <Row>
            <Col span="5">
            <OrgTree :state="true" @complete="init"></OrgTree>
            </Col>
        </Row>
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
    import SaleTable from "@/components/saletable.vue";
    import AbpBase from "@/lib/abpbase";
    import PageRequest from "../../../store/entities/page-request";
    import Util from "../../../lib/util";
    import OrgTree from "@/components/orgtree.vue";

    @Component({
        components: {
            SaleTable,
            OrgTree
        }
    })
    export default class PointC extends AbpBase {
        filters: any = {
            name: "",
            code: ""
        };
        get current() {
            return this.$store.state.category.currentOrg;
        }
        init() {}
        async created() {}
    }
</script>