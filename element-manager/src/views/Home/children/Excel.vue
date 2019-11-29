<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>会议管理</el-breadcrumb-item>
      <el-breadcrumb-item>文件列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="12">
          <el-input
            placeholder="请输入内容"
            v-model="queryInfo.name"
            class="input-with-select"
            clearable
            @clear="getExcelList"
          >
            <el-button slot="append" icon="el-icon-search" @click="getExcelList"></el-button>
          </el-input>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table :data="excelList" style="width: 100%" boder stripe>
        <el-table-column type="index" width="100"></el-table-column>
        <el-table-column prop="name" label="文件名" width="380"></el-table-column>
        <el-table-column label="操作">
          <!-- 使用作用域插槽获取当前行的数据 -->
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="删除" placement="top" :enterable="false">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="showDelDialog(scope.row.id)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="pageSizes"
        :page-size="queryInfo.length"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <!-- 提示对话框 -->
    <el-dialog title="删除文件" :visible.sync="dialogVisible" width="30%">
      <span>确定删除该文件吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cencelshowDelDialog">取 消</el-button>
        <el-button type="primary" @click="delExcel">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { request } from '../../../network/request'

export default {
  name: '',
  data () {
    return {
      queryInfo: {
        name: '',
        page: 1,
        length: 5
      },
      excelList: [],
      pageSizes: [5, 10, 100, 500],
      total: 0,
      dialogVisible: false,
      delId: 0
    }
  },
  methods: {
    getExcelList () {
      request({
        url: '/web/excel',
        params: this.queryInfo
      }).then(res => {
        this.excelList = res.data
        this.total = res.total
      })
    },
    handleSizeChange (length) {
      this.queryInfo.length = length
      this.getExcelList()
    },
    handleCurrentChange (current) {
      this.queryInfo.page = current
      this.getExcelList()
    },
    showDelDialog (id) {
      this.delId = id
      this.dialogVisible = true
    },
    cencelshowDelDialog () {
      this.delId = 0
      this.dialogVisible = false
    },
    delExcel () {

      request({
        url: '/web/excel/del',
        method: 'get',
        params: {
          id: this.delId
        }
      }).then(res => {
        this.$message({
          message: res.msg,
          type: 'success',
          showClose: true
        })
        this.dialogVisible = false
        this.getExcelList()
      })
    }
  },
  created () {
    this.getExcelList()
  }
}
</script>
<style>
</style>