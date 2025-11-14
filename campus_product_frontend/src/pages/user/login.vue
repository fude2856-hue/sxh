<template>
  <div class="login-container">
    <div class="login-card">
      <!-- 标题区域 -->
      <div class="header">
        <h2 class="title1">欢迎登录</h2>
        <div class="divider"></div>
      </div>

      <!-- 表单区域 -->
      <el-form :model="form" ref="loginForm" :rules="rules" class="login-form">
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

        <el-form-item>
          <el-button
              type="primary"
              class="login-btn"
              @click="handleLogin"
              :loading="loading"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <div class="footer">
        <p class="register-link">
          没有账号？<router-link to="/register">立即注册</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
// 引入Vue的响应式API
import { ref } from 'vue';
// 全局消息组件
import { ElMessage } from 'element-plus';
import { loginUser } from '@/api/user';
// 路由组件
import { useRouter } from 'vue-router';

const router = useRouter();
const form = ref({ username: '', password: '' });
//el-form 表单组件的引用
const loginForm = ref(null);
const loading = ref(false);

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
};

const handleLogin = () => {
  // 表单验证
  loginForm.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await loginUser(form.value);
        if (res.code === 0) {
          ElMessage.success("登录成功");
          // 存储用户信息到localStorage
          localStorage.setItem('user', JSON.stringify(res.data));
          //如果是管理员身份，则跳转到管理员页面
          if (res.data.username === 'admin'&& res.data.password==='123456') {
            router.push('/adminhome');
          }
          else{
            router.push('/home');
          }
        } else {
          ElMessage.error(res.msg||"登录失败");
        }
      } catch (err) {
        console.error("登录请求失败:", err);
        ElMessage.error("请求失败：" + err.message);
      }
    }
  });
};

</script>


<style scoped>
/* 整体容器 */
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
  background-size: cover;
  padding: 20px;
}

/* 登录卡片 */
.login-card {
  width: 100%;
  max-width: 420px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
  /*卡片内边距*/
  padding: 40px 30px;
  transform: translateY(-10%);
  animation: fadeInUp 0.6s ease-out;
}

/* 标题区域 */
.header {
  text-align: center;
  margin-bottom: 32px;
}
.divider {
  height: 3px;
  width: 60px;
  background: #3498db;
  margin: 16px auto;
  border-radius: 3px;
}

/* 表单样式 */
.login-form {
  margin-top: 10px;
  width: 100%;
}
.input-wrapper {
  position: relative;
  width: 100%;
}
/* 图标样式 */
.input-icon {
  position: absolute;
  left: 14px;
  top: 50%;
  transform: translateY(-50%);
  color: #7f8c8d;
  z-index: 2;
}
/* 输入框样式 */
::v-deep .el-input input {
  padding-left: 40px !important;
  height: 48px;
  width: 100%;
  border-radius: 5px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s;
  text-align: left;
}
/* 输入框聚焦样式 */
::v-deep .el-input input:focus {
  border-color: #3498db;
  box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
}

/* 登录按钮 */
.login-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 500;
  letter-spacing: 2px;
  border-radius: 8px;
  background: linear-gradient(to right, #3498db, #2c3e50);
  border: none;
  transition: all 0.3s;
}
.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(52, 152, 219, 0.4);
}
.login-btn:active {
  transform: translateY(0);
}

/* 注册链接 */
.footer {
  margin-top: 24px;
  text-align: center;
}
.register-link {
  color: #7f8c8d;
  font-size: 14px;
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

/* 动画效果 */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(-10%);
  }
}
</style>