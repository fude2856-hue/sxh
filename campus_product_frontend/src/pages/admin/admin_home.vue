<template>
  <div class="layout">
    <!-- 左侧菜单栏 -->
    <el-menu
        class="sidebar"
        :default-active="activeMenu"
        router
        :background-color="getCssVar('--sidebar-bg')"
        :text-color="getCssVar('--sidebar-text')"
        :active-text-color="getCssVar('--sidebar-active')"
    >
      <div class="logo" :style="{ color: getCssVar('--sidebar-text') }">
        {{ $t('adminTitle') }}
      </div>

      <!--谁什么时间注册/登录-->
      <el-menu-item index="/adminhome/logs">
        <el-icon><Postcard /></el-icon>
        <span>{{ $t('announcement') }}</span>
      </el-menu-item>

      <el-menu-item index="/adminhome/users">
        <el-icon><User /></el-icon>
        <span>{{ $t('userManage') }}</span>
      </el-menu-item>

      <el-menu-item index="/adminhome/products">
        <el-icon><Goods /></el-icon>
        <span>{{ $t('productManage') }}</span>
      </el-menu-item>

      <el-menu-item index="/adminhome/evaluations">
        <el-icon><Comment /></el-icon>
        <span>{{ $t('commentManage') }}</span>
      </el-menu-item>

      <el-menu-item index="/adminhome/feedback">
        <el-icon><Service /></el-icon>
        <span>{{ $t('feedback') }}</span>
      </el-menu-item>

      <el-menu-item index="/adminhome/settings">
        <el-icon><Setting /></el-icon>
        <span>{{ $t('setting') }}</span>
      </el-menu-item>

      <el-menu-item index="/adminhome/security">
        <el-icon><Lock /></el-icon>
        <span>{{ $t('security') }}</span>
      </el-menu-item>
    </el-menu>


    <!-- 右侧内容区域 -->
    <div class="main">
      <router-view />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import {
  Postcard,
  User,
  Goods,
  Comment,
  Service,
  Setting,
  Lock
} from '@element-plus/icons-vue';

const route = useRoute();
const activeMenu = computed(() => route.path);

function getCssVar(name) {
  return getComputedStyle(document.documentElement).getPropertyValue(name).trim()
}
</script>


<style scoped>
.layout {
  display: flex;
  min-height: 100vh;
}
.sidebar {
  width: 200px;
  min-height: 100vh;
  padding-top: 20px;
  background-color: var(--sidebar-bg);
  color: var(--sidebar-text);
}
.logo {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 30px;
}
.main {
  flex: 1;
  background: var(--main-bg);
  padding: 20px;
  overflow-y: auto;
}
</style>
