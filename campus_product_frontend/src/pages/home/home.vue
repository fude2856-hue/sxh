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
        {{ $t('userTitle') }}
      </div>

      <el-menu-item index="/home/productlist">
        <el-icon><ShoppingCart /></el-icon>
        <span>{{ $t('products') }}</span>
      </el-menu-item>

      <el-menu-item index="/home/createproduct">
        <el-icon><Plus /></el-icon>
        <span>{{ $t('createProduct') }}</span>
      </el-menu-item>

      <el-menu-item index="/home/myproduct">
        <el-icon><Goods /></el-icon>
        <span>{{ $t('myProducts') }}</span>
      </el-menu-item>

      <el-menu-item index="/home/orders">
        <el-icon><List /></el-icon>
        <span>{{ $t('myOrders') }}</span>
      </el-menu-item>

      <el-menu-item index="/home/interaction">
        <el-icon><Star /></el-icon>
        <span>{{ $t('myFavorites') }}</span>
      </el-menu-item>

      <el-menu-item index="/home/evaluation">
        <el-icon><ChatDotRound /></el-icon>
        <span>{{ $t('myComments') }}</span>
      </el-menu-item>

      <el-menu-item index="/home/message">
        <el-icon><Message /></el-icon>
        <span>{{ $t('myMessages') }}</span>
      </el-menu-item>

      <el-menu-item index="/home/information">
        <el-icon><User /></el-icon>
        <span>{{ $t('profile') }}</span>
      </el-menu-item>

      <el-menu-item index="/home/setting">
        <el-icon><Setting /></el-icon>
        <span>{{ $t('setting') }}</span>
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
  ShoppingCart,
  Plus,
  Goods,
  List,
  Star,
  ChatDotRound,
  Message,
  User,
  Setting
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