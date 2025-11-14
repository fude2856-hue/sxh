import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",//需代理的后端接口ig
        secure: true,//开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求
        changeOrigin: true,
        pathRewrite: {
          "^/api": "/",//重写匹配的字段。把/api 转为 /
        }
      }
    }
  },
})