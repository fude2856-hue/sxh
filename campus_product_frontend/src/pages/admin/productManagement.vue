<template>
  <div>
    <h2>商品管理</h2>
    <el-form :inline="true" :model="form" class="demo-form-inline">
      <el-form-item label="商品ID">
        <el-input v-model="form.productId" placeholder="输入商品ID" />
      </el-form-item>
      <el-form-item label="商品名称">
        <el-input v-model="form.productName" placeholder="输入商品名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchProducts">查询</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 商品表格 -->
    <el-table
        :data="pagedProducts"
        stripe
        style="width: 100%; margin-top: 20px"
    >
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="detail" label="描述" />
      <el-table-column label="操作" width="100">
        <template #default="scope">
          <el-button
              type="danger"
              size="small"
              @click="deleteProduct(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页控件 -->
    <el-pagination
        style="margin-top: 20px"
        background
        layout="prev, pager, next"
        :total="products.length"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="handlePageChange"
    />
  </div>
</template>

<script>
import {
  getAllProducts,
  getProductById,
  getProductByName,
  deleteProductById,
} from "@/api/adminproduct";

import { ElMessageBox, ElMessage } from "element-plus";

export default {
  data() {
    return {
      products: [],
      form: {
        productId: "",
        productName: "",
      },
      currentPage: 1,
      pageSize: 5,
    };
  },
  computed: {
    pagedProducts() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.products.slice(start, end);
    },
  },
  mounted() {
    this.loadAllProducts();
  },
  methods: {
    loadAllProducts() {
      getAllProducts().then((res) => {
        this.products = Array.isArray(res.data) ? res.data : [];
        this.currentPage = 1;
      });
    },
    searchProducts() {
      const { productId, productName } = this.form;

      if (productId) {
        getProductById(productId).then((res) => {
          this.products = res.data ? [res.data] : [];
          this.currentPage = 1;
        });
      } else if (productName) {
        getProductByName(productName).then((res) => {
          this.products = res.data ? [res.data] : [];
          this.currentPage = 1;
        });
      } else {
        this.loadAllProducts();
      }
    },
    resetForm() {
      this.form.productId = "";
      this.form.productName = "";
      this.loadAllProducts();
    },
    deleteProduct(id) {
      ElMessageBox.confirm("确定要删除该商品吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            deleteProductById(id).then(() => {
              ElMessage.success("删除成功");
              this.loadAllProducts();
            });
          })
          .catch(() => {
            ElMessage.info("已取消删除");
          });
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
  },
};
</script>

<style scoped>
.demo-form-inline {
  margin-bottom: 20px;
}
</style>
