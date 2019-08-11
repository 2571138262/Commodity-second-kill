package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.ItemModel;

import java.util.List;

public interface ItemService {

    /**
     * 创建商品
     * @param itemModel
     * @return
     */
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    /**
     * 商品列表浏览
     * @return
     */
    List<ItemModel> listItem();

    /**
     * 商品详情浏览
     * @param id
     * @return
     */
    ItemModel getItemById(Integer id);

    /**
     * 库存扣减
     * @param itemId 商品的id
     * @param amount 扣减的数量
     * @return
     * @throws BusinessException
     */
    boolean decreaseStock(Integer itemId, Integer amount)throws BusinessException;
    
    
    void increaseSales(Integer itemId, Integer amount)throws BusinessException;
}
