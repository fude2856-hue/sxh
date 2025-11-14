package com.dz.campus_product_backend.service.impl;
import com.dz.campus_product_backend.dao.MyProductMapper;
import com.dz.campus_product_backend.entity.Product;
import com.dz.campus_product_backend.service.MyProductService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyProductServiceImpl implements MyProductService {
    @Autowired
    private MyProductMapper myProductMapper;

    @Override
    public ResultVO getMyProductsByPage(int userId, String keyword, int pageNum, int pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            // 打印日志，确认传递给 Mapper 的参数
            System.out.println("Mapper 接收到的参数：userId=" + userId + ", keyword=" + keyword + ", offset=" + offset + ", pageSize=" + pageSize);
            List<Product> productList = myProductMapper.getMyProductsByPage(userId, keyword, offset, pageSize);
            int total = myProductMapper.getMyProductsTotal(userId, keyword);

            // 处理 productList 为 null 的情况
            if (productList == null) {
                productList = List.of();
            }

            Map<String, Object> data = new HashMap<>();
            data.put("list", productList);
            data.put("total", total);

            ResultVO result = ResultVO.success(data);
            // 打印日志，确认返回的结果
            //System.out.println("返回的结果：" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error("查询我的商品失败: " + e.getMessage());
        }
    }



    @Override
    public ResultVO updateMyProduct(Product product) {
        try {
            myProductMapper.updateMyProduct(product);
            return ResultVO.success("更新我的商品成功");
        } catch (Exception e) {
            // 实际项目中建议使用日志框架记录异常，这里简单打印
            System.err.println("更新我的商品失败: " + e.getMessage());
            return ResultVO.error("更新我的商品失败，请稍后重试");
        }

    }

    @Override
    @Transactional
    public int deleteMyProductById(String productId) {
        // 调用 DAO 层方法删除商品
         return myProductMapper.deleteById(productId);

    }

    @Override
    public int deleteByIds(List<String> ids) {

        // 调用 DAO 层方法批量删除商品
        return myProductMapper.deleteByIds(ids);
    }

}