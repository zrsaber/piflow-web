<template>
    <section>
        <div class="navbar">
            <!-- 初始栏的建立 -->
            <div class="left">
                <span>{{$t("sidebar.user")}}</span>
            </div>

            <!-- 添加用户的按钮建立 -->
            <div class="right">
                <span class="button-warp" @click="handleModalSwitch">
                    <Icon type="md-add" />
                </span>
            </div>
        </div>

        <!-- 搜索栏的建立，此处的搜索栏与系统的其他地方相同 -->
        <div class="input">
            <Input
                suffix="ios-search"
                v-model="param"
                :placeholder="$t('modal.placeholder')"
                style="width: 300px"
            />
        </div>

        <!-- 建立主体：表单 -->
        <Table border :columns="columns" :data="tableData">
        </Table>

        <!-- 下拉页面的建立 -->
        <div class="page">
            <Page
                :prev-text="$t('page.prev_text')"
                :next-text="$t('page.next_text')"
                show-elevator
                :show-total="true"
                :total="total"
                show-sizer
                @on-change="onPageChange"
                @on-page-size-change="onPageSizeChange"
            />
        </div>

        <!-- 点击添加按钮出现的 -->
    </section>
</template>

<script>
export default {
    name:"user",
    components:{},
    data() {
        return{
            isOpen :false,
            page:1,
            limit:10,
            total:0,
            tableData:[],
            param:"",

            row:null,

            //下面是表格的初始化
        }
    }
}
</script>
<style lang="scss" scoped>
@import "./index.scss";
</style>

