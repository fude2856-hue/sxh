import request from "@/utils/request";
//分页加模糊查询
export const getOrdersPage = (data) => {
    console.log('请求参数:', data); // 打印请求参数
    return request.get("/api/orders/getPage", { params: data });
};
//增加订单
export const insertOrders = data =>request.post("/api/orders/insert",data);
export const updateOrders = data =>request.put("/api/orders/update",data);
export const deleteOrdersById = (id) => {
    console.log('删除订单 ID:', id); // 打印要删除的订单 ID
    return request.delete(`/api/orders/delete/${id}`);
};
// 批量删除订单
export const deleteOrdersByIds = (ids) => {
    console.log('批量删除订单 IDs:', ids); // 打印要删除的订单 IDs
    return request.delete('/api/orders/delete', { data: ids });
};
