package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.Interaction;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.InteractionService;
import com.dz.campus_product_backend.vo.ResultVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interaction")
public class InteractionController {
    @Autowired
    InteractionService interactionService;
    @PostMapping("/toggle")
    public ResultVO InteractionToggle(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        Object productid = map.get("productId");
        String product_id = productid.toString();
        User loginUser = (User) request.getSession().getAttribute("user");
        String user_id = loginUser.getId();
        return interactionService.InteractionOperation(user_id, product_id);
    }




    @PostMapping("/insert")
//增加
    public ResultVO insertInteraction(@RequestBody Interaction interaction) {
        interactionService.insertInteraction(interaction);
        return ResultVO.success(interaction);
    }
    // 分页加模糊查询
    @GetMapping("/getPage")
    public ResultVO getInteractionsByPage(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String keyword) {
        List<Interaction> interactions = interactionService.getInteractionsByPage(pageNum, pageSize, keyword);
        int total = interactionService.getTotalCount(keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("list", interactions);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);

        return ResultVO.success(result);
    }
    //根据id修改
    @PutMapping("/update")
    public ResultVO updateInteractionById(@RequestBody Interaction interaction) {
        interactionService.updateInteractionById(interaction);
        return ResultVO.success(interaction);
    }

}
