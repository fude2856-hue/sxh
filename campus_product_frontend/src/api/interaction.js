import request from "@/utils/request";

export const insertInteraction = data =>request.post("/api/interaction/insert",data);
//修改收藏
export const updateInteraction = data =>request.put("/api/interaction/update",data);
//分页加模糊查询
export const getInteractionPage = (data) => {
    console.log('请求参数:', data); // 打印请求参数
    return request.get("/api/interaction/getPage", { params: data });
};
