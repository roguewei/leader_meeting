<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>会议管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/meeting' }">会议列表</el-breadcrumb-item>
      <el-breadcrumb-item>座位安排</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item>
              <el-button type="success" @click="exportleader" :disabled="!isExist">导出</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="showUploadDialog" v-if="!isExist">导入数据</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="danger" @click="open" v-if="isExist">删除会议人员</el-button>
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
      formInline: {
        meetingroom: '',
      },
      uploadUrl: net.BASE_URL,
      leaderList: [],
      meetingroomRes: {},
      leaderspan: 8,
      isExist: false,
      uploadDiologVisible: false,
    }
  },
  methods: {
    open () {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showCancelButton: true
      }).then(() => {
        this.delLeader()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    showUploadDialog () {
      this.uploadDiologVisible = true;
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
            mId: this.$store.state.meetingId,
            eId: response.data
          }
        }).then(res => {
          this.$message({
            message: res.msg,
            type: 'success',
            showClose: true
          })
          this.uploadDiologVisible = false;
          this.getLeaderList()
        }).catch(err => {
          console.log(err);
        })
      }
    },
    delLeader () {
      request({
        url: '/web/meetingleader/delByMid',
        params: {
          mId: this.$store.state.meetingId
        }
      }).then(res => {
        this.$message({
          message: res.msg,
          type: 'error',
          showClose: true
        })
        this.isExist = false
        this.getLeaderList()
      }).catch(err => {
        console.log(err);
      })
    },
    exportleader () {
      request({
        url: '/web/image/export',
        responseType: "blob",
        params: {
          mId: this.$store.state.meetingId
        }
      })
        .then(res => {

          let result = res.type
          if (result.indexOf("json") != -1) {
            this.$message({
              message: "没有可导出的数据",
              type: 'error',
              showClose: true
            })
            return
          }

          let blob = new Blob([res], { type: 'image/png' })
          let downloadElement = document.createElement('a')
          let href = window.URL.createObjectURL(blob); //创建下载的链接
          console.log(href);

          downloadElement.href = href;
          downloadElement.download = "领导座位图"; //下载后文件名
          document.body.appendChild(downloadElement);
          downloadElement.click(); //点击下载
          document.body.removeChild(downloadElement); //下载完成移除元素
          window.URL.revokeObjectURL(href); //释放blob对象

        })
        .catch(err => {
          console.log(err);
        })
    },
    getLeaderList () {
      request({
        url: '/web/leader/sort',
        params: {
          mId: this.$store.state.meetingId
        }
      }).then(res => {
        if (res.data === null) {
          this.leaderList = []
          return
        }
        this.isExist = true
        this.meetingroomRes = res.data.meetingroom
        this.leaderList = res.data.leaders
        this.leaderspan = parseInt(24 / this.meetingroomRes.col)
        if (this.leaderspan == 0) {
          this.leaderspan = 1
        }
      }).catch(err => {
        console.log(err);
      })
    }
  },
  created () {
    this.getLeaderList()
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