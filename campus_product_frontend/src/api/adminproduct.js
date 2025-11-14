import request from "@/utils/request";

// 查询所有商品
export const getAllProducts = () => request.get("/api/admin/product/findAll");

// 根据 ID 查询商品
export const getProductById = (id) => request.get("/api/admin/product/findById", { params: { productId: id } });

// 根据名称查询商品
export const getProductByName = (name) => request.get("/api/admin/product/findByName", { params: { productName: name } });

// 删除商品（根据 ID）
export const deleteProductById = (id) => request.delete("/api/admin/product/deleteById", { params: { productId: id } });

// 删除商品（根据名称）
export const deleteProductByName = (name) => request.delete("/api/admin/product/deleteByName", { params: { productName: name } });

// 新增：创建商品
export const createProduct = (productData) => {
    return request.post("/api/admin/product/create", productData);
};
