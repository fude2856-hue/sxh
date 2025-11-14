<script setup>
import { ref, onMounted, watch, inject } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useI18n } from 'vue-i18n'

const router = useRouter()
const { t, locale } = useI18n()

// 主题 & 颜色 & 语言（系统设置部分）
const themeState = inject('themeState')
const updateTheme = inject('updateTheme')

const isDark = ref(themeState.theme === 'dark')
const selectedSidebarColor = ref(themeState.sidebarBg)
const language = ref(localStorage.getItem('language') || 'zh')

const colors = [
  { name: '墨绿色', value: '#1f2d3d' },
  { name: '紫色', value: '#cfaf5e' },
  { name: '绿色', value: '#3ac270' },
  { name: '红色', value: '#c3f56c' },
  { name: '灰色', value: '#5a5a5a' }
]

watch(isDark, val => {
  themeState.theme = val ? 'dark' : 'light'
  localStorage.setItem('theme', themeState.theme)
  updateTheme()
})

watch(selectedSidebarColor, color => {
  themeState.sidebarBg = color
  localStorage.setItem('themeSidebarColor', color)
  updateTheme()
})

function changeLanguage(lang) {
  language.value = lang
  localStorage.setItem('language', lang)
  locale.value = lang
  ElMessage.success(t('languageChanged', { lang: lang === 'zh' ? '简体中文' : 'English' }))
}

// 安全中心操作函数
const logout = async () => {
  await axios.get('/api/user/logout')
  router.push('/login')
}

const switchAccount = () => logout()

const deleteAccount = async () => {
  try {
    await ElMessageBox.confirm(
        t('confirmDeleteContent'),
        t('confirmDeleteTitle'),
        {
          confirmButtonText: t('confirm'),
          cancelButtonText: t('cancel'),
          type: 'warning'
        }
    )
    // 用户点击确认
    await axios.delete('/api/user/delete-account')
    ElMessage.success(t('accountDeleted'))
    router.push('/login')
  } catch (err) {
    // 如果是取消按钮，不做任何提示
    if (err !== 'cancel' && err !== 'close') {
      ElMessage.error(err.response?.data?.msg || t('deleteAccountFailed'))
    }
  }
}

onMounted(() => {
  isDark.value = themeState.theme === 'dark'
  selectedSidebarColor.value = themeState.sidebarBg
})
</script>

<template>
  <div class="user-setting">

    <!-- 系统设置 -->
    <el-card class="setting-card">
      <div class="section">
        <div class="section-title">🌗 {{ t('themeMode') }}</div>
        <el-switch v-model="isDark" :active-text="t('dark')" :inactive-text="t('light')" />
      </div>

      <el-divider />

      <div class="section">
        <div class="section-title">🎨 {{ t('sidebarColor') }}</div>
        <div class="color-options">
          <div
              v-for="color in colors"
              :key="color.name"
              class="color-box"
              :style="{
                backgroundColor: color.value,
                border: selectedSidebarColor === color.value ? '3px solid #000' : '1px solid #ccc'
              }"
              @click="selectedSidebarColor = color.value"
              :title="color.name"
          />
        </div>
      </div>

      <el-divider />

      <div class="section">
        <div class="section-title">🌐 {{ t('languageSelection') }}</div>
        <el-select v-model="language" @change="changeLanguage" :placeholder="t('selectLanguage')">
          <el-option :label="t('simplifiedChinese')" value="zh" />
          <el-option :label="t('english')" value="en" />
        </el-select>
      </div>
    </el-card>

    <!-- 安全中心 -->
    <el-card class="security-card" style="margin-top: 30px;">
      <div class="section-title">{{ t('accountOperations') }}</div>

      <div class="btn-group">
        <el-button type="primary" @click="logout">{{ t('logout') }}</el-button>
        <el-button type="success" @click="switchAccount">{{ t('switchAccount') }}</el-button>
        <el-button type="danger" @click="deleteAccount">{{ t('deleteAccount') }}</el-button>
      </div>
    </el-card>

  </div>
</template>

<style scoped>
.user-setting {
  max-width: 600px;
  margin: 0 auto;
}
.setting-card, .security-card {
  padding: 20px;
}
.section {
  margin-bottom: 20px;
}
.section-title {
  font-weight: bold;
  margin-bottom: 10px;
  font-size: 16px;
}
.color-options {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}
.color-box {
  width: 36px;
  height: 36px;
  border-radius: 6px;
  cursor: pointer;
  transition: 0.3s;
}
.btn-group {
  display: flex;
  gap: 10px;
}
</style>
