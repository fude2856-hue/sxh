<template>
  <div class="security-center">
    <el-card class="box-card">
      <div class="section-title">{{ $t('AdminUserManagement') }}</div>

      <!-- 用户列表 -->
<!--      <el-table-->
<!--          :data="users"-->
<!--          style="width: 100%; margin-bottom: 20px"-->
<!--          :empty-text="$t('getUsersFailed')"-->
<!--      >-->
<!--        <el-table-column prop="id" label="ID" width="60" />-->
<!--        <el-table-column prop="username" :label="$t('username')" />-->
<!--        <el-table-column prop="phone" :label="$t('phone')" />-->
<!--      </el-table>-->

      <!-- 添加用户表单 -->
      <el-form
          :model="newUser"
          ref="formRef"
          label-width="80px"
          label-position="left"
          class="add-user-form"
      >
        <el-form-item :label="$t('username')" prop="username" :rules="[{ required: true, message: $t('enterUsername'), trigger: 'blur' }]">
          <el-input v-model="newUser.username" autocomplete="off" />
        </el-form-item>

        <el-form-item :label="$t('phone')" prop="phone" :rules="[{ required: true, pattern: /^1[3-9]\d{9}$/, message: $t('enterPhone'), trigger: 'blur' }]">
          <el-input v-model="newUser.phone" autocomplete="off" />
        </el-form-item>

        <el-form-item :label="$t('password')" prop="password" :rules="[{ required: true, message: $t('enterPassword'), trigger: 'blur' }]">
          <el-input v-model="newUser.password" type="password" autocomplete="off" />
        </el-form-item>

        <div class="btn-group">
          <el-button type="primary" @click="handleAddUser">{{ $t('addAdminUser') }}</el-button>
          <el-button @click="handleReset">{{ $t('reset') }}</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

// 用户列表数据
const users = ref([])

// 新用户表单数据
const newUser = ref({
  username: '',
  phone: '',
  password: ''
})

const formRef = ref(null)

// 获取用户列表
// const fetchUsers = async () => {
//   try {
//     const res = await axios.get('/api/user/users')
//     users.value = res.data || []
//   } catch (err) {
//     ElMessage.error(err.response?.data?.msg || 'Failed to fetch users')
//   }
// }

// 添加新用户
const handleAddUser = () => {
  formRef.value.validate(async valid => {
    if (!valid) return

    try {
      const res=await axios.post('/api/user/users', newUser.value)
      ElMessage.success("添加管理员成功")
      handleReset()
    } catch (err) {
      ElMessage.error(err.response?.data?.msg || 'Failed to add user')
    }
  })
}

// 重置表单
const handleReset = () => {
  formRef.value.resetFields()
}

onMounted(() => {

})
</script>

<style scoped>
.security-center {
  max-width: 700px;
  margin: 0 auto;
  padding-top: 20px;
}

.section-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
}

/* 表单按钮居中 */
.btn-group {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 10px;
}

/* 表格底部留白 */
.el-table {
  margin-bottom: 30px;
}
</style>
