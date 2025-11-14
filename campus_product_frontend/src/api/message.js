import request from "@/utils/request";
export const sendFeedback=(content) => request.post("/api/message/add",{content});
export const getComments=params=>request.get("/api/message/comment",{ params});
export const getOrders=params=>request.get("/api/message/order",{ params });
export const getReplies=params=>request.get("/api/message/message",{params});
export const getInteractions=params=>request.get("/api/message/interaction",{params});
