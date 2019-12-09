<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>会议管理</el-breadcrumb-item>
      <el-breadcrumb-item>会议室列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="12">
          <el-input
            placeholder="请输入会议室名称"
            v-model="queryInfo.name"
            class="input-with-select"
            clearable
            @clear="getMeetingRoom"
          >
            <el-button slot="append" icon="el-icon-search" @click="getMeetingRoom"></el-button>
          </el-input>
        </el-col>

        <el-col :span="6">
          <el-button type="primary" @click="showAddDialog()">新增</el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table :data="roomList" style="width: 100%" boder stripe>
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column prop="name" label="会议室名称" width="180"></el-table-column>
        <el-table-column prop="num" label="会议室容量" width="180"></el-table-column>
        <el-table-column prop="row" label="行数" width="180"></el-table-column>
        <el-table-column prop="col" label="列数" width="180"></el-table-column>
        <el-table-column label="操作">
          <!-- 使用作用域插槽获取当前行的数据 -->
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="修改" placement="top" :enterable="false">
              <el-button
                type="warning"
                icon="el-icon-edit"
                size="mini"
                @click="showEditDialog(scope.row.id)"
              ></el-button>
            </el-tooltip>
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

    <!-- 新增、修改用户对话框 -->
    <el-dialog
      title="新增、修改用户"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClose"
      @before-close="addDialogBeforeClose"
    >
      <!-- 内容主体区 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="id" prop="id" class="el-form-item-id">
          <el-input v-model="addForm.id"></el-input>
        </el-form-item>
        <el-form-item label="会议室名称" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="会议室座位数" prop="num">
          <el-input v-model="addForm.num"></el-input>
        </el-form-item>
        <el-form-item label="会议室行数" prop="row">
          <el-input v-model="addForm.row"></el-input>
        </el-form-item>
        <el-form-item label="会议室列数" prop="col">
          <el-input v-model="addForm.col"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部按钮区 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRoom">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示对话框 -->
    <el-dialog title="删除会议室" :visible.sync="dialogVisible" width="30%">
      <span>确定删除会议室吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="delRoom">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { request } from '../../../network/request'

export default {
  name: '',
  isAddDialog: true,
  data () {
    // 自定义规则
    var checkNum = (rule, value, callback) => {
      const regNum = /\d+/
      if (!regNum.test(value)) {
        return callback(new Error('请输入阿拉伯数字'))
      } else {
        callback()
      }
    }
    var checkRow = (rule, value, callback) => {
      const regRow = /\d+/
      if (!regRow.test(value)) {
        return callback(new Error('请输入阿拉伯数字'))
      } else {
        callback()
      }
    }
    var checkCol = (rule, value, callback) => {
      const regCol = /\d+/
      if (!regCol.test(value)) {
        return callback(new Error('请输入阿拉伯数字'))
      } else if ((value * this.addForm.row) + '' !== this.addForm.num) {
        return callback(new Error('行列相乘不等于总座位数'))
      } else {
        callback()
      }
    }
    return {
      queryInfo: {
        name: '',
        page: 1,
        length: 10
      },
      roomList: [],
      total: 0,
      pageSizes: [5, 10, 100, 500],
      dialogVisible: false,
      // 添加会议室对话框的显示隐藏
      addDialogVisible: false,
      // 上传文件提示框
      uploadDiologVisible: false,
      selectRoomId: '',
      // 添加会议室的表单数据对象
      addForm: {
        id: '',
        name: '',
        num: '',
        row: '',
        col: ''
      },
      delId: '',
      token: {
        authorization: window.sessionStorage.getItem("token")
      },
      // 表单验证规则
      addFormRules: {
        name: [
          { required: true, message: '请输入会议室名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        num: [
          { required: true, message: '请输入座位数', trigger: 'blur' },
          { validator: checkNum, trigger: 'blur' }
        ],
        row: [
          { required: true, message: '请输入行数', trigger: 'blur' },
          { validator: checkRow, trigger: 'blur' }
        ],
        col: [
          { required: true, message: '请输入列数', trigger: 'blur' },
          { validator: checkCol, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    getMeetingRoom () {
      request({
        url: '/web/meetingroom/',
        method: 'get',
        params: this.queryInfo
      }).then(data => {

        this.roomList = data.data
        this.total = data.total
      }).catch(err => {
        console.log(err);
      })
    },
    handleSizeChange (length) {
      this.queryInfo.length = length
      this.getMeetingRoom()
    },
    handleCurrentChange (current) {
      this.queryInfo.page = current
      this.getMeetingRoom()
    },
    showAddDialog () {

      this.isAddDialog = true
      this.addDialogVisible = true
    },
    showEditDialog (id) {
      this.addDialogVisible = true
      this.isAddDialog = false

      request({
        url: '/web/meetingroom/' + id,
        method: 'get'
      }).then(res => {

        this.addForm = res.data
      }).catch(err => {
        console.log(err);
      })
    },
    showUploadDialog (id) {
      this.uploadDiologVisible = true;
      this.selectRoomId = id
    },
    addDialogClose () {
      this.$refs.addFormRef.resetFields()
    },
    addDialogBeforeClose () {
      this.$refs.addFormRef.resetFields()
    },
    showDelDialog (id) {
      this.dialogVisible = true
      this.delId = id;
    },
    addRoom () {
      this.$refs.addFormRef.validate(valid => {
        if (!valid) return
        let reqPath = ''
        if (this.isAddDialog) {
          reqPath = '/web/meetingroom/add'
        } else {
          reqPath = '/web/meetingroom/update'
        }
        request({
          url: reqPath,
          data: this.addForm,
          method: 'post'
        }).then(res => {
          // this.$message.success(res.msg)
          this.$message({
            message: res.msg,
            type: 'success',
            showClose: true
          })
          this.addDialogVisible = false
          this.getMeetingRoom()
        }).catch(err => {
          console.log(err);
        })
      })
    },
    delRoom () {
      request({
        url: '/web/meetingroom/del',
        method: 'post',
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
        this.getMeetingRoom()
      }).catch(err => {
        console.log(err);
      })
    },
  },
  created () {
    this.getMeetingRoom()
  }
}
</script>
<style>
.el-form-item-id {
  display: none;
}
.el-form-item label {
  width: 110px !important;
}
.el-form-item .el-input {
  width: 85% !important;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>