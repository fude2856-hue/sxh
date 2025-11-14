import request from "@/utils/request";

export const getCategories = () => request.get("/api/category/list");

export const enableCategoryApi = (categoryId) => request.post('/api/category/use', { categoryId });
