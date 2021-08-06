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
            :title="id?$t('user_columns.update_title'):$t('user_columns.create_title')"
            :ok-text="$t('modal.ok_text')"
            :cancel-text="$t('modal.cancel_text')"
            @on-ok="handleSaveUpdateData">
            <div class="modal-warp">
                <!-- 此处我们将会按照一个个数据添加，此处在建立完成下面的数据之后需要再次进行修改 -->
                <div class="item">
                    <label>{{$t('user_columns.name')}}：</label>
                    <Input
                    v-model="name"
                    show-word-limit
                    maxlength="100"
                    :placeholder="$t('modal.placeholder')"
                    style="width: 350px"/>
                </div>
                <div class="item">
                    <label>{{$t('user_columns.userName')}}：</label>
                <Input
                    v-model="userName"
                    show-word-limit
                    maxlength="100"
                    :placeholder="$t('modal.placeholder')"
                    style="width: 350px"/>
                </div>
                <div class="item">
                    <label>{{$t('user_columns.password')}}：</label>
                    <Input
                        v-model="password"
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
            password:"",
            //操作记录需要在修改页进行显示，但是要设置成不可修改
            //设置最后更新时间，在进行修改之后需要同步更新
            crtDttm:""
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
        param(/*val*/) {
            this.page = 1;
            this.limit = 10;
            this.getTableData();
        }
    },

    computed:{
        columns() {
            return[
                {
                    //用户名
                    title:this.$t("user_columns.name"),
                    key:"name",
                    sortable:true
                },
                //登陆账号
                {
                    title:this.$t("user_columns.userName"),
                    key:"userName",
                    sortable:true
                },
                //创建时间
                {
                    title:this.$t("user_columns.createTime"),
                    key:"createDttm"
                },
            
                //status状态列
                {
                    title:this.$t("user_columns.status"),
                    key:"status",
                    render: (h, params) => {
                            const row = params.row;
                            const color = row.status === 1 ? 'primary' : row.status === 2 ? 'success' : 'error';
                            const text = row.status === 1 ? 'Working' : row.status === 2 ? 'Freezing' : 'Closing';

                            return h('Tag', {
                                props: {
                                    type: 'dot',
                                    color: color
                                }
                            }, text);
                        }
                },

                //action操作
                {
                title: this.$t("user_columns.action"),
                slot: "action",
                width: 200,
                align: "center"
                }
            ];
        }
    },

    created() {
        this.getTableData();
    },
    methods:{
        handleReset() {
            this.page = 1;
            this.limit = 10;
            this.id = "";
            this.name = "";
            this.userName = "";
            this.password= "";
        },

        //对应的是action列的内容
        handleButtonSelect(row,key) {
            switch (key) {
                case 1:
                    this.getRowData(row);
                    break;
                case 2:
                    this.handleDeleteRow(row);
                    break;
                default:
                    break;
            }
        },

        handleSaveUpdateData() {
            let data = {
                name : this.name,
                userName: this.userName,
                password: this.password
            };


            //此处要开始接触到后端的内容
            if (this.id) {
                data.id = this.id;
                this.$axios
                .get("/user/updateUserInfo",{params : data})
                .then(res=>{
                    if(res.data.code === 200) {
                        this.$Modal.success({
                            title:this.$t("tip.title"),
                            content:`${this.name} ` + this.$t("tip.update_success_content")
                        });
                        this.isOpen = false;
                        this.handleReset();
                        this.getTableData();
                    } else {
                        this.$Message.error({
                            content: `${this.name} ` + this.$t("tip.update_fail_content"),
                            duration: 3
                        });
                    }
                })
                .catch(error => {
                    console.log(error);
                    this.$Message.error({
                    content: this.$t("tip.fault_content"),
                    duration: 3
                });
            });
            } else {
                this.$axios
                .get("/user/addUserInfo", { params: data })
                .then(res => {
                    if (res.data.code === 200) {
                    this.$Modal.success({
                        title: this.$t("tip.title"),
                        content: `${this.name} ` + this.$t("tip.add_success_content")
                    });
                    this.isOpen = false;
                    this.handleReset();
                    this.getTableData();
                } else {
                    this.$Message.error({
                    content: `${this.name} ` + this.$t("tip.add_fail_content"),
                    duration: 3
                    });
                }
            })
                .catch(error => {
                    console.log(error);
                    this.$Message.error({
                        content: this.$t("tip.fault_content"),
                        duration: 3
                    });
                });
            }
        },

        handleDeleteRow(row) {
            this.$Modal.confirm({
                title: this.$t("tip.title"),
                okText: this.$t("modal.confirm"),
                cancelText: this.$t("modal.cancel_text"),
                content: `${this.$t("modal.delete_content")} ${row.jobName}?`,
                onOk: () => {
                let data = {
                    sysUserId: row.id
                };
                this.$axios
                .get("/user/delUserInfo", { params: data })
                .then(res => {
                if (res.data.code === 200) {
                    this.$Modal.success({
                    title: this.$t("tip.title"),
                    content:
                    `${row.name} ` + this.$t("tip.delete_success_content")
                    });
                    this.handleReset();
                    this.getTableData();
                } else {
                    this.$Message.error({
                    content: this.$t("tip.delete_fail_content"),
                    duration: 3
                    });
                }
            })
            .catch(error => {
                console.log(error);
                this.$Message.error({
                content: this.$t("tip.fault_content"),
                duration: 3
                });
                });
            }
        })
    },

    getTableData() {
        let data = { page: this.page, limit: this.limit };
        if (this.param) {
            data.param = this.param;
        }
        this.$axios
        .get("/user/getUserListPage", {
            params: data
        })
        .then(res => {
            if (res.data.code === 200) {
                let data = res.data.data;
                this.tableData = data.map(item => {
                item.state = item.status.text;
                return item;
                });
                this.total = res.data.count;
            } else {
                this.$Message.error({
                content: this.$t("tip.request_fail_content"),
                duration: 3
                });
            }
        })
        .catch(error => {
            console.log(error);
                this.$Message.error({
                content: this.$t("tip.fault_content"),
                duration: 3
            });
        });
    },
    onPageChange(pageNo) {
        this.page = pageNo;
        this.getTableData()
    },
    onPageSizeChange(pageSize) {
        this.limit = pageSize;
        this.getTableData()
    },

    handleModalSwitch() {
        this.isOpen = !this.isOpen;
        }
    }

    
};
</script>
<style lang="scss" scoped>
@import "./index.scss";
</style>

