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
        <el-col :span="18">
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="excel文件">
              <el-select
                filterable
                v-model="formInline.excel"
                placeholder="选择文件"
                clearable
                @change="selectExcelGet"
              >
                <el-option
                  v-for="item in formInline.excelList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="会议室">
              <el-select
                filterable
                v-model="formInline.meetingroom"
                placeholder="选择会议室"
                clearable
                @change="selectRoomGet"
              >
                <el-option
                  v-for="item in formInline.meetingroomList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="getMeetingLeader">查询</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>

      <!-- 展示座位 -->
      <el-row :gutter="12" v-for="item in leaderList" class="el-card__body_leader">
        <el-col :span="leaderspan" v-for="leader in item">
          <el-card shadow="hover">{{leader.name}}</el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>
<script>
import { request } from '../../../network/request'

export default {
  name: '',
  data () {
    return {
      formInline: {
        user: '',
        meetingroom: '',
        excel: '',
        meetingroomList: [],
        excelList: []
      },
      total: '',
      selectedRoomId: '',
      selectedExcelId: '',
      leaderList: [],
      meetingroomRes: {},
      leaderspan: 8
    }
  },
  methods: {
    selectRoomGet (val) {

      this.selectedRoomId = val;

    },
    selectExcelGet (val) {
      this.selectedExcelId = val;
    },
    getMeetingLeader () {

      if (this.selectedRoomId === '' || this.selectedExcelId === '') {
        this.$message({
          message: '请选择会议室和文件',
          type: 'error',
          showClose: true
        })
        return
      }
      request({
        url: '/web/leader/sort',
        params: {
          roomId: this.selectedRoomId,
          excelId: this.selectedExcelId
        }
      }).then(res => {
        this.$message({
          message: res.msg,
          type: 'success',
          showClose: true
        })
        if (res.data === null) {
          this.leaderList = []
          return
        }
        this.meetingroomRes = res.data.meetingroom
        this.leaderList = res.data.leaders
        this.leaderspan = parseInt(24 / this.meetingroomRes.col)
        if (this.leaderspan == 0) {
          this.leaderspan = 1
        }

      })
    },
    getMeetingRoom () {
      request({
        url: '/web/meetingroom/',
        method: 'get',
        params: {
          page: 1,
          length: 100
        }
      }).then(data => {
        this.formInline.meetingroomList = data.data
      })
    },
    getExcel () {
      request({
        url: '/web/excel',
        method: 'get',
        params: {
          page: 1,
          length: 100
        }
      }).then(data => {
        this.formInline.excelList = data.data;
      })
    }
  },
  created () {
    new Promise((resolve, reject) => {
      this.getMeetingRoom()
      resolve()
    }).then(() => {
      this.getExcel()
    })
  }
}
</script>
<style>
.el-row {
  margin-top: 10px;
}
.el-card__body_leader .el-card__body {
  text-align: center;
  height: 59px;
}
</style>