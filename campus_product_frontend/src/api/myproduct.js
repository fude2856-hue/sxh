import request from '@/utils/request';
/**
 * 我的商品分页加模糊查询
 * @param {number} userId 用户 ID
 * @param {string} keyword 模糊查询关键词
 * @param {number} pageNum 当前页码
 * @param {number} pageSize 每页显示数量
 * @returns {Promise} 请求的 Promise 对象
 */
// 获取我的商品列表
// 获取我的商品列表
export const getMyProductsByPage = async (params) => {
    const url = new URL('/api/myproduct/list', window.location.origin);
    Object.entries(params).forEach(([key, value]) => {
        url.searchParams.append(key, value);
    });

    try {
        const response = await fetch(url.toString(), {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return response.json();
    } catch (error) {
        console.error('请求商品列表出错:', error);
        throw error;
    }
};

// 修改我的商品
export const updateMyProduct = async (productData) => {
    const url = new URL('/api/myproduct/update', window.location.origin);

    try {
        const response = await fetch(url.toString(), {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return response.json();
    } catch (error) {
        console.error('修改商品出错:', error);
        throw error;
    }
};

// 删除我的商品
export const deleteMyProduct = (id) => {
    console.log('删除我的商品 ID:', id); // 打印要删除的订单 ID
    return request.delete(`/api/myproduct/delete/${id}`);
};
// 批量删除我的商品
export const deleteMyProducts = (ids) => {
    console.log('批量删除我的商品 IDs:', ids); // 打印要删除的订单 IDs
    return request.delete('/api/myproduct/delete', { data: ids });
};