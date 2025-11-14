<template>
  <div class="product-form-container">
    <el-form :model="form" ref="formRef" :rules="rules" label-width="100px">
      <el-form-item label="商品名称" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>

      <el-form-item label="商品描述" prop="detail">
        <el-input type="textarea" v-model="form.detail" rows="4" />
      </el-form-item>

      <el-form-item label="商品图片">
        <el-upload
            action="#"
            :http-request="uploadImage"
            list-type="picture-card"
            :file-list="fileList"
            :on-remove="handleRemove"
            :limit="1"
            :on-exceed="handleExceed"
        >
          <template #default>
            <el-icon v-if="fileList.length === 0"><Plus /></el-icon>
          </template>
        </el-upload>
      </el-form-item>

      <el-form-item label="新旧程度" prop="old_level">
        <el-slider v-model="form.old_level" :min="1" :max="10" show-input />
      </el-form-item>

      <el-form-item label="分类" prop="category_id">
        <el-select v-model="form.category_id" placeholder="请选择分类">
          <el-option
              v-for="cat in categories"
              :key="cat.id"
              :label="cat.name"
              :value="cat.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="价格" prop="price">
        <el-input-number v-model="form.price" :precision="2" :min="0" />
      </el-form-item>

      <el-form-item label="库存" prop="inventory">
        <el-input-number v-model="form.inventory" :min="1" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { Plus } from '@element-plus/icons-vue';
import { createProduct } from '@/api/product';
import { getCategories } from '@/api/category';

const form = ref({
  name: '',
  detail: '',
  cover_list: [],
  old_level: 5,
  category_id: null,
  price: 0,
  inventory: 1
});
const formRef = ref(null);
const fileList = ref([]);
const categories = ref([]);

// 表单验证规则
const rules = {
  name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  detail: [{ required: true, message: '请输入商品描述', trigger: 'blur' }],
  category_id: [{ required: true, message: '请选择分类', trigger: 'change' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  inventory: [{ required: true, message: '请输入库存', trigger: 'blur' }]
};

// 图片上传（转 base64编码）
const uploadImage = async ({ file }) => {
  const reader = new FileReader();
  reader.onload = () => {
    fileList.value = [{ url: reader.result }]; // 强制只保留一张
    form.value.cover_list = [reader.result];
  };
  reader.readAsDataURL(file);
};

// 图片移除
const handleRemove = (file, fileListArr) => {
  form.value.cover_list = fileListArr.map(item => item.url);
  fileList.value = fileListArr;
};

// 超出图片上传限制
const handleExceed = () => {
  ElMessage.warning('最多只能上传 1 张图片');
};

// 提交表单
const submitForm = () => {
  formRef.value.validate(async valid => {
    if (!valid) return;
    const formData = {
      ...form.value,
      cover_list: form.value.cover_list.join(',')
    };
    const res = await createProduct(formData);
    if (res.code === 0) {
      ElMessage.success('商品发布成功');
      // 可以跳转或清空表单
    } else {
      ElMessage.error(res.msg || '发布失败');
    }
  });
};

// 获取分类
onMounted(async () => {
  const res = await getCategories();
  if (res.code === 0) {
    categories.value = res.data;
  }
});
</script>

<style scoped>
.product-form-container {
  max-width: 800px;
  margin: auto;
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>
