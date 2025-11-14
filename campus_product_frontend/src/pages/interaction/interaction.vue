<template>
  <div class="interaction-container">
    <!-- 搜索框 -->
    <el-input
        v-model="searchKeyword"
        placeholder="请输入商品 ID或用户 ID 进行搜索"
        @keyup.enter="handleSearch"
    >
      <template #append>
        <el-button @click="handleSearch">搜索</el-button>
      </template>
    </el-input>
    <!-- 添加按钮 -->
<!--    <el-button type="primary" @click="showDialog = true; resetForm(); isEdit = false">添加收藏</el-button>-->
    <!-- 表单 -->
<!--    <el-form :model="form" ref="formRef" :rules="rules" label-width="100px">-->
<!--      <el-form-item label="用户 ID" prop="user_id">-->
<!--        <el-input-number v-model="form.user_id" :min="1" />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="商品 ID" prop="product_id">-->
<!--        <el-input-number v-model="form.product_id" :min="1" />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="收藏时间" prop="datetime">-->
<!--        <el-date-picker-->
<!--            v-model="form.datetime"-->
<!--            type="datetime"-->
<!--            placeholder="请选择收藏时间"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" @click="submitForm">{{ isEdit ? '修改' : '添加' }}</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->
    <!-- 表格展示收藏信息 -->
    <el-table :data="interactions" stripe style="width: 100%">
      <el-table-column prop="user_id" label="用户 ID" width="120" />
      <el-table-column prop="product_id" label="商品 ID" width="120" />
      <el-table-column prop="datetime" label="收藏时间" width="200">
        <template #default="scope">
          {{ formatDate(scope.row.datetime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button size="small" @click="editInteraction(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :total="total"
        layout="prev, pager, next, jumper"
        @current-change="handlePageChange"
        @size-change="handlePageSizeChange"
    />
    <!-- 对话框用于添加或修改 -->
    <el-dialog v-model="showDialog" :title="isEdit ? '修改收藏' : '添加收藏'">
      <el-form :model="form" ref="formRef" :rules="rules" label-width="100px">
        <el-form-item label="用户 ID" prop="user_id">
          <el-input-number v-model="form.user_id" :min="1" />
        </el-form-item>
        <el-form-item label="商品 ID" prop="product_id">
          <el-input-number v-model="form.product_id" :min="1" />
        </el-form-item>
        <el-form-item label="收藏时间" prop="datetime">
          <el-date-picker
              v-model="form.datetime"
              type="datetime"
              placeholder="请选择收藏时间"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showDialog = false">取消</el-button>
          <el-button type="primary" @click="submitForm">{{ isEdit ? '修改' : '确定' }}</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import {
  insertInteraction,
  updateInteraction,
  getInteractionPage
} from '@/api/interaction';
const interactions = ref([]);
const form = ref({
  id: null,
  user_id: null,
  product_id: null,
  datetime: null
});
const formRef = ref(null);
const searchKeyword = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const showDialog = ref(false);
const isEdit = ref(false);

const rules = {
  user_id: [{ required: true, message: '请输入用户 ID', trigger: 'blur' }],
  product_id: [{ required: true, message: '请输入商品 ID', trigger: 'blur' }],
  datetime: [{ required: true, message: '请选择收藏时间', trigger: 'change' }]
};

// 格式化时间
const formatDate = (date) => {
  if (!date) return '';
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

// 获取收藏列表
const fetchInteractions = async () => {
  try {
    const params = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      keyword: searchKeyword.value
    };
    console.log('请求参数:', params);
    const res = await getInteractionPage(params);
    console.log('接口响应:', res);
    if (res.code === 0) {
      // 检查返回的数据是否包含 list 数组
      if (res.data && Array.isArray(res.data.list)) {
        // 提取 list 数组赋值给 interactions
        interactions.value = res.data.list;
        // 从返回数据中获取 total 值
        total.value = res.data.total;
      } else {
        console.error('接口返回的数据结构不符合预期:', res.data);
        interactions.value = [];
        total.value = 0;
      }
    } else {
      ElMessage.error(res.msg || '获取收藏列表失败');
    }
  } catch (error) {
    console.error('获取收藏列表出错:', error);
    ElMessage.error('获取收藏列表失败');
  }
};
// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (!valid) return;
    try {
      let res;
      if (isEdit) {
        res = await updateInteraction(form.value);
      } else {
        res = await insertInteraction(form.value);
      }
      if (res.code === 0) {
        ElMessage.success(isEdit ? '修改收藏成功' : '添加收藏成功');
        showDialog.value = false;
        fetchInteractions();
        resetForm();
        isEdit.value = false;
      } else {
        ElMessage.error(res.msg || (isEdit ? '修改收藏失败' : '添加收藏失败'));
      }
    } catch (error) {
      console.error(isEdit ? '修改收藏出错' : '添加收藏出错', error);
      ElMessage.error(isEdit ? '修改收藏失败' : '添加收藏失败');
    }
  });
};

// 编辑收藏信息
const editInteraction = (row) => {
  isEdit.value = true;
  form.value = { ...row };
  showDialog.value = true;
};

// 重置表单
const resetForm = () => {
  form.value = {
    id: null,
    user_id: null,
    product_id: null,
    datetime: null
  };
};

// 处理分页变化
const handlePageChange = (page) => {
  currentPage.value = page;
  fetchInteractions();
};

// 处理页面大小变化
const handlePageSizeChange = (size) => {
  pageSize.value = size;
  currentPage.value = 1;
  fetchInteractions();
};

// 处理搜索
const handleSearch = () => {
  currentPage.value = 1;
  fetchInteractions();
};

onMounted(() => {
  fetchInteractions();
});
</script>

<style scoped>
.interaction-container {
  max-width: 800px;
  margin: auto;
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>