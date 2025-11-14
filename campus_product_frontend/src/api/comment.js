import request from "@/utils/request.js";

export const submitComment = (productId, content,score) => request.post('/api/comment/add', { productId, content ,score});
