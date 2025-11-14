<template>
  <div class="product-detail">
    <el-card class="product-container">
      <div class="product-main">
        <!-- 左侧图片 -->
        <img
            v-if="product.cover_list && product.cover_list.length > 0"
            :src="product.cover_list"
            alt="cover"
            class="product-img"
        />

        <!-- 右侧信息 -->
        <div class="product-info">
          <h2>{{ product.name }}</h2>
          <p>{{ product.detail }}</p>
          <p>价格：{{ product.price }} 元</p>
          <p>库存：{{ product.inventory }}</p>

          <div class="action-buttons">
            <el-button type="primary" @click="buy">购买</el-button>
            <el-button :type="product.is_collected ? 'danger' : 'success'" @click="toggle">
              {{ product.is_collected ? '取消收藏' : '收藏' }}
            </el-button>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 评论区域 -->
    <el-card class="comment-section">
      <h3>发表评论</h3>
      <el-input
          type="textarea"
          v-model="comment"
          placeholder="请输入评论内容"
          rows="3"
      />
      <el-rate v-model="score" allow-half :max="5" />
      <el-button type="success" @click="submit">发布</el-button>
    </el-card>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getProductById } from '@/api/product';
import { submitComment } from '@/api/comment';
import { toggleFavorite } from '@/api/favorite';
import { buyProduct } from '@/api/order';
import { ElMessage, ElMessageBox } from 'element-plus';

const route = useRoute();
const productId = route.params.id;

const product = ref({});
const comment = ref('');
const score = ref(0);

const fetchProduct = async () => {
  const res = await getProductById(productId);
  if (res.code === 0) {
    product.value = res.data;
  }
};

const submit = async () => {
  if (!comment.value) return ElMessage.warning('请输入评论内容');
  const res = await submitComment(productId, comment.value, score.value);
  if (res.code === 0) {
    ElMessage.success(res.data);
    comment.value = '';
    score.value = 0;
  }
  else{
    ElMessage.error(res.msg);
  }
};

const toggle = async () => {
  const res = await toggleFavorite(productId);
  if (res.code === 0) {
    ElMessage.success(res.data);
    product.value.is_collected = !product.value.is_collected;
  }
  else{
    ElMessage.error(res.msg);
  }
};

const buy = async () => {
  try {
    await ElMessageBox.confirm('确认购买该商品吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });
    const res = await buyProduct(productId);
    if (res.code === 0) {
      ElMessage.success(res.data);
      fetchProduct();
    }
    else{
      ElMessage.error(res.msg);
    }
  } catch (e) {

  }
};

onMounted(() => {
  fetchProduct();
});
</script>

<style scoped>
.product-detail {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

/* 外层卡片内容，关键：flex 横排！ */
.product-main {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
}

/* 左侧图片样式 */
.product-img {
  width: 350px;
  height: 350px;
  object-fit: cover;
  border-radius: 10px;
  margin-right: 90px;
}

/* 右侧信息部分 */
.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

/* 按钮横排 */
.action-buttons {
  margin-top: 20px;
  display: flex;
  gap: 20px;
}

/* 评论卡片 */
.comment-section {
  margin-top: 30px;
  padding: 20px;
}

</style>
