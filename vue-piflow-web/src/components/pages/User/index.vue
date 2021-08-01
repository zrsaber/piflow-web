<template>
    <section>
        <div class="navbar">
            <!-- 初始栏的建立 -->
            <div class="left">
                <span>{{$t("sidebar.user")}}</span>
            </div>
            <div class="right">
                <span class="button-warp" @click="handleModalSwitch">
                    <Icon type="md-add" />
                </span>
            </div>
        </div>
        <div class="input">
            <Input
                suffix="ios-search"
                v-model="param"
                :placeholder="$t('modal.placeholder')"
                style="width: 300px"
            />
        </div>
        <Table border :columns="columns" :data="tableData">
        </Table>
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
    </section>
</template>

<script>
</script>
<style lang="scss" scoped>
@import "./index.scss";
</style>

