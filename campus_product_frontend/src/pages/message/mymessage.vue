<template>
  <div class="message-container">
    <h2>消息中心</h2>
    <div class="content">
      <div class="comments-section">
        <h3>评论消息</h3>
        <el-table :data="comments" style="width: 100%" v-loading="loadingComments">
          <el-table-column prop="user_id" label="用户ID" width="280"></el-table-column>
          <el-table-column prop="content" label="评论内容"></el-table-column>
          <el-table-column prop="create_time" label="评论时间" width="180"></el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChangec"
            @current-change="handleCurrentChangec"
            :current-page="currentcPage"
            :page-sizes="[3, 5, 10]"
            :page-size="cpageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="ctotal"
        >
        </el-pagination>
      </div>
      <div class="orders-section">
        <h3>订单消息</h3>
               <el-table :data="orders" style="width: 100%" v-loading="loadingOrders">
          <el-table-column prop="user_id" label="买家ID" width="280"></el-table-column>
          <el-table-column prop="product_id" label="商品ID" ></el-table-column>
          <el-table-column prop="trade_time" label="创建时间" width="180"></el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChangeo"
            @current-change="handleCurrentChangeo"
            :current-page="currentoPage"
            :page-sizes="[3, 5, 10]"
            :page-size="opageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="ototal"
        >
        </el-pagination>
      </div>
      <div class="interactions-section">
        <h3>收藏消息</h3>
        <el-table :data="interactions" style="width: 100%" v-loading="loadingInteractions">
          <el-table-column prop="user_id" label="收藏者ID" width="280"></el-table-column>
          <el-table-column prop="product_id" label="商品ID" ></el-table-column>
          <el-table-column prop="datetime" label="收藏时间" width="180">
            <template #default="scope">
              {{ formatDate(scope.row.datetime) }}
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChangei"
            @current-change="handleCurrentChangei"
            :current-page="currentiPage"
            :page-sizes="[3, 5, 10]"
            :page-size="ipageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="itotal"
        >
        </el-pagination>
      </div>
        <div class="replies-section">
          <h3>反馈回复</h3>
          <el-table :data="replies" style="width: 100%" v-loading="loadingReplies">
            <el-table-column prop="send_user_id" label="管理员ID" width="280"></el-table-column>
            <el-table-column prop="content" label="回复内容"></el-table-column>
            <el-table-column prop="create_time" label="回复时间" width="180"></el-table-column>
          </el-table>
          <el-pagination
              @size-change="handleSizeChanger"
              @current-change="handleCurrentChanger"
              :current-page="currentrPage"
              :page-sizes="[3, 5, 10]"
              :page-size="rpageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="rtotal"
          >
          </el-pagination>
        </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import { ElMessage } from 'element-plus';
import {getComments, getOrders, getInteractions,getReplies} from '@/api/message';
// 格式化时间
const formatDate = (date) => {
  if (!date) return '';
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};
const loadingComments = ref(true);
const loadingOrders = ref(true);
const loadingReplies=ref(true);
const loadingInteractions=ref(true);
const comments = ref([]);
const orders = ref([]);
const replies=ref([]);
const interactions=ref([]);
// 加载收到的评论
const currentcPage = ref(1);
// 每页显示数量
const cpageSize = ref(3);
// 总记录数
const ctotal = ref(0);
const currentoPage = ref(1);
// 每页显示数量
const opageSize = ref(3);
// 总记录数
const ototal = ref(0);
const currentiPage = ref(1);
// 每页显示数量
const ipageSize = ref(3);
// 总记录数
const itotal = ref(0);
const currentrPage=ref(1);
// 每页显示数量
const rpageSize = ref(3);
// 总记录数
const rtotal = ref(0);
// 加载所有评论
const loadComments = async () => {
  try {
    loadingComments.value = true;
    const res = await getComments({pageNum:currentcPage.value,pageSize:cpageSize.value});
    if (res.code === 0) {
      comments.value = res.data.list;
      ctotal.value=res.data.total;
    }
    else if(Array.isArray(res.data)) {
      comments.value = res.data;
      ctotal.value=res.data.length;
    }else{
      throw new Error("数据格式错误：未找到评论消息列表");
    }
  } catch (err) {
    ElMessage.error('获取评论失败: ' + err.message);
  } finally {
    loadingComments.value = false;
  }
};

// 加载订单消息
const loadOrders = async () => {
  try {
    loadingOrders.value = true;
    const res = await getOrders({pageNum:currentoPage.value,pageSize:opageSize.value});
    if (res.code === 0) {
      orders.value = res.data.list;
      ototal.value=res.data.total;
    } else if(Array.isArray(res.data)) {
      orders.value = res.data;
      ototal.value=res.data.length;
    }else{
      throw new Error("数据格式错误：未找到订单消息列表");
    }
  } catch (err) {
    ElMessage.error('获取订单消息失败: ' + err.message);
  } finally {
    loadingOrders.value = false;
  }
};
const loadInteractions = async () => {
  try {
    loadingInteractions.value = true;
    const res = await getInteractions({pageNum:currentiPage.value,ipageSize:opageSize.value});
    if (res.code === 0) {
      interactions.value = res.data.list;
      itotal.value=res.data.total;
    } else if(Array.isArray(res.data)) {
      interactions.value = res.data;
      itotal.value=res.data.length;
    }else{
      throw new Error("数据格式错误：未找到收藏消息列表");
    }
  } catch (err) {
    ElMessage.error('获取收藏消息失败: ' + err.message);
  } finally {
    loadingInteractions.value = false;
  }
};
const loadReplies = async () => {
  try {
    loadingReplies.value = true;
    const res = await getReplies({pageNum:currentrPage.value,pageSize:rpageSize.value});
    if (res.code === 0) {
      replies.value = res.data.list;
      rtotal.value=res.data.total;
    } else if(Array.isArray(res.data)) {
      replies.value = res.data;
      rtotal.value=res.data.length;
    }else{
      throw new Error("数据格式错误：未找到订单消息列表");
    }
  } catch (err) {
    ElMessage.error('获取订单消息失败: ' + err.message);
  } finally {
    loadingReplies.value = false;
  }
};
const handleSizeChangec=(newSize)=>{
  cpageSize.value=newSize;
  loadComments();
}
const handleCurrentChangec=(newPage)=>{
  currentcPage.value=newPage;
  loadComments();
}
const handleSizeChangeo=(newSize)=>{
  opageSize.value=newSize;
  loadOrders();
}
const handleCurrentChangeo=(newPage)=>{
  currentoPage.value=newPage;
  loadOrders();
}
const handleSizeChangei=(newSize)=>{
  ipageSize.value=newSize;
  loadInteractions();
}
const handleCurrentChangei=(newPage)=>{
  currentiPage.value=newPage;
  loadInteractions();
}
const handleSizeChanger=(newSize)=>{
  rpageSize.value=newSize;
  loadReplies();
}
const handleCurrentChanger=(newPage)=>{
  currentrPage.value=newPage;
  loadReplies();
}
// 初始化加载数据
onMounted(() => {
    loadComments();
    loadOrders();
    loadInteractions();
    loadReplies();
});
</script>

<style scoped>
.message-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.content {
  margin-top: 20px;
}

.comments-section, .orders-section {
  margin-bottom: 20px;
}

h2 {
  text-align: center;
}

h3 {
  margin-bottom: 15px;
}
</style>
