package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewObject.ItemVo;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

// DEFAULT_ALLOWED_HEADERS;
// 允许跨域传输所有的header参数，将用于使用token放入header域，做session共享的跨域请求

@RestController
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*") // 完成所有的SpringBoot对应的返回web请求当中加上跨域请求的功能
public class ItemController extends BaseController {
    
    @Autowired
    private ItemService itemService;

    /**
     * 创建商品的Controller
     * @param title
     * @param descpription
     * @param price
     * @param stock
     * @param imgUrl
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                        @RequestParam(name = "descpription")String descpription,
                        @RequestParam(name = "price")BigDecimal price,
                        @RequestParam(name = "stock")Integer stock,
                        @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        // 封装Service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setDescpription(descpription);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        
        ItemModel itemModelForReturn = itemService.createItem(itemModel);

        ItemVo itemVo = convertVoFromModel(itemModelForReturn);
        
        return CommonReturnType.create(itemVo);
    }   
    
    private ItemVo convertVoFromModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel, itemVo);
        if (itemModel.getPromoModel() != null){
            // 有正在进行或即将进行的秒杀活动
            itemVo.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVo.setPromoId(itemModel.getPromoModel().getId());
            itemVo.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }
        itemVo.setPrice(itemModel.getPrice().doubleValue());
        return itemVo;
    }
    
    @GetMapping("/test")
    public String test(){
        return "可以";
    }

    /**
     * 商品详情页浏览
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id){
        ItemModel itemModel = itemService.getItemById(id);
        // 从model装换成vo
        ItemVo itemVo = convertVoFromModel(itemModel);
        return CommonReturnType.create(itemVo);
    }
    
    // 商品列表页面浏览
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public CommonReturnType listItem(){
        List<ItemModel> itemModelList = itemService.listItem();

        // 使用Stream api将List内的itemModel转化为ItemVo
        List<ItemVo> itemVoList = itemModelList.stream().map(itemModel -> {
            ItemVo itemVo = this.convertVoFromModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVoList);
    }
    
}
