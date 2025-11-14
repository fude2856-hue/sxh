
import request from "@/utils/request";

//发布商品
export const createProduct = data =>request.post("/api/product/createproduct",data);

//按商品名进行模糊查询
export const searchProductByName = (name) => request.get('/api/product/search', { params: { name } });

//获取属于启用类的商品
export const getProductsByEnableCategory = () => request.get('/api/product/enabled-category');

export const getProductById = (id) => request.get('/api/product/detail', { params: { id } });

// 分页获取商品列表
export const getProductListByPage = (currentPage, pageSize) => request.get('/api/product/all', { params: { currentPage, pageSize } });


