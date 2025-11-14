<template>
  <div class="announcement-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>📢 {{ $t('announcement') }}</span>
        </div>
      </template>

      <el-table :data="logs" stripe border style="width: 100%">
        <el-table-column prop="user_id" :label="$t('userId')" width="100" />
        <el-table-column prop="detail" :label="$t('operationDetail')" />
        <el-table-column
            prop="create_time"
            :label="$t('time')"
            width="200"
            :formatter="formatTime"
        />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

const logs = ref([])

onMounted(async () => {
  const res = await axios.get('/api/logs/all')
  if (res.data.code === 0) {
    logs.value = res.data.data
  }
})

function formatTime(row) {
  const t = new Date(row.create_time)
  return t.toLocaleString()
}
</script>

<style scoped>
.announcement-page {
  max-width: 1000px;
  margin: 0 auto;
}

.card-header {
  font-weight: bold;
  font-size: 18px;
  color: var(--el-color-primary);
}
</style>
