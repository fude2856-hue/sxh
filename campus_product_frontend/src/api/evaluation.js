import request from "@/utils/request";


// 获取当前用户的所有评论
export const getAllEvaluations = (params) => request.get('/api/evaluation/all',{params});
// 删除评论
export const deleteEvaluation = (id) => request.delete('/api/evaluation/delete', {params:{id}  });

