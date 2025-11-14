<template>
  <div class="profile-container">
    <h2>个人信息</h2>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="100px" class="form-box">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" type="password" placeholder="不修改请留空" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">保存修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getUserInfo, updateUserInfo } from '@/api/user';
import { ElMessage } from 'element-plus';

const form = ref({ id: '',username: '', phone: '', password: '' });
const formRef = ref(null);

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
      // 手机号正则表达式
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ]
};

const handleSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      const res = await updateUserInfo(form.value);
      if (res.code === 0) {
        ElMessage.success(res.data);
      } else {
        ElMessage.error(res.msg);
      }
    }
  });
};

onMounted(async () => {
  const res = await getUserInfo();
  if (res.code === 0) {
    form.value.id = res.data.id;
    form.value.username = res.data.username;
    form.value.phone = res.data.phone;
  }
});
</script>

<style scoped>
.profile-container {
  max-width: 500px;
  margin: auto;
  padding: 30px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}
.form-box {
  margin-top: 20px;
}
</style>
