<template>
  <div class="my-product-page">
    <!-- 搜索框 -->
    <el-input
        v-model="keyword"
        placeholder="请输入商品名称或详情关键词"
        @keyup.enter="handleSearch"
    >
      <template #append>
        <el-button @click="handleSearch">搜索</el-button>
      </template>
    </el-input>
    <!-- 批量删除按钮 -->
    <el-button
        type="danger"
        @click="batchDeleteProducts"
        :disabled="selectedProducts.length === 0"
        @mouseenter="checkButtonStatus"
    >
      批量删除
    </el-button>
    <!-- 表格展示商品信息 -->
    <el-table
        :data="productList"
        stripe
        style="width: 100%"
        :row-key="row => row.id"
        @selection-change="handleSelectionChange"
    >
      <el-table-column
          type="selection"
      />
      <el-table-column prop="name" label="商品名称" />
      <el-table-column prop="price" label="价格" width="100" />
      <el-table-column prop="detail" label="商品详情" />
      <el-table-column prop="old_level" label="新旧程度" width="100" />
      <el-table-column prop="category_id" label="商品分类" width="100" />
      <el-table-column prop="inventory" label="商品库存" width="100" />
      <el-table-column prop="create_time" label="创建时间" width="200">
        <template #default="scope">
          {{ formatDate(scope.row.create_time) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="editProduct(scope.row)">修改</el-button>
          <!-- 确保 scope.row 不为 null 再调用 deleteProduct 方法 -->
          <el-button
              size="small"
              type="danger"
              v-if="scope.row"
              @click="deleteProduct(scope.row)"
          >
            删除
          </el-button>
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

    <!-- 对话框用于修改 -->
    <el-dialog v-model="showDialog" title="修改商品">
      <el-form
          :model="form"
          ref="formRef"
          :rules="rules"
          label-width="100px"
      >
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :min="0" />
        </el-form-item>
        <el-form-item label="商品详情" prop="detail">
          <el-input v-model="form.detail" type="textarea" />
        </el-form-item>
        <el-form-item label="新旧程度" prop="old_level">
          <el-input-number v-model="form.old_level" :min="0" :max="10" />
        </el-form-item>
        <el-form-item label="商品分类" prop="category_id">
          <el-input-number v-model="form.category_id" :min="0" />
        </el-form-item>
        <el-form-item label="商品库存" prop="inventory">
          <el-input-number v-model="form.inventory" :min="0" />
        </el-form-item>
        <el-form-item label="创建时间" prop="create_time">
          <el-date-picker
              v-model="form.create_time"
              type="datetime"
              placeholder="请选择创建时间"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showDialog = false">取消</el-button>
          <el-button type="primary" @click="submitForm">修改</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import {
  getMyProductsByPage,
  updateMyProduct,
  deleteMyProduct,
  deleteMyProducts
} from '@/api/myproduct';

const form = ref({
  id: null,
  name: '',
  price: 0,
  detail: '',
  old_level: 0,
  category_id: 0,
  inventory: 0,
  create_time: null
});
const formRef = ref(null);
const productList = ref([]);
const keyword = ref('');
const pageNum = ref(1);
const pageSize = ref(10);
const total = ref(0);
const showDialog = ref(false);
const selectedProducts = ref([]); // 存储选中的商品

const rules = {
  name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  old_level: [
    { required: true, message: '请输入新旧程度', trigger: 'blur' },
    { type: 'number', min: 0, max: 10, message: '新旧程度需在 0 - 10 之间', trigger: 'blur' }
  ],
  category_id: [{ required: true, message: '请输入商品分类', trigger: 'blur' }],
  inventory: [{ required: true, message: '请输入商品库存', trigger: 'blur' }],
  create_time: [{ required: true, message: '请选择创建时间', trigger: 'change' }]
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

// 获取商品列表
const fetchMyProducts = async () => {
  try {
    const params = {
      keyword: keyword.value,
      pageNum: pageNum.value,
      pageSize: pageSize.value
    };
    console.log('即将请求商品列表，参数:', params);
    const res = await getMyProductsByPage(params);
    console.log('获取商品列表响应:', res);
    if (res.code === 0) {
      productList.value = res.data.list;
      console.log('更新后的商品数据:', productList.value);
      total.value = res.data.total;
      selectedProducts.value = []; // 重置选中商品
      await nextTick(); // 等待组件更新完成
      console.log('更新后的总数量:', total.value);
    } else {
      ElMessage.error(res.msg || '获取商品列表失败');
    }
  } catch (error) {
    console.error('获取商品列表出错:', error);
    ElMessage.error('获取商品列表失败');
  }
};

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (!valid) return;
    try {
      const res = await updateMyProduct(form.value);
      if (res.code === 0) {
        ElMessage.success('修改商品成功');
        showDialog.value = false;
        fetchMyProducts();
      } else {
        ElMessage.error(res.msg || '修改商品失败');
      }
    } catch (error) {
      console.error('修改商品出错', error);
      ElMessage.error('修改商品失败');
    }
  });
};

// 编辑商品信息
const editProduct = (row) => {
  form.value = { ...row };
  showDialog.value = true;
};

// 删除商品
const deleteProduct = async (row) => {
  // 检查 row 对象是否为 null 或 undefined
  if (!row) {
    ElMessage.error('商品信息为空，无法删除');
    return;
  }
  try {
    const confirmResult = await ElMessageBox.confirm('确定要删除该商品吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    if (confirmResult === 'confirm') {
      const res = await deleteMyProduct(row.id);
      if (res.code === 0) {
        ElMessage.success('删除商品成功');
        fetchMyProducts();
      } else {
        ElMessage.error(res.msg || '删除商品失败');
      }
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除商品出错:', error);
      ElMessage.error('删除商品失败');
    }
  }
};

// 批量删除商品
const batchDeleteProducts = async () => {
  console.log('批量删除方法被调用');
  if (selectedProducts.value.length === 0) {
    ElMessage.warning('请先选择要删除的商品');
    return;
  }
  try {
    const confirmResult = await ElMessageBox.confirm('确定要批量删除选中的商品吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    if (confirmResult === 'confirm') {
      const productIds = selectedProducts.value.map(product => product.id);
      console.log('即将批量删除的商品 ID:', productIds);
      const res = await deleteMyProducts(productIds);
      console.log('批量删除接口响应:', res);
      if (res.code === 0) {
        ElMessage.success('批量删除商品成功');
        await fetchMyProducts();
      } else {
        ElMessage.error(res.msg || '批量删除商品失败');
      }
    }
  } catch (error) {
    console.error('批量删除商品出错:', error);
    ElMessage.error('批量删除商品失败');
  }
};

// 处理表格选中状态变化
const handleSelectionChange = (selection) => {
  selectedProducts.value = selection;
  console.log('选中的商品:', selectedProducts.value);
};

// 检查按钮状态
const checkButtonStatus = () => {
  console.log('选中商品数量:', selectedProducts.value.length);
  console.log('按钮是否禁用:', selectedProducts.value.length === 0);
};

// 处理搜索
const handleSearch = () => {
  pageNum.value = 1;
  fetchMyProducts();
};

// 处理分页变化
const handlePageChange = (page) => {
  pageNum.value = page;
  fetchMyProducts();
};

// 处理每页数量变化
const handlePageSizeChange = (size) => {
  pageSize.value = size;
  pageNum.value = 1;
  fetchMyProducts();
};

onMounted(() => {
  fetchMyProducts();
});
</script>

<style scoped>
.my-product-page {
  padding: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>