<template>
<div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
        <el-input v-model="listQuery.username" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户名"/>
        <el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户Id"/>
        <el-input v-model="listQuery.mobile" clearable class="filter-item" style="width: 200px;" placeholder="请输入手机号"/>
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
        <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 需要查询的结果 -->
    <el-table v-loadin="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>
        <!-- 获取其中的基本信息 -->
        <el-table-column align="center" width="100px" label="用户ID" prop="id" sortable/>
        <el-table-column align="center" width="100px" label="用户名" prop="username" sortable/>
        <el-table-column align="center" width="100px" label="用户姓名" prop="name" sortable/>
        <el-table-column align="center" label="性别" prop="gender">
            <template slot-scope="scope">
                <el-tag >{{ genderDic[scope.row.gender] }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column align="center" label="用户等级" prop="userLevel">
            <template slot-scope="scope">
                <el-tag >{{ levelDic[scope.row.userLevel] }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column align="center" label="状态" prop="status">
            <template slot-scope="scope">
                <el-tag>{{ statusDic[scope.row.status] }}</el-tag>
            </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="250" class-name="small-padding fixed-width">
            <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="handleDetail(scope.row)">详情</el-button>
            </template>
        </el-table-column>
    </el-table>

    <!-- 这句话暂时存在疑问，先粘贴过来，等会看详细的内容 -->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    
    <!-- 用户编辑对话框 -->
    <el-dialog :visible.sync="userDialogVisible" title="用户编辑">
        <el-form ref="userDetail" :model="userDetail" status-icon label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="用户名" prop="username">
            <el-input v-model="userDetail.username" :disabled="true" />
        </el-form-item>
        <el-form-item label="用户姓名" prop="name">
            <el-input v-model="userDetail.nickname" />
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
            <el-input v-model="userDetail.password" />
        </el-form-item>
        <el-form-item label="用户性别" prop="gender">
            <el-select v-model="userDetail.gender" placeholder="请选择"><el-option v-for="(item, index) in genderDic" :key="index" :label="item" :value="index" /></el-select>
        </el-form-item>
        <el-form-item label="用户等级" prop="userLevel">
            <el-select v-model="userDetail.userLevel" placeholder="请选择"><el-option v-for="(item, index) in levelDic" :key="index" :label="item" :value="index" /></el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
            <el-select v-model="userDetail.status" placeholder="请选择"><el-option v-for="(item, index) in statusDic" :key="index" :label="item" :value="index" /></el-select>
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
        <el-button @click="userDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUserUpdate">确定</el-button>
        </div>
    </el-dialog>
</div>
</template>