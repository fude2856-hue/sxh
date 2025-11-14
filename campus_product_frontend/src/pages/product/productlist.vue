<template>
  <div class="home">
    <div class="top-bar" style="display: flex; align-items: center; gap: 100px">
      <!-- 分类按钮 -->
      <div class="category-bar">
        <el-button
            v-for="cat in categories"
            :key="cat.id"
            @click="enableCategory(cat.id)"
        >
          {{ cat.name }}
        </el-button>
        <el-button type="primary" @click="fetchPage">全部</el-button>
      </div>

      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-input v-model="searchName" placeholder="搜索商品..." style="width: 300px" />
        <el-button type="primary" @click="searchByName">搜索</el-button>
      </div>
    </div>

    <!-- 商品展示（每行一个商品） -->
    <div class="product-list">
      <el-card
          v-for="item in products"
          :key="item.id"
          class="product-card"
          @click="goToDetail(item.id)"
      >
        <div class="product-item">
          <img
              v-if="item.cover_list && item.cover_list.length > 0"
              :src="item.cover_list"
              alt="cover"
              class="product-img"
          />
          <div class="product-info">
            <h3>{{ item.name }}</h3>
            <p>价格：{{ item.price }} 元</p>
          </div>
        </div>
      </el-card>
    </div>
    <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="handlePageChange"
        style="margin-top: 20px; text-align: center;"
    ></el-pagination>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import {
  getProductListByPage,
  searchProductByName,
  getProductsByEnableCategory
} from '@/api/product';
import { enableCategoryApi, getCategories } from '@/api/category';

const products = ref([]);
const categories = ref([]);
const searchName = ref('');
const router = useRouter();
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);


const fetchPage = async () => {
  const res = await getProductListByPage(currentPage.value, pageSize.value);
  if (res.code === 0) {
    products.value = res.data.list;
    total.value = res.data.total;
  }
};

const enableCategory = async (categoryId) => {
  await enableCategoryApi(categoryId);
  const res = await getProductsByEnableCategory();
  if (res.code === 0) {
    products.value = res.data;
  }
};

const searchByName = async () => {
  const res = await searchProductByName(searchName.value);
  if (res.code === 0) {
    products.value = res.data;
  }
};

const goToDetail = (id) => {
  router.push(`/product/${id}`);
};

const handlePageChange = async (page) => {
  currentPage.value = page;
    fetchPage(); // 默认全部商品分页
};


onMounted(async () => {
  const catRes = await getCategories();
  if (catRes.code === 0) categories.value = catRes.data;
  fetchPage();
});

</script>

<style scoped>
.product-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: flex-start;
  margin-top: 20px;
}

.product-card {
  width: 220px;
  cursor: pointer;
  transition: box-shadow 0.2s;
}

.product-card:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.2);
}

.product-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.product-img {
  width: 100%;
  height: 180px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 10px;
}

.product-info {
  text-align: center;
}

.product-info h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}

.product-info p {
  margin: 6px 0 0;
  font-size: 14px;
  color: #666;
}

.category-bar,
.search-bar {
  margin-bottom: 20px;
}
</style>