<template>
  <el-container class="home-container">
    <!-- 头部区域 -->
    <el-header>
      <div>
        <img src="../../assets/logo.png" alt="aaa" />
        <span>项目管理系统</span>
      </div>
      <el-button type="info" @click="logout">退出登录</el-button>
    </el-header>
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isClose ? '64px' : '200px'">
        <div class="toggle-button" @click="togglemenu">|||</div>
        <el-menu
          background-color="#333744"
          text-color="#fff"
          active-text-color="#ffd04b"
          unique-opened
          :collapse="isClose"
          :collapse-transition="false"
          :router="true"
        >
          <el-submenu index="1" key="1">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/user" key="1-1">
              <template slot="title">
                <i class="el-icon-user"></i>
                <span>用户列表</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/parameter" key="1-2">
              <template slot="title">
                <i class="el-icon-coin"></i>
                <span>职位列表</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2" key="2">
            <template slot="title">
              <i class="el-icon-receiving"></i>
              <span>会议管理</span>
            </template>
            <el-menu-item index="/meetingroom" key="3">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>会议室列表</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/meeting" key="4">
              <template slot="title">
                <i class="el-icon-reading"></i>
                <span>会议列表</span>
              </template>
            </el-menu-item>
            <!-- <el-menu-item index="/leader" key="5">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>领导座位列表</span>
              </template>
            </el-menu-item>-->
            <!-- <el-menu-item index="/excel" key="6">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>文件列表</span>
              </template>
            </el-menu-item>-->
          </el-submenu>
          <!-- 一级菜单 -->
          <!-- <el-submenu :index="item.id + ''" v-for="item in menuList" :key="item.id">
            一级菜单内容
            <template slot="title">
              <i :class="item.icon"></i>
              <span>{{ item.name }}</span>
            </template>

            二级菜单
            <el-menu-item
              :index="menuitem.path"
              v-for="menuitem in item.children"
              :key="menuitem.id"
            >
              二级菜单内容
              <template slot="title">
                <i :class="menuitem.icon"></i>
                <span>{{ menuitem.name }}</span>
              </template>
            </el-menu-item>
          </el-submenu>-->
        </el-menu>
      </el-aside>
      <!-- 右侧内容主体 -->
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import { request } from '../../network/request'
import { log } from 'util'

export default {
  name: '',
  data () {
    return {
      msg: 'this is home page',
      menuList: [],
      isClose: false
    }
  },
  methods: {
    logout () {
      console.log('logout-----------')

      request({
        url: '/logout',
        method: 'get',
      }).then(res => {
        this.$message({
          message: res.data,
          type: 'success',
          showClose: true
        })
      }).catch(err => {
        console.log(err);
      })
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    getMenuList () {
      console.log(this.$store.state.userInfo);

      request({
        url: '/web/per/queryByUsername',
        method: 'get',
        params: {
          username: window.sessionStorage.getItem("username")
        }
      }).then(data => {
        if (data.status !== 200) {
          return this.$message({
            message: data.msg,
            type: 'success',
            showClose: true
          })
        } else {
          this.menuList = data.data
        }
      }).catch(err => {
        console.log(err);
      })
    },
    togglemenu () {
      this.isClose = !this.isClose
    }
  },
  created () {
    this.getMenuList()
  }
}
</script>
<style>
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;
}

.el-header div {
  display: flex;
  align-items: center;
}

.el-header div span {
  margin-left: 15px;
}
.el-header img {
  height: 50px;
  width: 50px;
}
.el-aside {
  background-color: #333744;
}
.el-menu {
  border-right: none;
}
.el-main {
  background-color: #eaedf1;
}
.home-container {
  color: #fff;
  height: 100%;
}
.el-menu i {
  margin-right: 5px;
}
.toggle-button {
  background-color: #4a5064;
  text-align: center;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  /* 字符的间距 */
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
