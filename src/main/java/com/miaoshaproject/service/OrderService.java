package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.OrderModel;

public interface OrderService {

    /**
     * 使用1、通过前端URL上传过来秒杀活动id，然后下单接口内校验对应id是否属于对应商品且活动已开始
     * 2、直接再下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单
     * 生成订单
     * @param userId 下单用户的id
     * @param itemId 下单的商品的id
     * @param amount 下单的数量
     * @return
     */
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
    
}
