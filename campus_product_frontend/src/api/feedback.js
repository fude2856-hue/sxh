import request from "@/utils/request";
export const getFeedbacks = (params) => request.get("/api/feedback/all",{params});
export const sendReplyToFeedback=(feedbackId,content)=>request.post("/api/feedback/reply",{feedbackId,content});