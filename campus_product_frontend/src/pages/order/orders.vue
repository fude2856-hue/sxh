<template>
  <div>
    <!-- 搜索表单 -->
    <div class="search-form">
      <input v-model="searchKeyword" placeholder="输入搜索关键词" />
      <button @click="searchOrders">搜索</button>
    </div>

    <!-- 新增订单按钮和批量删除按钮 -->
    <div class="add-button">
<!--      <button @click="showAddModal = true">新增订单</button>-->
      <button @click="batchDeleteOrders" :disabled="selectedOrders.length === 0">批量删除</button>
    </div>

    <!-- 订单列表 -->
    <table>
      <thead>
      <tr>
        <th><input type="checkbox" v-model="selectAll" @change="toggleSelectAll" /></th>
        <th>订单 ID</th>
        <th>商品 ID</th>
        <th>用户 ID</th>
        <th>购买价格</th>
        <th>交易时间</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="order in orders" :key="order.id">
        <td><input type="checkbox" v-model="selectedOrders" :value="order.id" /></td>
        <td>{{ order.id }}</td>
        <td>{{ order.product_id }}</td>
        <td>{{ order.user_id }}</td>
        <td>{{ order.buy_price }}</td>
        <td>{{ formatDateTime(order.trade_time) }}</td>
        <td>
<!--          <button @click="showEditModal = true; editOrder = { ...order }">编辑</button>-->
          <button @click="deleteOrder(order.id)">删除</button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 分页组件 -->
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>{{ currentPage }} / {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
    </div>

    <!-- 新增订单模态框 -->
    <div v-if="showAddModal" class="modal">
      <div class="modal-content">
        <h2>新增订单</h2>
        <div>
          <label for="productId">商品 ID:</label>
          <input id="productId" v-model="newOrder.product_id" placeholder="商品 ID" />
        </div>
        <div>
          <label for="userId">用户 ID:</label>
          <input id="userId" v-model="newOrder.user_id" placeholder="用户 ID" />
        </div>
        <div>
          <label for="buyPrice">购买价格:</label>
          <input id="buyPrice" v-model="newOrder.buy_price" placeholder="购买价格" />
        </div>
        <div>
          <label for="tradeTime">交易时间:</label>
          <input id="tradeTime" type="datetime-local" v-model="newOrder.trade_time" />
        </div>
        <button @click="addOrder">确认</button>
        <button @click="showAddModal = false">取消</button>
      </div>
    </div>

    <!-- 编辑订单模态框 -->
    <div v-if="showEditModal" class="modal">
      <div class="modal-content">
        <h2>编辑订单</h2>
        <div>
          <label for="editProductId">商品 ID:</label>
          <input id="editProductId" v-model="editOrder.product_id" placeholder="商品 ID" />
        </div>
        <div>
          <label for="editUserId">用户 ID:</label>
          <input id="editUserId" v-model="editOrder.user_id" placeholder="用户 ID" />
        </div>
        <div>
          <label for="editBuyPrice">购买价格:</label>
          <input id="editBuyPrice" v-model="editOrder.buy_price" placeholder="购买价格" />
        </div>
        <div>
          <label for="editTradeTime">交易时间:</label>
          <input id="editTradeTime" type="datetime-local" v-model="editOrder.trade_time" />
        </div>
        <button @click="updateOrder">确认</button>
        <button @click="showEditModal = false">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getOrdersPage,
  insertOrders,
  updateOrders,
  deleteOrdersById,
  deleteOrdersByIds // 新增批量删除接口
} from '@/api/orders';

export default {
  data() {
    return {
      orders: [],
      currentPage: 1,
      pageSize: 10,
      totalPages: 1,
      searchKeyword: '',
      showAddModal: false,
      newOrder: {
        product_id: '',
        user_id: '',
        buy_price: '',
        trade_time: ''
      },
      showEditModal: false,
      editOrder: {
        product_id: '',
        user_id: '',
        buy_price: '',
        trade_time: ''
      },
      selectedOrders: [], // 存储选中的订单 ID
      selectAll: false // 全选状态
    };
  },
  mounted() {
    this.fetchOrders();
  },
  methods: {
    async fetchOrders() {
      try {
        const data = {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          keyword: this.searchKeyword
        };
        const response = await getOrdersPage(data);
        this.orders = response.data.list;
        this.totalPages = Math.ceil(response.data.total / this.pageSize);
        // 重置全选状态
        this.selectAll = false;
        this.selectedOrders = [];
      } catch (error) {
        console.error('获取订单列表失败:', error);
      }
    },
    searchOrders() {
      this.currentPage = 1;
      this.fetchOrders();
    },
    // async addOrder() {
    //   try {
    //     // 转换时间格式以匹配后端要求
    //     if (this.newOrder.trade_time) {
    //       this.newOrder.trade_time = this.formatDateTimeForBackend(this.newOrder.trade_time);
    //     }
    //     console.log('新增订单数据:', JSON.stringify(this.newOrder, null, 2));
    //     await insertOrders(this.newOrder);
    //     this.showAddModal = false;
    //     this.newOrder = {
    //       product_id: '',
    //       user_id: '',
    //       buy_price: '',
    //       trade_time: ''
    //     };
    //     // 重新获取订单列表
    //     await this.fetchOrders();
    //   } catch (error) {
    //     alert(error.response?.data?.message || '新增订单失败，请稍后重试');
    //     console.error('新增订单失败:', error);
    //   }
    // },
    // async updateOrder() {
    //   try {
    //     // 转换时间格式以匹配后端要求
    //     if (this.editOrder.trade_time) {
    //       this.editOrder.trade_time = this.formatDateTimeForBackend(this.editOrder.trade_time);
    //     }
    //     await updateOrders(this.editOrder);
    //     this.showEditModal = false;
    //     this.editOrder = {
    //       product_id: '',
    //       user_id: '',
    //       buy_price: '',
    //       trade_time: ''
    //     };
    //     this.fetchOrders();
    //   } catch (error) {
    //     alert(error.response?.data?.message || '更新订单失败，请稍后重试');
    //     console.error('更新订单失败:', error);
    //   }
    // },
    async deleteOrder(id) {
      try {
        if (confirm('确定要删除该订单吗？')) {
          await deleteOrdersById(id);
          this.fetchOrders();
        }
      } catch (error) {
        alert(error.response?.data?.message || '删除订单失败，请稍后重试');
        console.error('删除订单失败:', error);
      }
    },
    async batchDeleteOrders() {
      try {
        if (confirm('确定要批量删除选中的订单吗？')) {
          await deleteOrdersByIds(this.selectedOrders);
          this.fetchOrders();
          this.selectedOrders = [];
          this.selectAll = false;
        }
      } catch (error) {
        alert(error.response?.data?.message || '批量删除订单失败，请稍后重试');
        console.error('批量删除订单失败:', error);
      }
    },
    toggleSelectAll() {
      if (this.selectAll) {
        this.selectedOrders = this.orders.map(order => order.id);
      } else {
        this.selectedOrders = [];
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchOrders();
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.fetchOrders();
      }
    },
    formatDateTime(dateTime) {
      if (!dateTime) return '';
      const date = new Date(dateTime);
      return date.toLocaleString();
    },
    formatDateTimeForBackend(dateTime) {
      if (!dateTime) return '';
      const date = new Date(dateTime);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
  }
};
</script>

<style scoped>
.search-form {
  margin-bottom: 20px;
}

.add-button {
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: left;
}

.pagination {
  margin-top: 20px;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
}

.modal-content div {
  margin-bottom: 10px;
}

.modal-content label {
  display: inline-block;
  width: 80px;
  margin-right: 10px;
}

.modal-content input {
  width: 200px;
}
</style>