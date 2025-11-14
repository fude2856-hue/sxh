import request from "@/utils/request.js";

export const buyProduct = (productId) => request.post('/api/order/buy', { productId });
