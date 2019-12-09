<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>会议管理</el-breadcrumb-item>
      <el-breadcrumb-item>会议列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="12">
          <el-input
            placeholder="请输入会议名称"
            v-model="queryInfo.name"
            class="input-with-select"
            clearable
            @clear="getMeetingList"
          >
            <el-button slot="append" icon="el-icon-search" @click="getMeetingList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="showAddDialog()">新增</el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table :data="meetingList" style="width: 100%" boder stripe>
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column prop="name" label="会议名称" width="180"></el-table-column>
        <el-table-column prop="roomName" label="会议室名称" width="180"></el-table-column>
        <el-table-column prop="num" label="会议室容量" width="180"></el-table-column>
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
            <el-tooltip
              class="item"
              effect="dark"
              content="上传excel文件"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="success"
                icon="el-icon-plus"
                size="mini"
                @click="showUploadDialog(scope.row.id)"
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
            <el-tooltip class="item" effect="dark" content="查看" placement="top" :enterable="false">
              <el-button
                type="primary"
                icon="el-icon-view"
                size="mini"
                @click="showLeader(scope.row.id)"
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

    <el-dialog
      :title="dialogTitle"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClose"
      @before-close="addDialogBeforeClose"
      @open="getRoomList"
    >
      <!-- 内容主体区 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="90px">
        <el-form-item label="id" prop="id" class="el-form-item-id">
          <el-input v-model="addForm.id"></el-input>
        </el-form-item>
        <el-form-item label="会议名称" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="选择会议室" prop="rId">
          <el-select
            filterable
            v-model="addForm.rId"
            placeholder="参数类型"
            clearable
            @blur="roomChange"
            :disabled="!isAddDialog"
          >
            <el-option v-for="item in roomList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 底部按钮区 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMeeting">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除对话框 -->
    <el-dialog title="删除会议" :visible.sync="delDialogVisible" width="30%">
      <span>确定删除该会议吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="delMeeting">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 上传文件 -->
    <el-dialog
      title="上传对应excel"
      :visible.sync="uploadDiologVisible"
      @close="clearUpload"
      width="50%"
    >
      <!-- 内容主体区 -->
      <el-upload
        class="upload-demo"
        ref="upload"
        :action="uploadUrl+'/web/excel/upload'"
        :file-list="fileList"
        :auto-upload="false"
        :limit="1"
        :on-success="uploadSuccess"
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button
          style="margin-left: 10px;"
          size="small"
          type="success"
          @click="submitUpload"
        >上传到服务器</el-button>
        <div slot="tip" class="el-upload__tip">只能上传一个xls/xlsx文件</div>
      </el-upload>
    </el-dialog>
  </div>
</template>
<script>
import { request } from '../../../network/request'
import net from "../../../common/const";
export default {
  name: '',
  data () {
    return {
      dialogTitle: '',
      addDialogVisible: false,
      delDialogVisible: false,
      isAddDialog: true,
      uploadDiologVisible: false,
      uploadUrl: net.BASE_URL,
      selectMeetingId: '',
      delId: '',
      meetingList: [],
      fileList: [],
      total: 0,
      pageSizes: [5, 10, 100, 500],
      queryInfo: {
        name: '',
        page: 1,
        length: 5
      },
      addForm: {
        name: '',
        rId: ''
      },
      roomList: [],
      // 表单验证规则
      addFormRules: {
        name: [
          { required: true, message: '请输入参数名', trigger: 'blur' },
        ],
        rId: { required: true, message: '请选择活动区域', trigger: 'change' }
      }
    }
  },
  methods: {
    roomChange (val) {
      // console.log(val);

    },
    handleSizeChange (length) {
      this.queryInfo.length = length
      this.getParameterList()
    },
    handleCurrentChange (current) {
      this.queryInfo.page = current
      this.getParameterList()
    },
    showLeader (id) {
      this.$store.commit('setMeetingId', id)
      this.$router.push("/leader")
    },
    showUploadDialog (id) {
      request({
        url: '/web/meetingleader',
        method: 'get',
        params: {
          mId: id
        }
      }).then(res => {
        if (res.data !== 0) {
          this.$message({
            message: "该会议室已被他人使用",
            type: 'error',
            showClose: true
          })
          return
        }
        this.uploadDiologVisible = true;
        this.selectMeetingId = id
      }).catch(err => {
        console.log(err);
      })

    },
    showEditDialog (id) {
      this.dialogTitle = '修改参数'
      this.addDialogVisible = true
      this.isAddDialog = false;
      request({
        url: '/web/meeting/' + id,
        method: 'get'
      }).then(res => {
        this.addForm = res.data
      }).catch(err => {
        console.log(err);
      })
    },
    showDelDialog (id) {
      this.delDialogVisible = true
      this.delId = id
    },
    showAddDialog () {
      this.dialogTitle = '添加参数'
      this.addDialogVisible = true
      this.isAddDialog = true
    },
    // 手动执行文件上传
    submitUpload () {
      this.$refs.upload.submit();
    },
    uploadSuccess (response, file, fileList) {
      console.log(response);
      if (response.status !== 200) {
        this.$message({
          message: response.msg,
          type: 'error',
          showClose: true
        })
        return
      } else {
        request({
          url: '/web/meetingleader/add',
          method: 'post',
          data: {
            mId: this.selectMeetingId,
            eId: response.data
          }
        }).then(res => {
          this.$message({
            message: res.msg,
            type: 'success',
            showClose: true
          })
          this.uploadDiologVisible = false;
        }).catch(err => {
          console.log(err);
        })
      }
    },
    clearUpload () {
      this.$refs.upload.clearFiles()
    },
    addMeeting () {
      this.$refs.addFormRef.validate(valid => {
        if (!valid) return
        let reqPath = ''
        if (this.isAddDialog) {
          reqPath = '/web/meeting/add'
        } else {
          reqPath = '/web/meeting/update'
        }
        request({
          url: reqPath,
          data: this.addForm,
          method: 'post'
        }).then(res => {
          this.$message({
            message: res.msg,
            type: 'success',
            showClose: true
          })
          this.addDialogVisible = false;
          this.getMeetingList()
        }).catch(err => {
          console.log(err);
        })
      })
    },
    delMeeting () {
      request({
        url: '/web/meeting/del',
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
        this.delDialogVisible = false;
        this.getMeetingList()
      }).catch(err => {
        console.log(err);
      })
    },
    getRoomList () {
      request({
        url: '/web/meetingroom',
        params: {
          page: 1,
          length: 1000
        }
      }).then(res => {
        this.roomList = res.data
      }).catch(err => {
        console.log(err);
      })
    },
    getMeetingList () {
      request({
        url: '/web/meeting',
        params: this.queryInfo
      }).then(res => {

        this.meetingList = res.data
        this.total = res.total
      }).catch(err => {
        console.log(err);
      })
    },
    addDialogClose () {
      this.$refs.addFormRef.resetFields()
    },
    addDialogBeforeClose () {
      this.$refs.addFormRef.resetFields()
    },
  },
  created () {
    this.getMeetingList()
  }
}
</script>
<style>
</style>