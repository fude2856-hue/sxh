import request from "@/utils/request";

export const toggleFavorite = (productId) => request.post('/api/interaction/toggle', { productId });
