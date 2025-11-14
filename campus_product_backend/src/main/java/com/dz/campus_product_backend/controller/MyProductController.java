package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.Product;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.MyProductService;
import com.dz.campus_product_backend.vo.ResultVO;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/myproduct")
public class MyProductController {

    @Autowired
    private MyProductService myProductService;

    /**
     * 我的商品分页加模糊查询
     *
     * @param keyword  模糊查询关键词
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @return 统一响应结果
     */
    @GetMapping("/list")
    public ResultVO getMyProductsByPage(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            HttpServletRequest request
    ) {
        // 打印日志，确认控制器接收到的参数
        User loginUser=(User) request.getSession().getAttribute("user");
        System.out.println("Controller 接收到的参数：userId=" + loginUser.getId() + ", keyword=" +keyword + ", pageNum=" + pageNum + ", pageSize=" + pageSize);
        ResultVO result = myProductService.getMyProductsByPage(Integer.parseInt(loginUser.getId()), keyword, pageNum, pageSize);
        // 打印日志，确认服务层返回的结果
        //System.out.println("服务层返回的结果：" + result);
        return result;
    }


    //修改我的商品
    @PutMapping("/update")
    public ResultVO updateMyProduct(@RequestBody Product product) {
        try {
            myProductService.updateMyProduct(product);
            return ResultVO.success("商品更新成功");
        } catch (Exception e) {
            return ResultVO.error("商品更新失败: " + e.getMessage());
        }
    }

    //删除我的商品
    @DeleteMapping("/delete/{id}")
    public ResultVO deleteMyProduct(@PathVariable String id) {
        myProductService.deleteMyProductById(id);
        return ResultVO.success("商品删除成功");
    }

    //批量删除我的商品
    @DeleteMapping("/delete")
    public ResultVO deleteByIds(@RequestBody List<String> ids) {
        myProductService.deleteByIds(ids);
        return ResultVO.success("商品批量删除成功");
    }
}