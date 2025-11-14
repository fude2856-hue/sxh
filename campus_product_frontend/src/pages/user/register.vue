<template>
  <div class="login-container">
    <div class="login-card">
      <!-- 标题区域 -->
      <div class="header">
        <h2 class="title1">注册账号</h2>
        <div class="divider"></div>
      </div>

      <!-- 表单区域 -->
      <el-form :model="form" ref="registerForm" :rules="rules" class="login-form">
        <el-form-item prop="username">
          <div class="input-wrapper">
            <svg-icon icon-class="user" class="input-icon" />
            <el-input
                v-model="form.username"
                placeholder="请输入用户名"
                @focus="handleInputFocus"
            />
          </div>
        </el-form-item>

        <el-form-item prop="password">
          <div class="input-wrapper">
            <svg-icon icon-class="lock" class="input-icon" />
            <el-input
                v-model="form.password"
                type="password"
                placeholder="请输入密码"
                show-password
                @focus="handleInputFocus"
            />
          </div>
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <div class="input-wrapper">
            <svg-icon icon-class="lock" class="input-icon" />
            <el-input
                v-model="form.confirmPassword"
                type="password"
                placeholder="请确认密码"
                show-password
                @focus="handleInputFocus"
            />
          </div>
        </el-form-item>

        <el-form-item prop="phone">
          <div class="input-wrapper">
            <svg-icon icon-class="phone" class="input-icon" />
            <el-input
                v-model="form.phone"
                placeholder="请输入手机号"
                @focus="handleInputFocus"
            />
          </div>
        </el-form-item>

        <el-form-item>
          <el-button
              type="primary"
              class="login-btn"
              @click="handleRegister"
              :loading="loading"
          >
            注册
          </el-button>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <div class="footer">
        <p class="register-link">
          已有账号？<router-link to="/login">立即登录</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { registerUser } from '@/api/user';
import { useRouter } from 'vue-router';

const router = useRouter();
const form = ref({
  username: '',
  password: '',
  confirmPassword: '',
  phone: ''
});
const registerForm = ref(null);
const loading = ref(false);

// 自定义密码确认验证规则
const validatePassConfirm = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== form.value.password) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};

// 手机号验证规则
const validatePhone = (rule, value, callback) => {
  const reg = /^1[3-9]\d{9}$/;
  if (!reg.test(value)) {
    callback(new Error('请输入有效的手机号码'));
  } else {
    callback();
  }
};

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validatePassConfirm, trigger: 'blur' }
  ],
  phone: [
    { required: true, validator: validatePhone, trigger: 'blur' }
  ]
};

const handleInputFocus = (event) => {
  event.target.parentElement.classList.add('input-focused');
};

const handleRegister = () => {
  registerForm.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      try {
        const res = await registerUser({
          username: form.value.username,
          password: form.value.password,
          phone: form.value.phone
        });
        if (res.code === 0) {
          ElMessage.success(res.data);
          router.push('/login');
        } else {
          ElMessage.error(res.msg || '注册失败');
        }
      } catch (err) {
        console.error("注册请求失败:", err);
        ElMessage.error("请求失败：" + err.message);
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>

<style scoped>
/* 复用登录页面样式基础上进行扩展 */
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
  background-size: cover;
  padding: 20px;
}

.login-card {
  width: 100%;
  max-width: 460px; /* 比登录卡片稍宽 */
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
  padding: 40px 35px;
  animation: fadeInUp 0.6s ease-out;
}

.header {
  text-align: center;
  margin-bottom: 32px;
}

.title1 {
  color: #2c3e50;
  font-size: 28px;
  font-weight: 600;
  letter-spacing: 1px;
  margin-bottom: 15px;
}

.divider {
  height: 3px;
  width: 60px;
  background: linear-gradient(to right, #3498db, #2c3e50);
  margin: 0 auto;
  border-radius: 3px;
}

.login-form {
  margin-top: 10px;
  width: 100%;
}

.input-wrapper {
  position: relative;
  width: 100%;
  margin-bottom: 10px;
}

.input-icon {
  position: absolute;
  left: 14px;
  top: 50%;
  transform: translateY(-50%);
  color: #7f8c8d;
  z-index: 2;
}

::v-deep .el-input input {
  padding-left: 40px !important;
  height: 48px;
  width: 100%;
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s;
}

::v-deep .el-input input:focus {
  border-color: #3498db;
  box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
}

.login-btn {
  width: 100%;
  height: 50px;
  font-size: 18px;
  font-weight: 500;
  letter-spacing: 2px;
  border-radius: 8px;
  background: linear-gradient(to right, #3498db, #2c3e50);
  border: none;
  margin-top: 20px;
  transition: all 0.3s;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(52, 152, 219, 0.4);
}

.login-btn:active {
  transform: translateY(0);
}

.footer {
  margin-top: 24px;
  text-align: center;
}

.register-link {
  color: #7f8c8d;
  font-size: 15px;
  margin-top: 15px;
}

.register-link a {
  color: #3498db;
  font-weight: 500;
  text-decoration: none;
  transition: all 0.2s;
}

.register-link a:hover {
  color: #2980b9;
  text-decoration: underline;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>