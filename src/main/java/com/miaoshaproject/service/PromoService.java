package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoModel;

/**
 * 秒杀活动的service
 */
public interface PromoService {

    /**
     * 根据itemId获取即将进行的或者正在进行的秒杀活动
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);
    
}
