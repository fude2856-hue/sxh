<template>
  <div>
    <h2>评论管理</h2>

    <!-- 查询条件 -->
    <div style="margin-bottom: 20px;">
      <el-input v-model="searchForm.id" placeholder="评论ID" style="width: 200px; margin-right: 10px;" />
      <el-input v-model="searchForm.userId" placeholder="用户ID" style="width: 200px; margin-right: 10px;" />
      <el-input v-model="searchForm.productId" placeholder="商品ID" style="width: 200px; margin-right: 10px;" />
      <el-button type="primary" @click="handleSearch">查询</el-button>
      <el-button @click="handleReset">重置</el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="pageData" stripe style="width: 100%">
      <el-table-column prop="id" label="评论ID" width="100" />
      <el-table-column prop="user_id" label="用户ID" width="100" />
      <el-table-column prop="product_id" label="商品ID" width="100" />
      <el-table-column prop="score" label="评分" width="100" />
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="create_time" label="时间" width="180" />
      <el-table-column label="操作" width="100">
        <template #default="scope">
          <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="margin-top: 20px; text-align: right;">
      <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          :current-page="currentPage"
          @current-change="handlePageChange"
      />

    </div>
  </div>
</template>

<script>
import {
  getAllEvaluations,
  deleteEvaluationById
} from '@/api/adminevaluation';

export default {
  name: 'EvaluationManagement',
  data() {
    return {
      evaluations: [],        // 所有数据
      pageData: [],           // 当前页显示数据
      currentPage: 1,         // 当前页码
      pageSize: 5,            // 每页显示数量
      total: 0,               // 总条数，用于分页
      searchForm: {
        id: '',
        userId: '',
        productId: ''
      }
    };
  },
  methods: {
    loadAllEvaluations() {
      getAllEvaluations().then(res => {
        console.log('✅ 后端返回数据：', res.data);
        const all = res.data || [];
        this.evaluations = all;
        this.total = all.length;
        this.currentPage = 1;
        this.setPageData();
      }).catch(err => {
        console.error('❌ 获取评论失败:', err);
        this.evaluations = [];
        this.pageData = [];
        this.total = 0;
      });
    },
    setPageData() {
      // 手动过滤
      const filtered = this.evaluations.filter(item => {
        const matchId = this.searchForm.id === '' || item.id.includes(this.searchForm.id);
        const matchUserId = this.searchForm.userId === '' || item.user_id.includes(this.searchForm.userId);
        const matchProductId = this.searchForm.productId === '' || item.product_id.includes(this.searchForm.productId);
        return matchId && matchUserId && matchProductId;
      });

      this.total = filtered.length;

      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.pageData = filtered.slice(start, end);

      console.log('✅ 当前分页数据：', this.pageData);
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.setPageData();
    },
    handleSearch() {
      this.currentPage = 1;
      this.setPageData();
    },
    handleReset() {
      this.searchForm = { id: '', userId: '', productId: '' };
      this.currentPage = 1;
      this.setPageData();
    },
    handleDelete(id) {
      this.$confirm('确定要删除这条评论吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteEvaluationById(id).then(() => {
          this.$message.success('删除成功');
          this.loadAllEvaluations();
        });
      }).catch(() => {});
    }
  },
  mounted() {
    this.loadAllEvaluations();
  }
};
</script>
