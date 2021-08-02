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
            <!-- 建立操作页面 -->
            <template slot-scope="{ row }" slot="action">
                <Tooltip content="Edit" placement="top-start">
                    <span class="button-warp" @click="handleButtonSelect(row,1)">
                    <Icon type="ios-create-outline" />
                    </span>
                </Tooltip>
                <Tooltip content="Delete" placement="top-start">
                    <span class="button-warp" @click="handleButtonSelect(row,2)">
                    <Icon type="ios-trash" />
                    </span>
                </Tooltip>
            </template>
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
        <Modal
            v-model="isOpen"
            :title="id?$t('admin_columns.update_title'):$t('admin_columns.create_title')"
            :ok-text="$t('modal.ok_text')"
            :cancel-text="$t('modal.cancel_text')"
            @on-ok="handleSaveUpdateData">
            <div class="modal-warp">
                <!-- 此处我们将会按照一个个数据添加，此处在建立完成下面的数据之后需要再次进行修改 -->
                <div class="item">
                    <label>{{$t('admin_columns.jobName')}}：</label>
                    <Input
                    v-model="jobName"
                    show-word-limit
                    maxlength="100"
                    :placeholder="$t('modal.placeholder')"
                    style="width: 350px"/>
                </div>
                <div class="item">
                    <label>{{$t('admin_columns.jobClass')}}：</label>
                <Input
                    v-model="jobClass"
                    show-word-limit
                    maxlength="100"
                    :placeholder="$t('modal.placeholder')"
                    style="width: 350px"/>
                </div>
                <div class="item">
                    <label>{{$t('admin_columns.cronExpression')}}：</label>
                    <Input
                        v-model="cronExpression"
                        show-word-limit
                        maxlength="100"
                        :placeholder="$t('modal.placeholder')"
                        style="width: 350px"/>
                </div>
            </div>
        </Modal>
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

            //下面是表格的初始化
            row:null,
            id:"",
            name:"",
            userName:"",
            ipAddress:"",
            //操作记录需要再次进行添加
            //设置最后更新时间
            lastUpdateDttm:""
            //此处是否要对状态进行初始化，等待实验
        };
    },

    //设置的是最开始可以看到的数据
    watch:{
        //此处设置页面展示的页数是多少个
        isOpen(state) {
            if (!state) {
                this.handleReset();
            }
        },
        // 此处判断最开始显示第几页并且显示多少条数据
        param(val) {
            this.page = 1;
            this.limit = 10;
            this.getTableData();
        }
    },

    computed:{
        columns() {
            return[
                {
                    title:this.$t("user_colums.")
                }
            ]
        }
    }
}
</script>
<style lang="scss" scoped>
@import "./index.scss";
</style>

