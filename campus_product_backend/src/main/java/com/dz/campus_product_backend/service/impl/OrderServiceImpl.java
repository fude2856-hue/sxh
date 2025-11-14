package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.OrderMapper;
import com.dz.campus_product_backend.dao.ProductMapper;
import com.dz.campus_product_backend.entity.Orders;
import com.dz.campus_product_backend.entity.Product;
import com.dz.campus_product_backend.service.OrderService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResultVO createOrder(String user_id,String product_id) {
        Product product=productMapper.selectByProductId(product_id);
        int stock= Integer.parseInt(product.getInventory());
        if(stock<=0){
            //productMapper.deleteByProductId(product_id);
            return ResultVO.error("库存不足,已下架");
        }
        else{
            productMapper.updateStock(product_id);
            Orders order=new Orders();
            order.setUser_id(user_id);
            order.setProduct_id(product_id);
            order.setBuy_price(product.getPrice());
            order.setTrade_time(String.valueOf(LocalDateTime.now()));
            orderMapper.insertOrder(order);
            return ResultVO.success("成功购买");
        }
    }
}
