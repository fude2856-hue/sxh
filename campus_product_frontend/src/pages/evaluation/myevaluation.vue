<template>
  <div class="evaluation-container">
      <h2>我的评论与反馈</h2>
<div class="content">
  <div class="evaluation-list">
    <h3>我的评论</h3>
    <!-- 评论列表 -->
    <el-table :data="evaluations" style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="100"></el-table-column>
      <el-table-column prop="product_id" label="商品ID" width="120"></el-table-column>
      <el-table-column prop="score" label="评分" width="100">
        <template #default="{ row }">
          {{ row.score }}星
        </template>
      </el-table-column>
      <el-table-column prop="content" label="评论内容"></el-table-column>
      <el-table-column prop="create_time" label="评论时间" width="180"></el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button
              type="danger"
              size="small"
              @click="handleDelete(row.id)"
              :loading="row.deleting"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加分页组件 -->
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
  <div class="feedback-form">
    <h3>提交使用反馈</h3>
      <el-form :model="newMessage" label-width="100px">
        <el-form-item label="反馈内容">
          <el-input type="textarea" v-model="newMessage.content" rows="4" placeholder="请输入反馈内容"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              @click="handleSendFeedback"
              :loading="sending"
          >
            提交反馈
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage} from 'element-plus';
import { getAllEvaluations, deleteEvaluation  } from '@/api/evaluation';
import { sendFeedback  } from '@/api/message';

// 加载状态
const loading = ref(true);
const sending = ref(false);

// 评论列表数据
const evaluations = ref([]);

// 反馈
const newMessage = ref({
  content: ''
});
// 当前页码
const currentPage = ref(1);
// 每页显示数量
const pageSize = ref(3);
// 总记录数
const total = ref(0);
// 加载所有评论
const loadEvaluations = async () => {
  try {
    loading.value = true;
    const res = await getAllEvaluations({pageNum:currentPage.value,pageSize:pageSize.value});
    if (res.code === 0) {
      evaluations.value = res.data.list.map(item => ({
        ...item,
        deleting: false
      }));
      total.value=res.data.total;
    } else if(Array.isArray(res.data)) {
      evaluations.value = res.data.map(item => ({
        ...item,
        deleting: false
      }));
      total.value=res.data.length;
    }else{
      throw new Error("数据格式错误：未找到评论列表");
    }
  } catch (err) {
    ElMessage.error('获取评论失败: ' + err.message);
  } finally {
    loading.value = false;
  }
};

// 删除评论
const handleDelete = async (id) => {
  try {
    // 设置删除状态
    const index = evaluations.value.findIndex(e => e.id === id);
    if (index !== -1) {
      evaluations.value[index].deleting = true;

      const res = await deleteEvaluation(id);
      if (res.code === 0) {
        ElMessage.success('删除成功');
        evaluations.value.splice(index, 1);
      } else {
        ElMessage.error(res.msg || '删除失败');
      }
    }
  } catch (err) {
    ElMessage.error("删除失败"||err.message);
  } finally {
    const index = evaluations.value.findIndex(e => e.id === id);
    if (index !== -1) {
      evaluations.value[index].deleting = false;
    }
  }
};

// 提交新反馈
const handleSendFeedback = async () => {
  try {
    sending.value = true;
    const res = await sendFeedback(newMessage.value.content);
    if (res.code === 0) {
      ElMessage.success('反馈提交成功');
      newMessage.value.content='';
    } else {
      ElMessage.error(res.msg || '反馈提交失败');
    }
  } catch (err) {
    console.error("反馈提交失败", err);
  } finally {
    sending.value = false;
  }
};
const handleSizeChange=(newSize)=>{
  pageSize.value=newSize;
  loadEvaluations();
}
const handleCurrentChange=(newPage)=>{
  currentPage.value=newPage;
  loadEvaluations();
}

// 初始化加载数据
onMounted(() => {
  loadEvaluations();
});
</script>

<style scoped>
.evaluation-container {
  max-width: 1200px;
  min-height: 80vh;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}
.content{
  display: flex;
  flex-direction: column;
  align-items: center;
  gap:50px;
}
.evaluation-list{
  width:100%;
}
.feedback-form{
  width: 100%;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 8px;
  border:1px solid #eee;
}
</style>
