<template>
  <div class="feedback-container">
    <h2>反馈中心</h2>
    <div class="feedback-list">
      <el-table :data="feedbacks" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="100"></el-table-column>
        <el-table-column prop="send_user_id" label="用户ID" width="150"></el-table-column>
        <el-table-column prop="content" label="反馈内容"></el-table-column>
        <el-table-column prop="create_time" label="反馈时间" width="180"></el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="openReplyDialog(row)"> 回复 </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 添加分页组件 -->
      <div class="pagination-wrapper">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[3, 5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
    </div>
    <!-- 回复反馈对话框 -->
    <el-dialog v-model="replyDialogVisible" title="回复反馈">
      <el-form :model="replyMessage" label-width="100px">
        <el-form-item label="回复内容">
          <el-input type="textarea" v-model="replyMessage.content" rows="4" placeholder="请输入回复内容"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="replyDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="sendReply">确 定</el-button>
      </span>
    </el-dialog>
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { getFeedbacks, sendReplyToFeedback } from '@/api/feedback';

const loading = ref(true);
const feedbacks = ref([]);
const replyDialogVisible = ref(false);
const replyMessage = ref({
  content: '',
  feedbackId: null,
});
// 当前页码
const currentPage = ref(1);
// 每页显示数量
const pageSize = ref(10);
// 总记录数
const total = ref(0);
// 加载收到的反馈
const loadFeedbacks = async () => {
  try {
    loading.value = true;
    const res = await getFeedbacks({pageNum:currentPage.value,pageSize:pageSize.value});
    console.log('返回数据:', res);
    if (res.code === 0) {
      feedbacks.value = res.data.list;
      total.value=res.data.total;
    } else if(Array.isArray(res.data)){
      feedbacks.value=res.data;
      total.value=res.data.length;
    }
  else {
      throw new Error("数据格式错误：未找到反馈列表");
    }
  } catch (err) {
    ElMessage.error('获取反馈失败: '+ err.message);
  } finally {
    loading.value = false;
  }
};

// 打开回复对话框
const openReplyDialog = (feedback) => {
  if (!feedback.id) {
    ElMessage.warning('无法识别此反馈');
    return;
  }
  replyMessage.value.content = ''; // 清空之前的内容
  replyMessage.value.feedbackId = feedback.id; // 设置反馈ID
  replyDialogVisible.value = true; // 显示对话框
};

// 处理发送回复
const sendReply = async () => {
  if (!replyMessage.value.content.trim()) {
    ElMessage.warning('回复内容不能为空');
    return;
  }

  try {
    const res = await sendReplyToFeedback(replyMessage.value.feedbackId, replyMessage.value.content);
    if (res.code === 0) {
      ElMessage.success('回复成功');
      replyDialogVisible.value = false; // 关闭对话框
      await loadFeedbacks(); // 重新加载反馈以显示回复
    } else {
      ElMessage.error(res.msg || '回复失败');
    }
  } catch (err) {
    ElMessage.error('回复失败: ' + err.message);
  }
};
const handleSizeChange=(newSize)=>{
  pageSize.value=newSize;
  loadFeedbacks();
}
const handleCurrentChange=(newPage)=>{
  currentPage.value=newPage;
  loadFeedbacks();
}
// 初始化加载数据
onMounted(() => {
  loadFeedbacks();
});
</script>

<style scoped>
.feedback-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.feedback-list {
  margin-top: 20px;
}
.pagination-wrapper {
  display: flex;
  justify-content: center;
}

h2 {
  text-align: center;
}
</style>
