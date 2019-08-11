package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

// DEFAULT_ALLOWED_HEADERS;
// 允许跨域传输所有的header参数，将用于使用token放入header域，做session共享的跨域请求
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*") // 完成所有的SpringBoot对应的返回web请求当中加上跨域请求的功能
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
    
    public static final Logger log =  LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private HttpServletRequest request;

    /**
     * 下单请求
     * @param itemId
     * @param amount
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}) 
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,@RequestParam(name = "promoId", required = false)Integer promoId, @RequestParam(name = "amount")Integer amount) throws BusinessException {
        
        log.debug("参数：itemId ===" + itemId + ";  amount === " + amount );

        Boolean isLogin = (Boolean) request.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "用户还未登录，不能下单");
        }

        UserModel loginUser = (UserModel) request.getSession().getAttribute("LOGIN_USER");
        
        OrderModel orderModel = orderService.createOrder(loginUser.getId(), itemId, promoId, amount);
        return CommonReturnType.create(orderModel);
    }
    
}
