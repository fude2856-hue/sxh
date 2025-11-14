import { createApp, reactive } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import i18n from './i18n'

// 主题状态（不再包含 sidebarText）
const themeState = reactive({
    theme: localStorage.getItem('theme') || 'dark',
    sidebarBg: localStorage.getItem('themeSidebarColor') || '#1f2d3d',
    primaryColor: localStorage.getItem('themeColor') || '#409EFF'
})

// 应用主题样式
function applyTheme(theme, bg, primaryColor) {
    document.documentElement.setAttribute('data-theme', theme)
    document.documentElement.style.setProperty('--sidebar-bg', bg)
    document.documentElement.style.setProperty('--sidebar-text', '#ffffff') // 始终白色
    document.documentElement.style.setProperty('--el-color-primary', primaryColor)
}

function updateTheme() {
    applyTheme(themeState.theme, themeState.sidebarBg, themeState.primaryColor)
}

updateTheme()

const app = createApp(App)
app.provide('themeState', themeState)
app.provide('updateTheme', updateTheme)

app.use(router)
app.use(ElementPlus)
app.use(i18n)

app.mount('#app')
