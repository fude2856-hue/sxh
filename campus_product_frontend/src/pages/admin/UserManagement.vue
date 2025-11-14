<template>
  <div>
    <h2>用户管理</h2>
    <el-form :inline="true" :model="form" class="demo-form-inline">
      <el-form-item label="ID">
        <el-input v-model="form.userId" placeholder="输入用户ID" />
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="form.username" placeholder="输入用户名" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.phone" placeholder="输入手机号" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchUsers">查询</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table :data="pagedUsers" stripe style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column label="操作" width="100">
        <template #default="scope">
          <el-button
              type="danger"
              size="small"
              @click="deleteUser(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        style="margin-top: 20px"
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="users.length"
        :current-page="currentPage"
        @current-change="handlePageChange"
    />
  </div>
</template>

<script>
import {
  getAllUsers,
  getUserById,
  getUserByUsername,
  getUserByPhone,
  deleteUserById,
} from "@/api/adminuser";

import { ElMessageBox, ElMessage } from "element-plus";

export default {
  data() {
    return {
      users: [],
      form: {
        userId: "",
        username: "",
        phone: "",
      },
      currentPage: 1,
      pageSize: 5,
    };
  },
  computed: {
    pagedUsers() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.users.slice(start, end);
    },
  },
  mounted() {
    this.loadAllUsers();
  },
  methods: {
    loadAllUsers() {
      getAllUsers().then((res) => {
        this.users = Array.isArray(res.data) ? res.data : [];
        this.currentPage = 1;
      });
    },
    searchUsers() {
      const { userId, username, phone } = this.form;

      if (userId) {
        getUserById(userId).then((res) => {
          this.users = res.data ? [res.data] : [];
          this.currentPage = 1;
        });
      } else if (username) {
        getUserByUsername(username).then((res) => {
          this.users = res.data ? [res.data] : [];
          this.currentPage = 1;
        });
      } else if (phone) {
        getUserByPhone(phone).then((res) => {
          this.users = res.data ? [res.data] : [];
          this.currentPage = 1;
        });
      } else {
        this.loadAllUsers();
      }
    },
    resetForm() {
      this.form.userId = "";
      this.form.username = "";
      this.form.phone = "";
      this.loadAllUsers();
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    deleteUser(id) {
      ElMessageBox.confirm("确定要删除该用户吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            deleteUserById(id).then(() => {
              ElMessage.success("删除成功");
              this.loadAllUsers();
            });
          })
          .catch(() => {
            ElMessage.info("已取消删除");
          });
    },
  },
};
</script>

<style scoped>
.demo-form-inline {
  margin-bottom: 20px;
}
</style>
