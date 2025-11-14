import request from '@/utils/request';

// 查询所有评论
export const getAllEvaluations = () => request.get('/api/admin/evaluation/findAll');

// 根据评论ID查询
export const getEvaluationById = (evaluationId) =>
    request.get('/api/admin/evaluation/findById', { params: { evaluationId } });

// 根据用户ID查询
export const getEvaluationsByUserId = (userId) =>
    request.get('/api/admin/evaluation/findByUserId', { params: { userId } });

// 根据商品ID查询
export const getEvaluationsByProductId = (productId) =>
    request.get('/api/admin/evaluation/findByProductId', { params: { productId } });

// 删除评论：根据评论ID
export const deleteEvaluationById = (evaluationId) =>
    request.delete('/api/admin/evaluation/deleteById', { params: { evaluationId } });

// 删除评论：根据用户ID
export const deleteEvaluationByUserId = (userId) =>
    request.delete('/api/admin/evaluation/deleteByUserId', { params: { userId } });

// 删除评论：根据商品ID
export const deleteEvaluationByProductId = (productId) =>
    request.delete('/api/admin/evaluation/deleteByProductId', { params: { productId } });
