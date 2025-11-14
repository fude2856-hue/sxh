<template>
  <router-view />
</template>

<script setup>
// 无需逻辑
</script>

<style>
:root[data-theme='light'] {
  --sidebar-bg: #ffffff;
  --sidebar-text: #ffffff; /* 始终白色字体 */
  --sidebar-active: var(--el-color-primary);
  --main-bg: #f5f5f5;
}

:root[data-theme='dark'] {
  --sidebar-bg: #1f2d3d;
  --sidebar-text: #ffffff; /* 始终白色字体 */
  --sidebar-active: var(--el-color-primary);
  --main-bg: #141414;
}
</style>
